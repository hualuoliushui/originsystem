package com.scut.originsystem.util;

import com.scut.originsystem.config.ApiSetting;
import com.scut.originsystem.session.TokenSession;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisUtil {
    private static RedisTemplate<String, Object> template;

    static {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.afterPropertiesSet();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate();
        //将刚才的redis连接工厂设置到模板类中
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.afterPropertiesSet();
        template=redisTemplate;
    }

    public static TokenSession getTokenSession(String token){
        TokenSession tokenSession = (TokenSession) template.opsForValue().get(token);
        return  tokenSession;
    }

    public static String setTokenSession(String username,String role){
        String token = UuidUtil.getUUID();
        TokenSession tokenSession = new TokenSession(token, role, username);
        template.opsForValue().set(token,tokenSession, ApiSetting.HTTP_ACCESS_TOKEN_EXPIRE_INTERVAL, TimeUnit.SECONDS);
        return token;
    }

    public static void updateToken(String token){
        template.expire(token,ApiSetting.HTTP_ACCESS_TOKEN_EXPIRE_INTERVAL,TimeUnit.SECONDS);
    }
}
