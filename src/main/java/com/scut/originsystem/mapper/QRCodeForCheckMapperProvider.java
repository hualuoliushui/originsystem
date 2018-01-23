package com.scut.originsystem.mapper;

import com.scut.originsystem.util.SqlUtil;
import org.springframework.util.StringUtils;

import java.util.Map;

public class QRCodeForCheckMapperProvider {
    public String getMyCheck(Map<String, Object> param){
        int check_state = (int) param.get("check_state");

        String sql = " select * from qrcode_for_check where merchant_id = #{merchant_id}";
        if (check_state == 1 || check_state == 2){
            sql += " and check_state = #{check_state} ";
        }
        if (check_state == 3){
            sql += " and check_state <> 0";
        }
        if (check_state == 0){
            sql += " order by create_date desc";
        }else {
            sql += " order by check_date desc";
        }
        sql +=  " limit " + SqlUtil.PAGE_LIMIT +
                " offset #{offset}" ;

        return sql;
    }

    public String getMyCheckCount(Map<String, Object> param){
        int check_state = (int) param.get("check_state");

        String sql = " select count(*) from qrcode_for_check where merchant_id = #{merchant_id}";
        if (check_state == 1 || check_state == 2){
            sql += " and check_state = #{check_state} ";
        }
        if (check_state == 3){
            sql += " and check_state <> 0";
        }

        return sql;
    }
}
