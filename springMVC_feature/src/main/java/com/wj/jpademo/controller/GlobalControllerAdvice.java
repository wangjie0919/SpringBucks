package com.wj.jpademo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常拦截
 */
@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(ValidationException.class) //异常拦截注解
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> validationExceprionHandler(ValidationException exception ){
        Map<String,String> map = new HashMap<>();
        map.put("message",exception.toString());
        return map;
    }
}
