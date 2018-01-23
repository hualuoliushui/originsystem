package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.DetectSample;
import com.scut.originsystem.util.SqlUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "detectSampleMapper")
public interface DetectSampleMapper {
    @Insert("insert into detect_sample_t " +
            "(detect_code,detect_project,detect_var,detect_unit,detect_ceiling,detect_bottom,detect_method,merchant_id) " +
            "values(#{detect_code},#{detect_project},#{detect_var},#{detect_unit},#{detect_ceiling},#{detect_bottom},#{detect_method},#{merchant_id})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn = "id")
    int insertDetectSample(DetectSample detectSample);

    @Delete("delete from detect_sample_t where id=#{id}")
    int deleteDetectSample(@Param("id")int id);

    @Update("update detect_sample_t " +
            "set " +
            "detect_code=#{detect_code}," +
            "detect_project=#{detect_project}," +
            "detect_var=#{detect_var}," +
            "detect_unit=#{detect_unit}," +
            "detect_ceiling=#{detect_ceiling}," +
            "detect_bottom=#{detect_bottom}," +
            "detect_method=#{detect_method} " +
            "where id=#{id}")
    int updateDetectSample(DetectSample detectSample);

    String sql_findAllDetectSample = " from detect_sample_t ";
    @Select("select * " + sql_findAllDetectSample)
    List<DetectSample> findAllDetectSample();
    @Select("select * " + sql_findAllDetectSample + SqlUtil.PAGE_LIMIT_SQL)
    List<DetectSample> findAllDetectSampleWithPage(@Param("limit") int limit,
                                                   @Param("offset") int offset);
    @Select(SqlUtil.COUNT_SQL + sql_findAllDetectSample)
    int getTotal_findAllDetectSample();

    String sql_findDetectSamplesByMerchant = " from detect_sample_t " +
            "where merchant_id=#{merchant_id} ";
    @Select("select * " + sql_findDetectSamplesByMerchant)
    List<DetectSample> findDetectSamplesByMerchant(@Param("merchant_id")int merchant_id);
    @Select("select * " + sql_findDetectSamplesByMerchant + SqlUtil.PAGE_LIMIT_SQL)
    List<DetectSample> findDetectSamplesByMerchantWithPage(@Param("merchant_id")int merchant_id,
                                                   @Param("limit") int limit,
                                                   @Param("offset") int offset);
    @Select(SqlUtil.COUNT_SQL + sql_findDetectSamplesByMerchant)
    int getTotal_findDetectSamplesByMerchant(@Param("merchant_id")int merchant_id);

}