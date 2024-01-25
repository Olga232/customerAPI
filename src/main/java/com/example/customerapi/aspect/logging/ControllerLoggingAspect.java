package com.example.customerapi.aspect.logging;

import com.example.customerapi.aspect.util.LogMessageBuilder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Logs the REST API layer.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
@Component
@Aspect
@Slf4j
public class ControllerLoggingAspect {

    @Pointcut("execution(* com.example.customerapi.controller.*.*(..))")
    public void controllerMethods() {
    }

    @Around("controllerMethods()")
    public Object logControllerMethods(ProceedingJoinPoint pjp) throws Throwable {
        final String argsNamesAndValues = LogMessageBuilder.argsNamesAndValuesAsString(pjp);

        log.info(pjp.getSignature().getName() + ".E: " + argsNamesAndValues);

        final Object returnValue = pjp.proceed();

        log.info(pjp.getSignature().getName() + ".X: " + returnValue);
        return returnValue;
    }

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "ex")
    public void afterThrowingAdvice(JoinPoint joinPoint, Exception ex) {
        log.error(joinPoint.getSignature().getName() + ": exception message: " + ex.getMessage());
    }
}
