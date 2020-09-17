package cn.com.codehub.workflow.service.impl;

import cn.com.codehub.workflow.entity.dto.RelationUserRole;
import cn.com.codehub.workflow.entity.dto.Task;
import cn.com.codehub.workflow.entity.dto.TaskInstance;
import cn.com.codehub.workflow.entity.enums.NodeTypeEnum;
import cn.com.codehub.workflow.entity.enums.ProcessLogicEnum;
import cn.com.codehub.workflow.entity.enums.TaskStateEnum;
import cn.com.codehub.workflow.entity.enums.TaskStatusEnum;
import cn.com.codehub.workflow.service.RelationUserRoleService;
import cn.com.codehub.workflow.service.TaskInstanceService;
import cn.com.codehub.workflow.service.TaskService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Data
@Slf4j
public class ThreadPoolTaskImpl implements Callable<List<Long>> {

    private Long taskInstanceId;
    private Long userId;
    private TaskStatusEnum taskStatus;
    private String message;
    private Long nextTaskInstanceId;

    @Resource
    private TaskInstanceService taskInstanceService;
    @Resource
    private TaskService taskService;
    @Resource
    private RelationUserRoleService relationUserRoleService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void setTaskInstanceService(TaskInstanceService taskInstanceService) {
        this.taskInstanceService = taskInstanceService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    public void setRelationUserRoleService(RelationUserRoleService relationUserRoleService) {
        this.relationUserRoleService = relationUserRoleService;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    ThreadPoolTaskImpl(Long taskInstanceId, Long userId, TaskStatusEnum taskStatus, String message) {
        this.taskInstanceId = taskInstanceId;
        this.userId = userId;
        this.taskStatus = taskStatus;
        this.message = message;
    }

    @Override
    public List<Long> call() throws Exception {
        List<Long> nextTaskInstanceIds = new ArrayList<>();
        // 获取当前节点的锁
        String lockedKey = "task_instance_" + this.taskInstanceId;
        boolean isLockedSuccess = redisTemplate.opsForValue().setIfAbsent(lockedKey, true);
        if (!isLockedSuccess) {
            throw new Exception("此申请已在处理中");
        }
        // 设置过期时间
        redisTemplate.expire(lockedKey, 30000, TimeUnit.MILLISECONDS);

        try {
            // 检查用户是否有权限
            TaskInstance taskInstance = this.taskInstanceService.getById(this.taskInstanceId);
            Task task = this.taskService.getById(taskInstance.getTaskId());
            // 如果已处理完
            if (taskInstance.getTaskState() == TaskStateEnum.FINISHED_STATE.ordinal()) {
                throw new Exception("此申请已处理操作");
            }
            Long roleId = task.getRoleId();
            boolean isUseRole = (roleId != null && roleId != 0L);
            boolean isAutoTask = this.userId == 0L;
            if (isUseRole) {
                if (isAutoTask) {
                    // 自动触发的，且需要用户角色的任务
                    taskInstance.setTaskState(TaskStateEnum.DOING_STATE.ordinal());
                    taskInstanceService.updateById(taskInstance);
                    return nextTaskInstanceIds;
                } else {
                    // 有角色存在，需验证
                    Wrapper<RelationUserRole> query = new QueryWrapper<RelationUserRole>()
                            .lambda()
                            .eq(RelationUserRole::getRoleId, roleId)
                            .eq(RelationUserRole::getUserId, this.userId);
                    RelationUserRole relation = this.relationUserRoleService.getOne(query, false);
                    if (relation == null) {
                        throw new Exception("无权限操作");
                    }
                }
            }

            // 检查前置任务，是否已经都通过
            String preTaskInstanceIdStr = taskInstance.getPreTaskInstanceIds();
            Integer processLogic = task.getProcessLogic();
            Integer nodeType = task.getNodeType();
            if (preTaskInstanceIdStr != null && !preTaskInstanceIdStr.isEmpty() && nodeType != NodeTypeEnum.START_NODE.ordinal()) {
                // 有前置任务，且不是开始节点，需要验证前置任务
                List<Long> preTaskInstanceIds = Arrays.asList(preTaskInstanceIdStr.split(","))
                        .stream()
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
                int preTaskInstanceIdCount = preTaskInstanceIds.size();

                LambdaQueryWrapper<TaskInstance> taskQuery = new QueryWrapper<TaskInstance>()
                        .lambda()
                        .in(TaskInstance::getTaskInstanceId, preTaskInstanceIds)
                        .eq(TaskInstance::getTaskStatus, TaskStatusEnum.PASSED_STATUS);
                int preTaskInstancePassedCount = this.taskInstanceService.count(taskQuery);
                switch (ProcessLogicEnum.values()[processLogic]) {
                    case OR_MERGE_NODE:
                        // 前置完成一个即可
                        if (preTaskInstancePassedCount == 0) {
                            throw new Exception("前置任务未完成");
                        }
                        break;
                    case AND_MERGE_NODE:
                    default:
                        // 前置必须全部都完成
                        if (preTaskInstancePassedCount < preTaskInstanceIdCount) {
                            throw new Exception("前置任务未完成");
                        }
                }
            }

            // 处理当前任务
            taskInstance.setUserId(this.userId);
            taskInstance.setTaskMessage(this.message);
            taskInstance.setTaskState(TaskStateEnum.FINISHED_STATE.ordinal());
            taskInstance.setTaskStatus(this.taskStatus.ordinal());
            taskInstance.setEndTime(LocalDateTime.now());
            taskInstanceService.updateById(taskInstance);

            // 返回需触发的后续任务
            String nextTaskInstanceIdStr = taskInstance.getNextTaskInstanceIds();
            if (this.taskStatus == TaskStatusEnum.PASSED_STATUS && nextTaskInstanceIdStr != null && !nextTaskInstanceIdStr.isEmpty()) {
                nextTaskInstanceIds = Arrays.asList(nextTaskInstanceIdStr.split(","))
                        .stream()
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            // 推出需删除key
            redisTemplate.delete(lockedKey);
        }

        return nextTaskInstanceIds;
    }
}
