package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.DownloadHistory;
import com.scut.originsystem.util.SqlUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "downloadHistoryMapper")
public interface DownloadHistoryMapper {
    @Insert("insert into download_history( " +
            "create_date," +
            "qrcode_number," +
            "url," +
            "merchant_id," +
            "good_code," +
            "good_name," +
            "good_batch," +
            "good_id" +
            ") " +
            "values(" +
            "#{create_date}," +
            "#{qrcode_number}," +
            "#{url}," +
            "#{merchant_id}," +
            "#{good_code}," +
            "#{good_name}," +
            "#{good_batch}," +
            "#{good_id}" +
            ")")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn = "id")
    int insertDownloadHistory(DownloadHistory downloadHistory);

    @Select("select * from download_history where merchant_id = #{merchant_id} " +
            " order by create_date desc " +
            " limit " + SqlUtil.PAGE_LIMIT +
            " offset #{offset}")
    List<DownloadHistory> findHistory(@Param("merchant_id") int merchant_id,
                                      @Param("offset") int offset);

    @Select("select count(*) from download_history where merchant_id = #{merchant_id} ")
    int findHistoryCount(@Param("merchant_id") int merchant_id);



}


