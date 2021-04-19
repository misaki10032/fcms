package com.cxy.fcms.service.impl;

import com.cxy.fcms.mapper.ComUserMapper;
import com.cxy.fcms.pojo.ComUser;
import com.cxy.fcms.service.UserService;
import com.cxy.fcms.util.RedisUtil;
import com.cxy.fcms.util.TimeOutSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    ComUserMapper comUserMapper;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public int addUser(HashMap<String, String> map) {
        try {
            int i = comUserMapper.addUser(map);//添加新注册的用户
            redisUtil.delete("comUsers");//删除原来的list
            System.out.println("=====================从MySQL中读取数据=======================");
            List<ComUser> comUsers = comUserMapper.selUser();
            System.out.println("=====================向 Redis 中存数据=======================");
            for (ComUser user : comUsers) {
                redisUtil.lRightPush("comUsers", user);
            }
            return i;
        } catch (Exception e) {
            return comUserMapper.addUser(map);
        }
    }

    @Override
    public int delUser(String id) {
        try {
            int i = comUserMapper.delUser(id);//添加新注册的用户
            redisUtil.delete("comUsers");//删除原来的list
            System.out.println("=====================从MySQL中读取数据=======================");
            List<ComUser> comUsers = comUserMapper.selUser();
            System.out.println("=====================向 Redis 中存数据=======================");
            for (ComUser user : comUsers) {
                redisUtil.lRightPush("comUsers", user);
            }
            redisUtil.expire("comUsers", TimeOutSetting.REDIS_TIME_OUT, TimeUnit.SECONDS);
            return i;
        } catch (Exception e) {
            return comUserMapper.delUser(id);
        }
    }

    @Override
    public int revUser(Map<String, Object> map) {
        try {
            int i = comUserMapper.revUser(map);//添加新注册的用户
            redisUtil.delete("comUsers");//删除原来的list
            System.out.println("=====================从MySQL中读取数据=======================");
            List<ComUser> comUsers = comUserMapper.selUser();
            System.out.println("=====================向 Redis 中存数据=======================");
            for (ComUser user : comUsers) {
                redisUtil.lRightPush("comUsers", user);
            }
            redisUtil.expire("comUsers", TimeOutSetting.REDIS_TIME_OUT, TimeUnit.SECONDS);
            return i;
        } catch (Exception e) {
            return comUserMapper.revUser(map);
        }
    }

    @Override
    public ComUser logUser(Map<String, Object> map) {
        return comUserMapper.logUser(map);
    }

    @Override
    public List<ComUser> selUser() {
        try {
            List<Object> comUsers = redisUtil.lRange("comUsers", 0, -1);
            if (!comUsers.isEmpty()) {
                System.out.println("=====================从Redis中读取数据=======================");
                List<ComUser> comUsers1 = new ArrayList<>();
                for (Object user : comUsers) {
                    comUsers1.add((ComUser) user);
                }
                return comUsers1;
            } else {
                List<ComUser> comUsers1 = comUserMapper.selUser();
                for (ComUser user : comUsers1) {
                    redisUtil.lRightPush("comUsers", user);
                }
                redisUtil.expire("comUsers", TimeOutSetting.REDIS_TIME_OUT, TimeUnit.SECONDS);
                return comUsers1;
            }
        } catch (Exception e) {
            return comUserMapper.selUser();
        }
    }
}
