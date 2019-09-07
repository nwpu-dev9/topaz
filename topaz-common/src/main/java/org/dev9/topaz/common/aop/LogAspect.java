package org.dev9.topaz.common.aop;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.jruby.util.Join;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    private Logger logger= LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(public * org.dev9.topaz.api.controller..*.*(..))")
    public void apiLog(){}

    @Pointcut("execution(public * org.dev9.topaz.back.controller..*.*(..))")
    public void backLog(){}

    @Pointcut("execution(public * org.dev9.topaz.front.controller..*.*(..))")
    public void frontLog(){}

    @Before("apiLog() || backLog() || frontLog()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        if (null == request)
            return;

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "apiLog() || backLog() || frontLog()")
    public void doAfter(Object ret){
        if (null != ret)
            logger.info("RESPONSE : " + ret.toString());
    }
}
