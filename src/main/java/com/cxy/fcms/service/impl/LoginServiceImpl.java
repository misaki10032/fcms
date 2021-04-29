package com.cxy.fcms.service.impl;

import com.cxy.fcms.mapper.SysAdminMapper;
import com.cxy.fcms.pojo.SysAdmin;
import com.cxy.fcms.service.LoginService;
import com.cxy.fcms.util.RedisUtil;
import com.cxy.fcms.util.TimeOutSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
/**
 * @ClassName UserController
 * @Author 陈新予
 * @Date 2021/4/16 15:22
 * @Version 1.0
 */
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
            //不为空则读取,为空则查询数据库
            if(!allAdmins.isEmpty()){
                List<SysAdmin> admins = new ArrayList<>();
                for (Object admin: allAdmins) {
                    admins.add((SysAdmin)admin);
                }
                return admins;
            }else{
                List<SysAdmin> allAdmins1 = adminMapper.getAllAdmins();
                for (SysAdmin admin:allAdmins1) {
                    redisUtil.lLeftPush("allAdmins",admin);
                }
                redisUtil.expire("allAdmins", TimeOutSetting.REDIS_TIME_OUT, TimeUnit.SECONDS);
                return allAdmins1;
            }
        }catch (Exception e){
            System.out.println("=====================  Redis 宕机   =======================");
            return  adminMapper.getAllAdmins();
        }
    }
    @Override
    public void addAdmin(HashMap<String,String> map) {
        try {
            adminMapper.addAdmin(map);//添加新注册的用户
            List<SysAdmin> allAdmins = adminMapper.getAllAdmins();//更新redis信息
            redisUtil.delete("allAdmins");//删除原来的list
            List<SysAdmin> allAdmins1 = adminMapper.getAllAdmins();
            for (SysAdmin admin:allAdmins1) {
                redisUtil.lLeftPush("allAdmins",admin);
            }
            redisUtil.expire("allAdmins", 300, TimeUnit.SECONDS);
        } catch (Exception e) {
            adminMapper.addAdmin(map);
        }
    }
}
