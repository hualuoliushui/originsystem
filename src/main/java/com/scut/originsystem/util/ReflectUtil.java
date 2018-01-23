package com.scut.originsystem.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static java.util.Locale.ENGLISH;

/**
 * @create 2018-01-07 17:47
 * @desc 反射工具类，封装使用了java9中新特性的功能，便于后期修改实现(目前不支持函数重载）
 **/
public class ReflectUtil {
    public static Object getFieldValue(Object object, Field field) {
        Method getMethod;
        Class clazz = object.getClass();
        Object o = null;
        try {
            getMethod = getReadMethod(field,clazz);
            getMethod.setAccessible(true);
            o = getMethod.invoke(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static void setFieldValue (Object object, Field field, Object arg){
        Class clazz = object.getClass();
        Method setMethod;
        try {
            setMethod = getWriteMethod(field,clazz);
            setMethod.setAccessible(true);
            setMethod.invoke(object,arg);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Method getReadMethod(Field field,Class clazz){
//        PropertyDescriptor pd = null;
//        try {
//            pd = new PropertyDescriptor(field.getName(),clazz);
//        } catch (IntrospectionException e) {
//            e.printStackTrace();
//        }
//        return pd.getReadMethod();
        try {
            return getMethod(clazz, getReadMethodName(field));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method getWriteMethod(Field field,Class clazz){
        PropertyDescriptor pd = null;
//        try {
//            pd = new PropertyDescriptor(field.getName(),clazz);
//        } catch (IntrospectionException e) {
//            e.printStackTrace();
//        }
//        return pd.getWriteMethod();
        try {
            return getMethod(clazz,getWriteMethodName(field));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final String SET_PREFIX = "set";
    private static final String GET_PREFIX = "get";

    private static String getReadMethodName(Field field){
        return GET_PREFIX + capitalize(field.getName());
    }

    private static String getWriteMethodName(Field field){
        return SET_PREFIX + capitalize(field.getName());
    }

    private static String capitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        return name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
    }

    private static Map<String,Map<String,Method>> class_methodName_method_map = new HashMap<>();

    private static Method getMethod(Class clazz, String methodName) throws Exception {
        initClassMethodMap(clazz);
        Map<String,Method> map = class_methodName_method_map.get(clazz.getName());
        if(!map.containsKey(methodName))
            throw new Exception("Not found method by method name :"+ methodName +
                    " in class " + clazz.getName());
        return map.get(methodName);
    }

    private static void initClassMethodMap(Class clazz){
        if(!class_methodName_method_map.containsKey(clazz.getName())){
            Map<String,Method> map = new HashMap();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                map.put(method.getName(),method);
            }
            class_methodName_method_map.put(clazz.getName(),map);
        }
    }
}
