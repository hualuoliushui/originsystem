package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.QRCode;
import com.scut.originsystem.returnentity.QRCodeCount;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "qRCodeMapper")

public interface QRCodeMapper {
    //申请二维码信息
    @Insert("insert into qrcode_t(qrcode_url,qrcode_valid,good_id) " +
            "values(#{qrcode_url},#{qrcode_valid},#{good_id})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn = "id")
    int insertQRCode(@Param("qrcode_url") String qrcode_url,
                     @Param("qrcode_valid") String qrcode_valid,
                     @Param("good_id") int good_id);

    //查找二维码信息
    @Select("select * from qrcode_t where qrcode_url = #{qrcode_url}")
    @Results({
            @Result(id=true,column = "id",property = "id"),
            @Result(property = "scanInfoList",column = "id",many =
            @Many(select = "com.scut.originsystem.mapper.QRCodeScanInfoMapper.find"))
    })
    QRCode findQRCodeByUrl(@Param("qrcode_url") String qrcode_url);

    //查找二维码的验证码
    @Select("select qrcode_valid from qrcode_t where qrcode_url = #{qrcode_url}")
    @Results({
            @Result(id=true,column = "id",property = "id"),
            @Result(property = "scanInfoList",column = "id",many =
            @Many(select = "com.scut.originsystem.mapper.QRCodeScanInfoMapper.find"))
    })
    QRCode findQRCodeValid(@Param("qrcode_url") String qrcode_url);

    //用good_id查找二维码
    @Select("select * from qrcode_t where good_id = #{good_id}")
    @Results({
            @Result(id=true,column = "id",property = "id"),
            @Result(property = "scanInfoList",column = "id",many =
            @Many(select = "com.scut.originsystem.mapper.QRCodeScanInfoMapper.find"))
    })
    List<QRCode> findQRCodeByGoodId(@Param("good_id") int good_id);

    //扫描次数
    @Update("update qrcode_t " +
            "set scan_time = scan_time +1 " +
            "where qrcode_url = #{qrcode_url}")
    int scanQRCode(@Param("qrcode_url") String qrcode_url);

    //第一次扫描时间
    @Update("update qrcode_t " +
            "set first_scan = #{first_scan} " +
            "where qrcode_url = #{qrcode_url}")
    int firstScanAddTime(@Param("qrcode_url") String qrcode_url,
                         @Param("first_scan") String first_scan);

    //删除
    @Delete("delete from qrcode_t where good_id = #{good_id}")
    int delteQRCode(@Param("good_id") int good_id);

    @Select(" SELECT " +
            "   PRO.description AS province,     " +
            "   CITY.city_name AS city,     " +
            "   CITY.district_name AS district,     " +
            "   CITY.qrcode_total,     " +
            "   CITY.origin_code AS area_code     " +
            " FROM     " +
            "   area_t AS PRO,     " +
            "   (     " +
            "     SELECT     " +
            "       AREA.description AS city_name,     " +
            "       TEMP.district_name,     " +
            "       TEMP.qrcode_total,     " +
            "       AREA.belong_to_code,     " +
            "       TEMP.origin_code     " +
            "     FROM     " +
            "       area_t AS AREA,     " +
            "       (SELECT     " +
            "          COALESCE(SUM(GOOD.qrcode_totle),0) AS qrcode_total,     " +
            "          DISTRICT.code AS district_code,     " +
            "          DISTRICT.belong_to_code AS belong_to_code,     " +
            "          DISTRICT.description AS district_name,     " +
            "          DISTRICT.code AS origin_code     " +
            "        FROM     " +
            "          good_info_t AS GOOD,     " +
            "          (SELECT " +
            "             * " +
            "           FROM " +
            "             area_t " +
            "           WHERE " +
            "             code LIKE CONCAT(#{code},'%') AND type = 3) AS DISTRICT " +
            "        WHERE     " +
            "          merchant_id IN (     " +
            "            SELECT merchant_id FROM company_t WHERE area_code = DISTRICT.code     " +
            "          )     " +
            "       GROUP BY     " +
            "         origin_code) AS TEMP     " +
            "     WHERE AREA.code = TEMP.belong_to_code     " +
            "   ) AS CITY     " +
            " WHERE PRO.code = CITY.belong_to_code ")
    List<QRCodeCount> findQRCodeTotalByArea(@Param("code") String code);

    @Select("SELECT " +
            "  COALESCE(SUM(qrcode_left),0) AS qrcode_left " +
            "FROM " +
            "  good_managerment_t " +
            "WHERE " +
            "  good_id IN ( " +
            "    SELECT " +
            "      id " +
            "    FROM good_info_t " +
            "    WHERE " +
            "      merchant_id IN ( " +
            "        SELECT " +
            "          merchant_id " +
            "        FROM " +
            "          company_t " +
            "        WHERE " +
            "          area_code = #{area_code} " +
            "      ) " +
            "  )")
    int findQRCodeLeftByArea(@Param("area_code") String area_code);

}
