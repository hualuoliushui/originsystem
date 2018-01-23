package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.Good;
import com.scut.originsystem.returnentity.GoodType_1;
import com.scut.originsystem.returnentity.Good_1;
import com.scut.originsystem.util.SqlUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component(value = "goodMapper")

public interface GoodMapper {
    //添加商品信息
    @Insert("insert into good_info_t(" +
            "type_code," +
            "produce_place," +
            "good_code," +
            "good_name," +
            "basic_info," +
            "good_batch," +
            "produce_date," +
            "pack_type," +
            "good_number," +
            "qrcode_totle," +
            "merchant_id," +
            "type_id," +
            "life_time," +
            "expiry_date " +
            ") " +
            "values(" +
            "#{type_code}," +
            "#{produce_place}," +
            "#{good_code}," +
            "#{good_name}," +
            "#{basic_info}," +
            "#{good_batch}," +
            "#{produce_date}," +
            "#{pack_type}," +
            "#{good_number}," +
            "0," +
            "#{merchant_id}," +
            "#{type_id}," +
            "#{life_time}," +
            "#{expiry_date} " +
            ")")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn = "id")
    int insertGood(Good good);

    //购买二维码
    @Update("update good_info_t " +
            "set qrcode_totle = qrcode_totle + #{number} " +
            "where id = #{id}")
    int buyQRCode(@Param("id") int id,
                  @Param("number") int number);

    //修改商品信息
    @Update("update good_info_t " +
            "set " +
            "good_code = #{good_code}," +
            "good_name = #{good_name}," +
            "basic_info = #{basic_info}," +
            "good_batch = #{good_batch}," +
            "produce_date = #{produce_date}," +
            "pack_type = #{pack_type}," +
            "good_number = #{good_number}," +
            "produce_place = #{produce_place}," +
            "life_time = #{life_time}," +
            "expiry_date = #{expiry_date}," +
            "type_code = #{type_code} " +
            "where id = #{id}")
    int updateGood(Good good);

    //删除商品信息
    String sql_delete = "update good_info_t set deleted_code=1 ";
    @Update(sql_delete+
            "where id = #{id}")
    int deleteGood(@Param("id") int id);
    @Update(sql_delete+
            "where type_id = #{type_id}")
    int deleteGoodByType(@Param("type_id") int type_id);
    @Update(sql_delete+
            "where merchant_id = #{merchant_id}")
    int deleteGoodByMerchant(@Param("merchant_id") int merchant_id);

    //通过merchant查找
    String sql_findGoodsByMerchantId = " from good_info_t where merchant_id = #{merchant_id} and deleted_code<>1 ";
    @Select("select * " + sql_findGoodsByMerchantId)
    @Results({
            @Result(id=true,column = "id",property = "id"),
            @Result(property = "qrCodeForCheck",column = "id",many =
            @Many(select = "com.scut.originsystem.mapper.QRCodeForCheckMapper.findOneByGood")),
            @Result(property = "goodManager",column = "id",many =
            @Many(select = "com.scut.originsystem.mapper.GoodManagerMapper.findGoodManagerByGoodId"))
    })
    List<Good> findGoodsByMerchantId(@Param("merchant_id") int merchant_id);
    @Select("select * " + sql_findGoodsByMerchantId + SqlUtil.PAGE_LIMIT_SQL)
    @Results({
            @Result(id=true,column = "id",property = "id"),
            @Result(property = "qrCodeForCheck",column = "id",many =
            @Many(select = "com.scut.originsystem.mapper.QRCodeForCheckMapper.findOneByGood")),
            @Result(property = "goodManager",column = "id",many =
            @Many(select = "com.scut.originsystem.mapper.GoodManagerMapper.findGoodManagerByGoodId"))
    })
    List<Good> findGoodsByMerchantIdWithPage(@Param("merchant_id") int merchant_id,
                                     @Param("limit") int limit,
                                     @Param("offset") int offset);
    @Select(SqlUtil.COUNT_SQL + sql_findGoodsByMerchantId)
    int getTotal_findGoodsByMerchantId(@Param("merchant_id") int merchant_id);

    String sql_findGoodsByType = " from good_info_t where type_code = #{type_code} and merchant_id = #{merchant_id} and deleted_code<>1 ";
    @Select("select * " + sql_findGoodsByType)
    List<Good> findGoodsByType(@Param("type_code") String type_code,
                               @Param("merchant_id") int merchant_id);
    @Select("select * " + sql_findGoodsByType+ SqlUtil.PAGE_LIMIT_SQL)
    List<Good> findGoodsByTypeWithPage(@Param("type_code") String type_code,
                                       @Param("merchant_id") int merchant_id,
                                       @Param("limit") int limit,
                                       @Param("offset") int offset);
    @Select(SqlUtil.COUNT_SQL + sql_findGoodsByType)
    int getTotal_findGoodsByType(@Param("type_code") String type_code,
                                 @Param("merchant_id") int merchant_id);

    String sql_findGoodsType = " from good_info_t where merchant_id = #{merchant_id} and deleted_code<>1 ";
    @Select("select distinct(type_code) " + sql_findGoodsType)
    List<String> findGoodsType(@Param("merchant_id") int merchant_id);

    @Select("select distinct(type_code) " + sql_findGoodsType + SqlUtil.PAGE_LIMIT_SQL)
    List<String> findGoodsTypeWithPage(@Param("merchant_id") int merchant_id,
                                       @Param("limit") int limit,
                                       @Param("offset") int offset);

    @Select("select count(distinct type_code) " + sql_findGoodsType)
    int getTotal_findGoodsType(@Param("merchant_id") int merchant_id);

    @Select("select count(*) from good_info_t where merchant_id = #{merchant_id} and good_code = #{good_code} and deleted_code<>1 ")
    int getGoodCertainType(@Param("merchant_id") int merchant_id,
                           @Param("good_code") String good_code);

    String sql_findGoodInventory = " from good_info_t where type_id = #{type_id} and deleted_code<>1 ";
    @Select("select * " + sql_findGoodInventory)
    List<Good> findGoodInventory(@Param("type_id")int type_id);
    @Select("select * " + sql_findGoodInventory + SqlUtil.PAGE_LIMIT_SQL)
    List<Good> findGoodInventoryWithPage(@Param("type_id")int type_id,
                                         @Param("limit") int limit,
                                         @Param("offset") int offset);
    @Select(SqlUtil.COUNT_SQL + sql_findGoodInventory)
    int getTotal_findGoodInventory(@Param("type_id")int type_id);

    String sql_findGoodInfoByMerchant = " from good_info_t where merchant_id=#{merchant_id} and deleted_code<>1 ";
    @Select("select distinct good_code,good_name,produce_place " + sql_findGoodInfoByMerchant)
    List<Good> findGoodInfoByMerchant(@Param("merchant_id")int merchant_id);
    @Select("select distinct good_code,good_name,produce_place " + sql_findGoodInfoByMerchant + SqlUtil.PAGE_LIMIT_SQL)
    List<Good> findGoodInfoByMerchantWithPage(@Param("merchant_id")int merchant_id,
                                              @Param("limit") int limit,
                                              @Param("offset") int offset);
    @Select("select count(distinct good_code,good_name,produce_place) " + sql_findGoodInfoByMerchant)
    int getTotal_findGoodInfoByMerchant(@Param("merchant_id")int merchant_id);

    String sql_findGoodInfoByGoodName = " from good_info_t " +
            "where merchant_id=#{merchant_id} and good_name like #{good_name} and deleted_code<>1 ";
    @Select("select distinct good_code,good_name,produce_place " + sql_findGoodInfoByGoodName)
    List<Good> findGoodInfoByGoodName(@Param("good_name")String good_name,
                                      @Param("merchant_id")int merchant_id);
    @Select("select distinct good_code,good_name,produce_place " + sql_findGoodInfoByGoodName)
    List<Good> findGoodInfoByGoodNameWithPage(@Param("good_name")String good_name,
                                              @Param("merchant_id")int merchant_id,
                                              @Param("limit") int limit,
                                              @Param("offset") int offset);
    @Select( "select count(distinct good_code,good_name,produce_place) " + sql_findGoodInfoByGoodName)
    int getTotal_findGoodInfoByGoodName(@Param("good_name")String good_name,
                                        @Param("merchant_id")int merchant_id);

    @Select("select * from good_info_t where id = #{id}")
    Good findGoodsById(@Param("id") int id);

    @Select("select distinct(good_name) from good_info_t where merchant_id = #{merchant_id} and deleted_code<>1 ")
    List<String> findGoodNames(@Param("merchant_id") int merchant_id);

    @Select("select COALESCE(SUM(qrcode_totle),0) from good_info_t where merchant_id = #{merchant_id} and deleted_code<>1 ")
    int countQRCode(@Param("merchant_id") int merchant_id);

    @Select("select COALESCE(SUM(good_number),0) from good_info_t where merchant_id = #{merchant_id} and deleted_code<>1 ")
    int countGoodNumber(@Param("merchant_id") int merchant_id);

    @Select("select COALESCE(SUM(qrcode_totle),0) from good_info_t where merchant_id = #{merchant_id} and good_name = #{good_name} and deleted_code<>1 ")
    int goodAndQRCodeTotal(@Param("merchant_id") int merchant_id,
                           @Param("good_name") String good_name);

    @Select("select COALESCE(SUM(good_number),0) from good_info_t where merchant_id = #{merchant_id} and good_name = #{good_name} and deleted_code<>1 ")
    int goodAndGoodTotal(@Param("merchant_id") int merchant_id,
                         @Param("good_name") String good_name);

    @Select("select * from good_info_t " +
            "where merchant_id = #{merchant_id} " +
            "and type_id = #{type_id} and deleted_code<>1 "+
            SqlUtil.PAGE_LIMIT_SQL)
    List<Good> findGoodByTypeId(@Param("merchant_id") int merchant_id,
                                @Param("type_id") int type_id,
                                @Param("limit") int limit,
                                @Param("offset") int offset);
    @Select("select count(*) from good_info_t " +
            "where merchant_id = #{merchant_id} " +
            "and type_id = #{type_id} and deleted_code<>1 ")
    int findGoodByTypeIdAmount(@Param("merchant_id") int merchant_id,
                                      @Param("type_id") int type_id);

    @SelectProvider(type = GoodMapperProvider.class, method = "selectiveSql")
    List<Good> selectiveGood(@Param("good_batch") String good_batch,
                             @Param("produce_place") String produce_place,
                             @Param("pack_type") String pack_type,
                             @Param("good_name") String good_name,
                             @Param("good_code") String good_code,
                             @Param("merchant_id") int merchant_id,
                             @Param("type_id")int type_id,
                             @Param("limit") int limit,
                             @Param("offset") int offset);
    @SelectProvider(type = GoodMapperProvider.class, method = "selectiveAmountSql")
    int selectiveAoumt(@Param("good_batch") String good_batch,
                       @Param("produce_place") String produce_place,
                       @Param("pack_type") String pack_type,
                       @Param("good_name") String good_name,
                       @Param("good_code") String good_code,
                       @Param("merchant_id") int merchant_id,
                       @Param("type_id")int type_id);

    @Select("select * from good_info_t where good_code like concat('%',#{good_code},'%') " +
            " and merchant_id=#{merchant_id} and deleted_code<>1 ")
    List<Good_1> findGood_1s(@Param("good_code")String good_code,
                             @Param("merchant_id")int merchant_id);

    @Select("select * from good_type_t where good_code like concat('%',#{good_code},'%') " +
            " and merchant_id=#{merchant_id} and deleted_code <> 1")
    List<GoodType_1> findGoodType_1s(@Param("good_code")String good_code,
                                     @Param("merchant_id")int merchant_id);

}
