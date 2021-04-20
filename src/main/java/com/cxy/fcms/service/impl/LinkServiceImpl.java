package com.cxy.fcms.service.impl;

import com.cxy.fcms.mapper.ComLinkMapper;
import com.cxy.fcms.pojo.ComComment;
import com.cxy.fcms.pojo.ComLink;
import com.cxy.fcms.pojo.SysAdmin;
import com.cxy.fcms.service.LinkService;
import com.cxy.fcms.util.RedisUtil;
import com.cxy.fcms.util.TimeOutSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    ComLinkMapper linkMapper;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 查询所有链接
     */
    @Override
    public List<ComLink> searchLinks() {
        try{
            //从redis中查找allAdmins的list
            List<Object> allLinks=redisUtil.lRange("allLinks",0,-1);
            System.out.println("===================尝试redis读取数据=========================");
            //不为空则读取,为空则查询数据库
            if(!allLinks.isEmpty()){
                System.out.println("=====================从Redis中读取数据=======================");
                List<ComLink> links = new ArrayList<>();
                for (Object link: allLinks) {
                    links.add((ComLink)link);
                }
                return links;
            }else{
                System.out.println("===================== Redis 中没有数据=======================");
                System.out.println("=====================从MySQL中读取数据=======================");
                List<ComLink> comLinks = linkMapper.searchLinks();
                System.out.println("=====================向 Redis 中存数据=======================");
                for (ComLink link:comLinks) {
                    redisUtil.lRightPush("allLinks", link);
                }
                redisUtil.expire("allLinks", TimeOutSetting.REDIS_TIME_OUT, TimeUnit.SECONDS);
                return comLinks;
            }
        }catch (Exception e){

            return  linkMapper.searchLinks();
        }
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
        try {
            int i = linkMapper.addLink(map);
            List<ComLink> comLinks = linkMapper.searchLinks();
            redisUtil.delete("allLinks");
            List<ComLink> comLinks1 = linkMapper.searchLinks();
            for (ComLink comLink:comLinks1) {
                redisUtil.lLeftPush("allLinks",comLink);
            }
            return i;
        } catch (Exception e) {
            return linkMapper.addLink(map);
        }
    }

    /**
     * 删除链接
     */
    @Override
    public int delLink(String id) {
        try {
            int i = linkMapper.delLink(id);
            List<ComLink> comLinks = linkMapper.searchLinks();
            redisUtil.delete("allLinks");
            List<ComLink> comLinks1 = linkMapper.searchLinks();
            for (ComLink comLink:comLinks1) {
                redisUtil.lLeftPush("allLinks",comLink);
            }
            return i;
        } catch (Exception e) {
            return linkMapper.delLink(id);
        }
    }

    /**
     * 修改链接
     */
    @Override
    public int revLink(HashMap<String, Object> map) {
        try {
            int i = linkMapper.revLink(map);
            List<ComLink> comLinks = linkMapper.searchLinks();
            redisUtil.delete("allLinks");
            List<ComLink> comLinks1 = linkMapper.searchLinks();
            for (ComLink comLink:comLinks1) {
                redisUtil.lLeftPush("allLinks",comLink);
            }
            return i;
        } catch (Exception e) {
            return linkMapper.revLink(map);
        }
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
