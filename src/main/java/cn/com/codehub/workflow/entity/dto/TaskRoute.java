package cn.com.codehub.workflow.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class TaskRoute extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 迁移ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 过程定义ID
     */
    private Long processId;

    /**
     * 当前任务ID
     */
    private Long taskId;

    /**
     * 后续任务ID
     */
    private Long nextTaskId;


}
