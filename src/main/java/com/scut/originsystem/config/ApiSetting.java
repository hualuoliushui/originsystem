package com.scut.originsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.Inet4Address;
import java.net.UnknownHostException;

@Configuration
public class ApiSetting {
    // HTTP访问令牌过期间隔（单位秒）
    public static final int HTTP_ACCESS_TOKEN_EXPIRE_INTERVAL = 2 * 60 * 60;
    // 验证码过期间隔
    public static final int VERIFY_CODE_EXPIRE_INTERVAL = 5 * 60;
    // 邮箱验证token过期间隔
    public static final int EMAIL_ACTIVATE_TOKEN_EXPIRE_INTERVAL = 24 * 60 * 60;
    // 用户终端会话过期时间
    public static  final int USER_TERMINAL_SESSION_EXPIRE_INTERVAL = 2 * 60 * 60;

    private String host;

    @Value("${server.port}")
    private Integer port;

    public String getHost() {
        try {
            this.host = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return host;
    }

    public Integer getPort() {
        return port;
    }
}
