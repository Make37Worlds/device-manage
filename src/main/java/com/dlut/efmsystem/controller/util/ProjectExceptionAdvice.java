package com.dlut.efmsystem.controller.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionAdvice {

    @ExceptionHandler
    public Result doException(Exception e){
        e.printStackTrace();
        return new Result(false,null,"系统异常，操作失败，请稍后重试");
    }

    @ExceptionHandler(RuntimeException.class)
    public Result doRuntimeException(RuntimeException re){
        re.printStackTrace();
        return new Result(false,null,re.getMessage());
    }

}
