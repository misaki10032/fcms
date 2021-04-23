package com.cxy.fcms.controller;

import com.cxy.fcms.pojo.ComUser;
import com.cxy.fcms.pojo.SysAdmin;
import com.cxy.fcms.service.LoginService;
import com.cxy.fcms.util.IDUtil;
import com.cxy.fcms.util.ShiroMd5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;
    //起始页
    @GetMapping({"/","hello"})
    public String welcome(){
        return "index";
    }
    /**
     * 登录相关
     */
    @GetMapping("/tologin")
    public String toLogin(){
        return "login";
    }

    @PostMapping("/login")
    public String Login(Model model, String nums, String pwd){
        Subject subject = SecurityUtils.getSubject();//获取用户信息
        UsernamePasswordToken token = new UsernamePasswordToken(nums,pwd);//封装
        try {
            subject.login(token);
            //通过subject取
            Session session = subject.getSession();
            Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
            System.out.println("isAdmin" + isAdmin);
            if (isAdmin) {
                SysAdmin admin = (SysAdmin) session.getAttribute("admin");
                model.addAttribute("adminName", admin.getAdminName());
                return "back/adminHome";
            } else {
                model.addAttribute("msg", "没有用户名为-->" + token.getPrincipal());
                return "login";
            }
        } catch (UnknownAccountException uae) {
            model.addAttribute("msg", "没有用户名为-->" + token.getPrincipal());
            return "login";
        } catch (IncorrectCredentialsException ice) {
            model.addAttribute("msg", "账号或密码错误!");
            return "login";
        } catch (LockedAccountException lae) {
            model.addAttribute("msg", "用户名 : " + token.getPrincipal() + ",被锁定了.请联系您的管理员解锁.");
            return "login";
        } catch (Exception e) {
            model.addAttribute("msg", "系统发生未知错误!");
            return "userlogin";
        }
    }
    @GetMapping("/logOut")
    public String logOut(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "index";
    }

    /**
     * 管理员注册相关
     */
    @GetMapping("/withResiger")
    public void resiger(String phone, String number, String pwd, String pwd2, String name, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        List<SysAdmin> admins = loginService.getAdmins();
        if (phone == null || number == null || pwd == null || pwd2 == null || name == null) {
            out.println("发生了系统故障!");
        }else if(phone.equals("")||number.equals("")||pwd.equals("")||pwd2.equals("")||name.equals("")){
            out.println("您的表单没有填完哦!");
        }else {
            for (SysAdmin admin : admins) {
                if(admin.getAdminNum().equals(number)){
                    out.println("账号已存在!");
                    return;
                }
                if(admin.getAdminPhone().equals(phone)){
                    out.println("手机号已存在!");
                    return;
                }
            }
            if(pwd.length()<3){
                out.println("密码太短了!");
            }else if (!pwd.equals(pwd2)) {
                out.println("两次密码不一致!");
            } else if (number.matches("^\\d*$")) {
                out.print("账号不能只是数字组成!");
            } else {
                out.print("ok");
            }
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

    /**
     * 用户登录
     */
    @GetMapping("/usertologin")
    public String userToLogin() {
        return "userlogin";
    }

    @PostMapping("/userlogin")
    public String userLogin(Model model, String nums, String pwd) {
        Subject subject = SecurityUtils.getSubject();//获取用户信息
        UsernamePasswordToken token = new UsernamePasswordToken(nums, pwd);//封装
        try {
            subject.login(token);
            //通过subject取
            Session session = subject.getSession();
            Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
            System.out.println("isAdmin" + isAdmin);
            if (!isAdmin) {
                ComUser user = (ComUser) session.getAttribute("user");
                session.setAttribute("user", user);
                model.addAttribute("username", user.getUserName());
                return "index";
            } else {
                model.addAttribute("msg", "系统发生位置错误!");
                return "userlogin";
            }
        } catch (UnknownAccountException uae) {
            model.addAttribute("msg", "没有用户名为-->" + token.getPrincipal());
            return "userlogin";
        } catch (IncorrectCredentialsException ice) {
            model.addAttribute("msg", "账号或密码错误!");
            return "userlogin";
        } catch (LockedAccountException lae) {
            model.addAttribute("msg", "用户名 : " + token.getPrincipal() + ",被锁定了.请联系您的管理员解锁.");
            return "userlogin";
        } catch (Exception e) {
            model.addAttribute("msg", "系统发生未知错误!");
            return "userlogin";
        }
    }

}
