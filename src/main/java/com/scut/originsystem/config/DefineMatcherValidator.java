package com.scut.originsystem.config;

import com.scut.originsystem.annotation.DefineMatcher;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Size;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefineMatcherValidator implements ConstraintValidator<DefineMatcher,Object> {
    private String patternString;

    @Override
    public void initialize(DefineMatcher constraintAnnotation) {
        patternString = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        String value = null;
        if (o instanceof Integer){
            value = Integer.toString((Integer) o);
        }
        if (o instanceof Double){
            value = Double.toString((Double) o);
        }
        if (o instanceof String){
            value = (String) o;
        }
        if(StringUtils.isEmpty(value)){
            return true;
        }
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(value);
        if(!matcher.matches()){
            return false;
        }else {
            return true;
        }
    }
}
