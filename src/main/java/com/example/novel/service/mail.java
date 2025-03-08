package com.example.novel.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public final class mail {

    public static final String USER = "2763457093@qq.com"; // 发件人邮箱
    private static final String PASSWORD = "hdpjmowxdxljdhaf"; // 授权码

    /**
     * 发送邮件
     * @param to    收件人邮箱
     * @param text  邮件正文
     * @param title 邮件标题
     * @return      发送成功返回 true，失败返回 false
     */
    public static boolean sendMail(String to, String text, String title) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.qq.com");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.auth", "true");

            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USER, PASSWORD);
                }
            };

            Session mailSession = Session.getInstance(props, authenticator);
            MimeMessage message = new MimeMessage(mailSession);

            message.setFrom(new InternetAddress(USER));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(title);
            message.setContent(text, "text/html;charset=UTF-8"); // 或 "text/plain;charset=UTF-8"

            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("邮件发送失败：" + e.getMessage());
            return false;
        }
    }
}