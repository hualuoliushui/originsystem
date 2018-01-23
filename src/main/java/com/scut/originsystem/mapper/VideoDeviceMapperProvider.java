package com.scut.originsystem.mapper;

import com.scut.originsystem.enums.DeviceState;
import com.scut.originsystem.receiveentity.VideoDeviceConditions;
import com.scut.originsystem.util.SqlUtil;

public class VideoDeviceMapperProvider {
    static String table_name = "vedio_device_t";

    public String getTotal_findVideoDeviceConditions(VideoDeviceConditions videoDeviceConditions){
        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtil.COUNT_SQL + " from " + table_name + " where online<>" + DeviceState.DELETED + " ");
        sql.append(SqlUtil.CountResolve(videoDeviceConditions));
        return sql.toString();
    }

    public String findVideoDeviceConditions(VideoDeviceConditions videoDeviceConditions){
        StringBuilder sql = new StringBuilder();
        sql.append("select * from " + table_name + " where online<>"+DeviceState.DELETED + " ");
        sql.append(SqlUtil.ConditionsResolve(videoDeviceConditions));
        return sql.toString();
    }
}
