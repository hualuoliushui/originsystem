package com.scut.originsystem.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlUtil {
    public static final int PAGE_LIMIT = 10;

    public static int getOffset(int page){
        return (page-1) * PAGE_LIMIT;
    }

    public static final String PAGE_LIMIT_SQL =
        " limit #{limit} offset #{offset} ";

    public static final String COUNT_SQL =
            "select count(*) ";

    public static Map resultHelper(List list,int total){
        Map<String,Object> map = new HashMap<>();
        map.put("list",list);
        map.put("total",total);
        return map;
    }

    public static String concat(String ...strs){
        StringBuilder stringBuilder = new StringBuilder("concat(");
        if(strs.length!=0){
            int i=0;
            for (i = 0; i < strs.length-1; i++) {
                stringBuilder.append(strs[i])
                        .append(",");
            }
            stringBuilder.append(strs[i]);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public static String pStrp(String name){
        return concat("'%'","#{"+name+"}","'%'");
    }

    public static String pStr(String name){
        return concat("'%'","#{"+name+"}");
    }

    public static String strp(String name){
        return concat("#{"+name+"}","'%'");
    }

    public static String andLike(String name){
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("and ")
                .append(name)
                .append(" like ")
                .append(SqlUtil.pStrp(name))
                .append(" ");
        return stringBuilder.toString();
    }

    public static String andpLike(String name){
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("and ")
                .append(name)
                .append(" like ")
                .append(SqlUtil.pStr(name))
                .append(" ");
        return stringBuilder.toString();
    }

    public static String andLikep(String name){
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("and ")
                .append(name)
                .append(" like ")
                .append(SqlUtil.strp(name))
                .append(" ");
        return stringBuilder.toString();
    }

    public static String andEquel(String name){
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("and ")
                .append(name)
                .append(" = #{")
                .append(name)
                .append(" } ");
        return stringBuilder.toString();
    }

    public static String andBetweenDateTime(String name, String start_name, String end_name){
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("and ")
                .append(UNIX_TIMESTAMP_VAR(start_name))
                .append(" < ")
                .append(UNIX_TIMESTAMP(name))
                .append(" and ")
                .append(UNIX_TIMESTAMP(name))
                .append(" < ")
                .append(UNIX_TIMESTAMP_VAR(end_name))
                .append(" ");
        return stringBuilder.toString();
    }

    public static String UNIX_TIMESTAMP(String name){
        return "UNIX_TIMESTAMP("+name+")";
    }

    public static String UNIX_TIMESTAMP_VAR(String name){
        return "UNIX_TIMESTAMP(#{"+name+"})";
    }

    public static String orderBy(List<String> orders){
        StringBuilder stringBuilder = new StringBuilder();
        if(orders.size()!=0){
            int i=0;
            for(;i<orders.size()-1;i++){
                stringBuilder.append(orders.get(i))
                        .append(",");
            }
            stringBuilder.append(orders.get(i));
        }
        return stringBuilder.toString();
    }

    public static String CountResolve(Object object){
        return new Condition(object).countResolve();
    }


    public static String ConditionsResolve(Object object){
        return new Condition(object).conditionResolve();
    }


}
