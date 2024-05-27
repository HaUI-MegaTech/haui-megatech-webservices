package shop.haui_megatech.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class RepositoryLogger {
    @Value("${application.repository.query-limit-warning-ms:60}")
    private int executionLimitMs;

    @Around("execution(* shop.haui_megatech.repository.*.*(..))")
    public Object logQueryTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        String message = joinPoint.getSignature() + " exec in " + executionTime + " ms";
        if (executionTime >= executionLimitMs) {
            log.warn("{} : SLOW QUERY", message);
        }
        return proceed;
    }
}
