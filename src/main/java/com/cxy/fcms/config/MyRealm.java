package com.cxy.fcms.config;

import com.cxy.fcms.pojo.ComUser;
import com.cxy.fcms.pojo.SysAdmin;
import com.cxy.fcms.service.LoginService;
import com.cxy.fcms.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    LoginService loginService;
    @Autowired
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("=====================授权========================");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        SysAdmin admin = (SysAdmin) session.getAttribute("admin");
        String author = admin.getAdminAuthority();
        System.out.println("授予用户:" + author + "权限");
        info.addStringPermission(author);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("=====================管理员认证========================");
        List<SysAdmin> admins = loginService.getAdmins();
        UsernamePasswordToken userToken = (UsernamePasswordToken)token;
        String username = userToken.getUsername();
        for (SysAdmin admin : admins) {
            if (admin.getAdminNum().equals(username)) {
                System.out.println("------->admin------->ok");
                Subject subject = SecurityUtils.getSubject();
                Session session = subject.getSession();
                session.setAttribute("admin", admin);
                session.setAttribute("isAdmin", true);
                return new SimpleAuthenticationInfo("", admin.getAdminPwd(), "");
            }
        }
        System.out.println("===============================开始用户认证===============================");
        List<ComUser> users = userService.selUser();
        for (ComUser user : users) {
            if (user.getUserPhone().equals(username)) {
                System.out.println("------->user------->ok");
                Subject subject = SecurityUtils.getSubject();
                Session session = subject.getSession();
                session.setAttribute("user", user);
                session.setAttribute("isAdmin", false);
                return new SimpleAuthenticationInfo("", user.getUserPwd(), "");
            }
        }
        return null;
    }
}
