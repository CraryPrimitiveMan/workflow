package cn.com.codehub.workflow.service;

import cn.com.codehub.workflow.entity.enums.TaskStatusEnum;

public interface EngineService {
    public void run(Long taskInstanceId, Long userId, TaskStatusEnum taskStatus, String message);
}
