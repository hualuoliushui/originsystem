package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.Merchant;
import org.springframework.util.StringUtils;

import java.util.Map;

public class SqlProvider {
    public String voucherByDate(Map<String, Object> param){
        int limit = (int) param.get("limit");
        int offset = (int) param.get("offset");
        String pre_date = (String) param.get("pre_date");
        String post_date = (String) param.get("post_date");
        int merchant_id = (int) param.get("merchant_id");

        String sql = " select * from voucher_t" +
                     " where 1 = 1";
        if(!StringUtils.isEmpty(pre_date) && !StringUtils.isEmpty(post_date)){
            sql += " and create_date > " + "\"" + pre_date + "\"";
            sql += " and create_date <= " + "\"" + post_date + " 23:59:59" + "\"";
        }
        sql +=  " and merchant_id = " + merchant_id +
                " limit " + limit +
                " offset " + offset;
        return sql;
    }

    public String count_merchantCondition(Map<String, Object> param){
        String merchant_name = (String) param.get("merchant_name");
        String company_area = (String) param.get("company_area");
        String area_code = (String)param.get("area_code");

        String sql = "select count(*) from " +
                MerchantMapper.sql_merchant_join_company_join_user + " where " +
                " u.activation_code<>4 ";
        if(!StringUtils.isEmpty(merchant_name)){
            sql += " and m.merchant_name = " + "\"" + merchant_name + "\"";
        }
        if(!StringUtils.isEmpty(company_area)){
            sql += " and c.company_area = " + "\"" + company_area + "\"";
        }
        if(!StringUtils.isEmpty(area_code)){
            sql += " and c.area_code like " +  "\"" + area_code + "%\"";
        }
        return sql;
    }

    public String merchantCondition(Map<String, Object> param){
        int limit = (int) param.get("limit");
        int offset = (int) param.get("offset");
        String merchant_name = (String) param.get("merchant_name");
        String company_area = (String) param.get("company_area");
        String area_code = (String)param.get("area_code");

        String sql = "select " + MerchantMapper.sql_select_field +" from "+ MerchantMapper.sql_merchant_join_company_join_user + " where " +
                " u.activation_code<>4";
        if(!StringUtils.isEmpty(merchant_name)){
            sql += " and m.merchant_name = " + "\"" + merchant_name + "\"";
        }
        if(!StringUtils.isEmpty(company_area)){
            sql += " and c.company_area = " + "\"" + company_area + "\"";
        }
        if(!StringUtils.isEmpty(area_code)){
            sql += " and c.area_code like " +  "\"" + area_code + "%\"";
        }
        if(offset >= 0){
            sql += " limit " + limit +
                    " offset " + offset;
        }
        return sql;
    }

    public String detectionCondition(Map<String, Object> param){
        String merchant_id = (String) param.get("merchant_id");
        String min_date = (String) param.get("min_date");
        String max_date = (String) param.get("max_date");
        String limit = (String) param.get("limit");
        String offset = (String) param.get("offset");

        String sql = "select * from "+DetectInfoMapper.view_name+" where  deleted_code<>1 ";
        if(!StringUtils.isEmpty(merchant_id)){
            sql += " and merchant_id = " +  merchant_id ;
        }
        if(!StringUtils.isEmpty(min_date)){
            sql += " and create_date > " + "\"" + min_date + "\"";
        }
        if(!StringUtils.isEmpty(max_date)){
            sql += " and create_date < " + "\"" + max_date + "\"";
        }

        sql += " limit " + limit +
                " offset " + offset;

        return sql;
    }

    public String countDetectionCondition(Map<String, Object> param){
        String merchant_id = (String) param.get("merchant_id");
        String min_date = (String) param.get("min_date");
        String max_date = (String) param.get("max_date");

        String sql = "select count(*) from "+DetectInfoMapper.view_name+" where deleted_code<>1 ";
        if(!StringUtils.isEmpty(merchant_id)){
            sql += " and merchant_id = " + "\"" + merchant_id + "\"";
        }
        if(!StringUtils.isEmpty(min_date)){
            sql += " and create_date > " + "\"" + min_date + "\"";
        }
        if(!StringUtils.isEmpty(max_date)){
            sql += " and create_date < " + "\"" + max_date + "\"";
        }

        return sql;
    }

    public String countByDate(Map<String, Object> param){
        String pre_date = (String) param.get("pre_date");
        String post_date = (String) param.get("post_date");
        int merchant_id = (int) param.get("merchant_id");

        String sql = " select count(*) from voucher_t" +
                " where 1 = 1";
        if(!StringUtils.isEmpty(pre_date) && !StringUtils.isEmpty(post_date)){
            sql += " and create_date > " + "\"" + pre_date + "\"";
            sql += " and create_date < " + "\"" + post_date + " 23:59:59" + "\"";
        }
        sql +=  " and merchant_id = " + merchant_id;
        return sql;
    }

    public String amountByDate(Map<String, Object> param){
        String pre_date = (String) param.get("pre_date");
        String post_date = (String) param.get("post_date");
        int merchant_id = (int) param.get("merchant_id");

        String sql = " select ROUND(SUM(voucher_amount),2) from voucher_t" +
                " where 1 = 1";
        if(!StringUtils.isEmpty(pre_date) && !StringUtils.isEmpty(post_date)){
            sql += " and create_date > " + "\"" + pre_date + "\"";
            sql += " and create_date < " + "\"" + post_date + " 23:59:59" + "\"";
        }
        sql +=  " and merchant_id = " + merchant_id;
        return sql;
    }


}
