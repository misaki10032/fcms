package com.cxy.fcms.service.impl;

import com.cxy.fcms.mapper.ComFictionMapper;
import com.cxy.fcms.mapper.ComTypeMapper;
import com.cxy.fcms.pojo.ComFiction;
import com.cxy.fcms.pojo.ComType;
import com.cxy.fcms.pojo.SysAdmin;
import com.cxy.fcms.service.FictionService;
import com.cxy.fcms.util.IDUtil;
import com.cxy.fcms.util.RedisUtil;
import com.cxy.fcms.util.TimeOutSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
            System.out.println("===================尝试redis读取数据=========================");
            //不为空则读取,为空则查询数据库
            if(!redisFic.isEmpty()){
                System.out.println("=====================从Redis中读取数据=======================");
                List<ComFiction> fictions = new ArrayList<>();
                for (Object fiction: redisFic) {
                    fictions.add((ComFiction)fiction);
                }
                return fictions;
            }else{
                System.out.println("===================== Redis 中没有数据=======================");
                System.out.println("=====================从MySQL中读取数据=======================");
                List<ComFiction> fictions = fictionMapper.getFictions();
                System.out.println("=====================向 Redis 中存数据=======================");
                for (ComFiction fiction:fictions) {
                    redisUtil.lLeftPush("fictions",fiction);
                }
                redisUtil.expire("fictions", TimeOutSetting.REDIS_TIME_OUT, TimeUnit.SECONDS);
                return fictions;
            }
        }catch (Exception e){
            System.out.println("=====================   Redis 宕机   =======================");
            System.out.println("=====================从MySQL中读取数据=======================");
            return fictionMapper.getFictions();
        }

    }
    @Override
    public void addFiction(HashMap<String,String> ficmap,String ficid,String text,String type) {
        fictionMapper.addFiction(ficmap);
        fictionMapper.addFictionData(IDUtil.getID(), ficid, text);
        String typeId = typeMapper.getTypeIdByName(type);
        fictionMapper.addFictionType(IDUtil.getID(), ficid, typeId);
        //更新redis
        redisUtil.delete("fictions");//删除原来的list
        List<ComFiction> fictions = fictionMapper.getFictions();
        System.out.println("=====================更新 Redis  数据=======================");
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
            System.out.println("===================尝试redis读取数据=========================");
            //不为空则读取,为空则查询数据库
            if (!redisFic.isEmpty()) {
                System.out.println("=====================从Redis中读取数据=======================");
                List<ComFiction> fictions = new ArrayList<>();
                for (Object fiction : redisFic) {
                    fictions.add((ComFiction) fiction);
                }
                return fictions;
            } else {
                System.out.println("===================== Redis 中没有数据=======================");
                System.out.println("=====================从MySQL中读取数据=======================");
                List<ComFiction> fictions = fictionMapper.getFictionsOrderByTime();
                System.out.println("=====================向 Redis 中存数据=======================");
                for (ComFiction fiction : fictions) {
                    redisUtil.lRightPush("fictionsOrderByTime", fiction);
                }
                redisUtil.expire("fictionsOrderByTime", TimeOutSetting.REDIS_TIME_OUT, TimeUnit.SECONDS);

                return fictions;
            }
        } catch (Exception e) {
            System.out.println("=====================   Redis 宕机   =======================");
            System.out.println("=====================从MySQL中读取数据=======================");
            return fictionMapper.getFictionsOrderByTime();
        }
    }
    @Override
    public List<ComFiction> getFictionsOrderByHost() {
        try {
            //从redis查询
            List<Object> redisFic = redisUtil.lRange("fictionsOrderByHost", 0, -1);
            System.out.println("===================尝试redis读取数据=========================");
            //不为空则读取,为空则查询数据库
            if (!redisFic.isEmpty()) {
                System.out.println("=====================从Redis中读取数据=======================");
                List<ComFiction> fictions = new ArrayList<>();
                for (Object fiction : redisFic) {
                    fictions.add((ComFiction) fiction);
                }
                return fictions;
            } else {
                System.out.println("===================== Redis 中没有数据=======================");
                System.out.println("=====================从MySQL中读取数据=======================");
                List<ComFiction> fictions = fictionMapper.getFictionsOrderByHost();
                System.out.println("=====================向 Redis 中存数据=======================");
                for (ComFiction fiction : fictions) {
                    redisUtil.lRightPush("fictionsOrderByHost", fiction);
                }
                redisUtil.expire("fictionsOrderByHost", TimeOutSetting.REDIS_TIME_OUT, TimeUnit.SECONDS);
                System.out.println("=====================  设置300s后过期 =======================");
                return fictions;
            }
        } catch (Exception e) {
            System.out.println("=====================   Redis 宕机   =======================");
            System.out.println("=====================从MySQL中读取数据=======================");
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
        System.out.println("=====================更新 Redis  数据=======================");
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


}
