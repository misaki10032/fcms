package com.cxy.fcms.service.impl;

import com.cxy.fcms.mapper.SysAdminMapper;
import com.cxy.fcms.pojo.SysAdmin;
import com.cxy.fcms.service.LoginService;
import com.cxy.fcms.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    SysAdminMapper adminMapper;//adminmapper注入
    @Autowired
    RedisUtil redisUtil;
    @Override
    public List<SysAdmin> getAdmins() {
        try{
            //从redis中查找allAdmins的list
            List<Object> allAdmins=redisUtil.lRange("allAdmins",0,-1);
            System.out.println("===================尝试redis读取数据=========================");
            //不为空则读取,为空则查询数据库
            if(!allAdmins.isEmpty()){
                System.out.println("=====================从Redis中读取数据=======================");
                List<SysAdmin> admins = new ArrayList<>();
                for (Object admin: allAdmins) {
                    admins.add((SysAdmin)admin);
                }
                return admins;
            }else{
                System.out.println("===================== Redis 中没有数据=======================");
                System.out.println("=====================从MySQL中读取数据=======================");
                List<SysAdmin> allAdmins1 = adminMapper.getAllAdmins();
                System.out.println("=====================向 Redis 中存数据=======================");
                for (SysAdmin admin:allAdmins1) {
                    redisUtil.lLeftPush("allAdmins",admin);
                }
                return allAdmins1;
            }
        }catch (Exception e){
            System.out.println("=====================  Redis 宕机   =======================");
            System.out.println("=====================从MySQL中读取数据=======================");
            return  adminMapper.getAllAdmins();
        }
    }
    @Override
    public void addAdmin(HashMap<String,String> map) {
        try {
            adminMapper.addAdmin(map);//添加新注册的用户
            List<SysAdmin> allAdmins = adminMapper.getAllAdmins();//更新redis信息
            redisUtil.delete("allAdmins");//删除原来的list
            System.out.println("=====================从MySQL中读取数据=======================");
            List<SysAdmin> allAdmins1 = adminMapper.getAllAdmins();
            System.out.println("=====================向 Redis 中存数据=======================");
            for (SysAdmin admin:allAdmins1) {
                redisUtil.lLeftPush("allAdmins",admin);
            }
        } catch (Exception e) {
            adminMapper.addAdmin(map);
        }
    }
}
