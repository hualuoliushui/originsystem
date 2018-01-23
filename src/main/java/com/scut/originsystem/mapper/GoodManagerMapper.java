package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.GoodManager;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "goodManagerMapper")

public interface GoodManagerMapper {
    //添加商品管理信息
    @Insert("insert into good_managerment_t(qrcode_left,good_id) " +
            "values(0,#{good_id})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn = "id")
    int insetGoodManagerment(@Param("good_id") int good_id);

    //使用1个二维码
    @Update("update good_managerment_t " +
            "set qrcode_left = qrcode_left - 1 " +
            "where good_id = #{good_id}")
    int useOneQRCode(@Param("good_id") int good_id);

    //购买N个二维码
    @Update("update good_managerment_t " +
            "set qrcode_left = qrcode_left + #{number} " +
            "where good_id = #{good_id}")
    int buyQRCode(@Param("good_id") int good_id,
                  @Param("number") int number);

    //销售增加数量
    @Update("update good_managerment_t " +
            "set sell_number = sell_number + #{number} " +
            "where good_id = #{good_id}")
    int sellGood(@Param("number") int number,
                  @Param("good_id") int good_id);

    //查询商品管理信息
    @Select("select * from good_managerment_t where good_id = #{good_id}")
    GoodManager findGoodManagerByGoodId(@Param("good_id") int good_id);

    //删除
    String sql_delete = "update good_managerment_t set deleted_code=1 ";
    @Delete(sql_delete+
            "where good_id = #{good_id}")
    int deleteGoodManager(@Param("good_id") int good_id);
}
