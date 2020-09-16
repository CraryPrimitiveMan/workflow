package cn.com.codehub.workflow.service;

import cn.com.codehub.workflow.entity.dto.Task;
import cn.com.codehub.workflow.entity.vo.TaskVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author guangjunsun
 * @since 2020-09-07
 */
public interface TaskService extends IService<Task> {
    List<TaskVO> listByProcessId(Long processId);
}
