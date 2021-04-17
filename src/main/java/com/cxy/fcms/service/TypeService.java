package com.cxy.fcms.service;

import java.util.HashMap;
import java.util.Map;

public interface TypeService {
    /**
     * 添加类型
     */
    int addType(HashMap<String, String> map);

    /**
     * 通过tyName获取类型id
     */
    String getTypeIdByName(String tyName);

    /**
     * 通过类型id获取tyName
     */
    String getTypeNameById(String id);

    /**
     * 修改类型
     */
    int revType(Map<String, Object> map);

    /**
     * 删除类型
     */
    int delType(String id);

    /**
     * 不显示类型
     */
    int noShowType(String id);

    /**
     * 显示类型
     */
    int showType(String id);
}
