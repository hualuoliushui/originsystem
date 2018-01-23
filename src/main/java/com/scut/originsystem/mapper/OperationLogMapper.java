package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.OperationLog;
import com.scut.originsystem.util.SqlUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "operationLogMapper")
public interface OperationLogMapper {
    @Insert("insert into operation_log_t " +
            "(operation_content,operation_date,merchant_id," +
            "good_id,operation_file,operation_title,operation_picture) " +
            "values(#{operation_content},#{operation_date},#{merchant_id}," +
            "#{good_id},#{operation_file},#{operation_title},#{operation_picture})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertOperationLog(OperationLog operationLog);

    String sql_delete = "update operation_log_t set deleted_code=1 ";
    @Update( sql_delete +
            "where id=#{id}")
    int deleteOperationLog(@Param("id") int id);

    @Update( sql_delete +
            "where good_id=#{good_id}")
    int deleteOperationLogByGood(@Param("good_id") int good_id);

    @Update( sql_delete +
            "where merchant_id=#{merchant_id}")
    int deleteOperationLogByMerchant(@Param("merchant_id") int merchant_id);

    @Update("update operation_log_t " +
            "set " +
            "operation_content=#{operation_content}," +
            "operation_file=#{operation_file}," +
//            "operation_date=#{operation_date}, " + //不修改日期
            "operation_title=#{operation_title}, " +
            "operation_picture=#{operation_picture} " +
            "where id=#{id}")
    int updateOperationLog(OperationLog operationLog);

    String sql_findByGood = " from operation_log_t " +
            "where good_id=#{good_id} and deleted_code<>1 ";

    @Select("select * " + sql_findByGood)
    List<OperationLog> findByGood(@Param("good_id") int good_id);

    @Select("select * " + sql_findByGood + SqlUtil.PAGE_LIMIT_SQL)
    List<OperationLog> findByGoodWithPage(@Param("good_id") int good_id,
                                          @Param("limit") int limit,
                                          @Param("offset") int offset);

    @Select(SqlUtil.COUNT_SQL + sql_findByGood)
    int getTotal_findByGood(@Param("good_id") int good_id);

    String sql_findByMerchant = " from operation_log_t " +
            "where merchant_id=#{merchant_id} and deleted_code<>1  ";

    @Select("select * " + sql_findByMerchant)
    List<OperationLog> findByMerchant(@Param("merchant_id") int merchant_id);

    @Select("select * " + sql_findByMerchant + SqlUtil.PAGE_LIMIT_SQL)
    List<OperationLog> findByMerchantWithPage(@Param("merchant_id") int merchant_id,
                                              @Param("limit") int limit,
                                              @Param("offset") int offset);

    @Select(SqlUtil.COUNT_SQL + sql_findByMerchant)
    int getTotal_findByMerchant(@Param("merchant_id") int merchant_id);

    String sql_findAllOperationLog = " from operation_log_t where deleted_code<>1 ";

    @Select("select * " + sql_findAllOperationLog)
    List<OperationLog> findAllOperationLog();

    @Select("select * " + sql_findAllOperationLog + SqlUtil.PAGE_LIMIT_SQL)
    List<OperationLog> findAllOperationLogWithPage(@Param("limit") int limit,
                                                   @Param("offset") int offset);

    @Select(SqlUtil.COUNT_SQL + sql_findAllOperationLog)
    int getTotal_findAllOperationLog();
}
