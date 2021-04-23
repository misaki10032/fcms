package com.cxy.fcms.Util;

import com.cxy.fcms.util.IDUtil;
import com.cxy.fcms.util.RedisUtil;
import com.cxy.fcms.util.ShiroMd5Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.internet.MimeMessage;

@SpringBootTest
public class utilText {
    @Autowired
    JavaMailSenderImpl mailSender;
    @Autowired
    RedisUtil redisUtil;

    @Test
    void ShiroUtil1() {
        String num = "15667650616";
        String pwd = "111";
        String s = ShiroMd5Util.toPwdMd5(num, pwd);
        System.out.println(s);
    }

    @Test
    void emil() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("你好");
        mailMessage.setText("这时一个测试邮件");
        mailMessage.setTo("1069664381@qq.com");
        mailMessage.setFrom("1069664381@qq.com");
        mailSender.send(mailMessage);
    }

    @Test
    void getuuid() {
        String msg = IDUtil.getID().substring(0, 6);
        redisUtil.set("111", msg);
        String o = (String) redisUtil.get("111");
        System.out.println(o);

    }
}
