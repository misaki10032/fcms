package com.cxy.fcms.service.impl;

import com.cxy.fcms.mapper.ComCollectMapper;
import com.cxy.fcms.pojo.ComCollect;
import com.cxy.fcms.pojo.SysAdmin;
import com.cxy.fcms.service.CollectService;
import com.cxy.fcms.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    ComCollectMapper comCollectMapper;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public ComCollect selCollectById(String id) {
        return comCollectMapper.selCollectById(id);
    }

    @Override
    public List<ComCollect> selCollectsByUserId(String userid) {
        try {
            List<Object> collectByUID = redisUtil.lRange("collectByUID", 0, -1);
            System.out.println("===================尝试redis读取数据=========================");
            //不为空则读取,为空则查询数据库
            if (!collectByUID.isEmpty()) {
                System.out.println("=====================从Redis中读取数据=======================");
                List<ComCollect> collects = new ArrayList<>();
                for (Object collect : collectByUID) {
                    collects.add((ComCollect) collect);
                }
                return collects;
            } else {
                System.out.println("===================== Redis 中没有数据=======================");
                System.out.println("=====================从MySQL中读取数据=======================");
                List<ComCollect> collects = comCollectMapper.selCollectsByUserId(userid);
                System.out.println("=====================向 Redis 中存数据=======================");
                for (ComCollect collect : collects) {
                    redisUtil.lLeftPush("collectByUID", collect);
                }
                return collects;
            }
        } catch (Exception e) {
            System.out.println("=====================  Redis 宕机   =======================");
            System.out.println("=====================从MySQL中读取数据=======================");
            return comCollectMapper.selCollectsByUserId(userid);
        }
    }

    @Override
    public List<ComCollect> selCollectsByFicId(String ficid) {
        try {
            List<Object> collectByUID = redisUtil.lRange("collectByFID", 0, -1);
            System.out.println("===================尝试redis读取数据=========================");
            //不为空则读取,为空则查询数据库
            if (!collectByUID.isEmpty()) {
                System.out.println("=====================从Redis中读取数据=======================");
                List<ComCollect> collects = new ArrayList<>();
                for (Object collect : collectByUID) {
                    collects.add((ComCollect) collect);
                }
                return collects;
            } else {
                System.out.println("===================== Redis 中没有数据=======================");
                System.out.println("=====================从MySQL中读取数据=======================");
                List<ComCollect> collects = comCollectMapper.selCollectsByFicId(ficid);
                System.out.println("=====================向 Redis 中存数据=======================");
                for (ComCollect collect : collects) {
                    redisUtil.lLeftPush("collectByFID", collect);
                }
                return collects;
            }
        } catch (Exception e) {
            System.out.println("=====================  Redis 宕机   =======================");
            System.out.println("=====================从MySQL中读取数据=======================");
            return comCollectMapper.selCollectsByFicId(ficid);
        }
    }

    @Override
    public int delCollect(String id) {
        try {
            int i = comCollectMapper.delCollect(id);//添加新注册的用户
            ComCollect collect = comCollectMapper.selCollectById(id);
            redisUtil.delete("collectByUID");//删除原来的list
            redisUtil.delete("collectByFID");//删除原来的list
            System.out.println("=====================从MySQL中读取数据=======================");
            List<ComCollect> collects2 = comCollectMapper.selCollectsByUserId(collect.getUserId());//更新redis信息
            List<ComCollect> collects3 = comCollectMapper.selCollectsByFicId(collect.getFicId());//更新redis信息
            System.out.println("=====================向 Redis 中存数据=======================");
            for (ComCollect collect1 : collects2) {
                redisUtil.lLeftPush("collectByUID", collect1);
            }
            for (ComCollect collect1 : collects3) {
                redisUtil.lLeftPush("collectByFID", collect1);
            }
            return i;
        } catch (Exception e) {
            return comCollectMapper.delCollect(id);
        }
    }


    @Override
    public int addCollect(Map<String, Object> map) {
        try {
            int i = comCollectMapper.addCollect(map);
            ComCollect collect = comCollectMapper.selCollectById((String) map.get("id"));
            redisUtil.delete("collectByUID");//删除原来的list
            redisUtil.delete("collectByFID");//删除原来的list
            System.out.println("=====================从MySQL中读取数据=======================");
            List<ComCollect> collects2 = comCollectMapper.selCollectsByUserId(collect.getUserId());//更新redis信息
            List<ComCollect> collects3 = comCollectMapper.selCollectsByFicId(collect.getFicId());//更新redis信息
            System.out.println("=====================向 Redis 中存数据=======================");
            for (ComCollect collect1 : collects2) {
                redisUtil.lLeftPush("collectByUID", collect1);
            }
            for (ComCollect collect1 : collects3) {
                redisUtil.lLeftPush("collectByFID", collect1);
            }
            return i;
        } catch (Exception e) {
            return comCollectMapper.addCollect(map);
        }
    }

    @Override
    public int noShow(String id) {
        return comCollectMapper.noShow(id);
    }

    @Override
    public int show(String id) {
        return comCollectMapper.show(id);
    }
}
