package com.scut.originsystem.annotation;

import com.scut.originsystem.enums.SystemLogType;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLogControllerAnno {
    String description() default "";
    SystemLogType type() default SystemLogType.COMMON;
}
