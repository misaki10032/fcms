package com.cxy.fcms.controller;

import com.cxy.fcms.pojo.SysAdmin;
import com.cxy.fcms.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;
    @GetMapping({"/","hello"})
    public String welcome(){
        return "index";
    }
    @GetMapping("/tologin")
    public String toLogin(){
        return "login";
    }
    @PostMapping("/login")
    @ResponseBody
    public String Login(){
        //获取
        List<SysAdmin> admins = loginService.getAdmins();
        return "session";
    }
}
