package com.scut.originsystem.tool;

import com.scut.originsystem.util.DateUtil;
import com.scut.originsystem.util.ReflectUtil;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class AutoInit {

    protected static Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Object init(Class clazz){
        return init(clazz,0);
    }

    public static Object init(Class clazz,int index) {
        Object object = null;
        try {
            object = clazz.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        List<Field> fields = new LinkedList<>();
        Field[] fields1 = clazz.getDeclaredFields();
        for (Field field : fields1) {
            fields.add(field);
        }
        if (!clazz.getSuperclass().equals(Object.class)) {
            Field[] fields2 = clazz.getSuperclass().getDeclaredFields();
            for (Field field : fields2) {
                fields.add(field);
            }
        }
        for (Field field : fields) {
            Method setMethod = ReflectUtil.getWriteMethod(field,clazz);
            try {
                if (isInt(field.getType())) {

                    setMethod.invoke(object, 0);

                } else if (isString(field.getType())) {
                    if(field.getName().startsWith("order_by")){
                        setMethod.invoke(object,"true");
                    } else if(field.getName().endsWith("date_start")) {
                        setMethod.invoke(object, DateUtil.getFutureDate(-7));
                    } else if(field.getName().endsWith("date_end")){
                        setMethod.invoke(object,DateUtil.getFutureDate(7));
                    } else if(field.getName().endsWith("date")){
                        setMethod.invoke(object,format.format(new Date()));
                    } else
                        setMethod.invoke(object, field.getName()+String.valueOf(index));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    public static boolean isInt(Class o) {
        return o.equals(Integer.class);
    }

    public static boolean isString(Class o) {
        return o.equals(String.class);
    }
}
