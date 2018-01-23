package com.scut.originsystem.mapper;

import com.scut.originsystem.enums.DeviceState;
import com.scut.originsystem.receiveentity.DetectionDeviceConditions;
import com.scut.originsystem.util.SqlUtil;

/**
 * @create 2018-01-10 15:46
 * @desc sql动态生成
 **/
public class DetectionDeviceMapperProvider {
    static String table_name = "detection_device_t";

    public String getTotal_findDetectionDeviceConditions(DetectionDeviceConditions detectionDeviceConditions){
        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtil.COUNT_SQL + " from " + table_name + " where online<>"+ DeviceState.DELETED + " ");
        sql.append(SqlUtil.CountResolve(detectionDeviceConditions));
        return sql.toString();
    }

    public String findDetectionDeviceConditions(DetectionDeviceConditions detectionDeviceConditions){
        StringBuilder sql = new StringBuilder();
        sql.append("select * from " + table_name + " where online<>"+ DeviceState.DELETED + " ");
        sql.append(SqlUtil.ConditionsResolve(detectionDeviceConditions));
        return sql.toString();
    }
}
