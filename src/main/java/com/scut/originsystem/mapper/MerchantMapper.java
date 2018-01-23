package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.Merchant;
import com.scut.originsystem.util.SqlUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "merchantMapper")

public interface MerchantMapper {
    String company_t = "company_t";
    String merchant_t = "merchant_t";
    String user_t = "user_t";

    String sql_select_field = " m.id,m.merchant_name,m.create_date,m.user_id,m.activation_code,u.username ";
    String sql_merchant_join_company_join_user = " " + merchant_t + " as m join " + company_t + " as c on m.id=c.merchant_id " +
            "join " + user_t + " as u on m.user_id=u.id ";
    String sql_merchant_join_user = " " + merchant_t + " as m join " + user_t + " as u on m.user_id=u.id ";

    //插入商户
    @Insert("insert into merchant_t(merchant_name,create_date,user_id) " +
            "values(#{merchant_name},#{create_date},#{user_id})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertMerchant(Merchant merchant);

    //修改商户名称
    @Update("update merchant_t " +
            "set merchant_name = #{merchant_name} " +
            "where user_id = #{user_id}")
    int modifyMerchant(@Param("merchant_name") String merchant_name,
                       @Param("user_id") int user_id);

    //修改商户激活码
    @Update("update merchant_t " +
            "set activation_code = #{activation_code} " +
            "where user_id = #{user_id}")
    int setActivationCodeByUserId(@Param("activation_code") int activation_code,
                                  @Param("user_id") int user_id);

    //修改商户激活码
    @Update("update merchant_t " +
            "set activation_code = #{activation_code} " +
            "where id = #{id}")
    int setActivationCodeById(@Param("activation_code") int activation_code,
                              @Param("id") int id);

    //通过商户名、公司区域模糊查找
    @SelectProvider(type = SqlProvider.class, method = "merchantCondition")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(property = "company", column = "id", many =
            @Many(select = "com.scut.originsystem.mapper.CompanyMapper.find")),
            @Result(property = "videoDeviceList", column = "id", many =
            @Many(select = "com.scut.originsystem.mapper.VideoDeviceMapper.find")),
            @Result(property = "detectionDeviceList", column = "id", many =
            @Many(select = "com.scut.originsystem.mapper.DetectionDeviceMapper.find"))
    })
    List<Merchant> conditionMerchant(@Param("area_code") String area_code,
                                     @Param("merchant_name") String merchant_name,
                                     @Param("company_area") String company_area,
                                     @Param("limit") int limit,
                                     @Param("offset") int offset);

    @SelectProvider(type = SqlProvider.class, method = "count_merchantCondition")
    int getTotal_conditionMerchant(@Param("area_code") String area_code,
                                   @Param("merchant_name") String merchant_name,
                                   @Param("company_area") String company_area);

    //查找商户
    @Select("select " + sql_select_field + " from " + sql_merchant_join_user +" where m.user_id = #{user_id}")
    Merchant findMerchantByUserId(@Param("user_id") int user_id);

    //查找商户
    @Select("select " + sql_select_field + " from "+sql_merchant_join_user+" where m.id = #{id}")
    Merchant findMerchantById(@Param("id") int id);

    @Select("select " + sql_select_field + " from " + sql_merchant_join_user +
            "where m.merchant_name like concat('%',#{merchant_name},'%') and u.activation_code<>4 ")
    List<Merchant> findMerchantByMerchantName(@Param("merchant_name") String merchant_name);

    //查找所有已激活的商户
    String sql_findAcitvativedMerchant = " from " + sql_merchant_join_company_join_user +
            "where u.activation_code = 1 and c.area_code like concat(#{area_code},'%') ";

    @Select("select " + sql_select_field + sql_findAcitvativedMerchant)
    List<Merchant> findAcitvativedMerchant(@Param("area_code") String area_code);

    @Select("select " + sql_select_field + sql_findAcitvativedMerchant + SqlUtil.PAGE_LIMIT_SQL)
    List<Merchant> findAcitvativedMerchantWithPage(@Param("area_code") String area_code,
                                                   @Param("limit") int limit,
                                                   @Param("offset") int offset);

    @Select(SqlUtil.COUNT_SQL + sql_findAcitvativedMerchant)
    int getTotal_findAcitvativedMerchant(@Param("area_code") String area_code);

    //查找所有商户
    String sql_findAllMerchant = " from " + sql_merchant_join_company_join_user +
            "where u.activation_code<>4 and c.area_code like concat(#{area_code},'%') ";

    @Select("select " + sql_select_field + sql_findAllMerchant)
    List<Merchant> findAllMerchant(@Param("area_code") String area_code);

    @Select("select " + sql_select_field + sql_findAllMerchant + SqlUtil.PAGE_LIMIT_SQL)
    List<Merchant> findAllMerchantWithPage(@Param("area_code") String area_code,
                                           @Param("limit") int limit,
                                           @Param("offset") int offset);

    @Select(SqlUtil.COUNT_SQL + sql_findAllMerchant)
    int getTotal_findAllMerchant(@Param("area_code") String area_code);

    //查找未激活的未审核的商户列表
    String sql_findAllNoActiveMerchant = " from " + sql_merchant_join_company_join_user +
            "where u.activation_code=0 " +
            "and u.check_state=0 " +
            "and c.area_code like concat(#{area_code},'%') ";

    @Select("select " + sql_select_field + sql_findAllNoActiveMerchant)
    List<Merchant> findAllNoActiveMerchant(@Param("area_code") String area_code);

    @Select("select " + sql_select_field + sql_findAllNoActiveMerchant
            + SqlUtil.PAGE_LIMIT_SQL)
    List<Merchant> findAllNoActiveMerchantWithPage(@Param("area_code") String area_code,
                                                   @Param("limit") int limit,
                                                   @Param("offset") int offset);

    @Select(SqlUtil.COUNT_SQL + sql_findAllNoActiveMerchant)
    int getTotal_findAllNoActiveMerchant(@Param("area_code") String area_code);

    // 通过区域码模糊查找
    String sql_findMerchantsByArea = " from " + sql_merchant_join_company_join_user +
            "where c.area_code like concat(#{area_code},'%') " +
            "and u.activation_code<>4 ";

    @Select("select " + sql_select_field + sql_findMerchantsByArea)
    List<Merchant> findMerchantsByArea(@Param("area_code") String area_code);

    @Select("select " + sql_select_field + sql_findMerchantsByArea + SqlUtil.PAGE_LIMIT_SQL)
    List<Merchant> findMerchantsByAreaWithPage(@Param("area_code") String area_code,
                                               @Param("limit") int limit,
                                               @Param("offset") int offset);

    @Select(SqlUtil.COUNT_SQL + sql_findMerchantsByArea)
    int getTotal_findMerchantsByArea(@Param("area_code") String area_code);

    @SelectProvider(type=MerchantMapperProvider.class,method = "findMerchantsByAreaCodeOrCreateDateOrActivationCode")
    List<Merchant> findMerchantsByAreaCodeOrCreateDateOrActivationCode(@Param("user_area_code")String user_area_code,
                                                                       @Param("area_code")String area_code,
                                                                       @Param("create_date_start")String create_date_start,
                                                                       @Param("create_date_end")String create_date_end,
                                                                       @Param("activation_code")int activation_code);
    @SelectProvider(type=MerchantMapperProvider.class,method = "findMerchantsByAreaCodeOrCreateDateOrActivationCodeWithPage")
    List<Merchant> findMerchantsByAreaCodeOrCreateDateOrActivationCodeWithPage(@Param("user_area_code")String user_area_code,
                                                                               @Param("area_code")String area_code,
                                                                               @Param("create_date_start")String create_date_start,
                                                                               @Param("create_date_end")String create_date_end,
                                                                               @Param("activation_code")int activation_code,
                                                                               @Param("limit")int limit,
                                                                               @Param("offset")int offset);
    @SelectProvider(type=MerchantMapperProvider.class,method = "getTotal_findMerchantsByAreaCodeOrCreateDateOrActivationCode")
    int getTotal_findMerchantsByAreaCodeOrCreateDateOrActivationCode(@Param("user_area_code")String user_area_code,
                                                                     @Param("area_code")String area_code,
                                                                     @Param("create_date_start")String create_date_start,
                                                                     @Param("create_date_end")String create_date_end,
                                                                     @Param("activation_code")int activation_code);

    @Select("SELECT COALESCE(SUM(qrcode_totle),0) " +
            "FROM good_info_t " +
            "WHERE merchant_id = (" +
            "SELECT id FROM merchant_t WHERE user_id = #{user_id}" +
            ") and good_info_t.deleted_code<>1 ")
    int getMerchantQRcodeTotal(@Param("user_id") int user_id);

    @Select("SELECT COALESCE(SUM(qrcode_left),0) " +
            "FROM good_managerment_t " +
            "WHERE good_id IN (" +
            "  SELECT id FROM good_info_t WHERE merchant_id = (" +
            "    SELECT id FROM merchant_t WHERE user_id = #{user_id}" +
            "  ) and good_info_t.deleted_code<>1 " +
            ") and good_managerment_t.deleted_code<>1 ")
    int getMerchantQRcodeLeft(@Param("user_id") int user_id);

}
