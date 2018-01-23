package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.QRCodeScanInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "qRCodeScanInfoMapper")
public interface QRCodeScanInfoMapper {
    @Insert("insert into qrcode_scan_info(" +
            "scan_location," +
            "scan_date," +
            "qrcode_id" +
            ") " +
            "values (" +
            "#{scan_location}," +
            "#{scan_date}," +
            "#{qrcode_id}" +
            ")")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn = "id")
    int insertQRCodeScanInfo (QRCodeScanInfo qrCodeScanInfo);

    @Select("select * from qrcode_scan_info where qrcode_id = #{qrcode_id}")
    List<QRCodeScanInfo> find(@Param("qrcode_id") int qrcode_id);
}
