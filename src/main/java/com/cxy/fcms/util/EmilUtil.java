package com.cxy.fcms.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

public class EmilUtil {
    @Autowired
    private static JavaMailSenderImpl mailSender;

    public static void sendEmal(String title, String Text, String to) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(title);
        mailMessage.setText(Text);
        mailMessage.setTo(to);
        mailMessage.setFrom("1069664381@qq.com");
        mailSender.send(mailMessage);
    }
}
