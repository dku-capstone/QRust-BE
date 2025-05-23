package com.qrust.annotation;

import com.qrust.user.domain.entity.vo.UserRole;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleOnly {
    UserRole[] value();
}
