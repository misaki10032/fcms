package com.cxy.fcms.service.impl;

import com.cxy.fcms.mapper.ComFictionMapper;
import com.cxy.fcms.mapper.ComTypeMapper;
import com.cxy.fcms.pojo.ComFicDate;
import com.cxy.fcms.pojo.ComFiction;
import com.cxy.fcms.pojo.ComType;
import com.cxy.fcms.pojo.SysAdmin;
import com.cxy.fcms.service.FictionService;
import com.cxy.fcms.util.IDUtil;
import com.cxy.fcms.util.RedisUtil;
import com.cxy.fcms.util.SelectRedis;
import com.cxy.fcms.util.TimeOutSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
/**
 * @ClassName UserController
 * @Author 陈新予
 * @Date 2021/4/18 16:22
 * @Version 1.0
 */
@Service
public class FictionServiceImpl implements FictionService {
    @Autowired
    ComFictionMapper fictionMapper;
    @Autowired
    ComTypeMapper typeMapper;
    @Autowired
    RedisUtil redisUtil;
    @Override
    public List<ComFiction> getFiction() {
        try {
            //从redis查询
            List<Object> redisFic = redisUtil.lRange("fictions", 0, -1);
            //不为空则读取,为空则查询数据库
            if(!redisFic.isEmpty()){
                List<ComFiction> fictions = new ArrayList<>();
                for (Object fiction: redisFic) {
                    fictions.add((ComFiction)fiction);
                }
                return fictions;
            }else{
                List<ComFiction> fictions = fictionMapper.getFictions();
                for (ComFiction fiction : fictions) {
                    redisUtil.lLeftPush("fictions", fiction);
                }
                redisUtil.expire("fictions", TimeOutSetting.REDIS_TIME_OUT, TimeUnit.SECONDS);
                return fictions;
            }
        } catch (Exception e) {
            System.out.println("=====================   Redis 宕机   =======================");
            return fictionMapper.getFictions();
        }
    }

    @Override
    public List<ComFiction> getFictionlimit(int page, int limit) {
        return fictionMapper.getFictionslimit(limit * (page - 1), limit);
    }

    @Override
    public void addFiction(HashMap<String, String> ficmap, String ficid, String text, String type) {
        fictionMapper.addFiction(ficmap);
        fictionMapper.addFictionData(IDUtil.getID(), ficid, text);
        String typeId = typeMapper.getTypeIdByName(type);
        fictionMapper.addFictionType(IDUtil.getID(), ficid, typeId);
        //更新redis
        redisUtil.delete("fictions");//删除原来的list
        List<ComFiction> fictions = fictionMapper.getFictions();
        for (ComFiction fiction : fictions) {
            redisUtil.lRightPush("fictions", fiction);
        }
        redisUtil.expire("fictions", TimeOutSetting.REDIS_TIME_OUT, TimeUnit.SECONDS);

    }
    @Override
    public List<ComFiction> getFictionOrderByTime() {
        try {
            //从redis查询
            List<Object> redisFic = redisUtil.lRange("fictionsOrderByTime", 0, -1);
            //不为空则读取,为空则查询数据库
            if (!redisFic.isEmpty()) {
                List<ComFiction> fictions = new ArrayList<>();
                for (Object fiction : redisFic) {
                    fictions.add((ComFiction) fiction);
                }
                return fictions;
            } else {
                List<ComFiction> fictions = fictionMapper.getFictionsOrderByTime();
                for (ComFiction fiction : fictions) {
                    redisUtil.lRightPush("fictionsOrderByTime", fiction);
                }
                redisUtil.expire("fictionsOrderByTime", TimeOutSetting.REDIS_TIME_OUT, TimeUnit.SECONDS);

                return fictions;
            }
        } catch (Exception e) {
            System.out.println("=====================   Redis 宕机   =======================");
            return fictionMapper.getFictionsOrderByTime();
        }
    }
    @Override
    public List<ComFiction> getFictionsOrderByHost() {
        try {
            //从redis查询
            List<Object> redisFic = redisUtil.lRange("fictionsOrderByHost", 0, -1);
            //不为空则读取,为空则查询数据库
            if (!redisFic.isEmpty()) {
                List<ComFiction> fictions = new ArrayList<>();
                for (Object fiction : redisFic) {
                    fictions.add((ComFiction) fiction);
                }
                return fictions;
            } else {
                List<ComFiction> fictions = fictionMapper.getFictionsOrderByHost();
                for (ComFiction fiction : fictions) {
                    redisUtil.lRightPush("fictionsOrderByHost", fiction);
                }
                redisUtil.expire("fictionsOrderByHost", TimeOutSetting.REDIS_TIME_OUT, TimeUnit.SECONDS);
                return fictions;
            }
        } catch (Exception e) {
            System.out.println("=====================   Redis 宕机   =======================");
            return fictionMapper.getFictionsOrderByHost();
        }
    }
    @Override
    public void delFiction(String id) {
        fictionMapper.delFicData(id);
        fictionMapper.delFicType(id);
        fictionMapper.delFiction(id);
        //更新redis
        redisUtil.delete("fictions");//删除原来的list
        List<ComFiction> fictions = fictionMapper.getFictions();
        for (ComFiction fiction : fictions) {
            redisUtil.lRightPush("fictions", fiction);
        }
        redisUtil.expire("fictions", TimeOutSetting.REDIS_TIME_OUT, TimeUnit.SECONDS);
    }
    @Override
    public List<String> getFictionsByType(String id) {
        System.out.println("----正在查找书id");
        return fictionMapper.getFictionByTypeId(id);
    }
    @Override
    public ComFicDate getFictionDataById(String id) {
        try {
            //从redis查询
            ComFicDate redisData = (ComFicDate) redisUtil.get("Data" + id);
            //不为空则读取,为空则查询数据库
            if (redisData != null) {
                ComFicDate comFicDate = new ComFicDate();
                comFicDate = redisData;
                return comFicDate;
            } else {
                ComFicDate data = fictionMapper.getFictionDataById(id);
                redisUtil.set("Data" + id, data);
                redisUtil.expire("Data" + id, TimeOutSetting.REDIS_TIME_OUT, TimeUnit.SECONDS);
                return data;
            }
        } catch (Exception e) {
            System.out.println("=====================   Redis 宕机   =======================");
            return fictionMapper.getFictionDataById(id);
        }
    }
}
