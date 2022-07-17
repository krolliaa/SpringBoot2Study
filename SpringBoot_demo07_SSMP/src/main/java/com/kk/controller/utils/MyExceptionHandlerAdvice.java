package com.kk.controller.utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandlerAdvice {
    @ExceptionHandler(value = {Exception.class})
    public MessageAgreement myExceptionHandler(Exception exception) {
        exception.printStackTrace();
        return new MessageAgreement("系统错误，请稍后再试！");
    }
}
