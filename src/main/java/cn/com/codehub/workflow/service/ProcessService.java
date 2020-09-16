package cn.com.codehub.workflow.service;

import cn.com.codehub.workflow.entity.dto.Process;
import cn.com.codehub.workflow.entity.vo.ProcessVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author guangjunsun
 * @since 2020-09-07
 */
public interface ProcessService extends IService<Process> {
    public ProcessVO getAllById(Long processId);
}
