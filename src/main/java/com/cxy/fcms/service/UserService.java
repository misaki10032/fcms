package com.cxy.fcms.service;

import com.cxy.fcms.pojo.ComUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 添加用户
     * */
    int addUser(HashMap<String,String> map);
    /**
     * 禁用用户
     * */
    int delUser(String id);
    /**
     * 修改用户
     * */
    int revUser(Map<String,Object> map);
    /**
     * 登录注册验证
     * */
    ComUser logUser(Map<String,Object> map);
    /**
     * 查询所有用户
     * */
    List<ComUser> selUser();
}
