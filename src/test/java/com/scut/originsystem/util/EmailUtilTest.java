package com.scut.originsystem.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailUtilTest {

    protected static Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void sendEmail() {
        String receiveMailAccount = "1150893190@qq.com";
        String subject = "打包通知";
        String username = "甘寿枢";
        StringBuilder content = new StringBuilder();
        content.append("<h1>"+subject+"</h1><br>");
        content.append(username + ",您好!<br>");
        content.append("originsystem 打包成功,时间：" + format.format(new Date())+"<br>");
        content.append("我又打包了，气不气<br>");
        try {
            EmailUtil.sendEmail(receiveMailAccount,subject,content.toString(),username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}