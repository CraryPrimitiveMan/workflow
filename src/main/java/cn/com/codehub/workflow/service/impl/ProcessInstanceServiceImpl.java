package cn.com.codehub.workflow.service.impl;

import cn.com.codehub.workflow.entity.dto.ProcessInstance;
import cn.com.codehub.workflow.entity.vo.ProcessInstanceVO;
import cn.com.codehub.workflow.mapper.ProcessInstanceMapper;
import cn.com.codehub.workflow.service.ProcessInstanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author guangjunsun
 * @since 2020-09-07
 */
@Service
public class ProcessInstanceServiceImpl extends ServiceImpl<ProcessInstanceMapper, ProcessInstance> implements ProcessInstanceService {

    public ProcessInstanceVO instance(Long processId, String description, Long startTaskInstanceId) throws Exception {
        ProcessInstanceVO processInstanceVO = new ProcessInstanceVO();
        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setProcessId(processId);
        processInstance.setDescription(description);
        processInstance.setStartTime(LocalDateTime.now());
        processInstance.setStartTaskInstanceId(startTaskInstanceId);
        boolean isSaveSuccess = save(processInstance);
        if (isSaveSuccess) {
            // 复制属性
            BeanUtils.copyProperties(processInstance, processInstanceVO);
            return processInstanceVO;
        } else {
            throw new Exception("process instance save failed");
        }
    }
}
