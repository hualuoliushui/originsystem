package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.Voucher;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "voucherMapper")

public interface VoucherMapper {
    //添加代金券
    @Insert("insert into voucher_t(voucher_code,voucher_amount,merchant_id,create_date) " +
            "values(#{voucher_code},#{voucher_amount},#{merchant_id},#{create_date})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn = "id")
    int insertVoucher(@Param("voucher_code") String voucher_code,
                      @Param("voucher_amount") double voucher_amount,
                      @Param("merchant_id") int merchant_id,
                      @Param("create_date") String create_date);

    //获取代金券后
    @Update("update voucher_t " +
            "set has_bid = 1 ,bid_date = #{bid_date},bid_id = #{bid_id} " +
            "where voucher_code = #{voucher_code}")
    int getVoucher(@Param("voucher_code") String voucher_code,
                   @Param("bid_date") String bid_date,
                   @Param("bid_id") int bid_id);

    //随机选一个代金券
    @Select("select * from voucher_t where has_bid = 0 and merchant_id = #{merchant_id} limit 1")
    Voucher findOneVoucher(@Param("merchant_id") int merchant_id);

    //使用代金券
    @Update("update voucher_t " +
            "set has_used = 1 ,use_date = #{use_date} " +
            "where voucher_code = #{voucher_code}")
    int useVoucher(@Param("voucher_code") String voucher_code,
                   @Param("use_date") String use_date);

    /**
     * 根据时间段查询已经发放的红包和金额
     */
    @SelectProvider(type = SqlProvider.class, method = "voucherByDate")
    List<Voucher> findVoucherByDate(@Param("limit") int limit,
                                    @Param("offset") int offset,
                                    @Param("pre_date") String pre_date,
                                    @Param("post_date") String post_date,
                                    @Param("merchant_id") int merchant_id);


    /**
     * 根据兑换码查询红包和金额
     */
    @Select("select * from voucher_t where voucher_code = #{voucher_code}")
    Voucher findVoucherByCode(@Param("voucher_code") String voucher_code);

    /**
     * 查询已发放的红包和金额统计
     */
    @Select("select * from voucher_t " +
            "where has_bid = 1 and merchant_id = #{merchant_id} " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<Voucher> findBidedVoucher(@Param("merchant_id") int merchant_id,
                                   @Param("limit") int limit,
                                   @Param("offset") int offset);

    /**
     * 查询已兑换的红包和金额统计
     */
    @Select("select * from voucher_t " +
            "where has_used = 1 and merchant_id = #{merchant_id} " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<Voucher> findUsedVoucher(@Param("merchant_id") int merchant_id,
                                  @Param("limit") int limit,
                                  @Param("offset") int offset);

    /**
     * 查询未兑换的红包和金额统计
     */
    @Select("select * from voucher_t " +
            "where has_used = 0 and merchant_id = #{merchant_id} " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<Voucher> findUnusedVoucher(@Param("merchant_id") int merchant_id,
                                    @Param("limit") int limit,
                                    @Param("offset") int offset);

    /**
     * 查询所有的红包和金额统计
     */
    @Select("select * from voucher_t " +
            "where merchant_id = #{merchant_id} " +
            "limit #{limit} " +
            "offset #{offset} ")
    List<Voucher> findAllVoucher(@Param("merchant_id") int merchant_id,
                                 @Param("limit") int limit,
                                 @Param("offset") int offset);
    //-----------------------------------------------------------------------------------------
    @Select("select count(*) from voucher_t " +
            "where has_bid = 1 and merchant_id = #{merchant_id} ")
    int countBidedVoucher(@Param("merchant_id") int merchant_id);

    @Select("select count(*) from voucher_t " +
            "where has_used = 1 and merchant_id = #{merchant_id} ")
    int countUsedVoucher(@Param("merchant_id") int merchant_id);

    @Select("select count(*) from voucher_t " +
            "where has_used = 0 and merchant_id = #{merchant_id} ")
    int countUnusedVoucher(@Param("merchant_id") int merchant_id);

    @Select("select count(*) from voucher_t " +
            "where merchant_id = #{merchant_id} ")
    int countAllVoucher(@Param("merchant_id") int merchant_id);

    @SelectProvider(type = SqlProvider.class, method = "countByDate")
    int countVoucherByDate(@Param("pre_date") String pre_date,
                           @Param("post_date") String post_date,
                           @Param("merchant_id") int merchant_id);
    //-----------------------------------------------------------------------------------------
    @Select("select COALESCE(ROUND(SUM(voucher_amount),2),0) from voucher_t " +
            "where has_bid = 1 and merchant_id = #{merchant_id} ")
    int amountBidedVoucher(@Param("merchant_id") int merchant_id);

    @Select("select COALESCE(ROUND(SUM(voucher_amount),2),0) from voucher_t " +
            "where has_used = 1 and merchant_id = #{merchant_id} ")
    int amountUsedVoucher(@Param("merchant_id") int merchant_id);

    @Select("select COALESCE(ROUND(SUM(voucher_amount),2),0) from voucher_t " +
            "where has_used = 0 and merchant_id = #{merchant_id} ")
    int amountUnusedVoucher(@Param("merchant_id") int merchant_id);

    @Select("select COALESCE(ROUND(SUM(voucher_amount),2),0) from voucher_t " +
            "where merchant_id = #{merchant_id} ")
    int amountAllVoucher(@Param("merchant_id") int merchant_id);

    @SelectProvider(type = SqlProvider.class, method = "amountByDate")
    int amounVoucherByDate(@Param("pre_date") String pre_date,
                           @Param("post_date") String post_date,
                           @Param("merchant_id") int merchant_id);

}
