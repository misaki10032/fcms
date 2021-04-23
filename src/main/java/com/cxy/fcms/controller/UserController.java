package com.cxy.fcms.controller;

import com.cxy.fcms.pojo.ComUser;
import com.cxy.fcms.pojo.SysAdmin;
import com.cxy.fcms.service.UserService;
import com.cxy.fcms.util.IDUtil;
import com.cxy.fcms.util.LayuiReplay;
import com.cxy.fcms.util.RedisUtil;
import com.cxy.fcms.util.ShiroMd5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author 码农天宇
 * @Date 2021/4/17 18:11
 * @Version 1.0
 */

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RedisUtil redisUtil;

    @GetMapping("/getUsers")
    @ResponseBody
    public Object getAllUsers() {
        List<ComUser> users = userService.selUser();
        return new LayuiReplay<ComUser>(0, "OK", users.size(), users);
    }

    @RequestMapping("/toUsers")
    public String toUsersInfos() {
        return "/back/user/userlist";
    }

    /**
     * 用户注册
     */
    @GetMapping("/userWithResiger")
    public void userResiger(String phone, String pwd, String pwd2, String name, String emil, String emilRes, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        List<ComUser> users = userService.selUser();
        if (phone == null || pwd == null || pwd2 == null || name == null) {
            out.println("发生了系统故障!");
        } else if (phone.equals("") || pwd.equals("") || pwd2.equals("") || name.equals("")) {
            out.println("您的表单没有填完哦!");
        } else {
            String msg = (String) redisUtil.get(emil + "_msg");
            if (msg == null || msg.equals("") || !emilRes.equals(msg)) {
                out.print("验证码错误，或已过期");
                return;
            }
            for (ComUser user : users) {
                if (user.getUserPhone().equals(phone)) {
                    out.println("手机号已存在!");
                    return;
                }
            }
            if (pwd.length() < 3) {
                out.println("密码太短了哦~");
            } else if (!pwd.equals(pwd2)) {
                out.println("两次密码不一致");
            } else {
                out.print("ok");
            }
        }
    }

    @GetMapping("/userResiger")
    public String UserResigerOk(String phone, String pwd, String name) {
        HashMap<String, String> map = new HashMap<>();
        //用账号作为盐值进行加密
        String saltPassWord = ShiroMd5Util.toPwdMd5(phone, pwd);
        map.put("id", IDUtil.getID());
        map.put("userPwd", saltPassWord);
        map.put("userName", name);
        map.put("userPhone", phone);
        userService.addUser(map);
        return "userlogin";
    }

    /**
     * 封禁用户
     */
    @GetMapping("/onlock")
    public void onlock(String id, String isBlock, HttpServletResponse rep) throws IOException {
        PrintWriter out = rep.getWriter();
        if (isBlock.equals("0")) {
            userService.delUser(id);
            out.print("1");
        } else {
            userService.unDelUser(id);
            out.print("0");
        }
    }

    @GetMapping("/toUserHome")
    public String toUserHome() {
        return "reception/userHome";
    }

    @GetMapping("/revUser")
    public String RevUser(String id) {
        /*
            需要携带的数据
         */
        return "reception/rev";
    }

}
