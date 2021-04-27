package com.cxy.fcms.service.impl;

import com.cxy.fcms.mapper.SysAdminInfoMapper;
import com.cxy.fcms.pojo.SysAdminInfo;
import com.cxy.fcms.service.UserInfoService;
import com.cxy.fcms.util.RedisUtil;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    SysAdminInfoMapper sysAdminInfoMapper;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public SysAdminInfo selAdmin(String id) {
        return sysAdminInfoMapper.selAdmin(id);
    }

    @Override
    public int revAdmin(Map<String, Object> map) {
        return sysAdminInfoMapper.revAdmin(map);
    }

    @Override
    public int addAdmin(Map<String, Object> map) {
        return sysAdminInfoMapper.addAdmin(map);
    }


}
