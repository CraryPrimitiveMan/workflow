package cn.com.codehub.workflow.service.impl;

import cn.com.codehub.workflow.entity.enums.TaskStatusEnum;
import cn.com.codehub.workflow.service.EngineService;
import cn.com.codehub.workflow.service.RelationUserRoleService;
import cn.com.codehub.workflow.service.TaskInstanceService;
import cn.com.codehub.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service("engineService")
@Slf4j
public class EngineServiceImpl implements EngineService {
    /**
     * 线程池
     */
    private  static ThreadPoolExecutor threadPool =
            new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10),
                    new ThreadPoolExecutor.DiscardOldestPolicy());

    @Resource
    private TaskInstanceService taskInstanceService;
    @Resource
    private TaskService taskService;
    @Resource
    private RelationUserRoleService relationUserRoleService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void run(Long taskInstanceId, Long userId, TaskStatusEnum taskStatus, String message) throws Exception {
        try {
            Future<List<Long>> task = threadPool.submit(getPoolTask(taskInstanceId, userId, taskStatus, message));
            handleNextTask(task);
        } catch (Exception e) {
            // TODO 处理失败
            e.printStackTrace();
            throw e;
        }
    }

    public void handleNextTask(Future<List<Long>> task) throws Exception {
        List<Long> nextTaskInstanceIds = task.get();
        if (nextTaskInstanceIds.size() > 0) {
            nextTaskInstanceIds.stream().parallel().forEach(nextTaskInstanceId -> {
                Future<List<Long>> subTask = threadPool.submit(getPoolTask(nextTaskInstanceId, 0L, TaskStatusEnum.DEFAULT_STATUS, ""));
                try {
                    handleNextTask(subTask);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public ThreadPoolTaskImpl getPoolTask(Long taskInstanceId, Long userId, TaskStatusEnum taskStatus, String message) {
        ThreadPoolTaskImpl task = new ThreadPoolTaskImpl(taskInstanceId, userId, taskStatus, message);
        task.setTaskService(taskService);
        task.setTaskInstanceService(taskInstanceService);
        task.setRelationUserRoleService(relationUserRoleService);
        task.setRedisTemplate(redisTemplate);
        return task;
    }
}
