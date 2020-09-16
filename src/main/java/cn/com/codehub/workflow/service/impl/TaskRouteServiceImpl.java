package cn.com.codehub.workflow.service.impl;

import cn.com.codehub.workflow.entity.dto.TaskRoute;
import cn.com.codehub.workflow.entity.vo.TaskRouteVO;
import cn.com.codehub.workflow.mapper.TaskRouteMapper;
import cn.com.codehub.workflow.service.TaskRouteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author guangjunsun
 * @since 2020-09-07
 */
@Service
public class TaskRouteServiceImpl extends ServiceImpl<TaskRouteMapper, TaskRoute> implements TaskRouteService {
    public List<TaskRouteVO> listByProcessId(Long processId) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("process_id", processId);
        List<TaskRoute> taskRouteList = super.listByMap(queryMap);
        List<TaskRouteVO> taskRouteVOList = new ArrayList<TaskRouteVO>();
        taskRouteList.stream().forEach(taskRoute->{
            TaskRouteVO taskRouteVO = new TaskRouteVO();
            // 复制属性
            BeanUtils.copyProperties(taskRoute, taskRouteVO);
            taskRouteVOList.add(taskRouteVO);
        });
        return taskRouteVOList;
    }
}
