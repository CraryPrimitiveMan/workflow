package cn.com.codehub.workflow.service;

import cn.com.codehub.workflow.entity.vo.*;

public interface WorkflowService {

    public ProcessVO getProcess(Long processId);

    public ProcessInstanceVO instanceProcess(InstanceRequestVO instanceRequestVO) throws Exception;

    public SubmitTaskResponseVO submitTask(SubmitTaskRequestVO submitTaskRequestVO) throws Exception;
}
