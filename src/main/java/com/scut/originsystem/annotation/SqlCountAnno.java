package com.scut.originsystem.annotation;

import java.lang.annotation.*;

/**
 *
 * 计算总数时，该字段作为条件
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SqlCountAnno {
}
