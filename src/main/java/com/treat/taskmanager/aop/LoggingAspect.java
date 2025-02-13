package com.treat.taskmanager.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("within(com.treat.taskmanager.service.TaskService)")
    public Object logServiceMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("Entering service method: {}", methodName);
        Object result = joinPoint.proceed();
        log.info("Exiting service method: {}", methodName);
        return result;
    }
    @Around("within(com.treat.taskmanager.controller.TaskController)")
    public Object logControllerMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("Entering controller method: {}", methodName);
        Object result = joinPoint.proceed();
        log.info("Exiting controller method: {}", methodName);
        return result;
    }
}