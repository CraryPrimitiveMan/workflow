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
 * @since 2020-09-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProcessInstance extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 流程实例ID
     */
    @TableId(value = "PROCESS_INSTANCE_ID", type = IdType.AUTO)
    private Long processInstanceId;

    /**
     * 业务实例的内容描述
     */
    private String description;

    /**
     * 所属过程定义ID
     */
    private Long processId;

    /**
     * 流程实例的创建时间
     */
    private LocalDateTime startTime;

    /**
     * 流程实例的结束时间
     */
    private LocalDateTime endTime;

    /**
     * 开始任务实例ID
     */
    private Long startTaskInstanceId;

    /**
     * 流程实例执行状态，0:默认值，1:创建完，2:任务进行中，3:任务已完成
     */
    private Integer processState;

    /**
     * 流程实例审核结果，0:默认值，1:通过，2:不通过
     */
    private Integer processStatus;


}
