package cn.com.codehub.workflow.controller;

import cn.com.codehub.workflow.entity.vo.*;
import cn.com.codehub.workflow.service.WorkflowService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 工作流控制器
 * </p>
 *
 * @author guangjunsun
 * @since 2020-09-06
 */
@RestController
@RequestMapping("/workflow")
public class WorkflowController extends BaseController {
    @Resource
    private WorkflowService workflowService;

    @ApiOperation("根据ID获取工作流配置")
    @GetMapping("/config/{id}")
    public ResponseVO<ProcessVO> findById(@PathVariable("id") Long processId) {
        ProcessVO processVO = workflowService.getProcess(processId);
        return response(processVO);
    }

    @ApiOperation("生成工作流实例")
    @PostMapping("/instance")
    public ResponseVO<ProcessInstanceVO> addInstance(@RequestBody InstanceRequestVO instanceRequestVO) throws Exception {
        ProcessInstanceVO instanceResponseVO = workflowService.instanceProcess(instanceRequestVO);
        return response(instanceResponseVO);
    }

    @ApiOperation("提交审批")
    @PostMapping("/submitTask")
    public ResponseVO<SubmitTaskResponseVO> submitTask(@RequestBody SubmitTaskRequestVO submitTaskRequestVO) throws Exception {
        return response(workflowService.submitTask(submitTaskRequestVO));
    }
}
