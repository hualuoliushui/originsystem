package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.WaitForCheckingMerchant;
import com.scut.originsystem.util.SqlUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "waitForCheckingMerchantMapper")
public interface WaitForCheckingMerchantMapper {
    //插入商户提交的待审核的商户信息（该商户已激活）
    @Insert("insert into wait_for_checking_merchant_t(username,password,contact_name,phone,email,merchant_name,company_name,company_introduction,gis_location,business_scope,company_location,company_area,legal_person,company_code,company_picture,create_date,area_code,merchant_id) " +
            "values(#{username},#{password},#{contact_name},#{phone},#{email},#{merchant_name},#{company_name},#{company_introduction},#{gis_location},#{business_scope},#{company_location},#{company_area},#{legal_person},#{company_code},#{company_picture},#{create_date},#{area_code},#{merchant_id})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn = "id")
    int insertWaitForCheckingMerchant(WaitForCheckingMerchant waitForCheckingMerchant);

    // 获取指定状态的merchant_id列表
    String sql_findAllMerchantIDByActivation_codeAndAreaCode =
            " from wait_for_checking_merchant_t " +
                    "where activation_code=#{activation_code} " +
                    "and has_expired=0 and area_code like concat(#{area_code},'%') ";
    @Select("select merchant_id " + sql_findAllMerchantIDByActivation_codeAndAreaCode)
    List<Integer> findAllMerchantIDByActivationCodeAndAreaCode(@Param("activation_code") int activation_code,
                                                                @Param("area_code")String area_code);
    @Select("select merchant_id " + sql_findAllMerchantIDByActivation_codeAndAreaCode + SqlUtil.PAGE_LIMIT_SQL)
    List<Integer> findAllMerchantIDByActivationCodeAndAreaCodeWithPage(@Param("activation_code") int activation_code,
                                                                       @Param("area_code")String area_code,
                                                                       @Param("limit") int limit,
                                                                       @Param("offset") int offset);
    @Select(SqlUtil.COUNT_SQL + sql_findAllMerchantIDByActivation_codeAndAreaCode)
    int getTotal_findAllMerchantIDByActivationCodeAndAreaCode(@Param("activation_code") int activation_code,
                                                               @Param("area_code")String area_code);

    // 通过指定merchant_id获取商户审核信息
    @Select("select * from wait_for_checking_merchant_t where merchant_id=#{merchant_id} and has_expired=0 limit 1")
    WaitForCheckingMerchant findWaitForCheckingMerchantByMerchantId(@Param("merchant_id") int merchant_id);

    // 通过指定merchant_id获取商户未审核的审核信息
    @Select("select * from wait_for_checking_merchant_t " +
            "where merchant_id=#{merchant_id} and has_expired=0 and activation_code=0")
    WaitForCheckingMerchant findNoCheckedInfoByMerchant(@Param("merchant_id")int merchant_id);

    @Select("select * from wait_from_checking_merchant_t where id=#{id}")
    WaitForCheckingMerchant findWaitForCheckingMerchantById(@Param("id")int id);

    // 设置指定商户信息审核状态(0:未审核；1：通过审核；2：不通过审核)
    @Update("update wait_for_checking_merchant_t "+
            "set activation_code=#{activation_code} "+
            "where merchant_id=#{merchant_id} and has_expired=0")
    int setActivationMerchant(@Param("merchant_id")int merchant_id,
                              @Param("activation_code")int activation_code);

    //设置指定商户的审核信息过期状态
    @Update("update wait_for_checking_merchant_t " +
            "set has_expired=1 " +
            "where merchant_id=#{merchant_id}")
    int setExpired(@Param("merchant_id")int merchant_id);

    // 删除指定商户的审核信息
    @Delete("delete from wait_for_checking_merchant_t where merchant_id=#{merchant_id}")
    int deleteWaitForCheckingMerchantByMerchantID(int merchant_id);
}
