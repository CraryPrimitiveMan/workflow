package cn.com.codehub.workflow.service.impl;

import cn.com.codehub.workflow.entity.dto.Process;
import cn.com.codehub.workflow.entity.vo.ProcessVO;
import cn.com.codehub.workflow.mapper.ProcessMapper;
import cn.com.codehub.workflow.service.ProcessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author guangjunsun
 * @since 2020-09-07
 */
@Service
public class ProcessServiceImpl extends ServiceImpl<ProcessMapper, Process> implements ProcessService {

    /**
     * 根据processId获取
     * @param processId 进程ID
     * @return ProcessVO
     */
    public ProcessVO getAllById(Long processId) {
        ProcessVO processVO = new ProcessVO();
        Process process = super.getById(processId);
        BeanUtils.copyProperties(process, processVO);
        return processVO;
    }
}
