package cn.com.codehub.workflow.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import cn.com.codehub.workflow.entity.dto.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author guangjunsun
 * @since 2020-09-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Task extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 节点（任务）ID
     */
    @TableId(value = "TASK_ID", type = IdType.AUTO)
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
    private Integer nodeType;

    /**
     * 节点的过程逻辑属性，0:默认值，1:and分支节点，2:and合并节点，3:or分支节点，4:or合并节点
     */
    private Integer processLogic;

    /**
     * 分配的任务角色ID，该角色可以操作此节点
     */
    private Long roleId;


}
