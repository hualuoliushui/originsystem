package com.scut.originsystem.annotation;

import com.scut.originsystem.enums.SqlConditionType;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SqlConditionAnno {
    String column() default "";
    SqlConditionType type() default SqlConditionType.AND_LIKE;

    /**
     * order by的顺序
     * @return
     */
    int order_index() default 0;

    /**
     * 当前字段是否作为日期处理
     * @return
     */
    boolean is_date() default false;
}
