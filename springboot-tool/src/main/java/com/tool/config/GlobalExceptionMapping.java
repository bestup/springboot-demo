package com.tool.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author tanglong
 * @date 2019-11-23
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionMapping {

    @Data
    static class ResponseData<T> {
        private int code;
        private String msg;
        private T data;
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseData globalExceptionHandler(Exception e) {
        ResponseData responseData = new ResponseData();
        if (e instanceof Exception) {
            //业务判断错误信息抛出
            responseData.setMsg(e.getMessage());
        } else {
            responseData.setMsg("系统异常：" + e.getMessage());
        }
        return responseData;
    }

}
