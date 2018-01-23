package com.scut.originsystem.controller;

import com.scut.originsystem.annotation.SystemLogControllerAnno;
import com.scut.originsystem.entity.User;
import com.scut.originsystem.enums.SystemLogType;
import com.scut.originsystem.service.UserService;
import com.scut.originsystem.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;

    //登录
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "登录",type = SystemLogType.LOGIN)
    public Object login(@Validated @RequestBody Map<String , String > map){
        return userService.login(map.get("username"),map.get("password"));
    }

    //退出


    //注册
    @RequestMapping(value = "/signUp",method = RequestMethod.POST)
    @ResponseBody
    public Object signUp(@Validated({com.scut.originsystem.validationInterface.InsertUser.class}) @RequestBody User user){
        return userService.signUp(user);
    }

    //审核不通过(注册）
    @RequestMapping(value = "/cantActiveUser",method = RequestMethod.GET)
    @ResponseBody
    public Object cantActiveUser(@RequestParam(value = "user_id", required = true) int user_id){
        return userService.cantActiveUser(user_id);
    }

    //激活
    @RequestMapping(value = "/activatUser",method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "激活用户")
    public Object activatUser(@RequestParam(value = "user_id", required = true) int user_id){
        return userService.activatUser(user_id);
    }

    //找回密码
    @RequestMapping(value = "/findPassword",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "找回密码")
    public Object findPassword(@RequestBody Map<String , Object > map){
        return userService.findPassword((String) map.get("username"),(String)map.get("email"));
    }

    //修改密码
    @RequestMapping(value = "/modifyPassword",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "修改密码")
    public Object modifyPassword(@RequestBody Map<String , Object > map){
        int user_id = (int) map.get("user_id");
        String new_password = (String) map.get("new_password");
        String old_password = (String) map.get("old_password");
        return userService.modifyPassword(user_id,new_password,old_password);
    }

    //修改邮箱
    @RequestMapping(value = "/modifyEmail",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "修改邮箱")
    public Object modifyEmail(@RequestBody Map<String , Object > map){
        return userService.modifyEmail((int)map.get("user_id"),(String)map.get("email"));
    }

    //
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
    public Object login(){
        return ResultUtil.resultGoodReturner();
    }

}
