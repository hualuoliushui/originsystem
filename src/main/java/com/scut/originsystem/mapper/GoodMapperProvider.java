package com.scut.originsystem.mapper;

import com.scut.originsystem.util.Condition;
import com.scut.originsystem.util.SqlUtil;
import org.springframework.util.StringUtils;

import java.util.Map;

public class GoodMapperProvider {

    public String selectiveSql(Map<String, Object> param){
        String good_batch = (String) param.get("good_batch");
        String produce_place = (String) param.get("produce_place");
        String pack_type = (String) param.get("pack_type");
        String good_name = (String) param.get("good_name");
        String good_code = (String) param.get("good_code");

        String sql = " select * from good_info_t " +
                     " where 1 = 1 and deleted_code<>1 ";
        if(!StringUtils.isEmpty(good_batch)){
            sql += " and good_batch like concat('%',#{good_batch},'%')";
        }
        if(!StringUtils.isEmpty(produce_place)){
            sql += " and produce_place like concat('%',#{produce_place},'%')";
        }
        if(!StringUtils.isEmpty(pack_type)){
            sql += " and pack_type like concat('%',#{pack_type},'%')";
        }
        if(!StringUtils.isEmpty(good_name)){
            sql += " and good_name like concat('%',#{good_name},'%')";
        }
        if(!StringUtils.isEmpty(good_code)){
            sql += " and good_code like concat('%',#{good_code},'%')";
        }
        sql +=  " and type_id = #{type_id}" +
                " and merchant_id = #{merchant_id}" +
                " limit  #{limit}" +
                " offset #{offset}" ;
        System.out.println(sql);
        return sql;
    }

    public String selectiveAmountSql(Map<String, Object> param){
        String good_batch = (String) param.get("good_batch");
        String produce_place = (String) param.get("produce_place");
        String pack_type = (String) param.get("pack_type");
        String good_name = (String) param.get("good_name");
        String good_code = (String) param.get("good_code");

        String sql = " select count(*) from good_info_t" +
                " where 1 = 1 and deleted_code<>1 ";
        if(!StringUtils.isEmpty(good_batch)){
            sql += " and good_batch like concat('%',#{good_batch},'%')";
        }
        if(!StringUtils.isEmpty(produce_place)){
            sql += " and produce_place like concat('%',#{produce_place},'%')";
        }
        if(!StringUtils.isEmpty(pack_type)){
            sql += " and pack_type like concat('%',#{pack_type},'%')";
        }
        if(!StringUtils.isEmpty(good_name)){
            sql += " and good_name like concat('%',#{good_name},'%')";
        }
        if(!StringUtils.isEmpty(good_code)){
            sql += " and good_code like concat('%',#{good_code},'%')";
        }
        sql +=  " and type_id = #{type_id} and merchant_id = #{merchant_id}" ;
        return sql;
    }
}
