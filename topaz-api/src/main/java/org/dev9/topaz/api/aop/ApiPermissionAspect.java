package org.dev9.topaz.api.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.dev9.topaz.api.exception.ApiNotFoundException;
import org.dev9.topaz.api.exception.ApiUnauthorizedException;
import org.dev9.topaz.common.annotation.Permission;
import org.dev9.topaz.common.aop.AbstractPermissionAspect;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.User;
import org.dev9.topaz.common.enums.PermissionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class ApiPermissionAspect extends AbstractPermissionAspect {
    private Logger logger=LoggerFactory.getLogger(ApiPermissionAspect.class);

    @Resource
    private UserRepository userRepository;

    @Pointcut("execution(public * org.dev9.topaz.api.controller..*.*(..))")
    public void apiPointcut(){}

    @Around("authorize(permission) && apiPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint, Permission permission) throws ApiUnauthorizedException {
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes)
            throw new ApiUnauthorizedException();

        HttpServletRequest request=attributes.getRequest();
        List<PermissionType> permissionTypes= Arrays.asList(permission.value());

        if (null == request)
            throw new ApiUnauthorizedException("impossible error");

        logger.info("CHECK PERMISSION: "+request.getRequestURL().toString());
        HttpSession session=request.getSession();
        if (null == session.getAttribute("userId"))
            throw new ApiUnauthorizedException("please login");

        Integer userId=(Integer)session.getAttribute("userId");
        User user=userRepository.findById(userId).orElse(null);

        if (!checkPermissoin(permissionTypes, user))
            throw new ApiUnauthorizedException("permission error");

        logger.info("PERMISSION PASSED: "+request.getRequestURL().toString());
        Object ret= null;
        try {
            ret = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return ret;
    }
}
