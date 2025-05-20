package com.qrust.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    // Controller, Service, Repository 계층 모두 포함
    @Pointcut("execution(* com.qrust..*Controller.*(..)) || " +
            "execution(* com.qrust..*Service.*(..)) || " +
            "execution(* com.qrust..*Repository.*(..))")
    public void applicationFlow() {}

    @Around("applicationFlow()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String layer = determineLayer(joinPoint);
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        log.info("➡️ [{}] 호출: {} args={}", layer, methodName, Arrays.toString(args));
        try {
            Object result = joinPoint.proceed();
            log.info("✅ [{}] 반환: {} result={}", layer, methodName, result);
            return result;
        } catch (Throwable t) {
            log.error("❌ [{}] 예외 발생: {} message={}", layer, methodName, t.getMessage(), t);
            throw t;
        }
    }

    private String determineLayer(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        if (className.contains("Controller")) return "Controller";
        if (className.contains("Service")) return "Service";
        if (className.contains("Repository")) return "Repository";
        return "Unknown";
    }
}

