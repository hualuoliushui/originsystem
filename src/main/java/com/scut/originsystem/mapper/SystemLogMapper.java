package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.SystemLog;
import com.scut.originsystem.util.SqlUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "systemLogMapper")
public interface SystemLogMapper {
    @Insert("insert into system_log_t(role,log_time,username,log_message,ip) " +
            "values(#{role},#{log_time},#{username},#{log_message},#{ip})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn = "id")
    int insertSystemLog(SystemLog systemLog);

    // 根据时间段，用户角色进行查询
    String sql_findSystemLogs = " from system_log_t " +
            "where " +
            "role=#{role} and " +
            "UNIX_TIMESTAMP(#{start_time}) <= UNIX_TIMESTAMP(log_time) and " +
            "UNIX_TIMESTAMP(#{end_time}) >= UNIX_TIMESTAMP(log_time) ";

    @Select("select * " + sql_findSystemLogs + SqlUtil.PAGE_LIMIT_SQL)
    List<SystemLog> findSystemLogsWithPage(@Param("role")String role,
                                           @Param("start_time")String start_time,
                                           @Param("end_time")String end_time,
                                           @Param("limit")int num,
                                           @Param("offset")int offset);
    @Select(SqlUtil.COUNT_SQL+sql_findSystemLogs)
    int getTotal_findSystemLogsWithPage(@Param("role")String role,
                                        @Param("start_time")String start_time,
                                        @Param("end_time")String end_time);
}
