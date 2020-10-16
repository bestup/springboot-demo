package com.demo.valid.config;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Object methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        StringBuilder b = new StringBuilder();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        for (int i = 0; i < allErrors.size(); i++) {
            b.append(allErrors.get(i).getDefaultMessage()).append(";");
            break;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("msg", b.toString());
        return map;
    }

}
