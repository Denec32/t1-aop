package ru.t1.java.demo.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.DataSourceErrorLog;
import ru.t1.java.demo.model.TimeLimitExceedLog;
import ru.t1.java.demo.service.DataSourceErrorLogService;
import ru.t1.java.demo.service.TimeLimitExceedLogService;

@Slf4j
@Aspect
@Component
@Order(0)
@RequiredArgsConstructor
public class LoggingAspect {

    private final DataSourceErrorLogService dataSourceErrorLogService;
    private final TimeLimitExceedLogService timeLimitExceedLogService;

    @Value("${tle_ms}")
    private int timeLimit;

    @AfterThrowing(pointcut = "execution(* ru.t1.java.demo.controller..*(..))", throwing = "e")
    public void handleException(JoinPoint joinPoint, Exception e) {
        String stacktraceText = e.getStackTrace()[0].toString();
        String message = e.getMessage();
        String methodSignature = joinPoint.getSignature().toLongString();

        var dseLog = DataSourceErrorLog.builder()
                .stacktraceExceptionText(stacktraceText)
                .message(message)
                .methodSignature(methodSignature)
                .build();

        dataSourceErrorLogService.logError(dseLog);
    }

    @Around("@annotation(TimeLimitControl)")
    public Object handleTimeLimitExceeded(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;
        if (executionTime <= timeLimit) return proceed;

        var tleLog = TimeLimitExceedLog.builder()
                .methodSignature(joinPoint.getSignature().toLongString())
                .executionTime(executionTime)
                .build();

        timeLimitExceedLogService.logTimeLimitExceed(tleLog);

        return proceed;
    }
}