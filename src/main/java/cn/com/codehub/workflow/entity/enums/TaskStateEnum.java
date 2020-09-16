package cn.com.codehub.workflow.entity.enums;

/**
 * 任务执行状态，0:默认值，1:创建完，2:任务进行中，3:任务已完成
 */
public enum TaskStateEnum {
    DEFAULT_STATE,
    CREATED_STATE,
    DOING_STATE,
    FINISHED_STATE,
}
