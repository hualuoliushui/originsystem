package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.Area;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "areaMapper")
public interface AreaMapper {
    String tableName = "area_t";

    @Select("select * from " + tableName + " where type=#{type}")
    List<Area> getAreaByType(@Param("type")int type);

    @Select("select * from " + tableName + " where type=#{type} and belong_to_code like concat(#{belong_to_code},'%')")
    List<Area> getAreasByTypeBelongToCode(@Param("type") int type,
                                          @Param("belong_to_code") String belong_to_code);

    @Select("SELECT *  " +
            "FROM " + tableName + "  " +
            "WHERE `code` IN " +
            "( " +
            "SELECT DISTINCT(SUBSTRING(c.area_code,1,#{type}*2)) " +
            "FROM merchant_t AS m join company_t AS c on m.id=c.merchant_id join user_t AS u on m.user_id=u.id " +
            "WHERE LENGTH(c.area_code)=6 AND u.activation_code<>4 AND c.area_code like concat(#{user_area_code},'%') " +
            "GROUP BY c.area_code " +
            ")  " +
            "AND " +
            "belong_to_code LIKE CONCAT(#{belong_to_code},'%')")
    List<Area> getAreasByTypeBelongToCode_merchant(@Param("user_area_code")String user_area_code,
                                                   @Param("type") int type,
                                                   @Param("belong_to_code") String belong_to_code);
}
