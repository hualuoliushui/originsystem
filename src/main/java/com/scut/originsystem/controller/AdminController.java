package com.scut.originsystem.controller;

import com.scut.originsystem.annotation.SystemLogControllerAnno;
import com.scut.originsystem.entity.TestEntity;
import com.scut.originsystem.entity.User;
import com.scut.originsystem.service.AdminService;
import com.scut.originsystem.validationInterface.InsertUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    //新增用户
    @RequestMapping(value = "/insertUser", method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "新增用户")
    public Object insertUser(@Validated({InsertUser.class}) @RequestBody User user) {
        return adminService.insertUser(user);
    }

    //批量新增用户
    @RequestMapping(value = "/insertUserWithBatch", method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "批量新增用户")
    public Object insertUserWithBatch(@Validated @RequestParam(value = "file", required = true) MultipartFile file) {
        return adminService.insertUserWithBatch(file);
    }

    //删除用户
    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "删除用户")
    public Object deleteUser(@RequestParam(value = "user_id", required = true) int user_id) {
        return adminService.deleteUser(user_id);
    }

    //更新用户
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "更新用户")
    public Object updateUser(@Validated @RequestBody User user) {
        return adminService.updateUser(user);
    }

    //查看营运商列表
    @RequestMapping(value = "/findAllUser", method = RequestMethod.GET)
    @ResponseBody
    public Object findAllUser(@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        return adminService.findAllUser(page);
    }

    //通过用户名查看用户列表(排除商户）
    @RequestMapping(value = "/findUsersByUserName", method = RequestMethod.GET)
    @ResponseBody
    public Object findUsersByUserName(@RequestParam(value = "user_name", required = true) String user_name,
                                      @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        return adminService.findUsersByUserName(user_name, page);
    }

    //通过用户名查看用户列表(只是商户)
    @RequestMapping(value = "/findUsersByUserNameOnlyMerchant", method = RequestMethod.GET)
    @ResponseBody
    public Object findUsersByUserNameOnlyMerchant(@RequestParam(value = "user_name", required = true) String user_name,
                                                  @RequestParam(value = "page", defaultValue = "1") int page) {
        return adminService.findUsersByUserNameOnlyMerchant(user_name, page);
    }

    //查看商户（全部）
    @RequestMapping(value = "/findAllMerchant", method = RequestMethod.GET)
    @ResponseBody
    public Object findAllMerchant(HttpServletRequest request,
                                  @RequestParam(value = "page", defaultValue = "1") int page) {
        return adminService.findAllMerchant(request,page);
    }

    //查看未激活商户列表
    @RequestMapping(value = "/findAllNoActiveMerchant", method = RequestMethod.GET)
    @ResponseBody
    public Object findAllNoActiveMerchant(HttpServletRequest request,
                                          @RequestParam(value = "page", defaultValue = "1") int page) {
        return adminService.findAllNoActiveMerchant(request, page);
    }

    //审核不通过(注册）（商户）
    @RequestMapping(value = "/cantActiveMerchant", method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "审核不通过(注册）（商户）")
    public Object cantActiveMerchant(@RequestParam(value = "user_id", required = true) int user_id,
                                     @RequestParam(value = "reason", required = false) String reason) {
        return adminService.cantActiveMerchant(user_id,reason);
    }

    //激活（商户）
    @RequestMapping(value = "/activateMerchant", method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "激活商户")
    public Object activateMerchant(@RequestParam(value = "user_id", required = true) int user_id) {
        return adminService.activateMerchant(user_id);
    }

    //注销商户，把激活码设为2
    @RequestMapping(value = "/cancelMerchant", method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "注销商户")
    public Object cancelMerchant(@RequestParam(value = "user_id", required = true) int user_id) {
        return adminService.cancelMerchant(user_id);
    }

    //查看某个用户信息
    @RequestMapping(value = "/findUser", method = RequestMethod.GET)
    @ResponseBody
    public Object findUser(@RequestParam(value = "user_id", required = true) int user_id) {
        return adminService.findUser(user_id);
    }

    //获取平台信息
    @RequestMapping(value = "/findSystem", method = RequestMethod.GET)
    @ResponseBody
    public Object findSystem() {
        return adminService.findSystem();
    }

    //修改平台名字
    @RequestMapping(value = "/setName", method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "修改平台名字")
    public Object setName(@RequestParam(value = "name", required = true) String name) {
        return adminService.setName(name);
    }

    @RequestMapping(value = "/getName", method = RequestMethod.GET)
    @ResponseBody
    public Object getName() {
        return adminService.getName();
    }

    //修改平台logo
    @RequestMapping(value = "/setLogo", method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "修改平台logo")
    public Object setLogo(@RequestParam(value = "file", required = true) MultipartFile file) {
        return adminService.setLogo(file);
    }

    //获取平台logo
    @RequestMapping(value = "/getLogo", method = RequestMethod.GET)
    public void getLogo(@RequestParam(value = "fileName", required = true) String fileName, HttpServletResponse response) {
        adminService.getLogo(fileName, response);
    }

    //没有任何卵用，只是用来测试API
    @RequestMapping(value = "/api", method = RequestMethod.POST)
    @ResponseBody
    public Object api(@Validated @RequestBody TestEntity testEntity) {
        return adminService.api(testEntity);
    }

    //获取待审核的（信息修改）商户列表
    @RequestMapping(value = "/findAllWaitForCheckingMerchant", method = RequestMethod.GET)
    @ResponseBody
    public Object findAllWaitForCheckingMerchant(HttpServletRequest request,
                                                 @RequestParam(value = "page", defaultValue = "1") int page) {
        return adminService.findAllWaitForCheckingMerchant(request, page);
    }

    //设置待审核的（信息修改）商户的状态
    @RequestMapping(value = "/setWaitForCheckingMerchantActivationCode", method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "设置待审核的（信息修改）商户的状态")
    public Object setWaitForCheckingMerchantActivationCode(@RequestParam(value = "merchant_id", required = true) int merchant_id,
                                                           @RequestParam(value = "activation_code", required = true) int activation_code) {
        return adminService.setWaitForCheckingMerchantActivationCode(merchant_id, activation_code);
    }

    //查看系统日志
    @RequestMapping(value = "/findSystemLogs", method = RequestMethod.GET)
    @ResponseBody
    public Object findSystemLogs(@RequestParam(value = "role", required = true) String role,
                                 @RequestParam(value = "start_time", required = true) String start_time,
                                 @RequestParam(value = "end_time", required = true) String end_time,
                                 @RequestParam(value = "page", required = true) int page) {
        return adminService.findSystemLogs(role, start_time, end_time, page);
    }

    //查看当前管理员所在区域的商户列表
    @RequestMapping(value = "findMerchantsByArea", method = RequestMethod.GET)
    @ResponseBody
    public Object findMerchantsByArea(HttpServletRequest request,
                                      @RequestParam(value = "page", defaultValue = "1") int page) {
        return adminService.findMerchantsByArea(request, page);
    }

    //查看有没有需要审核的二维码申请表
    @RequestMapping(value = "/getCheck", method = RequestMethod.GET)
    @ResponseBody
    public Object getCheck(@RequestParam(value = "page", defaultValue = "1") int page) {
        return adminService.getCheck(page);
    }


    //审核二维码申请表，写详情
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "审核二维码申请表，写详情")
    public Object check(@RequestParam(value = "check_state", required = true) int check_state,
                        @RequestParam(value = "check_man_id", required = true) int check_man_id,
                        @RequestParam(value = "check_detail", required = false) String check_detail,
                        @RequestParam(value = "check_id", required = true) int check_id) {
        return adminService.check(check_state, check_man_id, check_detail, check_id);
    }

    //查看某个二维码申请表
    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ResponseBody
    public Object getOne(@RequestParam(value = "id", required = true) int id) {
        return adminService.getOne(id);
    }

    //查看全部商户的用户信息
    @RequestMapping(value = "/findMerchantUser", method = RequestMethod.GET)
    @ResponseBody
    public Object findMerchantUser(@RequestParam(value = "page", defaultValue = "1") int page) {
        return adminService.findMerchantUser(page);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public Object test() {
        return adminService.test();
    }

    //查找所有的掉线的视频
    @RequestMapping(value = "/findAllDropVideo", method = RequestMethod.GET)
    @ResponseBody
    public Object findAllDropVideo(@RequestParam(value = "page", defaultValue = "1") int page) {
        return adminService.findAllDropVideo(page);
    }

    //查找所有的掉线的检测
    @RequestMapping(value = "/findAllDropDetection", method = RequestMethod.GET)
    @ResponseBody
    public Object findAllDropDetection(@RequestParam(value = "page", defaultValue = "1") int page) {
        return adminService.findAllDropDetection(page);
    }

    //查找某商户掉线的视频
    @RequestMapping(value = "/findMerchantDropVideo", method = RequestMethod.GET)
    @ResponseBody
    public Object findMerchantDropVideo(@RequestParam(value = "page",required = false, defaultValue = "1") int page,
                                        @RequestParam(value = "merchant_id") int merchant_id) {
        return adminService.findMerchantDropVideo(merchant_id,page);
    }

    //查找某商户掉线的检测
    @RequestMapping(value = "/findMerchantDropDetection", method = RequestMethod.GET)
    @ResponseBody
    public Object findMerchantDropDetection(@RequestParam(value = "page",required = false, defaultValue = "1") int page,
                                            @RequestParam(value = "merchant_id") int merchant_id) {
        return adminService.findMerchantDropDetection(merchant_id,page);
    }

    //查找指定区域的商户
    @RequestMapping(value = "/findMerchantByAreaCode",method = RequestMethod.GET)
    @ResponseBody
    public Object findMerchantByAreaCode(@RequestParam(value = "area_code",required = true,defaultValue = "")String area_code,
                                         @RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return adminService.findMerchantByAreaCode(area_code,page);
    }

    //查找指定区域的商户用户
    @RequestMapping(value = "/findMerchantUserByAreaCode",method = RequestMethod.GET)
    @ResponseBody
    public Object findMerchantUserByAreaCode(@RequestParam(value = "area_code",required = true,defaultValue = "")String area_code,
                                             @RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return adminService.findMerchantUserByAreaCode(area_code,page);
    }

    //条件查询商户用户信息
    @RequestMapping(value = "/findMerchantUsersByConditions",method = RequestMethod.GET)
    @ResponseBody
    public Object findMerchantUsersByConditions(
            @RequestParam(value = "area_code",required = false)String area_code,
            @RequestParam(value = "create_date_start",required = false)String create_date_start,
            @RequestParam(value = "create_date_end",required = false)String create_date_end,
            @RequestParam(value = "activation_code",required = false,defaultValue = "-1")int activation_code,
            @RequestParam(value = "page",required = false,defaultValue = "1")int page,
            HttpServletRequest request){
        return adminService.findMerchantUsersByConditions(
                area_code,
                create_date_start,
                create_date_end,
                activation_code,
                page,
                request);
    }

    @RequestMapping(value = "/download_findMerchantUsersByConditions",method = RequestMethod.GET)
    @ResponseBody
    public Object download_findMerchantUsersByConditions(
            @RequestParam(value = "area_code",required = false)String area_code,
            @RequestParam(value = "create_date_start",required = false)String create_date_start,
            @RequestParam(value = "create_date_end",required = false)String create_date_end,
            @RequestParam(value = "activation_code",required = false,defaultValue = "-1")int activation_code,
            @RequestParam(value = "page",required = false,defaultValue = "1")int page,
            HttpServletRequest request
    ){
        return adminService.download_findMerchantUsersByConditions(
                area_code,
                create_date_start,
                create_date_end,
                activation_code,
                page,
                request);
    }

    //按照区域查找该区域下的所有商户的二维码数量总数，已经扫描的二维码总数
    @RequestMapping(value = "/findQRCodeByArea",method = RequestMethod.GET)
    @ResponseBody
    public Object findQRCodeByArea(@RequestParam(value = "area_code",required = false)String area_code){
        return adminService.findQRCodeByArea(area_code);
    }
}