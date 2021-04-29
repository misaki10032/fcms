package com.cxy.fcms.controller;

import com.cxy.fcms.pojo.ComUser;
import com.cxy.fcms.pojo.SysAdminInfo;
import com.cxy.fcms.service.UserInfoService;
import com.cxy.fcms.service.UserService;
import com.cxy.fcms.util.IDUtil;
import com.cxy.fcms.util.LayuiReplay;
import com.cxy.fcms.util.ShiroMd5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserController
 * @Author 码农天宇 陈新予 李文博
 * @Date 2021/4/17 18:11
 * @Version 1.0
 */

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserInfoService userInfoService;
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
    public void userResiger(String phone, String pwd, String pwd2, String name, HttpServletResponse resp)
            throws IOException {
        PrintWriter out = resp.getWriter();
        List<ComUser> users = userService.selUser();
        if (phone == null || pwd == null || pwd2 == null || name == null) {
            out.println("发生了系统故障!");
        } else if (phone.equals("") || pwd.equals("") || pwd2.equals("") || name.equals("")) {
            out.println("您的表单没有填完哦!");
        } else {
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
    public String UserResigerOk(String phone, String pwd, String name, String emil) {
        HashMap<String, String> map = new HashMap<>();
        //用账号作为盐值进行加密
        String saltPassWord = ShiroMd5Util.toPwdMd5(phone, pwd);
        map.put("id", IDUtil.getID());
        map.put("userPwd", saltPassWord);
        map.put("userName", name);
        map.put("userPhone", phone);
        userService.addUser(map);
        //info
        ComUser comUser = userService.selUserByPhone(phone);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("adminId", comUser.getId());
        map1.put("emil", emil);
        map1.put("adminCall", comUser.getUserName());
        userInfoService.addAdmin(map1);
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
    public String toUserHome(String id, Model model) {
        SysAdminInfo sysAdminInfo = userInfoService.selAdmin(id);
        model.addAttribute("adminInfo", sysAdminInfo);
        System.out.println(sysAdminInfo);
        return "reception/userHome";
    }

    @GetMapping("/revUser")
    public String RevUser(String id, Model model) {
        SysAdminInfo sysAdminInfo = userInfoService.selAdmin(id);
        System.out.println(sysAdminInfo);
        model.addAttribute("userInfo", sysAdminInfo);
        return "reception/rev";
    }
    @GetMapping("/submitRev")
    @ResponseBody
    public String submitRev(String hidden,String call,String name,String adminSex,String adminAge,String adminAddress,
                            String adminDec,String adminEmail,Model model){
        System.out.println("hidden的值是"+hidden);
        Map<String, Object> map = new HashMap<>();
        map.put("adminName",name);
        map.put("adminAge",adminAge);
        map.put("adminSex",adminSex);
        map.put("adminAddress",adminAddress);
        map.put("adminDec",adminDec);
        map.put("adminCall",call);
        map.put("adminEmail",adminEmail);
        map.put("id",hidden);
        userInfoService.revAdmin(map);
        SysAdminInfo sysAdminInfo = userInfoService.selAdmin(hidden);
        model.addAttribute("adminInfo",sysAdminInfo);
        return "";
    }


}
