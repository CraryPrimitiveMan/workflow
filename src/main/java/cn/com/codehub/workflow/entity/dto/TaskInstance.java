package cn.com.codehub.workflow.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author guangjunsun
 * @since 2020-09-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskInstance extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务实例ID
     */
    @TableId(value = "TASK_INSTANCE_ID", type = IdType.AUTO)
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
     * 后续任务实例ID，逗号分隔
     */
    private String nextTaskInstanceIds;

    /**
     * 前驱任务实例ID，逗号分隔
     */
    private String preTaskInstanceIds;

    /**
     * 任务执行状态，0:默认值，1:创建完，2:任务进行中，3:任务已完成
     */
    private Integer taskState;

    /**
     * 任务审核结果，0:默认值，1:通过，2:不通过
     */
    private Integer taskStatus;

    /**
     * 任务审核信息，通过备注或拒绝理由
     */
    private String taskMessage;


}
