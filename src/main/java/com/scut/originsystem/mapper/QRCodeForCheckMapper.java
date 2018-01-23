package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.QRCodeForCheck;
import com.scut.originsystem.util.SqlUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "qRCodeForCheckMapper")
public interface QRCodeForCheckMapper {
    //提交申请表
    @Insert("insert into qrcode_for_check(" +
            "merchant_id," +
            "merchant_name," +
            "good_id," +
            "qrcode_buy_number," +
            "create_date" +
            ") values(" +
            "#{merchant_id}," +
            "#{merchant_name}," +
            "#{good_id}," +
            "#{qrcode_buy_number}," +
            "#{create_date}" +
            ")")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn = "id")
    int insertQRCodeCheck(QRCodeForCheck qrCodeForCheck);

    //查看有没有需要审核的二维码申请表
    String sql_findUncheckedQRCode = " from qrcode_for_check where check_state = 0 ";
    @Select("select * " + sql_findUncheckedQRCode)
    @Results({
            @Result(column = "good_id", property = "good_id"),
            @Result(property = "good", column = "good_id", many =
            @Many(select = "com.scut.originsystem.mapper.GoodMapper.findGoodsById"))
    })
    List<QRCodeForCheck> findUncheckedQRCode();

    @Select("select * " + sql_findUncheckedQRCode + SqlUtil.PAGE_LIMIT_SQL)
    @Results({
            @Result(column = "good_id", property = "good_id"),
            @Result(property = "good", column = "good_id", many =
            @Many(select = "com.scut.originsystem.mapper.GoodMapper.findGoodsById"))
    })
    List<QRCodeForCheck> findUncheckedQRCodeWithPage(@Param("limit") int limit,
                                                     @Param("offset") int offset);

    @Select(SqlUtil.COUNT_SQL + sql_findUncheckedQRCode)
    int getTotal_findUncheckedQRCode();

    //审核二维码申请表
    @Update("update qrcode_for_check " +
            "set check_state = #{check_state}," +
            "check_man_id = #{check_man_id}," +
            "check_detail = #{check_detail}," +
            "check_date = #{check_date} " +
            "where id = #{id}")
    int check(@Param("check_state") int check_state,
              @Param("check_man_id") int check_man_id,
              @Param("check_detail") String check_detail,
              @Param("check_date") String check_date,
              @Param("id") int id);

    //查看某个二维码申请表
    @Select("select * from qrcode_for_check where id = #{id}")
    @Results({
            @Result(column = "good_id", property = "good_id"),
            @Result(property = "good", column = "good_id", many =
            @Many(select = "com.scut.originsystem.mapper.GoodMapper.findGoodsById"))
    })
    QRCodeForCheck findOne(@Param("id") int id);

    //查看某商家有没有待审批的表
    String sql_findOneByMerchant = " from qrcode_for_check where merchant_id = #{merchant_id} and check_state = 0 ";
    @Select("select * " + sql_findOneByMerchant)
    @Results({
            @Result(column = "good_id", property = "good_id"),
            @Result(property = "good", column = "good_id", many =
            @Many(select = "com.scut.originsystem.mapper.GoodMapper.findGoodsById"))
    })
    List<QRCodeForCheck> findOneByMerchant(@Param("merchant_id") int merchant_id);

    @Select("select * " + sql_findOneByMerchant + SqlUtil.PAGE_LIMIT_SQL)
    @Results({
            @Result(column = "good_id", property = "good_id"),
            @Result(property = "good", column = "good_id", many =
            @Many(select = "com.scut.originsystem.mapper.GoodMapper.findGoodsById"))
    })
    List<QRCodeForCheck> findOneByMerchantWithPage(@Param("merchant_id") int merchant_id,
                                                   @Param("limit") int limit,
                                                   @Param("offset") int offset);

    @Select(SqlUtil.COUNT_SQL+sql_findOneByMerchant)
    int getTotal_findOneByMerchant(@Param("merchant_id") int merchant_id);

    //查看某商品有没有待审批的表
    String sql_findOneByGood = " from qrcode_for_check where good_id = #{good_id} and check_state = 0 ";
    @Select("select * " + sql_findOneByGood)
    @Results({
            @Result(column = "good_id", property = "good_id"),
            @Result(property = "good", column = "good_id", many =
            @Many(select = "com.scut.originsystem.mapper.GoodMapper.findGoodsById"))
    })
    List<QRCodeForCheck> findOneByGood(@Param("good_id") int good_id);

    @Select("select * " + sql_findOneByGood + SqlUtil.PAGE_LIMIT_SQL)
    @Results({
            @Result(column = "good_id", property = "good_id"),
            @Result(property = "good", column = "good_id", many =
            @Many(select = "com.scut.originsystem.mapper.GoodMapper.findGoodsById"))
    })
    List<QRCodeForCheck> findOneByGoodWithPage(@Param("good_id") int good_id,
                                               @Param("limit") int limit,
                                               @Param("offset") int offset);

    @Select(SqlUtil.COUNT_SQL + sql_findOneByGood)
    int getTotal_findOneByGood(@Param("good_id") int good_id);

    //查看商户的二维码审核列表，如果状态为0，就是看所有。
    @SelectProvider(type = QRCodeForCheckMapperProvider.class, method = "getMyCheck")
    @Results({
            @Result(column = "good_id", property = "good_id"),
            @Result(property = "good", column = "good_id", many =
            @Many(select = "com.scut.originsystem.mapper.GoodMapper.findGoodsById"))
    })
    List<QRCodeForCheck> getMyCheck(@Param("merchant_id") int merchant_id,
                                    @Param("check_state") int check_state,
                                    @Param("offset") int offset);

    @SelectProvider(type = QRCodeForCheckMapperProvider.class, method = "getMyCheckCount")
    int getMyCheckCount(@Param("merchant_id") int merchant_id,
                        @Param("check_state") int check_state);
}
