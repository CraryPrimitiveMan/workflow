package cn.com.codehub.workflow.service;

import cn.com.codehub.workflow.entity.dto.TaskRoute;
import cn.com.codehub.workflow.entity.vo.TaskRouteVO;
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
public interface TaskRouteService extends IService<TaskRoute> {

    List<TaskRouteVO> listByProcessId(Long processId);
}
