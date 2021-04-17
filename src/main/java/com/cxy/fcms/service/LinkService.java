package com.cxy.fcms.service;

import com.cxy.fcms.pojo.ComLink;

import java.util.HashMap;
import java.util.List;

public interface LinkService {
    /**
     * 查询所有链接
     */
    List<ComLink> searchLinks();

    /**
     * 查询可显示的链接
     */
    List<ComLink> searchShowLinks();

    /**
     * 增加链接
     */
    int addLink(HashMap<String, Object> map);

    /**
     * 删除链接
     */
    int delLink(String id);

    /**
     * 修改链接
     */
    int revLink(HashMap<String, Object> map);

    /**
     * 不显示链接
     * id
     */
    int noShowLink(String id);

    /**
     * 显示链接
     * id
     */
    int showLink(String id);
}
