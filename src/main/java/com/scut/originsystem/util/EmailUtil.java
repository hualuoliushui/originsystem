package com.scut.originsystem.util;

import org.springframework.beans.factory.annotation.Value;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class EmailUtil {
    @Value("${myEmailAccount}")
    private static String myEmailAccount = "282709085@qq.com";
    @Value("${myEmailPassword}")
    private static String myEmailPassword = "fbjlazhjztuebjea";
    @Value("${smtpPort}")
    private static String smtpPort = "465";
    @Value("${myEmailSMTPHost}")
    public static String myEmailSMTPHost = "smtp.qq.com";

    public static void sendEmail(String receiveMailAccount,String subject,String content,String username) throws Exception {
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证

        // 非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接
        // 需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助
        // QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看
        // 开启SSL连接
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);

        Session session = Session.getInstance(props);
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);

        MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount,subject,content,username);
        Transport transport = session.getTransport();
        transport.connect(myEmailAccount, myEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());

        transport.close();
    }

    /**
     * @param session 和服务器交互的会话
     * @param sendMail 发件人邮箱
     * @param receiveMail 收件人邮箱
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,String subject, String content, String username) throws Exception {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, "溯源系统", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, username, "UTF-8"));

        /*
        //收件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress("cc@receive.com", "USER_CC", "UTF-8"));
        //增加收件人（可选）
        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress("dd@receive.com", "USER_DD", "UTF-8"));
        //抄送（可选）
        message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress("ee@receive.com", "USER_EE", "UTF-8"));
        //密送（可选）
        message.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress("ff@receive.com", "USER_FF", "UTF-8"));
        */

        message.setSubject(subject, "UTF-8");
        message.setContent(content, "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();

        return message;
    }
}
