package com.example.common.handler;

import com.example.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result sysExceptionHandler(SystemException e) {
        if (e.getMessage().contains("phonenumber")) {
            String[] split = e.getMessage().split(" ");
            String msg = split[2] + "已存在!";
            return Result.error(msg);
        }
        return Result.error("等会再解决");
    }
}
