package com.cxy.fcms.controller;


import com.cxy.fcms.pojo.SysAdmin;
import com.cxy.fcms.service.LoginService;
import com.cxy.fcms.util.IDUtil;
import com.cxy.fcms.util.RedisUtil;
import com.cxy.fcms.util.ShiroMd5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
/**
 * @ClassName UserController
 * @Author 陈新予
 * @Date 2021/4/29 18:11
 * @Version 1.0
 */
@Controller
public class AdminController {
    @Autowired
    LoginService loginService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    JavaMailSenderImpl mailSender;

    /**
     * 打开小说表格
     *
     * @return 小说表格
     */
    @GetMapping("/toFicList")
    public String toFicList() {
        return "back/fiction/booklist";
    }

    /**
     * 管理员页面
     *
     * @return 管理员页面
     */
    @GetMapping("/gohome")
    public String toAdminInfo() {
        return "back/adminInfo";
    }

    /**
     * 管理员注册
     *
     * @param phone   手机号
     * @param number  账号
     * @param pwd     密码
     * @param pwd2    二次确认的密码
     * @param name    姓名
     * @param emil    邮箱
     * @param emilRes 邮箱验证码
     * @throws IOException io异常
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

    /**
     * 发送验证邮件
     *
     * @param emil 邮箱地址
     * @throws IOException io异常
     */
    @GetMapping("/sendAdminEmil")
    public void SendAdminEmil(String emil, HttpServletResponse rep) throws IOException {
        PrintWriter out = rep.getWriter();
        if (emil == null || !emil.matches("^\\w+@\\w{2,6}\\.\\w{2,6}$")) {
            out.print("0");
            return;
        }
        String msg = IDUtil.getID().substring(0, 6);
        String title = "【验证码】";
        String text = "【FCMS小说网】您的验证码为" + msg + "，3分中以内有效";
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(title);
        mailMessage.setText(text);
        mailMessage.setTo(emil);
        mailMessage.setFrom("fcms_snut@qq.com");
        mailSender.send(mailMessage);
        try {
            redisUtil.set(emil + "_msg", msg);
            redisUtil.expire(emil + "_msg", 360, TimeUnit.SECONDS);
        } catch (Exception e) {
            out.print("0");
        }
        out.print("已发送");
    }

    /**
     * 提交admin注册
     *
     * @param phone  手机号
     * @param number 账号
     * @param pwd    密码
     * @param name   姓名
     * @return 返回登陆页面
     */
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
