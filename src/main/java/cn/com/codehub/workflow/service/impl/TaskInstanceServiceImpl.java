package cn.com.codehub.workflow.service.impl;

import cn.com.codehub.workflow.entity.dto.TaskInstance;
import cn.com.codehub.workflow.mapper.TaskInstanceMapper;
import cn.com.codehub.workflow.service.TaskInstanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class TaskInstanceServiceImpl extends ServiceImpl<TaskInstanceMapper, TaskInstance> implements TaskInstanceService {

}
