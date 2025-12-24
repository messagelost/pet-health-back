package com.jacob.common.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class RequestLogAspect {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Around("execution(* com.jacob..web..*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                        .getRequest();

        long start = System.currentTimeMillis();

        log.info("IP={}-----------------------> {} 请求开始 请求路径=\"{}\" 参数={}",
                request.getRemoteAddr(),
                request.getMethod(),
                request.getRequestURI(),
                Arrays.toString(joinPoint.getArgs())
        );

        Object result = joinPoint.proceed();

        log.info("-----------------------> {} 请求结束 请求路径=\"{}\" 耗时:{}ms 返回结果={}",
                request.getMethod(),
                request.getRequestURI(),
                System.currentTimeMillis() - start,
                limit( toJsonSafe( result ) )
        );

        return result;
    }

    /**
     * 避免json序列化时出现循环引用
     * @param obj 返回结果
     * @return json字符串
     */
    private String toJsonSafe(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            return String.valueOf(obj);
        }
    }

    /**
     * 限制返回字符串长度 避免日志过长
     * @param str json字符串
     * @return 限制长度json字符串
     */
    private String limit(String str) {
        if (str.length() > 1000) {
            return str.substring(0, 1000) + "...(truncated)";
        }
        return str;
    }
}
