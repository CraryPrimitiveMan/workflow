package cn.com.codehub.workflow.controller;

import cn.com.codehub.workflow.entity.vo.ResponseVO;

public class BaseController<T> {

    protected ResponseVO<T> response(T data) {
        return new ResponseVO<>(data);
    }
}
