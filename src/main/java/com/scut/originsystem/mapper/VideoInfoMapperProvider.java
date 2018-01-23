package com.scut.originsystem.mapper;

import com.scut.originsystem.receiveentity.VideoInfoConditions;
import com.scut.originsystem.util.SqlUtil;

public class VideoInfoMapperProvider {
    static String table_name = "video_info_t";

    public String getTotal_findVideoInfoConditions(VideoInfoConditions videoInfoConditions){
        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtil.COUNT_SQL + " from " + table_name + " where deleted_code<>1  ");
        sql.append(SqlUtil.CountResolve(videoInfoConditions));
        return sql.toString();
    }

    public String findVideoInfoConditions(VideoInfoConditions videoInfoConditions){
        StringBuilder sql = new StringBuilder();
        sql.append("select * from " + table_name + " where deleted_code<>1  ");
        sql.append(SqlUtil.ConditionsResolve(videoInfoConditions));
        return sql.toString();
    }
}
