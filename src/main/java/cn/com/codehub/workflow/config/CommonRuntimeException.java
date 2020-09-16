package cn.com.codehub.workflow.config;

public class CommonRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String msg;
    private Integer code;

    public CommonRuntimeException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public CommonRuntimeException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public CommonRuntimeException(Integer code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public CommonRuntimeException(Integer code, String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}