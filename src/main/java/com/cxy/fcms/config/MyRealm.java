package com.cxy.fcms.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.Map;

public class MyRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权--------start!");
        //授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String author = (String) session.getAttribute("author");
        System.out.println("授予用户:"+author+"权限");
        info.addStringPermission(author);
        info.addStringPermission("select");

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        System.out.println("认证----start!-----");
//        //模拟数据库取数据
//        Map<String,String> map = new HashMap<>();
//        map.put("admin","123");
//        map.put("123","111");
//        map.put("user","111");
//        //数据库读取
//        List<Users> users = usersMapper.getAllUsers();
//        UsernamePasswordToken userToken = (UsernamePasswordToken)token;
//        String username = userToken.getUsername();
//        for (Users user:users) {
//            if(user.getUserPhone().equals(username)){
//                ByteSource credentialsSalt = ByteSource.Util.bytes(username+"c1x2y3e4h");
//                System.out.println("认证----isok!-----");
//                if(user.getUserStatus()==0){
//                    throw new LockedAccountException();
//                }
//                return new SimpleAuthenticationInfo(
//                        username, //存值可以通过subject取
//                        user.getUserPwd(), //密码
//                        credentialsSalt,//salt=username+salt
//                        getName()  //realm name
//                );
//            }
//        }
//        System.out.println("认证----admin!-----");
//        if(!map.containsKey(username)){
//            System.out.println("-------err!!-----");
//            return null;//用户不存在
//        }else{
//            System.out.println("认证----isok!-----");
//            ByteSource credentialsSalt = ByteSource.Util.bytes(username+"c1x2y3e4h");
//            return new SimpleAuthenticationInfo(
//                    username, //存值可以通过subject取
//                    map.get(username), //密码
//                    credentialsSalt,//salt=username+salt
//                    getName()  //realm name
//            );
//        }
        return null;
    }
}
