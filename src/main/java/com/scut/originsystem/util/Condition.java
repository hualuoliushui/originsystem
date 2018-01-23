package com.scut.originsystem.util;

import com.scut.originsystem.annotation.
        SqlConditionAnno;

import java.lang.reflect.Field;
import java.util.*;

public class Condition {
    private Set<Field> and_like_set = new HashSet<>();
    private Set<Field> and_equal_set = new HashSet<>();
    private Map<String,PairUtil<Field,Field>> and_between_date = new HashMap<>();
    private List<PairUtil<Integer,Field>> order_by_list = new LinkedList<>();
    private Field asc_field = null;
    private Field limit_field = null;
    private Field offset_field = null;

    private Object object;
    private Class clazz;

    public String countResolve(){
        StringBuilder part_sql = new StringBuilder();

        part_sql.append(deal_and_like());
        part_sql.append(deal_and_equal());
        part_sql.append(deal_and_between_date());

        return part_sql.toString();
    }

    public String conditionResolve(){
        StringBuilder part_sql = new StringBuilder();

        part_sql.append(deal_and_like());
        part_sql.append(deal_and_equal());
        part_sql.append(deal_and_between_date());
        part_sql.append(deal_order_by());
        part_sql.append(deal_page());

        return part_sql.toString();
    }

    public Condition(Object object){
        this.object = object;
        this.clazz = object.getClass();

        List<Field> fields = new LinkedList<>();
        Field[] fields1 = clazz.getDeclaredFields();
        for (Field field : fields1) {
            fields.add(field);
        }
        if(!clazz.getSuperclass().equals(Object.class)){
            Field[] fields2 = clazz.getSuperclass().getDeclaredFields();
            for (Field field : fields2) {
                fields.add(field);
            }
        }

        Field theOther = null;
        SqlConditionAnno annotation = null;
        PairUtil<Field,Field> pair = null;

        for (Field field : fields) {
            annotation = field.getAnnotation(SqlConditionAnno.class);
            if (annotation != null) {
                switch (annotation.type()){
                    case AND_LIKE:{
                        and_like_set.add(field);
                        break;
                    }
                    case AND_EQUAL:{
                        and_equal_set.add(field);
                        break;
                    }
                    case AND_BETWEEN_DATE_START:{
                        if(and_between_date.containsKey(annotation.column())){
                            pair = and_between_date.get(annotation.column());
                            theOther = pair.getValue();
                        }
                        and_between_date.put(annotation.column(),new PairUtil(field,theOther));
                        break;
                    }
                    case AND_BETWEEN_DATE_END:{
                        if(and_between_date.containsKey(annotation.column())){
                            pair = and_between_date.get(annotation.column());
                            theOther = pair.getKey();
                        }
                        and_between_date.put(annotation.column(),new PairUtil(theOther,field));
                        break;
                    }
                    case ORDER_BY:{
                        order_by_list.add(new PairUtil(annotation.order_index(),field));
                        break;
                    }
                    case IS_ASC:{
                        asc_field = field;
                        break;
                    }
                    case LIMIT:{
                        limit_field = field;
                        break;
                    }
                    case OFFSET:{
                        offset_field = field;
                    }
                    default:{
                        //类型错误
                    }
                }
            }
        }
    }

    public String deal_and_like(){
        StringBuilder part_sql = new StringBuilder();
        SqlConditionAnno sqlConditionAnno = null;
        for (Field field : and_like_set) {
            Object o = getFieldValue(object, field);
            if (!string_empty(o)) {
                sqlConditionAnno = field.getAnnotation(SqlConditionAnno.class);
                if (sqlConditionAnno != null && !string_empty(sqlConditionAnno.column()))
                    part_sql.append((SqlUtil.andLike(sqlConditionAnno.column())));
                else
                    part_sql.append(SqlUtil.andLike(field.getName()));
            }
        }
        return part_sql.toString();
    }

    public String deal_and_equal(){
        StringBuilder part_sql = new StringBuilder();
        SqlConditionAnno sqlConditionAnno = null;
        for (Field field : and_equal_set) {
            Object o = getFieldValue(object, field);
            if (!int_empty(o)) {
                sqlConditionAnno = field.getAnnotation(SqlConditionAnno.class);
                if (sqlConditionAnno != null && !string_empty(sqlConditionAnno.column()))
                    part_sql.append((SqlUtil.andEquel(sqlConditionAnno.column())));
                else
                    part_sql.append(SqlUtil.andEquel(field.getName()));
            }
        }
        return part_sql.toString();
    }

    public String deal_and_between_date(){
        StringBuilder part_sql = new StringBuilder();
        String name = null;
        PairUtil<Field,Field> pair = null;
        for(Map.Entry<String,PairUtil<Field,Field>> entry : and_between_date.entrySet()){
            name = entry.getKey();
            pair = entry.getValue();
            if(pair.getKey()!=null && pair.getValue()!=null){
                Object o_start = getFieldValue(object, pair.getKey());
                Object o_end = getFieldValue(object, pair.getValue());
                if (!string_empty(o_start) && !string_empty(o_end)) {
                    part_sql.append(SqlUtil.andBetweenDateTime(name,date_start(name),date_end(name)));
                }
            }
        }
        return part_sql.toString();
    }

    public String deal_order_by(){
        StringBuilder part_sql = new StringBuilder();
        Field field = null;
        SqlConditionAnno sqlConditionAnno = null;
        order_by_list.sort(Comparator.comparing(PairUtil::getKey));
        List<String> list = new LinkedList<>();
        PairUtil<String,Field> sfPair;
        for (PairUtil<Integer, Field> integerFieldPairUtil : order_by_list) {
            field = integerFieldPairUtil.getValue();
            sqlConditionAnno = field.getAnnotation(SqlConditionAnno.class);
            Object o = getFieldValue(object, field);
            if (!string_empty(o) && sqlConditionAnno!=null) {
                if(sqlConditionAnno.is_date()){
                    list.add(SqlUtil.UNIX_TIMESTAMP(sqlConditionAnno.column()));
                }else {
                    list.add(sqlConditionAnno.column());
                }
            }

        }
        if(list.size()!=0){
            part_sql.append(" order by ");
            int i =0;
            for (; i < list.size()-1; i++) {
                part_sql.append(list.get(i))
                        .append(",");
            }
            part_sql.append(list.get(i))
                    .append(" ");

            if (asc_field != null) {
                Object o = getFieldValue(object,asc_field);
                if(int_empty(o)){
                    part_sql.append(" asc ");
                }else{
                    part_sql.append(" desc ");
                }
            }
        }
        return part_sql.toString();
    }

    public String deal_page(){
        StringBuilder part_sql = new StringBuilder();
        if (limit_field != null && offset_field != null) {
            Object o = getFieldValue(object,limit_field);
            if(!int_empty(o)){
                part_sql.append(SqlUtil.PAGE_LIMIT_SQL);
            }
        }
        return part_sql.toString();
    }

    private static Object getFieldValue(Object object, Field field) {
       return ReflectUtil.getFieldValue(object,field);
    }

    private static String date_start(String name){
        return name + "_start";
    }

    private static String date_end(String name){
        return name + "_end";
    }

    public static boolean string_empty(Object o){
        return o==null || o.equals("");
    }

    public static boolean int_empty(Object o){
        return o==null || o.equals(0);
    }
}
