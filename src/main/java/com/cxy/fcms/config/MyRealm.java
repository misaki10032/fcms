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
/**
 * @ClassName UserController
 * @Author 陈新予
 * @Date 2021/4/16 15:22
 * @Version 1.0
 */
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
        Object admin = session.getAttribute("admin");
        SysAdmin thisadmin = null;
        if (admin instanceof SysAdmin) {
            thisadmin = (SysAdmin) admin;
            String author = thisadmin.getAdminAuthority();
            System.out.println("授予用户:" + author + "权限");
            info.addStringPermission(author);
        }
        info.addStringPermission("loginOK");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("=====================管理员认证========================");
        List<SysAdmin> admins = loginService.getAdmins();
        UsernamePasswordToken userToken = (UsernamePasswordToken)token;
        String username = userToken.getUsername();
        String realmName = getName();
        for (SysAdmin admin : admins) {
            if (admin.getAdminNum().equals(username)) {
                System.out.println("------->admin------->ok");
                Subject subject = SecurityUtils.getSubject();
                Session session = subject.getSession();
                if (admin.getIsDel() != null && !admin.getIsDel().equals("正常")) {
                    throw new LockedAccountException();
                }
                session.setAttribute("admin", admin);
                session.setAttribute("isAdmin", true);
                //盐值
                ByteSource salt = ByteSource.Util.bytes(admin.getAdminNum());
                return new SimpleAuthenticationInfo(admin, admin.getAdminPwd(), salt, realmName);
            }
        }
        System.out.println("===============================开始用户认证===============================");
        List<ComUser> users = userService.selUser();
        for (ComUser user : users) {
            if (user.getUserPhone().equals(username)) {
                if (user.getIsBanned() == 1) {
                    throw new LockedAccountException();
                }
                System.out.println("------->user------->ok");
                Subject subject = SecurityUtils.getSubject();
                Session session = subject.getSession();
                session.setAttribute("user", user);
                session.setAttribute("isAdmin", false);
                //盐值
                ByteSource salt = ByteSource.Util.bytes(user.getUserPhone());
                return new SimpleAuthenticationInfo(user, user.getUserPwd(), salt, realmName);
            }
        }
        return null;
    }
}
