package com.cxy.fcms.Util;

import com.cxy.fcms.mapper.ComFictionMapper;
import com.cxy.fcms.util.IDUtil;
import com.cxy.fcms.util.RedisUtil;
import com.cxy.fcms.util.SelectRedis;
import com.cxy.fcms.util.ShiroMd5Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.internet.MimeMessage;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@SpringBootTest
public class utilText {
    @Autowired
    JavaMailSenderImpl mailSender;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    ComFictionMapper fictionMapper;

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

    @Test
    void ttt1() {
        String emil = "1069664381@qq.com";
        boolean matches = emil.matches("^\\w+@\\w{2,6}\\.\\w{2,6}$");
        System.out.println(matches);
    }

    @Test
    void TTss1() {
        try {
            List<Object> objects = SelectRedis.selectRedis(redisUtil, "fictions", fictionMapper, "getFictions");
            System.out.println(objects);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    void T1111() {
        System.out.println(redisUtil.lRange("fictions", 0, -1));
    }
}
