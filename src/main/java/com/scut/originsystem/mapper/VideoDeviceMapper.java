package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.VideoDevice;
import com.scut.originsystem.enums.DeviceState;
import com.scut.originsystem.receiveentity.VideoDeviceConditions;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "videoDeviceMapper")
public interface VideoDeviceMapper {
    //insert
    @Insert("insert into vedio_device_t(" +
            "device_code," +
            "device_name," +
            "device_description," +
            "device_location," +
            "device_gis," +
            "device_brand," +
            "device_model," +
            "purchase_date," +
            "login_date," +
            "merchant_id," +
            "i_id" +
            ") values(" +
            "#{device_code}," +
            "#{device_name}," +
            "#{device_description}," +
            "#{device_location}," +
            "#{device_gis}," +
            "#{device_brand}," +
            "#{device_model}," +
            "#{purchase_date}," +
            "#{login_date}," +
            "#{merchant_id}," +
            "#{i_id}" +
            ")")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn = "id")
    int insertVideoDevice(VideoDevice videoDevice);

    //delete
    String sql_delete = "update vedio_device_t " +
            "set online="+DeviceState.DELETED+" ";
    @Delete(sql_delete+
            "where id = #{id}")
    int deleteVideoDevice(@Param("id")int id);

    @Delete(sql_delete+
            "where merchant_id=#{merchant_id}")
    int deleteVideoDeviceByMerchant(@Param("merchant_id")int merchant_id);

    @Update("update vedio_device_t " +
            "set " +
            "device_code=#{device_code}," +
            "device_name=#{device_name}," +
            "device_description=#{device_description}," +
            "device_location=#{device_location}," +
            "device_gis=#{device_gis}," +
            "device_brand=#{device_brand}," +
            "device_model=#{device_model}," +
            "purchase_date=#{purchase_date} " +
            "where id=#{id}")
    int updateVideoDevice(VideoDevice videoDevice);

    //select by id
    @Select("select * from vedio_device_t where id = #{id} ")
    @Results({
            @Result(id=true,column = "id",property = "id"),

            @Result(property = "videoInfoList",column = "id",many =
            @Many(select = "com.scut.originsystem.mapper.VideoInfoMapper.find"))
    })
    VideoDevice findVideoById(@Param("id")int id);

    //select by merchant
    @Select("select * from vedio_device_t where merchant_id = #{merchant_id} and online<>"+DeviceState.DELETED+" " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<VideoDevice> find(@Param("merchant_id")int merchant_id,
                           @Param("limit") int limit,
                           @Param("offset") int offset);

    @Select("select count(*) from vedio_device_t where merchant_id = #{merchant_id} and online<>"+DeviceState.DELETED+" ")
    int count(@Param("merchant_id")int merchant_id);


    //根据设备名查找
    @Select("select * from vedio_device_t " +
            "where merchant_id=#{merchant_id} and device_name like #{device_name} and online<>"+DeviceState.DELETED+" " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<VideoDevice> findVideoDeviceByDeviceName(@Param("device_name")String device_name,
                                                  @Param("merchant_id")int merchant_id,
                                                  @Param("limit") int limit,
                                                  @Param("offset") int offset);
    @Select("select count(*) from vedio_device_t " +
            "where merchant_id=#{merchant_id} and device_name like #{device_name} and online<>"+DeviceState.DELETED+" ")
    int countVideoDeviceByDeviceName(@Param("device_name")String device_name,
                                                  @Param("merchant_id")int merchant_id);


    //设置online
    @Update("update vedio_device_t " +
            "set online=#{online} " +
            "where id=#{id}")
    int setOnline(@Param("online") int online,
                  @Param("id") int id);

    //设置drop_date
    @Update("update vedio_device_t " +
            "set drop_date=#{drop_date} " +
            "where id=#{id}")
    int setDropDate(@Param("drop_date") String drop_date,
                    @Param("id") int id);

    //查找所有可以用的
    @Select("select * from vedio_device_t where online <> " + DeviceState.DELETED + " " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<VideoDevice> findAll(@Param("limit") int limit,
                              @Param("offset") int offset);

    @Select("select * from vedio_device_t where online <> " + DeviceState.DELETED )
    List<VideoDevice> findAll1( );

    //查找某商户的所有掉线
    @Select("select * from vedio_device_t where online = " + DeviceState.OFFLINE +" and merchant_id = #{merchant_id} " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<VideoDevice> findMerchantDrop(@Param("merchant_id")int merchant_id,
                                       @Param("limit") int limit,
                                       @Param("offset") int offset);
    @Select("select count(*) from vedio_device_t where online = "+ DeviceState.OFFLINE+" and merchant_id = #{merchant_id} ")
    int findMerchantDropPage(@Param("merchant_id")int merchant_id);

    @Select("select * from vedio_device_t where online = " + DeviceState.OFFLINE + " " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<VideoDevice> findAllDrop(@Param("limit") int limit,
                                  @Param("offset") int offset);
    @Select("select count(*) from vedio_device_t where online = " + DeviceState.OFFLINE + " ")
    int findAllDropPage( );


    @Select("select * from vedio_device_t where online = "+DeviceState.OFFLINE+" and merchant_id = #{merchant_id} ")
    List<VideoDevice> findAllDrop1(@Param("merchant_id")int merchant_id);

    //通过某些字段模糊查询
    @SelectProvider(type = VideoDeviceMapperProvider.class, method = "findVideoDeviceConditions")
    List<VideoDevice> findVideoDeviceConditions(VideoDeviceConditions videoDeviceConditions);
    @SelectProvider(type = VideoDeviceMapperProvider.class, method = "getTotal_findVideoDeviceConditions")
    int getTotal_findVideoDeviceConditions(VideoDeviceConditions videoDeviceConditions);
}
