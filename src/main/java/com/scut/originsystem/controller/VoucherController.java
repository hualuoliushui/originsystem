package com.scut.originsystem.controller;

import com.scut.originsystem.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/voucher")
public class VoucherController {
    @Autowired
    VoucherService voucherService;
    /**
     * 红包生成
     * 输入总金额和个数
     * 生成代金券存入数据库
     */
    @RequestMapping(value = "/produceVoucher",method = RequestMethod.GET)
    @ResponseBody
    public Object produceVoucher(@RequestParam(value = "amount", required = true) int amount,
                                 @RequestParam(value = "number", required = true) int number,
                                 @RequestParam(value = "merchant_id", required = true) int merchant_id){
        return voucherService.produceVoucher(amount,number,merchant_id);
    }

    /**
     * 根据时间段查询已经发放的红包和金额
     */

    /**
     * 根据兑换码查询红包和金额
     */
    @RequestMapping(value = "/checkVoucher",method = RequestMethod.GET)
    @ResponseBody
    public Object produceVoucher(@RequestParam(value = "voucher_code", required = true) String voucher_code){
        return voucherService.checkVoucherByCode(voucher_code);
    }

    /**
     * 兑换红包
     */
    @RequestMapping(value = "/useVoucher",method = RequestMethod.GET)
    @ResponseBody
    public Object useVoucher(@RequestParam(value = "voucher_code", required = true) String voucher_code){
        return voucherService.useVoucher(voucher_code);
    }

    /**
     * 查询已发放的红包和金额统计
     */
    @RequestMapping(value = "/findBidedVoucher",method = RequestMethod.GET)
    @ResponseBody
    public Object findBidedVoucher(@RequestParam(value = "merchant_id", required = true) int merchant_id,
                                   @RequestParam(value = "page", required = true,defaultValue = "1") int page){
        return voucherService.findBidedVoucher(merchant_id,page);
    }

    /**
     * 查询已兑换的红包和金额统计
     */
    @RequestMapping(value = "/findUsedVoucher",method = RequestMethod.GET)
    @ResponseBody
    public Object findUsedVoucher(@RequestParam(value = "merchant_id", required = true) int merchant_id,
                                  @RequestParam(value = "page", required = true,defaultValue = "1") int page){
        return voucherService.findUsedVoucher(merchant_id,page);
    }

    /**
     * 查询所有的红包和金额统计
     */
    @RequestMapping(value = "/findAllVoucher",method = RequestMethod.GET)
    @ResponseBody
    public Object findAllVoucher(@RequestParam(value = "merchant_id", required = true) int merchant_id,
                                 @RequestParam(value = "page", required = true,defaultValue = "1") int page){
        return voucherService.findAllVoucher(merchant_id,page);
    }



    /**
     * 查询未兑换的红包和金额统计
     */
    @RequestMapping(value = "/findUnusedVoucher",method = RequestMethod.GET)
    @ResponseBody
    public Object findUnusedVoucher(@RequestParam(value = "merchant_id", required = true) int merchant_id,
                                    @RequestParam(value = "page", required = true,defaultValue = "1") int page){
        return voucherService.findUnusedVoucher(merchant_id,page);
    }

    @RequestMapping(value = "/findVoucherByDate",method = RequestMethod.GET)
    @ResponseBody
    public Object findVoucherByDate(@RequestParam(value = "page", required = true,defaultValue = "1") int page,
                                    @RequestParam(value = "pre_date", required = false) String pre_date,
                                    @RequestParam(value = "post_date", required = false) String post_date,
                                    @RequestParam(value = "merchant_id", required = true) int merchant_id){
        return voucherService.findVoucherByDate(page, pre_date, post_date, merchant_id);
    }
}
