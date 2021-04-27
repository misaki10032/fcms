package com.cxy.fcms.service;

import com.cxy.fcms.pojo.SysAdminInfo;

import java.util.Map;

public interface UserInfoService {
    SysAdminInfo selAdmin(String id);

    int revAdmin(Map<String, Object> map);

    /**
     * 添加
     */
    int addAdmin(Map<String, Object> map);
}
