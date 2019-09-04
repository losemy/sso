package com.github.losemy.sso.web.advice;

import com.github.losemy.sso.client.exception.SSOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/**
 * Description: sso
 * Created by lose on 2019/8/27 9:43
 * @author lose
 * 异常需要细分 处理
 * Exception 用来收尾避免异常抛出
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(ConstraintViolationException.class)
    public void handleConstraintViolationException(HttpServletResponse resp, ConstraintViolationException ex) throws Exception {
        log.info("ConstraintViolationException");
        String msg = ex.getMessage();
        String[] msgs = msg.split(": ");
        resp.sendError(HttpStatus.OK.value(),  msgs[msgs.length-1]);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleBindException(MethodArgumentNotValidException ex) throws Exception{
        System.out.println(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletResponse resp,Exception ex) throws Exception {
        String msg = ex.getMessage();
        resp.sendError(HttpStatus.OK.value(),  msg);
    }

    @ExceptionHandler(SSOException.class)
    public void handleSSOException(HttpServletResponse resp,Exception ex) throws Exception {
        String msg = ex.getMessage();
        //判断是否是ajax请求 给与不同处理？
        resp.sendError(HttpStatus.OK.value(),  msg);
    }


}
