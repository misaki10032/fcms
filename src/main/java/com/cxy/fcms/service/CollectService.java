package com.cxy.fcms.service;

import com.cxy.fcms.pojo.ComCollect;

import java.util.List;
import java.util.Map;

public interface CollectService {
    /**
     * 用户根据userid 查询收藏
     * */
    List<ComCollect> selCollectsByUserId(String userid);
    /**
     * 管理员根据ficId 查询收藏
     * */
    List<ComCollect> selCollectsByFicId(String ficid);
    /**
     * 删除/取消收藏
     * */
     int delCollect(String id);
    /**
     * 添加收藏
     * */
     int addCollect(Map<String,Object> map);
    /**
     * 不显示收藏
     * */
     int noShow(String id);
    /**
     * 显示收藏
     * */
     int show(String id);
}
