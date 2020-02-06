package com.wj.jpademo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 切面定义，实现AOP
 */
@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    @Around("repositoryOps()")
    public Object logPerformance(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String name = "-";
        String result = "Y";

        try {
            name = proceedingJoinPoint.getSignature().toShortString() ;
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            result = "N";
            throw throwable;
        }finally {
            long endTime = System.currentTimeMillis();
            log.info("{};{};{}ms",name,result,endTime-startTime);
        }
    }

    @Pointcut(" execution(* com.wj.jpademo.repository..*(..))")
    private void repositoryOps(){

    }
}
