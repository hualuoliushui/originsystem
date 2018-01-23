package com.scut.originsystem.service;

import com.scut.originsystem.entity.Merchant;
import com.scut.originsystem.entity.User;
import com.scut.originsystem.mapper.MerchantMapper;
import com.scut.originsystem.mapper.UserMapper;
import com.scut.originsystem.session.TokenSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
@Service
public class TokenSessionService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    MerchantMapper merchantMapper;

    public User getUser(HttpServletRequest request){
        User user = null;
        try {

            TokenSession tokenSession = (TokenSession)request.getAttribute("tokenSession");
            user = userMapper.findUser(tokenSession.getUsername());
        } catch (Exception e) {
//            e.printStackTrace();
        } finally {
            return user;
        }
    }

    public Merchant getMerchant(HttpServletRequest request){
        Merchant merchant = null;
        try {
            TokenSession tokenSession = (TokenSession)request.getAttribute("tokenSession");
            User user = userMapper.findUser(tokenSession.getUsername());
            merchant = merchantMapper.findMerchantByUserId(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return merchant;
        }
    }
}
