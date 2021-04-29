package com.cxy.fcms.config;
import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * @ClassName UserController
 * @Author 陈新予
 * @Date 2021/4/16 15:22
 * @Version 1.0
 */
@Configuration
public class ShiroConfig {
    //MD5加密
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("md5");
        //加密次数
        credentialsMatcher.setHashIterations(1024);
        //是否存储为16进制(一定要开启)
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    //shiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("mysecurityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);//设置安全管理器
        /**
         * non:无序认证即可
         * authc:必须认证才可以访问
         * user:必须拥有记住我功能
         * perms:拥有某个资源的权限才可以访
         * role:拥有某个角色权限才可以访问
         */
        Map<String,String> filtermap = new LinkedHashMap<>();//过滤器map
//        filtermap.put("/add","perms[123]");
//        filtermap.put("/update","perms[111]");
//        filtermap.put("/select","perms[select]");
        bean.setFilterChainDefinitionMap(filtermap);//添加shiro过滤器

        bean.setLoginUrl("/login");//设置登录页面
        bean.setUnauthorizedUrl("/noauthor");//设置未授权跳转页面
        return bean;
    }

    //DefaultWebSecurityManner
    @Bean(name = "mysecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("myRealm") AuthorizingRealm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm);
        return securityManager;
    }

    //Realm
    @Bean(name = "myRealm")
    public AuthorizingRealm myRealm() {
        MyRealm realm = new MyRealm();
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

    //配置方言
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
