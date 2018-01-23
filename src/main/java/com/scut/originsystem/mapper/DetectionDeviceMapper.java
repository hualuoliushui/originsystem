package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.DetectionDevice;
import com.scut.originsystem.enums.DeviceState;
import com.scut.originsystem.receiveentity.DetectionDeviceConditions;
import com.scut.originsystem.returnentity.DetectionDevice_1;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "detectionDeviceMapper")
public interface DetectionDeviceMapper {
    //插入检测设备
    @Insert("insert into detection_device_t(" +
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
    int insertDetectionDevice(DetectionDevice detectionDevice);

    //删除检测设备
    String sql_delete = "update detection_device_t set online=" + DeviceState.DELETED + " ";
    @Update(sql_delete+
            "where id = #{id}")
    int deleteDetectionDevice(@Param("id")int id);

    @Update(sql_delete+
            "where merchant_id=#{merchant_id}")
    int deleteDetectionDeviceByMerchant(@Param("merchant_id")int merchant_id);

    @Update("update detection_device_t " +
            "set " +
            "device_code=#{device_code},device_name=#{device_name},device_description=#{device_description}," +
            "device_location=#{device_location},device_gis=#{device_gis},device_brand=#{device_brand}," +
            "device_model=#{device_model},purchase_date=#{purchase_date} " +
            "where id=#{id}")
    int updateDetectionDevice(DetectionDevice detectionDevice);

    //merchant查看检测设备
    @Select("select * from detection_device_t where merchant_id = #{merchant_id} and online<>"+ DeviceState.DELETED + " " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<DetectionDevice> find(@Param("merchant_id")int merchant_id,
                               @Param("limit") int limit,
                               @Param("offset") int offset);

    @Select("select count(*) from detection_device_t where merchant_id = #{merchant_id} and online<>"+ DeviceState.DELETED + " ")
    int count(@Param("merchant_id")int merchant_id);

    //根据设备名查找
    @Select("select * from detection_device_t " +
            "where merchant_id=#{merchant_id} and device_name like #{device_name} and online<>"+ DeviceState.DELETED + " " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<DetectionDevice> findDetectionDeviceByDeviceName(@Param("device_name")String device_name,
                                                          @Param("merchant_id")int merchant_id,
                                                          @Param("limit") int limit,
                                                          @Param("offset") int offset);

    @Select("select count(*) from detection_device_t " +
            "where merchant_id=#{merchant_id} and device_name like #{device_name} and online<>"+ DeviceState.DELETED + " ")
    int countDetectionDeviceByDeviceName(@Param("device_name")String device_name,
                                                          @Param("merchant_id")int merchant_id);


    //查看检测设备
    @Select("select * from detection_device_t where id = #{id} ")
    @Results({
            @Result(id=true,column = "id",property = "id"),

            @Result(property = "detectInfoList",column = "id",many =
            @Many(select = "com.scut.originsystem.mapper.DetectInfoMapper.find"))
    })
    DetectionDevice findDetectionById(@Param("id")int id);

    //设置online
    @Update("update detection_device_t " +
            "set online=#{online} " +
            "where id=#{id}")
    int setOnline(@Param("online") int online,
                  @Param("id") int id);

    //设置drop_date
    @Update("update detection_device_t " +
            "set drop_date=#{drop_date} " +
            "where id=#{id}")
    int setDropDate(@Param("drop_date") String drop_date,
                    @Param("id") int id);

    //查找所有可用
    @Select("select * from detection_device_t where online <> " + DeviceState.DELETED + " " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<DetectionDevice> findAll(@Param("limit") int limit,
                                  @Param("offset") int offset);

    @Select("select * from detection_device_t where online <> " + DeviceState.DELETED + " ")
    List<DetectionDevice> findAll1( );

    //查找某商户的所有掉线
    @Select("select * from detection_device_t where online = " + DeviceState.OFFLINE + " and merchant_id = #{merchant_id} " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<DetectionDevice> findMerchantDrop(@Param("merchant_id")int merchant_id,
                                           @Param("limit") int limit,
                                           @Param("offset") int offset);
    @Select("select count(*) from detection_device_t where online = " +DeviceState.OFFLINE + " and merchant_id = #{merchant_id} ")
    int findMerchantDropPage(@Param("merchant_id")int merchant_id);

    @Select("select * from detection_device_t where online = " + DeviceState.OFFLINE + " " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<DetectionDevice> findAllDrop(@Param("limit") int limit,
                                      @Param("offset") int offset);
    @Select("select count(*) from detection_device_t where online = " + DeviceState.OFFLINE + " ")
    int findAllDropPage( );

    @Select("select * from detection_device_t where online = " + DeviceState.OFFLINE +" and merchant_id = #{merchant_id} ")
    List<DetectionDevice> findAllDrop1(@Param("merchant_id")int merchant_id);

    //通过某些字段模糊查询
    @SelectProvider(type = DetectionDeviceMapperProvider.class, method = "findDetectionDeviceConditions")
    List<DetectionDevice> findDetectionDeviceConditions(DetectionDeviceConditions detectionDeviceConditions);
    @SelectProvider(type = DetectionDeviceMapperProvider.class, method = "getTotal_findDetectionDeviceConditions")
    int getTotal_findDetectionDeviceConditions(DetectionDeviceConditions detectionDeviceConditions);

    @Select("select * from detection_device_t where device_code like concat('%',#{device_code},'%')" +
            " and merchant_id = #{merchant_id} " +
            " and online <> " + DeviceState.DELETED + " ")
    List<DetectionDevice_1> findDetectionDevice_1s(@Param("device_code") String device_code,
                                                   @Param("merchant_id") int merchant_id);
}
