package com.cxy.fcms.service.impl;

import com.cxy.fcms.mapper.SysAdminMapper;
import com.cxy.fcms.pojo.SysAdmin;
import com.cxy.fcms.service.AdminService;
import com.cxy.fcms.util.RedisUtil;
import com.cxy.fcms.util.SelectRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName AdmnServiceImpl
 * @Author 陈新予(blank)
 * @Date 2021/5/11
 * @Version 1.0
 */
@Service
public class AdmnServiceImpl implements AdminService {
    @Autowired
    SysAdminMapper adminMapper;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 获取全部的管理员对象
     *
     * @return 返回集合类型
     */
    @Override
    public List<SysAdmin> getAdmins() {
        try {
            List<Object> objects = SelectRedis.selectRedis(redisUtil, "allAdmins", adminMapper, "getAllAdmins");
            List<SysAdmin> admins = new ArrayList<>();
            for (Object object : objects) {
                if (object instanceof SysAdmin) {
                    admins.add((SysAdmin) object);
                }
            }
            return admins;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return adminMapper.getAllAdmins();
    }

    @Override
    public void revAdmin(HashMap<String, String> map) {
        try {
            adminMapper.revAdminAuthorById(map);
            redisUtil.delete("allAdmins");
            List<SysAdmin> allAdmins1 = adminMapper.getAllAdmins();
            for (SysAdmin admin : allAdmins1) {
                redisUtil.lLeftPush("allAdmins", admin);
            }
            redisUtil.expire("allAdmins", 300, TimeUnit.SECONDS);
        } catch (Exception e) {
            adminMapper.revAdminAuthorById(map);
        }
    }

    @Override
    public void delAdmin(HashMap<String, String> map) {
        try {
            adminMapper.delAdmin(map);
            redisUtil.delete("allAdmins");
            List<SysAdmin> allAdmins1 = adminMapper.getAllAdmins();
            for (SysAdmin admin : allAdmins1) {
                redisUtil.lLeftPush("allAdmins", admin);
            }
            redisUtil.expire("allAdmins", 300, TimeUnit.SECONDS);
        } catch (Exception e) {
            adminMapper.delAdmin(map);
        }
    }
}
