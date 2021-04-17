package com.cxy.fcms.service.impl;

import com.cxy.fcms.mapper.ComFictionMapper;
import com.cxy.fcms.pojo.ComFiction;
import com.cxy.fcms.service.FictionService;
import com.cxy.fcms.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class FictionServiceImpl implements FictionService {
    @Autowired
    ComFictionMapper fictionMapper;
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
                return fictions;
            }
        }catch (Exception e){
            System.out.println("=====================   Redis 宕机   =======================");
            System.out.println("=====================从MySQL中读取数据=======================");
            return fictionMapper.getFictions();
        }

    }
}
