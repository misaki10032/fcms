package com.cxy.fcms.controller;

import com.cxy.fcms.pojo.SysAdmin;
import com.cxy.fcms.service.LoginService;
import com.cxy.fcms.util.EmilUtil;
import com.cxy.fcms.util.IDUtil;
import com.cxy.fcms.util.RedisUtil;
import com.cxy.fcms.util.ShiroMd5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class AdminController {
    @Autowired
    LoginService loginService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    JavaMailSenderImpl mailSender;

    @GetMapping("/toFicList")
    public String toFicList() {
        return "back/fiction/booklist";
    }

    @GetMapping("/gohome")
    public String toAdminInfo() {
        return "back/adminInfo";
    }

    /**
     * 管理员注册相关
     */
    @GetMapping("/withResiger")
    public void resiger(String phone, String number, String pwd, String pwd2, String name, String emil, String emilRes, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        List<SysAdmin> admins = loginService.getAdmins();
        if (phone == null || number == null || pwd == null || pwd2 == null || name == null) {
            out.println("发生了系统故障!");
        } else if (phone.equals("") || number.equals("") || pwd.equals("") || pwd2.equals("") || name.equals("")) {
            out.println("您的表单没有填完哦!");
        } else {
            String msg = (String) redisUtil.get(emil + "_msg");
            if (msg == null || msg.equals("") || !emilRes.equals(msg)) {
                out.print("验证码错误，或已过期");
                return;
            }
            for (SysAdmin admin : admins) {
                if (admin.getAdminNum().equals(number)) {
                    out.println("账号已存在!");
                    return;
                }
                if (admin.getAdminPhone().equals(phone)) {
                    out.println("手机号已存在!");
                    return;
                }
            }
            if (pwd.length() < 3) {
                out.println("密码太短了!");
            } else if (!pwd.equals(pwd2)) {
                out.println("两次密码不一致!");
            } else if (number.matches("^\\d*$")) {
                out.print("账号不能只是数字组成!");
            } else {
                out.print("ok");
            }
        }
    }

    @GetMapping("/sendAdminEmil")
    public void SendAdminEmil(String emil, HttpServletResponse rep) throws IOException {
        PrintWriter out = rep.getWriter();
        if (!emil.matches("^[a-zA-Z0-9_]+@[0-9a-z]+(\\\\.[a-z]+)+")) {
            out.print("1");
            return;
        }
        if (emil != null && !emil.equals("")) {
            String msg = IDUtil.getID().substring(0, 6);
            String title = "【验证码】";
            String text = "【FCMS小说网】您的验证码为" + msg + "，3分中以内有效";
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject(title);
            mailMessage.setText(text);
            mailMessage.setTo(emil);
            mailMessage.setFrom("1069664381@qq.com");
            mailSender.send(mailMessage);
//            EmilUtil.sendEmal(title,text,emil);
            //存入redis，设置60*3秒后过期
            redisUtil.set(emil + "_msg", msg);
            redisUtil.expire(emil + "_msg", 3, TimeUnit.MINUTES);
            out.print("已发送");
        } else {
            out.print("0");
        }
    }


    @GetMapping("/resiger")
    public String resigerOk(String phone, String number, String pwd, String name) {
        HashMap<String, String> map = new HashMap<>();
        //加密
        String pwdMd5 = ShiroMd5Util.toPwdMd5(number, pwd);
        map.put("id", IDUtil.getID());
        map.put("num", number);
        map.put("pwd", pwdMd5);
        map.put("name", name);
        map.put("phone", phone);
        loginService.addAdmin(map);
        return "login";
    }
}
