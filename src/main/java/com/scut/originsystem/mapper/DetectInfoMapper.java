package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.DetectInfo;
import com.scut.originsystem.receiveentity.DetectInfoConditions;
import com.scut.originsystem.returnentity.DetectInfo_v;
import com.scut.originsystem.util.SqlUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "detectInfoMapper")
public interface DetectInfoMapper {
    String table_name = "detect_info_t";
    String view_name = "detect_info_v";

    //插入检测信息
    @Insert("insert into detect_info_t(detect_code,detect_name,detect_date,detect_unit,detect_explain,detect_operation,device_id,good_id,create_date,merchant_id,detect_result,detect_sample_id) " +
            "values(#{detect_code},#{detect_name},#{detect_date},#{detect_unit},#{detect_explain},#{detect_operation},#{device_id},#{good_id},#{create_date},#{merchant_id},#{detect_result},#{detect_sample_id})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn = "id")
    int insertDetectInfo(DetectInfo detectInfo);

    //查找检测信息
    @Select("select * from "+view_name+" where good_id = #{good_id} and deleted_code<>1 " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<DetectInfo_v> findDetectInfo(@Param("good_id") int good_id,
                                      @Param("limit") int limit,
                                      @Param("offset") int offset);

    @Select("select * from "+view_name+" where good_id = #{good_id}  and deleted_code<>1 " )
    List<DetectInfo_v> findDetectInfo1(@Param("good_id") int good_id);

    @Select("select count(*) from "+view_name+" where good_id = #{good_id}  and deleted_code<>1 ")
    int countDetectInfo(@Param("good_id") int good_id);

    //通过detect_sample查找
    String sql_findDetectInfoByDetectSample = " from " + view_name + " where " +
            "detect_sample_id=#{detect_sample_id}  and deleted_code<>1 ";
    @Select("select * " + sql_findDetectInfoByDetectSample)
    List<DetectInfo_v> findDetectInfoByDetectSample(@Param("detect_sample_id")int detect_sample_id);
    @Select("select * " + sql_findDetectInfoByDetectSample + SqlUtil.PAGE_LIMIT_SQL)
    List<DetectInfo_v> findDetectInfoByDetectSampleWithPage(@Param("detect_sample_id")int detect_sample_id,
                                                            @Param("limit") int limit,
                                                            @Param("offset") int offset);
    @Select(SqlUtil.COUNT_SQL + sql_findDetectInfoByDetectSample)
    int getTotal_findDetectInfoByDetectSample(@Param("detect_sample_id")int detect_sample_id);

    //通过某些字段模糊查询
    @SelectProvider(type = DetectInfoMapperProvider.class, method = "findDetectInfoConditions")
    List<DetectInfo_v> findDetectInfoConditions(DetectInfoConditions detectInfoConditions);
    @SelectProvider(type = DetectInfoMapperProvider.class, method = "getTotal_findDetectInfoConditions")
    int getTotal_findDetectInfoConditions(DetectInfoConditions detectInfoConditions);

    @Select("select * from "+view_name+" where good_id = #{good_id}  and deleted_code<>1 ")
    List<DetectInfo_v> findAllDetectInfo(@Param("good_id") int good_id);

    @Select("select * from "+view_name+" where device_id = #{device_id}  and deleted_code<>1 " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<DetectInfo_v> find(@Param("device_id") int device_id,
                            @Param("limit") int limit,
                            @Param("offset") int offset);

    @Select("select count(*) from "+view_name+" where device_id = #{device_id}  and deleted_code<>1 ")
    int count(@Param("device_id") int device_id);

    //查找检测信息
    @Select("select * from "+view_name+" where id = #{id}  and deleted_code<>1 ")
    DetectInfo_v findById(@Param("id") int id);

    //查找检测信息
    @SelectProvider(type = SqlProvider.class, method = "detectionCondition")
    List<DetectInfo_v> findByMerchant(@Param("merchant_id") String merchant_id,
                                      @Param("min_date") String min_date,
                                      @Param("max_date") String max_date,
                                      @Param("limit") String limit,
                                      @Param("offset") String offset);

    @SelectProvider(type = SqlProvider.class, method = "countDetectionCondition")
    int countByMerchant(@Param("merchant_id") String merchant_id,
                                    @Param("min_date") String min_date,
                                    @Param("max_date") String max_date);

    //删除
    String sql_delete = "update detect_info_t set deleted_code = 1 ";
    @Update(sql_delete+
            "where id = #{id}")
    int deleteDetectInfo(@Param("id") int id);

    @Update(sql_delete +
            "where good_id = #{good_id}")
    int deleteDetectInfoByGood(@Param("good_id") int good_id);

    @Update(sql_delete +
            "where device_id = #{device_id}")
    int deleteDetectInfoByDevice(@Param("device_id") int device_id);

    @Update(sql_delete +
            "where merchant_id = #{merchant_id}")
    int deleteDetectInfoByMerchant(@Param("merchant_id") int merchant_id);

    @Update(sql_delete +
            "where detect_sample_id= #{detect_sample_id}")
    int deleteDetectInfoByDetectSample(@Param("detect_sample_id") int detect_sample_id);

    //更改
    @Update("update detect_info_t " +
            "set " +
            "detect_code=#{detect_code}, " +
            "detect_name=#{detect_name}, " +
            "detect_date=#{detect_date}, " +
            "detect_unit=#{detect_unit}, " +
            "detect_explain=#{detect_explain}, " +
            "detect_operation=#{detect_operation}, " +
            "merchant_id=#{merchant_id}, " +
            "detect_result=#{detect_result} " +
            "where id=#{id}")
    int updateDetectInfo(DetectInfo detectInfo);
}
