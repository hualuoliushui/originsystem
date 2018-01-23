package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.Promotion;
import com.scut.originsystem.returnentity.Promotion_v;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "promotionMapper")
public interface PromotionMapper {
    String viewName = "promotion_info_v";
    //添加
    @Insert("insert into promotion_info_t(" +
            "promotion_theme," +
            "promotion_content," +
            "promotion_picture," +
            "valid_start_date," +
            "valid_end_date," +
            "create_date," +
            "type_id," +
            "merchant_id" +
            ") values(" +
            "#{promotion_theme}," +
            "#{promotion_content}," +
            "#{promotion_picture}," +
            "#{valid_start_date}," +
            "#{valid_end_date}," +
            "#{create_date}," +
            "#{type_id}," +
            "#{merchant_id}" +
            ")")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn = "id")
    int insertPromotion(Promotion promotion);

    //删除
    @Update("update promotion_info_t set deleted_code=1 " +
            "where id=#{id}")
    int deletePromotion(@Param("id")int id);

    @Update("update promotion_info_t set deleted_code=1 " +
            "where type_id = #{type_id}")
    int deletePromotionByGoodType(@Param("type_id") int type_id);

    @Update("update promotion_info_t set deleted_code=1 " +
            "where merchant_id = #{merchant_id}")
    int deletePromotionByMerchant(@Param("merchant_id") int merchant_id);

    //更新促销信息
    @Update("update promotion_info_t " +
            "set promotion_theme=#{promotion_theme},promotion_content=#{promotion_content},valid_start_date=#{valid_start_date},valid_end_date=#{valid_end_date},promotion_picture=#{promotion_picture} " +
            "where id=#{id}")
    public int updatePromotion(Promotion promotion);

    //查询促销信息
    @Select("select * from "+viewName+" where id=#{id}")
    Promotion_v findPromotionById(@Param("id")int id);

    @Select("select * from "+viewName+" where type_id = #{type_id} and deleted_code<>1 " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<Promotion_v> findPage(@Param("type_id") int type_id,
                             @Param("limit") int limit,
                             @Param("offset") int offset);

    @Select("select * from "+viewName+" where type_id = #{type_id} and deleted_code<>1 ")
    List<Promotion_v> find(@Param("good_id") int type_id);

    @Select("select count(*) from "+viewName+" where type_id = #{type_id} and deleted_code<>1 ")
    int count(@Param("type_id") int type_id);

    @Select("select * from "+viewName+" where merchant_id=#{merchant_id} and deleted_code<>1  " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<Promotion_v> findByMerchant(@Param("merchant_id") int merchant_id,
                                   @Param("limit") int limit,
                                   @Param("offset") int offset);

    @Select("select count(*) from "+viewName+" where merchant_id=#{merchant_id} and deleted_code<>1 ")
    int countByMerchant(@Param("merchant_id") int merchant_id);

    @Select("select * from "+viewName+" where deleted_code<>1 " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<Promotion_v> findAllPromotion(@Param("limit") int limit,
                                       @Param("offset") int offset);

    @Select("select count(*) from "+viewName+" where deleted_code<>1 ")
    int countAllPromotion();
}
