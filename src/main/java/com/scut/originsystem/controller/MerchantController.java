package com.scut.originsystem.controller;

import com.scut.originsystem.annotation.SystemLogControllerAnno;
import com.scut.originsystem.entity.Merchant;
import com.scut.originsystem.entity.WaitForCheckingMerchant;
import com.scut.originsystem.entity.RegisterMerchantInfo;
import com.scut.originsystem.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/merchant")
public class MerchantController {
    @Autowired
    MerchantService merchantService;

    //@RequestBody RegisterMerchantInfo registerMerchantInfo
    @RequestMapping(value = "/registerMerchant",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "商户注册")
    public Object registerMerchant(@Validated @RequestBody RegisterMerchantInfo registerMerchantInfo){
//        return "good";
        return merchantService.registerMerchant(registerMerchantInfo);
    }

    //获取指定未激活商户的待审核信息
    @RequestMapping(value = "/getNoActiveMerchant",method = RequestMethod.GET)
    @ResponseBody
    public Object getNoActiveMerchant(@RequestParam(value = "merchant_id",required = true)int merchant_id){
        return merchantService.getNoActiveMerchant(merchant_id);
    }

    //用户注册后是商户角色，注册商户信息
    @RequestMapping(value = "/insertMerchant",method = RequestMethod.POST)
    @ResponseBody
    public Object insertMerchant(@Validated @RequestBody Merchant merchant){
        return merchantService.insertMerchant(merchant);
    }

    //修改商户名称
    @RequestMapping(value = "/modifyMerchant",method = RequestMethod.GET)
    @ResponseBody
    public Object modifyMerchant(@RequestParam(value = "merchant_name", required = true) String merchant_name,
                                 @RequestParam(value = "user_id", required = true) int user_id){
        return merchantService.modifyMerchant(merchant_name,user_id);
    }

    //查看某个商户的全部信息，包括商户信息，公司信息
    @RequestMapping(value = "/getMerchantDetail",method = RequestMethod.GET)
    @ResponseBody
    public Object getMerchantDetail(@RequestParam(value = "merchant_id", required = true) int merchant_id){
        return merchantService.getMerchantDetail(merchant_id);
    }

    //用user_id查看某商户全部信息
    @RequestMapping(value = "/getMerchantByUserId",method = RequestMethod.GET)
    @ResponseBody
    public Object getMerchantByUserId(@RequestParam(value = "user_id", required = true) int user_id){
        return merchantService.getMerchantByUserId(user_id);
    }

    //获取已激活商户的可修改信息
    @RequestMapping(value = "/getMerchantForUpdate",method = RequestMethod.GET)
    @ResponseBody
    public Object getMerchantForUpdate(@RequestParam(value = "merchant_id",required = true)int merchant_id){
        return merchantService.getMerchantForUpdate(merchant_id);
    }

    //提交商户待审核的修改信息的图片
    // upload picture
    @RequestMapping(value = "/uploadPictureForWaitForChecking",method = RequestMethod.POST)
    @ResponseBody
    public Object uploadPictureForWaitForChecking(@RequestParam(value = "file",required = true)MultipartFile file){
        return merchantService.uploadPictureForWaitForChecking(file);
    }

    @RequestMapping(value= "/getPictureForWaitForChecking",method = RequestMethod.GET)
    @ResponseBody
    public void getPictureForWaitForChecking(@RequestParam(value="fileName",required = true)String fileName, HttpServletResponse response){
        merchantService.getPictureForWaitForChecking(fileName, response);
    }

    //提交商户待审核的修改信息
    @RequestMapping(value = "/insertWaitForCheckingMerchant",method = RequestMethod.POST)
    @ResponseBody
    public Object insertWaitForCheckingMerchant(@Validated @RequestBody WaitForCheckingMerchant waitForCheckingMerchant){
        return merchantService.insertWaitForCheckingMerchant(waitForCheckingMerchant);
    }

    //获取指定商户的（信息修改）的商户信息
    @RequestMapping(value = "findWaitForCheckingMerchantByMerchant_id",method = RequestMethod.GET)
    @ResponseBody
    public Object findWaitForCheckingMerchantByMerchant_id(@RequestParam(value = "merchant_id",required = true)int merchant_id){
        return merchantService.findWaitForCheckingMerchantByMerchant(merchant_id);
    }

	//按商户名、区域查询商户
    @RequestMapping(value = "/conditionMerchant",method = RequestMethod.GET)
    @ResponseBody
    public Object conditionMerchant(HttpServletRequest request,
                                    @RequestParam(value = "merchant_name", required = false) String  merchant_name,
                                    @RequestParam(value = "company_area", required = false) String  company_area,
                                    @RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return merchantService.conditionMerchant(request,
                merchant_name,company_area,page);
    }

    //报表
    @RequestMapping(value = "/merchantReport",method = RequestMethod.GET)
    @ResponseBody
    public Object merchantReport(@RequestParam(value = "merchant_id", required = true) int  merchant_id){
        return merchantService.merchantReport(merchant_id);
    }

    //条件查询商户
    @RequestMapping(value = "/findMerchantsByAreaCodeOrCreateDateOrActivationCode",method = RequestMethod.GET)
    @ResponseBody
    public Object findMerchantsByAreaCodeOrCreateDateOrActivationCode(
            @RequestParam(value = "area_code",required = false)String area_code,
            @RequestParam(value = "create_date_start",required = false)String create_date_start,
            @RequestParam(value = "create_date_end",required = false)String create_date_end,
            @RequestParam(value = "activation_code",required = false,defaultValue = "-1")int activation_code,
            @RequestParam(value = "page",required = false,defaultValue = "1")int page,
            HttpServletRequest request){
        return merchantService.findMerchantsByAreaCodeOrCreateDateOrActivationCode(
                area_code,
                create_date_start,
                create_date_end,
                activation_code,
                page,
                request);
    }

    @RequestMapping(value = "/getMyCheck",method = RequestMethod.GET)
    @ResponseBody
    public Object getMyCheck(@RequestParam(value = "merchant_id") int merchant_id,
                             @RequestParam(value = "status", required = false, defaultValue = "0") int status,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page){
        return merchantService.getMyCheck(merchant_id,status,page);
    }


}
