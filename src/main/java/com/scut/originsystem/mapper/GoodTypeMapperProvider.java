package com.scut.originsystem.mapper;

import com.scut.originsystem.util.Condition;
import com.scut.originsystem.util.SqlUtil;
import org.springframework.util.StringUtils;

import java.util.Map;

public class GoodTypeMapperProvider {

    public String selectiveSql(Map<String, Object> param){
        String good_code = (String) param.get("good_code");
        String produce_place = (String) param.get("produce_place");
        String good_name = (String) param.get("good_name");

        String sql = " select * from good_type_t" +
                " where 1 = 1 and deleted_code <> 1 ";
        if(!StringUtils.isEmpty(good_code)){
            sql += " and good_code like concat('%',#{good_code},'%')";
        }
        if(!StringUtils.isEmpty(produce_place)){
            sql += " and produce_place like concat('%',#{produce_place},'%')}";
        }
        if(!StringUtils.isEmpty(good_name)){
            sql += " and good_name like concat('%',#{good_name},'%')";
        }
        sql +=  " and merchant_id = #{merchant_id}" +
                " limit  #{limit}" +
                " offset #{offset}" ;
        return sql;
    }

    public String selectiveAmountSql(Map<String, Object> param){
        String good_code = (String) param.get("good_code");
        String produce_place = (String) param.get("produce_place");
        String good_name = (String) param.get("good_name");

        String sql = " select count(*) from good_type_t" +
                " where 1 = 1 and deleted_code <> 1 ";
        if(!StringUtils.isEmpty(good_code)){
            sql += " and good_code like concat('%',#{good_code},'%')";
        }
        if(!StringUtils.isEmpty(produce_place)){
            sql += " and produce_place like concat('%',#{produce_place},'%')}";
        }
        if(!StringUtils.isEmpty(good_name)){
            sql += " and good_name like concat('%',#{good_name},'%')";
        }
        sql +=  " and merchant_id = #{merchant_id}" ;
        return sql;
    }

    private String findGoodTypesByGoodNameOrGoodCodeHelper(Map<String,Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append(" from ")
                .append(" good_type_t as g join company_t as c on c.merchant_id=g.merchant_id join merchant_t as m on m.id=g.merchant_id ")
                .append(" where deleted_code <> 1");
        String user_area_code = (String)params.get("user_area_code");
        String good_name = (String)params.get("good_name");
        String good_code = (String)params.get("good_code");
        String merchant_name = (String)params.get("merchant_name");
        if(!Condition.string_empty(user_area_code)){
            sql.append(" and c.area_code like concat(#{user_area_code},'%') ");
        }
        if(!Condition.string_empty(good_name)){
            sql.append(" and g.good_name like concat('%',#{good_name},'%') ");
        }
        if(!Condition.string_empty(good_code)){
            sql.append(" and g.good_code like concat('%',#{good_code},'%') ");
        }
        if(!Condition.string_empty(merchant_name)){
            sql.append(" and m.merchant_name like concat('%',#{merchant_name},'%') ");
        }
        return sql.toString();
    }

    String sql_MerchantGoodType_select_fields =
            " g.id,g.good_code,g.good_name,g.merchant_id,g.produce_place,g.deleted_code,g.picture_1,g.picture_2,g.picture_3,g.picture_4,g.picture_5,m.merchant_name ";

    public String findGoodTypesByGoodNameOrGoodCode(Map<String,Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append("select ")
                .append(sql_MerchantGoodType_select_fields)
                .append(findGoodTypesByGoodNameOrGoodCodeHelper(params));
        return sql.toString();
    }

    public String findGoodTypesByGoodNameOrGoodCodeWithPage(Map<String,Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append(findGoodTypesByGoodNameOrGoodCode(params));
        int limit = (int)params.get("limit");
        if(limit>0){
            sql.append(SqlUtil.PAGE_LIMIT_SQL);
        }
        return sql.toString();
    }

    public String getTotal_findGoodTypesByGoodNameOrGoodCode(Map<String,Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append("select count(g.id) ")
                .append(findGoodTypesByGoodNameOrGoodCodeHelper(params));
        return sql.toString();
    }

    public String getGoodNumWithGoodName_findGoodTypesByGoodNameOrGoodCode(Map<String,Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT temp.good_name as name,sum(good_number) as value FROM " +
                "(SELECT g.id AS id,g.good_name AS good_name FROM good_type_t as g join company_t as c on c.merchant_id=g.merchant_id join merchant_t as m on m.id=g.merchant_id ")
                .append(" where g.deleted_code<>1 ");
        String user_area_code = (String)params.get("user_area_code");
        String good_name = (String)params.get("good_name");
        String good_code = (String)params.get("good_code");
        String merchant_name = (String)params.get("merchant_name");
        if(!Condition.string_empty(user_area_code)){
            sql.append(" and c.area_code like concat(#{user_area_code},'%') ");
        }
        if(!Condition.string_empty(good_name)){
            sql.append(" and g.good_name like concat('%',#{good_name},'%') ");
        }
        if(!Condition.string_empty(good_code)){
            sql.append(" and g.good_code like concat('%',#{good_code},'%') ");
        }
        if(!Condition.string_empty(merchant_name)){
            sql.append(" and m.merchant_name like concat('%',#{merchant_name},'%') ");
        }
        sql.append(" )as temp " +
                "JOIN good_info_t as g ON temp.id=g.type_id " +
                "GROUP BY temp.good_name");
        return sql.toString();
    }

    private String findAllGoodTypesHelper(Map<String,Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append(" from good_type_t as g join company_t as c on g.merchant_id=c.merchant_id join merchant_t as m on m.id=g.merchant_id ")
                .append("where g.deleted_code<>1 ");
        String user_area_code = (String)params.get("user_area_code");
        if(!Condition.string_empty(user_area_code)){
            sql.append(" and c.area_code like concat(#{user_area_code},'%') ");
        }
        return sql.toString();
    }

    public String findAllGoodTypes(Map<String,Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append("select ")
                .append(sql_MerchantGoodType_select_fields)
                .append(findAllGoodTypesHelper(params));
        return sql.toString();
    }

    public String findAllGoodTypesWithPage(Map<String,Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append(findAllGoodTypes(params));
        int limit = (int)params.get("limit");
        if(limit>0){
            sql.append(SqlUtil.PAGE_LIMIT_SQL);
        }
        return sql.toString();
    }

    public String getTotal_findAllGoodTypes(Map<String,Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append("select count(g.id) ")
                .append(findAllGoodTypesHelper(params));
        return sql.toString();
    }
}
