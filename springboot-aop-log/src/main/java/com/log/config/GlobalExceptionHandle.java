package com.log.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author halo.l
 * @date 2019-12-26
 */
@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    public String globalExceptionHandler(Exception exception) {
        return "exception";
    }

}
