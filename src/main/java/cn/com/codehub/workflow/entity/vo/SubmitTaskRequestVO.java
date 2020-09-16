package cn.com.codehub.workflow.entity.vo;

import cn.com.codehub.workflow.entity.enums.TaskStatusEnum;
import lombok.Data;

@Data
public class SubmitTaskRequestVO {
    /**
     * 任务实例ID
     */
    private Long taskInstanceId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 通过备注或不通过原因
     */
    private String message;

    /**
     * 任务审核结果，1:通过，2:不通过
     */
    private TaskStatusEnum taskStatus;
}
