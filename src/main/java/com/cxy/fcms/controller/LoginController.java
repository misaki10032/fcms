package com.cxy.fcms.controller;

import com.cxy.fcms.pojo.ComFiction;
import com.cxy.fcms.pojo.ComUser;
import com.cxy.fcms.pojo.SysAdmin;
import com.cxy.fcms.pojo.SysAdminInfo;
import com.cxy.fcms.service.*;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName UserController
 * @Author 陈新予
 * @Date 2021/4/29 18:11
 * @Version 1.0
 */
@Controller
public class LoginController {
    @Autowired
    LoginService loginService;
    @Autowired
    FictionService fictionService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;

    /**
     * 欢迎页,起始页
     *
     * @return 欢迎页, 起始页
     */
    @GetMapping({"", "hello"})
    public String welcome(Model model, HttpSession session, HttpServletRequest req) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin != null && isAdmin) {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            session = req.getSession();
            List<ComFiction> fictionsOrderByHost = fictionService.getFictionsOrderByHost();
            List<ComFiction> fictionOrderByTime = fictionService.getFictionOrderByTime();
            List<String> host = new ArrayList<>();
            List<String> time = new ArrayList<>();
            List<String> timeimg = new ArrayList<>();
            List<String> hostimg = new ArrayList<>();
            for (ComFiction fiction : fictionsOrderByHost) {
                host.add(fiction.getFicName());
                hostimg.add(fiction.getFicImg());
            }
            for (ComFiction fiction : fictionOrderByTime) {
                time.add(fiction.getFicName());
                timeimg.add(fiction.getFicImg());

            }
            System.out.println(timeimg);
            System.out.println(hostimg);
            session.setAttribute("byhost", host);
            session.setAttribute("bytime", time);
            session.setAttribute("timeimg", timeimg);
            session.setAttribute("hostimg", hostimg);
            return "index";
        }
        session = req.getSession();
        List<ComFiction> fictionsOrderByHost = fictionService.getFictionsOrderByHost();
        List<ComFiction> fictionOrderByTime = fictionService.getFictionOrderByTime();
        List<String> host = new ArrayList<>();
        List<String> time = new ArrayList<>();
        List<String> timeimg = new ArrayList<>();
        List<String> hostimg = new ArrayList<>();
        for (ComFiction fiction : fictionsOrderByHost) {
            host.add(fiction.getFicName());
            hostimg.add(fiction.getFicImg());
        }
        for (ComFiction fiction : fictionOrderByTime) {
            time.add(fiction.getFicName());
            timeimg.add(fiction.getFicImg());
        }
        System.out.println(timeimg);
        System.out.println(hostimg);
        session.setAttribute("byhost", host);
        session.setAttribute("bytime", time);
        session.setAttribute("timeimg", timeimg);
        session.setAttribute("hostimg", hostimg);
        return "index";
    }

    /**
     * 去往管理员登录页面
     */
    @GetMapping("tologin")
    public String toLogin() {
        return "login";
    }

    /**
     * 管理员登录判断
     *
     * @param nums 账号
     * @param pwd  密码
     * @return 成功进入详细页, 失败返回提示信息
     */
    @PostMapping("login")
    public String Login(Model model, String nums, String pwd) {
        Subject subject = SecurityUtils.getSubject();//获取用户信息
        UsernamePasswordToken token = new UsernamePasswordToken(nums, pwd);//封装
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
            return "login";
        }
    }

    /**
     * 用户登录
     */
    @GetMapping("usertologin")
    public String userToLogin() {
        return "userlogin";
    }

    /**
     * 用户登录判断
     *
     * @param nums 账号
     * @param pwd  密码
     * @return 成功进入详细页, 失败返回提示信息
     */
    @PostMapping("userlogin")
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
                SysAdminInfo sysAdminInfo = userInfoService.selAdmin(user.getId());
                session.setAttribute("user", user);
                model.addAttribute("username", user.getUserName());
                model.addAttribute("userId", user.getId());
                session.setAttribute("adminInfo", sysAdminInfo);
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

    /**
     * 退出
     *
     * @return 清除session, 返回起始页
     */
    @GetMapping("logOut")
    public String logOut(HttpServletRequest req) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        HttpSession session = req.getSession();
        List<ComFiction> fictionsOrderByHost = fictionService.getFictionsOrderByHost();
        List<ComFiction> fictionOrderByTime = fictionService.getFictionOrderByTime();
        List<String> host = new ArrayList<>();
        List<String> time = new ArrayList<>();
        List<String> timeimg = new ArrayList<>();
        List<String> hostimg = new ArrayList<>();
        for (ComFiction fiction : fictionsOrderByHost) {
            host.add(fiction.getFicName());
            hostimg.add(fiction.getFicImg());
        }
        for (ComFiction fiction : fictionOrderByTime) {
            time.add(fiction.getFicName());
            timeimg.add(fiction.getFicImg());

        }
        System.out.println(timeimg);
        System.out.println(hostimg);
        session.setAttribute("byhost", host);
        session.setAttribute("bytime", time);
        session.setAttribute("timeimg", timeimg);
        session.setAttribute("hostimg", hostimg);

        return "index";
    }

    /**
     * 没有权限返回页面
     *
     * @return 没有权限返回页面
     */
    @GetMapping("noauthor")
    @ResponseBody
    public String noauthor() {
        return "您没有权限,或者没有登录!";
    }

    /**
     * 导航页
     *
     * @return 导航页
     */
    @GetMapping("todhzhuye")
    public String todao() {
        return "wodezhuye";
    }

    @GetMapping("toHeloPage")
    public String toUpdatePWDPage() {
        return "help/updatepwd";
    }

    @GetMapping("toLockPage")
    public String toUNlockpage() {
        return "help/numUnLock";
    }

    @PostMapping("updatePwd")
    public String UpdataPWD(Model model, String nums, String name, String newpwd, String newpwd1, String type) {
        if (type.equals("管理员")) {
            List<SysAdmin> admins = adminService.getAdmins();
            for (SysAdmin admin : admins) {
                if (admin.getAdminNum().equals(nums)) {
                    if (admin.getAdminPhone().equals(name)) {
                        if (newpwd.equals(newpwd1)) {
                            HashMap<String, String> map = new HashMap<>();
                            String s1 = ShiroMd5Util.toPwdMd5(nums, newpwd);
                            map.put("num", nums);
                            map.put("pwd", s1);
                            adminService.revAdminPwd(map);
                            model.addAttribute("msg", "修改成功");
                            return "login";
                        } else {
                            model.addAttribute("msg", "两次密码不一致");
                            return "help/updatepwd";
                        }
                    } else {
                        model.addAttribute("msg", "密码错误");
                        return "help/updatepwd";
                    }
                }
            }
            model.addAttribute("msg", "没有这个用户->" + nums);
            return "help/updatepwd";
        } else {
            List<ComUser> comUsers = userService.selUser();
            for (ComUser user : comUsers) {
                if (user.getUserPhone().equals(nums)) {
                    if (user.getUserName().equals(name)) {
                        if (newpwd.equals(newpwd1)) {
                            HashMap<String, String> map = new HashMap<>();
                            String s1 = ShiroMd5Util.toPwdMd5(nums, newpwd);
                            map.put("num", nums);
                            map.put("pwd", s1);
                            userService.revUserPwd(map);
                            model.addAttribute("msg", "修改成功");
                            return "userlogin";
                        } else {
                            model.addAttribute("msg", "两次密码不一致");
                            return "help/updatepwd";
                        }
                    } else {
                        model.addAttribute("msg", "密码错误");
                        return "help/updatepwd";
                    }
                }
            }
            model.addAttribute("msg", "没有这个用户->" + nums);
            return "help/updatepwd";
        }

    }
}
