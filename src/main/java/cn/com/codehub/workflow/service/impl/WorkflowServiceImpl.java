package cn.com.codehub.workflow.service.impl;

import cn.com.codehub.workflow.entity.dto.TaskInstance;
import cn.com.codehub.workflow.entity.enums.NodeTypeEnum;
import cn.com.codehub.workflow.entity.enums.TaskStateEnum;
import cn.com.codehub.workflow.entity.enums.TaskStatusEnum;
import cn.com.codehub.workflow.entity.vo.*;
import cn.com.codehub.workflow.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service("workflowService")
public class WorkflowServiceImpl implements WorkflowService {
    /**
     * 线程池
     */
    private  static ThreadPoolExecutor threadPool =
            new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
                    new ThreadPoolExecutor.DiscardOldestPolicy());
    @Resource
    private EngineService engineService;
    @Resource
    private ProcessService processService;
    @Resource
    private TaskService taskService;
    @Resource
    private ProcessInstanceService processInstanceService;
    @Resource
    private TaskInstanceService taskInstanceService;
    @Resource
    private TaskRouteService taskRouteService;

    @Override
    public ProcessVO getProcess(Long processId) {
        ProcessVO processVO = processService.getAllById(processId);
        processVO.setTasks(taskService.listByProcessId(processId));
        processVO.setTaskRoutes(taskRouteService.listByProcessId(processId));
        return processVO;
    }

    @Override
    public ProcessInstanceVO instanceProcess(InstanceRequestVO instanceRequestVO) throws Exception {
        ProcessVO processVO = getProcess(instanceRequestVO.getProcessId());
        // 创建task instance
        TaskInstance startTaskInstance = new TaskInstance();
        List<TaskInstance> taskInstanceList = processVO.getTasks().stream().map(task -> {
            TaskInstance taskInstance = new TaskInstance();
            taskInstance.setTaskId(task.getTaskId());
            taskInstance.setTaskState(TaskStateEnum.DEFAULT_STATE.ordinal());
            taskInstance.setTaskStatus(TaskStatusEnum.DEFAULT_STATUS.ordinal());
            taskInstance.setProcessInstanceId(0L);
            taskInstance.setStartTime(LocalDateTime.now());
            // 记录开始节点
            if (task.getNodeType() == NodeTypeEnum.START_NODE) {
                startTaskInstance.setTaskId(taskInstance.getTaskId());
            }
            return taskInstance;
        }).collect(Collectors.toList());
        taskInstanceService.saveBatch(taskInstanceList);
        // 创建process instance
        Map<Long, TaskInstance> taskInstanceMap = new HashMap<Long, TaskInstance>();
        taskInstanceList.forEach(taskInstance -> {
            if (startTaskInstance.getTaskId().equals(taskInstance.getTaskId())) {
                startTaskInstance.setTaskInstanceId(taskInstance.getTaskInstanceId());
            }
            taskInstanceMap.put(taskInstance.getTaskId(), taskInstance);
        });
        ProcessInstanceVO processInstanceVO = processInstanceService.instance(processVO.getProcessId(), instanceRequestVO.getDescription(), startTaskInstance.getTaskInstanceId());
        // 更新task instance
        Map<Long, List<Long>> nextRouteMap = new HashMap<Long, List<Long>>();
        Map<Long, List<Long>> preRouteMap = new HashMap<Long, List<Long>>();
        processVO.getTaskRoutes().stream().forEach(taskRouteVO -> {
            Long taskId = taskRouteVO.getTaskId();
            Long nextTaskId = taskRouteVO.getNextTaskId();
            List<Long> nextTaskList = nextRouteMap.getOrDefault(taskId, new ArrayList<Long>());
            nextTaskList.add(nextTaskId);
            List<Long> preTaskList = preRouteMap.getOrDefault(nextTaskId, new ArrayList<Long>());
            preTaskList.add(taskId);
            nextRouteMap.put(taskId, nextTaskList);
            preRouteMap.put(nextTaskId, preTaskList);
        });
        taskInstanceList = taskInstanceList.stream().map(taskInstance -> {
            Long taskId = taskInstance.getTaskId();
            List<Long> nextTaskIds = nextRouteMap.get(taskId);
            List<Long> preTaskIds = preRouteMap.get(taskId);
            String nextTaskIdsStr = "";
            String preTaskIdsStr = "";
            if (nextTaskIds != null) {
                nextTaskIdsStr = nextTaskIds.stream().map(nextTaskId -> {
                    TaskInstance nextTaskInstance = taskInstanceMap.get(nextTaskId);
                    return nextTaskInstance.getTaskInstanceId().toString();
                }).collect(Collectors.joining(","));
            }
            if (preTaskIds != null) {
                preTaskIdsStr = preTaskIds.stream().map(preTaskId -> {
                    TaskInstance preTaskInstance = taskInstanceMap.get(preTaskId);
                    return preTaskInstance.getTaskInstanceId().toString();
                }).collect(Collectors.joining(","));
            }

            taskInstance.setNextTaskInstanceIds(nextTaskIdsStr);
            taskInstance.setPreTaskInstanceIds(preTaskIdsStr);
            taskInstance.setProcessInstanceId(processInstanceVO.getProcessInstanceId());
            taskInstance.setTaskState(TaskStateEnum.CREATED_STATE.ordinal());
            return taskInstance;
        }).collect(Collectors.toList());
        taskInstanceService.updateBatchById(taskInstanceList);
        // 启动开始任务
        engineService.run(startTaskInstance.getTaskInstanceId(), 0L, TaskStatusEnum.PASSED_STATUS, "");
        taskInstanceList = taskInstanceService.listByIds(taskInstanceList.stream().map(task -> task.getTaskInstanceId()).collect(Collectors.toList()));
        processInstanceVO.setTaskInstances(taskInstanceList);
        return processInstanceVO;
    }

    @Override
    public SubmitTaskResponseVO submitTask(SubmitTaskRequestVO submitTaskRequestVO) throws Exception {
        // 启动任务
        engineService.run(submitTaskRequestVO.getTaskInstanceId(), submitTaskRequestVO.getUserId(), submitTaskRequestVO.getTaskStatus(), submitTaskRequestVO.getMessage());
        SubmitTaskResponseVO submitTaskResponseVO = new SubmitTaskResponseVO();
        submitTaskResponseVO.setTaskInstanceId(submitTaskRequestVO.getTaskInstanceId());
        return submitTaskResponseVO;
    }
}
