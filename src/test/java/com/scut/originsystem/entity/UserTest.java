package com.scut.originsystem.entity;

import com.scut.originsystem.controller.UserController;
import com.scut.originsystem.util.ReflectUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.*;
import javax.validation.constraints.Size;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    @Autowired
    UserController userController;

    public void parameter_model(User user){
        Class clazz = user.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Object o = ReflectUtil.getFieldValue(user,field);
            if (o == null) {
                o = new String("");
            }
            java.lang.System.out.println(o);
        }
    }
    @Test
    public void parameter_modeltest(){
//        User user = new User();
//        user.setUsername("1212312313213");
//        user.setPassword("21");
//        user.setEmail("dsafjkdjf@@@");
//        try {
//            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//            Validator validator  = validatorFactory.getValidator();
//            Set<ConstraintViolation<User>> set = validator.validate(user, com.scut.originsystem.validationInterface.TestUser.class);
//            for (ConstraintViolation<User> userConstraintViolation : set) {
//                java.lang.System.out.println(userConstraintViolation.getMessage());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}