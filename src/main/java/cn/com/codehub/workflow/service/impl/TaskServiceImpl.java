package cn.com.codehub.workflow.service.impl;

import cn.com.codehub.workflow.entity.dto.Task;
import cn.com.codehub.workflow.entity.enums.NodeTypeEnum;
import cn.com.codehub.workflow.entity.enums.ProcessLogicEnum;
import cn.com.codehub.workflow.entity.vo.TaskVO;
import cn.com.codehub.workflow.mapper.TaskMapper;
import cn.com.codehub.workflow.service.TaskService;
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
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    public List<TaskVO> listByProcessId(Long processId) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("process_id", processId);
        List<Task> taskList = super.listByMap(queryMap);
        List<TaskVO> taskVOList = new ArrayList<TaskVO>();
        taskList.stream().forEach(task->{
            TaskVO taskVO = new TaskVO();
            // 复制属性
            BeanUtils.copyProperties(task, taskVO);
            // enum转换
            taskVO.setNodeType(NodeTypeEnum.values()[task.getNodeType()]);
            taskVO.setProcessLogic(ProcessLogicEnum.values()[task.getProcessLogic()]);
            taskVOList.add(taskVO);
        });
        return taskVOList;
    }
}
