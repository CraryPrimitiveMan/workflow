package cn.com.codehub.workflow.entity.vo;

import lombok.Data;

@Data
public class TaskRouteVO {
    /**
     * 当前任务ID
     */
    private Long taskId;

    /**
     * 后续任务ID
     */
    private Long nextTaskId;
}
