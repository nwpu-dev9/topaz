package org.dev9.topaz.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.dev9.topaz.common.annotation.Permission;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.User;
import org.dev9.topaz.common.enums.PermissionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractPermissionAspect {
    @Pointcut("@annotation(permission)")
    public void authorize(Permission permission){}

    protected Boolean checkPermissoin(List<PermissionType> permissionTypes, User user){
        if (permissionTypes.contains(PermissionType.NONE))
            return true;

        if (permissionTypes.contains(PermissionType.USER) && null != user)
            return true;

        if (permissionTypes.contains(PermissionType.ADMIN) && null != user && user.isAdmin())
            return true;

        return false;
    }
}
