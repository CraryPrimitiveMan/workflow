package cn.com.codehub.workflow.entity.enums;

/**
 * 节点的过程逻辑属性，0:默认值，1:and分支节点，2:and合并节点，3:or分支节点，4:or合并节点
 */
public enum ProcessLogicEnum {
    DEFAULT_LOGIC_NODE,
    AND_MERGE_NODE,
    OR_MERGE_NODE,
}
