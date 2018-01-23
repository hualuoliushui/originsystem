package com.scut.originsystem.annotation;

import com.scut.originsystem.config.DefineMatcherValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DefineMatcherValidator.class)
@Documented
public @interface DefineMatcher {
    String message() default "Illgeal";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String pattern();
}