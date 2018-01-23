package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.GoodType;
import com.scut.originsystem.returnentity.GoodName_Num;
import com.scut.originsystem.returnentity.MerchantGoodType;
import com.scut.originsystem.util.SqlUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "goodTypeMapper")
public interface GoodTypeMapper {
    @Insert("insert into good_type_t(" +
            "good_code," +
            "produce_place," +
            "good_name," +
            "merchant_id," +
            "picture_1," +
            "picture_2," +
            "picture_3," +
            "picture_4," +
            "picture_5" +
            ") values(" +
            "#{good_code}," +
            "#{produce_place}," +
            "#{good_name}," +
            "#{merchant_id}," +
            "#{picture_1}," +
            "#{picture_2}," +
            "#{picture_3}," +
            "#{picture_4}," +
            "#{picture_5}" +
            ")")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn = "id")
    int insertGoodType(GoodType goodType);

    @Select("select * from good_type_t " +
            "where merchant_id = #{merchant_id} and deleted_code<>1 " +
            SqlUtil.PAGE_LIMIT_SQL)
    List<GoodType> findGoodTypeAll(@Param("merchant_id") int merchant_id,
                                   @Param("limit") int limit,
                                   @Param("offset") int offset);
    @Select("select count(*) from good_type_t " +
            "where merchant_id = #{merchant_id} and deleted_code<>1 ")
    int findGoodTypeAllAmount(@Param("merchant_id") int merchant_id);

    String sql_findGoodInventory = " from good_type_t where good_code=#{good_code} and good_name=#{good_name} and " +
            "produce_place=#{produce_place} and merchant_id=#{merchant_id} and deleted_code<>1 ";
    @Select("select IFNULL(MAX(id),-1) " + sql_findGoodInventory + " limit 1 ")
    int findId(@Param("good_code")String good_code,
                                 @Param("good_name")String good_name,
                                 @Param("produce_place")String produce_place,
                                 @Param("merchant_id") int merchant_id);

    @Select("select * from good_type_t " +
            "where deleted_code = 0 and merchant_id = #{merchant_id} and deleted_code<>1 " +
            SqlUtil.PAGE_LIMIT_SQL)
    List<GoodType> findGoodType(@Param("merchant_id") int merchant_id,
                                @Param("limit") int limit,
                                @Param("offset") int offset);
    @Select("select count(*) from good_type_t " +
            "where deleted_code = 0 and merchant_id = #{merchant_id} ")
    int findGoodTypeAmount(@Param("merchant_id") int merchant_id);

    @Select("select count(*) from good_type_t " +
            "where deleted_code = 0 and merchant_id = #{merchant_id} and good_code = #{good_code}")
    int findGoodTypeNumber(@Param("merchant_id") int merchant_id,
                     @Param("good_code") String good_code);

    @Select("select * from good_type_t " +
            "where id = #{id} ")
    GoodType findGoodTypeById(@Param("id") int id);

    String sql_delete = "update good_type_t set deleted_code=1 ";
    @Update(sql_delete +
            "where id = #{id}")
    int deleteGoodType(@Param("id") int id);
    @Update(sql_delete +
            "where merchant_id = #{merchant_id}")
    int deleteGoodTypeByMerchant(@Param("merchant_id") int merchant_id);

    @SelectProvider(type = GoodTypeMapperProvider.class, method = "selectiveSql")
    List<GoodType> selectiveGoodType(@Param("good_code") String good_code,
                                     @Param("produce_place") String produce_place,
                                     @Param("good_name") String good_name,
                                     @Param("merchant_id") int merchant_id,
                                     @Param("limit") int limit,
                                     @Param("offset") int offset);
    @SelectProvider(type = GoodTypeMapperProvider.class, method = "selectiveAmountSql")
    int selectiveAmount(@Param("good_code") String good_code,
                        @Param("produce_place") String produce_place,
                        @Param("good_name") String good_name,
                        @Param("merchant_id") int merchant_id);

    @SelectProvider(type = GoodTypeMapperProvider.class,method = "findGoodTypesByGoodNameOrGoodCode")
    List<MerchantGoodType> findGoodTypesByGoodNameOrGoodCode(@Param("user_area_code")String user_area_code,
                                                             @Param("good_name")String good_name,
                                                             @Param("good_code")String good_code,
                                                             @Param("merchant_name")String merchant_name);
    @SelectProvider(type = GoodTypeMapperProvider.class,method = "findGoodTypesByGoodNameOrGoodCodeWithPage")
    List<MerchantGoodType> findGoodTypesByGoodNameOrGoodCodeWithPage(@Param("user_area_code")String user_area_code,
                                                                     @Param("good_name")String good_name,
                                                                     @Param("good_code")String good_code,
                                                                     @Param("merchant_name")String merchant_name,
                                                                     @Param("limit") int limit,
                                                                     @Param("offset") int offset);
    @SelectProvider(type = GoodTypeMapperProvider.class,method = "getTotal_findGoodTypesByGoodNameOrGoodCode")
    int getTotal_findGoodTypesByGoodNameOrGoodCode(@Param("user_area_code")String user_area_code,
                                                   @Param("good_name")String good_name,
                                                   @Param("good_code")String good_code,
                                                   @Param("merchant_name")String merchant_name);
    @SelectProvider(type = GoodTypeMapperProvider.class,method = "getGoodNumWithGoodName_findGoodTypesByGoodNameOrGoodCode")
    List<GoodName_Num> getGoodNumWithGoodName_findGoodTypesByGoodNameOrGoodCode(@Param("user_area_code")String user_area_code,
                                                                                @Param("good_name")String good_name,
                                                                                @Param("good_code")String good_code,
                                                                                @Param("merchant_name")String merchant_name);

    @SelectProvider(type=GoodTypeMapperProvider.class,method = "findAllGoodTypes")
    List<MerchantGoodType> findAllGoodTypes(@Param("user_area_code")String user_area_code);
    @SelectProvider(type=GoodTypeMapperProvider.class,method = "findAllGoodTypesWithPage")
    List<MerchantGoodType> findAllGoodTypesWithPage(@Param("user_area_code")String user_area_code,
                                            @Param("limit") int limit,
                                            @Param("offset") int offset);
    @SelectProvider(type=GoodTypeMapperProvider.class,method = "getTotal_findAllGoodTypes")
    int getTotal_findAllGoodTypes(@Param("user_area_code")String user_area_code);
}
