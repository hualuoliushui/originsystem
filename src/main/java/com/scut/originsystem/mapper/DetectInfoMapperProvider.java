package com.scut.originsystem.mapper;

import com.scut.originsystem.receiveentity.DetectInfoConditions;
import com.scut.originsystem.util.SqlUtil;

import java.util.LinkedList;
import java.util.List;

public class DetectInfoMapperProvider {

    public String getTotal_findDetectInfoConditions(DetectInfoConditions detectInfoConditions){
        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtil.COUNT_SQL + " from " + DetectInfoMapper.view_name + " where deleted_code<>1  ");
        sql.append(SqlUtil.CountResolve(detectInfoConditions));
        return sql.toString();
    }

    public String findDetectInfoConditions(DetectInfoConditions detectInfoConditions){
        StringBuilder sql = new StringBuilder();
        sql.append("select * from " + DetectInfoMapper.view_name + " where deleted_code<>1  ");
        sql.append(SqlUtil.ConditionsResolve(detectInfoConditions));
        return sql.toString();
    }
}
