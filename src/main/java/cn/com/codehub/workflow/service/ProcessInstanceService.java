package cn.com.codehub.workflow.service;

import cn.com.codehub.workflow.entity.dto.ProcessInstance;
import cn.com.codehub.workflow.entity.vo.ProcessInstanceVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author guangjunsun
 * @since 2020-09-07
 */
public interface ProcessInstanceService extends IService<ProcessInstance> {
    public ProcessInstanceVO instance(Long processId, String description, Long startTaskInstanceId) throws Exception;
}
