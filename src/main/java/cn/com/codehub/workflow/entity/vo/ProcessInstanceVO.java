package cn.com.codehub.workflow.entity.vo;

import cn.com.codehub.workflow.entity.dto.TaskInstance;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProcessInstanceVO {

    /**
     * 流程实例ID
     */
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
     * 任务实例列表
     */
    private List<TaskInstance> taskInstances;

}
