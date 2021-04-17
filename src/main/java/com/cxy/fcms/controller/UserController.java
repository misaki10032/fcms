package com.cxy.fcms.controller;

import com.cxy.fcms.pojo.ComUser;
import com.cxy.fcms.service.UserService;
import com.cxy.fcms.util.LayuiReplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("/getUsers")
    @ResponseBody
    public Object getAllUsers(){
        List<ComUser> users = userService.selUser();
        return new LayuiReplay<ComUser>(0, "OK", users.size(), users);
    }

    @RequestMapping("/toUsers")
    public String toUsersInfos(){
        return "/back/user/userlist";
    }
}
