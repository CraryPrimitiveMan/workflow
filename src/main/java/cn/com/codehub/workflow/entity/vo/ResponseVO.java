package cn.com.codehub.workflow.entity.vo;

import lombok.Data;

@Data
public class ResponseVO<T> {
    private Integer code;
    private String message;
    private T data;

    public ResponseVO(T data) {
        this.data = data;
        this.message = "success";
        this.code = 0;
    }

    public ResponseVO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public ResponseVO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
