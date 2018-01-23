package com.scut.originsystem.service;

import com.scut.originsystem.annotation.SystemLogControllerAnno;
import com.scut.originsystem.entity.Result;
import com.scut.originsystem.entity.User;
import com.scut.originsystem.enums.RoleType;
import com.scut.originsystem.mapper.MerchantMapper;
import com.scut.originsystem.mapper.UserMapper;
import com.scut.originsystem.util.EmailUtil;
import com.scut.originsystem.util.EncryptUtil;
import com.scut.originsystem.util.RedisUtil;
import com.scut.originsystem.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    MerchantMapper merchantMapper;

    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    protected static Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 登录
     * 以后做成返回令牌
     */
    public Result login(String username,String password){
        if(username != null){
            User user = userMapper.findUser(username);
            if(user == null){
                return ResultUtil.resultBadReturner("找不到该用户");
            }else if (!user.getPassword().equals(EncryptUtil.encrypt(password))){
                return ResultUtil.resultBadReturner("密码错误");
            }else if (user.getActivation_code() == 0){
                return ResultUtil.resultBadReturner("用户未激活");
            }else if (user.getActivation_code() == 2){
                return ResultUtil.resultBadReturner("用户已注销");
            }
            else {
                logger.info("Login:" + user.getUsername() + " login,user_id is " + user.getId());
                user.setPassword(null);
                Map<String,Object> map = new HashMap<>();
                map.put("user",user);
                if (user.getRole().equals(RoleType.MERCHANT.getRole())){
                    map.put("merchant",merchantMapper.findMerchantByUserId(user.getId()));
                }
                String token = RedisUtil.setTokenSession(user.getUsername(),user.getRole());
                map.put("token",token);
                return ResultUtil.resultGoodReturner(map);
            }
        }else {
            return ResultUtil.resultBadReturner("用户名错误");
        }
    }

    /**
     * 退出
     * 删除令牌
     */

    //注册
    public Result signUp(User user){
        User tempUser = userMapper.findUser(user.getUsername());
        if (tempUser!=null){
            return ResultUtil.resultBadReturner("该用户名已被注册");
        }
        String today = format.format(new Date());
        user.setCreate_date(today);
        user.setPassword(EncryptUtil.encrypt(user.getPassword()));
        userMapper.insertUser(user);
        logger.info("Sign up:" + user.getUsername() + " sign up,user_id is " + user.getId());
        user.setPassword(null);
        return ResultUtil.resultGoodReturner(user);
    }

    ///注册审核不通过,activation_code=4,check_state=1
    public Result cantActiveUser(int user_id){
        User user = userMapper.findUserById(user_id);
        userMapper.setActivationCode(4,user_id);
//        if (user.getRole().equals(RoleType.MERCHANT.getRole())){
//            merchantMapper.setActivationCodeByUserId(1,user_id);
//        }
        logger.info("Cant user:user id is " + user_id);
        return ResultUtil.resultGoodReturner();
    }

    //激活,activation_code=1,check_state=1
    public Result activatUser(int user_id){
        User user = userMapper.findUserById(user_id);
        userMapper.setActivationCode(1,user_id);
//        if (user.getRole().equals(RoleType.MERCHANT.getRole())){
//            merchantMapper.setActivationCodeByUserId(1,user_id);
//        }
        logger.info("Activat user:user id is " + user_id);
        return ResultUtil.resultGoodReturner();
    }

    //找回密码
    public Result findPassword(String username, String email){
        User user = userMapper.findUser(username);
            if (user == null){
            return ResultUtil.resultBadReturner("找不到该用户");
        }
        if(!user.getEmail().equals(email)){
            return ResultUtil.resultBadReturner("邮箱验证失败");
        }else {
            try {
                // 新密码为16位随机数
                int len = 16;
                String rand_str = EncryptUtil.encrypt(user.getPassword());
                String new_password = null;
                if(rand_str.length()<len){
                    new_password = rand_str;
                }else{
                    new_password = rand_str.substring(0,len);
                }
                String encrypt_password = EncryptUtil.encrypt(new_password);
                userMapper.updatePassword(encrypt_password,user.getId());
                EmailUtil.sendEmail(email,"找回密码","您的密码是：" + new_password,user.getUsername());
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.resultBadReturner("找回密码失败");
            }
            logger.info("Find Password:" + user.getUsername() + " find back password,user_id is " + user.getId());
            return ResultUtil.resultGoodReturner();
        }
    }

    //修改密码
    public Result modifyPassword(int user_id ,String new_password,String old_password){
        User user = userMapper.findUserById(user_id);
        if (user == null){
            return ResultUtil.resultBadReturner("找不到该用户");
        }
        if (!user.getPassword().equals(old_password)){
            return ResultUtil.resultBadReturner("当前密码错误");
        }else {
            userMapper.modifyPassword(new_password,user_id);
            logger.info("Modify Password:" + user.getUsername() + " modify password,user_id is " + user.getId() +
            ",old password is " + old_password + ",new password is " + new_password);
            return ResultUtil.resultGoodReturner();
        }
    }

    //修改邮箱
    public Result modifyEmail(int user_id ,String new_email){
        User user = userMapper.findUserById(user_id);
        if (user == null){
            return ResultUtil.resultBadReturner("找不到该用户");
        }
        userMapper.modifyEmail(new_email,user_id);
        logger.info("Modify Email:" + user.getUsername() + " modify email,user_id is " + user.getId() +
                    ",new email is " + new_email);
        return ResultUtil.resultGoodReturner();
    }
}
