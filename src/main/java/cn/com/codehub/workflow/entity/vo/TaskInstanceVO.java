package cn.com.codehub.workflow.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskInstanceVO {

    /**
     * 任务实例ID
     */
    private Long taskInstanceId;

    /**
     * 节点（任务）ID
     */
    private Long taskId;

    /**
     * 流程实例ID
     */
    private Long processInstanceId;

    /**
     * 流程实例的创建时间
     */
    private LocalDateTime startTime;

    /**
     * 流程实例的结束时间
     */
    private LocalDateTime endTime;

    /**
     * 处理任务的用户ID
     */
    private Long userId;

    /**
     * 后续任务实例ID
     */
    private Long nextTaskInstanceId;

    /**
     * 前驱任务实例ID
     */
    private Long preTaskInstanceId;

    /**
     * 任务执行状态，0:默认值，1:创建完，2:任务进行中，3:任务已完成
     */
    private Integer taskState;
}
