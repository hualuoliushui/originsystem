package com.scut.originsystem.mapper;

import com.scut.originsystem.util.Condition;
import com.scut.originsystem.util.SqlUtil;

import java.util.Map;

/**
 * @create 2018-01-16 14:21
 * @desc
 **/
public class UserMapperProvider {
    public String findMerchantUsersByConditionsHelper(Map<String, Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append(" from user_t as u join merchant_t as m on u.id=m.user_id join company_t as c on m.id=c.merchant_id ")
                .append("where u.activation_code<>4 ");
        String user_area_code = (String)params.get("user_area_code");
        String area_code = (String)params.get("area_code");
        String create_date_start = (String)params.get("create_date_start");
        String create_date_end = (String)params.get("create_date_end");
        int activation_code = (int)params.get("activation_code");

        if(!Condition.string_empty(user_area_code)){
            sql.append(" and c.area_code like concat(#{user_area_code},'%') ");
        }

        if(!Condition.string_empty(area_code)){
            sql.append(" and c.area_code like concat(#{area_code},'%') ");
        }
        if(!Condition.string_empty(create_date_start)
                && !Condition.string_empty(create_date_end)){
            sql.append(" and UNIX_TIMESTAMP(#{create_date_start}) < UNIX_TIMESTAMP(u.create_date) " +
                    " and UNIX_TIMESTAMP(u.create_date) < UNIX_TIMESTAMP(#{create_date_end}) ");
        }
        if(activation_code >= 0){
            sql.append(" and u.activation_code=#{activation_code} ");
        }
        return sql.toString();
    }

    private static String sql_MerchantUser_select_fields =
            " u.id,u.username,u.role,u.activation_code,u.contact_name," +
                    "u.phone,u.email,u.create_date,u.area_code,u.check_state," +
                    "m.merchant_name as merchant_name,m.id as merchant_id ";

    public String findMerchantUsersByConditions(Map<String, Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append("select ")
                .append(sql_MerchantUser_select_fields)
                .append(findMerchantUsersByConditionsHelper(params));
        return sql.toString();
    }

    public String findMerchantUsersByConditionsWithPage(Map<String, Object> params){
        StringBuilder sql = new StringBuilder(findMerchantUsersByConditions(params));
        int limit = (int)params.get("limit");
        if(limit>0){
            sql.append(SqlUtil.PAGE_LIMIT_SQL);
        }
        return sql.toString();
    }

    public String getAreaCodeNum_findMerchantUsersByConditions(Map<String,Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.area_code as area_code,a1.description AS district,a2.description AS city,a3.description AS province,count(u.id) AS num " +
                "FROM user_t AS u JOIN merchant_t AS m on u.id=m.user_id JOIN company_t AS c ON m.id=c.merchant_id " +
                "JOIN area_t AS a1 ON a1.code=c.area_code JOIN area_t AS a2 ON a1.belong_to_code=a2.code JOIN area_t AS a3 ON a2.belong_to_code=a3.code ")
                .append(" where u.activation_code<> 4 ");
        String user_area_code = (String)params.get("user_area_code");
        String area_code = (String)params.get("area_code");
        String create_date_start = (String)params.get("create_date_start");
        String create_date_end = (String)params.get("create_date_end");
        int activation_code = (int)params.get("activation_code");

        if(!Condition.string_empty(user_area_code)){
            sql.append(" and c.area_code like concat(#{user_area_code},'%') ");
        }

        if(!Condition.string_empty(area_code)){
            sql.append(" and c.area_code like concat(#{area_code},'%') ");
        }
        if(!Condition.string_empty(create_date_start)
                && !Condition.string_empty(create_date_end)){
            sql.append(" and UNIX_TIMESTAMP(#{create_date_start}) < UNIX_TIMESTAMP(u.create_date) " +
                    " and UNIX_TIMESTAMP(u.create_date) < UNIX_TIMESTAMP(#{create_date_end}) ");
        }
        if(activation_code >= 0){
            sql.append(" and u.activation_code=#{activation_code} ");
        }
        sql.append("GROUP BY c.area_code,a1.description,a2.description,a3.description ");
        return sql.toString();
    }

    public String getTotal_findMerchantUsersByConditions(Map<String, Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append("select count(u.id) ")
                .append(findMerchantUsersByConditionsHelper(params));
        return sql.toString();
    }
}
