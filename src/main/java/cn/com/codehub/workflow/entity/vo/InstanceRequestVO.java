package cn.com.codehub.workflow.entity.vo;

import lombok.Data;

@Data
public class InstanceRequestVO {
    /**
     * 流程ID
     */
    private Long processId;

    /**
     * 流程描述
     */
    private String description;
}
