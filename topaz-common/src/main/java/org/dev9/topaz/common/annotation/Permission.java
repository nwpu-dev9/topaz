package org.dev9.topaz.common.annotation;

import org.dev9.topaz.common.enums.PermissionType;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {
    PermissionType[] value() default PermissionType.NONE;
}
