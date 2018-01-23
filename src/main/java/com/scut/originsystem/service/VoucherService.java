package com.scut.originsystem.service;

import com.scut.originsystem.entity.Result;
import com.scut.originsystem.entity.Voucher;
import com.scut.originsystem.mapper.VoucherMapper;
import com.scut.originsystem.util.RandomUtil;
import com.scut.originsystem.util.ResultUtil;
import com.scut.originsystem.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VoucherService {
    @Autowired
    VoucherMapper voucherMapper;

    private static Logger logger = LoggerFactory.getLogger(VoucherService.class);
    protected static Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 红包生成
     * 输入总金额和个数
     * 生成代金券存入数据库
     */
    public Result produceVoucher(int amount ,int number , int merchant_id){
        double[] randomVoucher = RandomUtil.randomVoucher(amount,number);
        String today = format.format(new Date());
        for(int i = 0;i < randomVoucher.length;i++){
            String code = UuidUtil.getUUID().substring(16);
            Voucher voucher = voucherMapper.findVoucherByCode(code);
            if (voucher!=null) continue;
            voucherMapper.insertVoucher(code,randomVoucher[i],merchant_id,today);
        }
        logger.info("Produce voucher:merchant_id is " + merchant_id + ",amount is " + amount + ",number is " + number);
        return ResultUtil.resultGoodReturner();
    }

    /**
     * 根据兑换码查询红包和金额
     */
    public Result checkVoucherByCode(String voucher_code){
        Voucher voucher = voucherMapper.findVoucherByCode(voucher_code);
        logger.info("Check voucher:voucher_code is " + voucher_code);
        return ResultUtil.resultGoodReturner(voucher);
    }

    /**
     * 查询已发放的红包和金额统计
     */
    public Result findBidedVoucher(int merchant_id,int page){
        Map<String,Object> map = new HashMap<>();
        int offset = (page-1)*10;
        List<Voucher> voucherList = voucherMapper.findBidedVoucher(merchant_id,10,offset);
        double sum = 0;
        for(Voucher voucher : voucherList){
            sum += voucher.getVoucher_amount();
        }

        map.put("voucherList",voucherList);
        map.put("sum",sum);
        map.put("total",voucherMapper.countBidedVoucher(merchant_id));
        map.put("amount",voucherMapper.amountBidedVoucher(merchant_id));

        return ResultUtil.resultGoodReturner(map);
    }

    /**
     * 查询已兑换的红包和金额统计
     */
    public Result findUsedVoucher(int merchant_id,int page){
        Map<String,Object> map = new HashMap<>();
        int offset = (page-1)*10;
        List<Voucher> voucherList = voucherMapper.findUsedVoucher(merchant_id,10,offset);
        double sum = 0;
        for(Voucher voucher : voucherList){
            sum += voucher.getVoucher_amount();
        }

        map.put("voucherList",voucherList);
        map.put("sum",sum);
        map.put("total",voucherMapper.countUsedVoucher(merchant_id));
        map.put("amount",voucherMapper.amountUsedVoucher(merchant_id));

        return ResultUtil.resultGoodReturner(map);
    }

    /**
     * 查询所有的红包和金额统计
     */
    public Result findAllVoucher(int merchant_id,int page){
        Map<String,Object> map = new HashMap<>();
        int offset = (page-1)*10;
        List<Voucher> voucherList = voucherMapper.findAllVoucher(merchant_id,10,offset);
        double sum = 0;
        for(Voucher voucher : voucherList){
            sum += voucher.getVoucher_amount();
        }

        map.put("voucherList",voucherList);
        map.put("sum",sum);
        map.put("total",voucherMapper.countAllVoucher(merchant_id));
        map.put("amount",voucherMapper.amountAllVoucher(merchant_id));

        return ResultUtil.resultGoodReturner(map);
    }

    /**
     * 兑换红包
     * 填写兑换日期
     * 修改标志位
     */
    public Result useVoucher(String voucher_code){
        String today = format.format(new Date());
        voucherMapper.useVoucher(voucher_code,today);
        logger.info("Use voucher:voucher_code is " + voucher_code);
        return ResultUtil.resultGoodReturner();
    }

    /**
     * 查询未兑换的红包和金额统计
     */
    public Result findUnusedVoucher(int merchant_id,int page){
        Map<String,Object> map = new HashMap<>();
        int offset = (page-1)*10;
        List<Voucher> voucherList = voucherMapper.findUnusedVoucher(merchant_id,10,offset);
        double sum = 0;
        for(Voucher voucher : voucherList){
            sum += voucher.getVoucher_amount();
        }

        map.put("voucherList",voucherList);
        map.put("sum",sum);
        map.put("total",voucherMapper.countUnusedVoucher(merchant_id));
        map.put("amount",voucherMapper.amountUnusedVoucher(merchant_id));

        return ResultUtil.resultGoodReturner(map);
    }

    /**
     * 根据时间段查询已经发放的红包和金额
     */
    public Result findVoucherByDate(int page, String pre_date, String post_date, int merchant_id){
        Map<String,Object> map = new HashMap<>();
        int offset = (page-1)*10;
        List<Voucher> voucherList = voucherMapper.findVoucherByDate(10, offset, pre_date, post_date, merchant_id);
        double sum = 0;
        for(Voucher voucher : voucherList){
            sum += voucher.getVoucher_amount();
        }

        map.put("voucherList",voucherList);
        map.put("sum",sum);
        map.put("total",voucherMapper.countVoucherByDate(pre_date,post_date,merchant_id));
        map.put("amount",voucherMapper.amounVoucherByDate(pre_date,post_date,merchant_id));

        return ResultUtil.resultGoodReturner(map);
    }
}
