package com.cxy.fcms.service.impl;

import com.cxy.fcms.mapper.ComLinkMapper;
import com.cxy.fcms.pojo.ComLink;
import com.cxy.fcms.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

public class LinkServiceImpl implements LinkService {
    @Autowired
    ComLinkMapper linkMapper;

    /**
     * 查询所有链接
     */
    @Override
    public List<ComLink> searchLinks() {
        return linkMapper.searchLinks();
    }

    /**
     * 查询显示的链接
     */
    @Override
    public List<ComLink> searchShowLinks() {
        return linkMapper.searchShowLinks();
    }

    /**
     * 增加链接
     */
    @Override
    public int addLink(HashMap<String, Object> map) {
        return linkMapper.addLink(map);
    }

    /**
     * 删除链接
     */
    @Override
    public int delLink(String id) {
        return linkMapper.delLink(id);
    }

    /**
     * 修改链接
     */
    @Override
    public int revLink(HashMap<String, Object> map) {
        return linkMapper.revLink(map);
    }

    /**
     * 不显示链接
     */
    @Override
    public int noShowLink(String id) {
        return linkMapper.noShowLink(id);
    }

    /**
     * 显示链接
     */
    @Override
    public int showLink(String id) {
        return linkMapper.showLink(id);
    }
}
