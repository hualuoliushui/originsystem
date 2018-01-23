package com.scut.originsystem.mapper;

import com.scut.originsystem.util.Condition;
import com.scut.originsystem.util.SqlUtil;

import java.util.Map;

/**
 * @create 2018-01-15 23:13
 * @desc
 **/
public class MerchantMapperProvider {
    public String findMerchantsByAreaCodeOrCreateDateOrActivationCodeHelper(Map<String, Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append(" from ")
                .append(MerchantMapper.sql_merchant_join_company_join_user)
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
            sql.append(" and UNIX_TIMESTAMP(#{create_date_start}) < UNIX_TIMESTAMP(m.create_date) " +
                    " and UNIX_TIMESTAMP(m.create_date) < UNIX_TIMESTAMP(#{create_date_end}) ");
        }
        if(activation_code >= 0){
            sql.append(" and u.activation_code=#{activation_code} ");
        }
        return sql.toString();
    }

    public String findMerchantsByAreaCodeOrCreateDateOrActivationCode(Map<String, Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append("select ")
                .append(MerchantMapper.sql_select_field)
                .append(findMerchantsByAreaCodeOrCreateDateOrActivationCodeHelper(params));
        return sql.toString();
    }

    public String findMerchantsByAreaCodeOrCreateDateOrActivationCodeWithPage(Map<String, Object> params){
        StringBuilder sql = new StringBuilder(findMerchantsByAreaCodeOrCreateDateOrActivationCode(params));
        int limit = (int)params.get("limit");
        if(limit>0){
            sql.append(SqlUtil.PAGE_LIMIT_SQL);
        }
        return sql.toString();
    }

    public String getTotal_findMerchantsByAreaCodeOrCreateDateOrActivationCode(Map<String, Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append("select count(m.id) ")
                .append(findMerchantsByAreaCodeOrCreateDateOrActivationCodeHelper(params));
        return sql.toString();
    }

}
