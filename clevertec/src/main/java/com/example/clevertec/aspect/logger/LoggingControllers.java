package com.example.clevertec.aspect.logger;

import com.example.clevertec.aspect.constant.Constants;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingControllers {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoggingControllers.class);

    @AfterReturning("execution(public * com.example.clevertec.controller.*.getAll())")
    public void afterFindAllRecords(JoinPoint joinPoint) {

        LOGGER.info(Constants.RECEIVED_ALL + getReturnType(joinPoint));
    }

    @AfterReturning("execution(public * com.example.clevertec.controller.*.get())")
    public void afterFindByIdRecord(JoinPoint joinPoint) {

        LOGGER.info(Constants.RECEIVED_BY_ID + getReturnType(joinPoint));
    }

    @AfterReturning("execution(public * com.example.clevertec.controller.*.create())")
    public void afterCreateRecord(JoinPoint joinPoint) {

        LOGGER.info(Constants.CREATE_RECORD + getReturnType(joinPoint));
    }

    @AfterReturning("execution(public * com.example.clevertec.controller.*.delete())")
    public void afterDeleteByIdRecord(JoinPoint joinPoint) {

        LOGGER.info(Constants.DELETE_BY_ID + getReturnType(joinPoint));
    }

    @AfterReturning("execution(public * com.example.clevertec.controller.*.update())")
    public void afterUpdateRecord(JoinPoint joinPoint) {

        LOGGER.info(Constants.UPDATE + getReturnType(joinPoint));
    }

    public String getReturnType(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        return methodSignature.getMethod().getGenericReturnType().getTypeName();
    }
}
