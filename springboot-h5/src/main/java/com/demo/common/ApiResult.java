package com.demo.common;

public enum ApiResult {
    SUCCESS(0,"操作成功"),
    ERROR(-1,"系统异常"),
    FAIL(1,"操作失败"),
    UNAUTHORIZED(401, "token无效");

    private int code;
    private String msg;

    private ApiResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
