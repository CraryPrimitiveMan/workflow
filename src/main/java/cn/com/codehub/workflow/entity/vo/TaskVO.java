package cn.com.codehub.workflow.entity.vo;

import cn.com.codehub.workflow.entity.enums.NodeTypeEnum;
import cn.com.codehub.workflow.entity.enums.ProcessLogicEnum;
import lombok.Data;

@Data
public class TaskVO {
    private static final long serialVersionUID = 1L;

    /**
     * 节点（任务）ID
     */
    private Long taskId;

    /**
     * 节点（任务）名称
     */
    private String taskName;

    /**
     * 所属过程定义ID
     */
    private Long processId;

    /**
     * 节点类型，0:默认值，1:开始节点，2:结束节点，3:任务节点）
     */
    private NodeTypeEnum nodeType;

    /**
     * 节点的过程逻辑属性，0:默认值，1:and分支节点，2:and合并节点，3:or分支节点，4:or合并节点
     */
    private ProcessLogicEnum processLogic;

    /**
     * 分配的任务角色ID，该角色可以操作此节点
     */
    private Long roleId;
}
