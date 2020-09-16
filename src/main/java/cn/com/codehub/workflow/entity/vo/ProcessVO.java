package cn.com.codehub.workflow.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProcessVO {
    private static final long serialVersionUID = 1L;

    /**
     * 过程ID
     */
    private Long processId;

    /**
     * 过程名称
     */
    private String processName;

    /**
     * 过程别名
     */
    private String processAlias;

    /**
     * 任务列表
     */
    private List<TaskVO> tasks;

    /**
     * 任务关系列表
     */
    private List<TaskRouteVO> taskRoutes;

}
