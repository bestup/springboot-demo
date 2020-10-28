package com.demo.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author halo.l
 * @date 2019-12-07
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private T data;

    public Result() {
    }

    public static Result success() {
        Result result = new Result();
        result.setCode(ApiResult.SUCCESS.getCode());
        result.setMsg(ApiResult.SUCCESS.getMsg());
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(ApiResult.SUCCESS.getCode());
        result.setMsg(ApiResult.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static Result success(Object data, String msg) {
        Result result = new Result();
        result.setCode(ApiResult.SUCCESS.getCode());
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result fail() {
        Result result = new Result();
        result.setCode(ApiResult.FAIL.getCode());
        result.setMsg(ApiResult.FAIL.getMsg());
        return result;
    }

    public static Result fail(String msg) {
        Result result = new Result();
        result.setCode(ApiResult.FAIL.getCode());
        result.setMsg(msg);
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.setCode(ApiResult.ERROR.getCode());
        result.setMsg(ApiResult.ERROR.getMsg());
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(ApiResult.ERROR.getCode());
        result.setMsg(msg);
        return result;
    }

    public static Result unAuthorized(String msg) {
        Result result = new Result();
        result.setCode(ApiResult.UNAUTHORIZED.getCode());
        result.setMsg(msg);
        return result;
    }

}
