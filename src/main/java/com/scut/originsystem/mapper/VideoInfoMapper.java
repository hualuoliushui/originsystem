package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.VideoInfo;
import com.scut.originsystem.receiveentity.VideoInfoConditions;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "videoInfoMapper")
public interface VideoInfoMapper {
    //插入视频信息
    @Insert("insert into video_info_t(video_code,video_description,video_location,video_name,video_url,device_id,good_id,merchant_id,create_date) " +
            "values(#{video_code},#{video_description},#{video_location},#{video_name},#{video_url},#{device_id},#{good_id},#{merchant_id},#{create_date})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn = "id")
    int insertVideoInfo(VideoInfo videoInfo);

    //查找视频信息
    @Select("select * from video_info_t where good_id = #{good_id} and deleted_code<>1  " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<VideoInfo> findVideoInfo(@Param("good_id") int good_id,
                                  @Param("limit") int limit,
                                  @Param("offset") int offset);

    @Select("select * from video_info_t where good_id = #{good_id} and deleted_code<>1  ")
    List<VideoInfo> findVideoInfo1(@Param("good_id") int good_id);

    @Select("select count(*) from video_info_t where good_id = #{good_id} and deleted_code<>1  ")
    int countVideoInfo(@Param("good_id") int good_id);


    @Select("select * from video_info_t where good_id = #{good_id} and deleted_code<>1  ")
    List<VideoInfo> findAllVideoInfo(@Param("good_id") int good_id);

    //查找视频信息
    @Select("select * from video_info_t where device_id = #{device_id} and deleted_code<>1  " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<VideoInfo> find(@Param("device_id") int device_id,
                         @Param("limit") int limit,
                         @Param("offset") int offset);

    @Select("select count(*) from video_info_t where device_id = #{device_id} and deleted_code<>1  ")
    int count(@Param("device_id") int device_id);

    //查找视频信息
    @Select("select * from video_info_t where merchant_id = #{merchant_id} and deleted_code<>1  " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<VideoInfo> findByMernchant(@Param("merchant_id") int merchant_id,
                                    @Param("limit") int limit,
                                    @Param("offset") int offset);

    @Select("select count(*) from video_info_t where merchant_id = #{merchant_id} and deleted_code<>1  ")
    int countByMernchant(@Param("merchant_id") int merchant_id);

    //delete
    String sql_delete = "update video_info_t set deleted_code=1 ";
    @Update(sql_delete +
            "where id = #{id}")
    int deleteVideoInfo(@Param("id") int id);

    @Update(sql_delete +
            "where good_id = #{good_id}")
    int deleteVideoInfoByGood(@Param("good_id") int good_id);

    @Update(sql_delete+
            "where device_id = #{device_id}")
    int deleteVideoInfoByDevice(@Param("device_id") int device_id);

    @Update(sql_delete+
            "where merchant_id = #{merchant_id}")
    int deleteVideoInfoByMerchant(@Param("merchant_id") int merchant_id);

    //修改视频信息
    @Update("update video_info_t " +
            "set " +
            "video_code=#{video_code},video_description=#{video_description}," +
            "video_location=#{video_location},video_name=#{video_name}," +
            "video_url=#{video_url} " +
            "where id=#{id}")
    int updateVideoInfo(VideoInfo videoInfo);

    //通过某些字段模糊查询
    @SelectProvider(type = VideoInfoMapperProvider.class, method = "findVideoInfoConditions")
    List<VideoInfo> findVideoInfoConditions(VideoInfoConditions videoInfoConditions);
    @SelectProvider(type = VideoInfoMapperProvider.class, method = "getTotal_findVideoInfoConditions")
    int getTotal_findVideoInfoConditions(VideoInfoConditions videoInfoConditions);
}
