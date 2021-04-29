package com.cxy.fcms.service.impl;

import com.cxy.fcms.mapper.ComFictionMapper;
import com.cxy.fcms.mapper.ComTypeMapper;
import com.cxy.fcms.pojo.ComType;
import com.cxy.fcms.pojo.SysAdmin;
import com.cxy.fcms.service.FictionService;
import com.cxy.fcms.service.TypeService;
import com.cxy.fcms.util.RedisUtil;
import com.cxy.fcms.util.TimeOutSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
/**
 * @ClassName UserController
 * @Author 李文博
 * @Date 2021/4/18 16:22
 * @Version 1.0
 */
@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    ComTypeMapper comTypeMapper;
    @Autowired
    FictionService fictionService;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<ComType> selType() {
        try {
            List<Object> comTypes = redisUtil.lRange("comTypes", 0, -1);
            if (!comTypes.isEmpty()) {
                List<ComType> comTypes1 = new ArrayList<>();
                for (Object type : comTypes) {
                    comTypes1.add((ComType) type);
                }
                return comTypes1;
            } else {
                List<ComType> comTypes1 = comTypeMapper.selType();
                for (ComType type : comTypes1) {
                    redisUtil.lRightPush("comTypes", type);
                }
                redisUtil.expire("comTypes", TimeOutSetting.REDIS_TIME_OUT, TimeUnit.SECONDS);
                return comTypes1;
            }
        } catch (Exception e) {
            return comTypeMapper.selType();
        }
    }

    @Override
    public int addType(HashMap<String, String> map) {
        try {
            int i = comTypeMapper.addType(map);//添加新注册的用户
            List<ComType> comTypes = comTypeMapper.selType();//更新redis信息
            redisUtil.delete("comTypes");//删除原来的list
            List<ComType> comType = comTypeMapper.selType();
            for (ComType type : comType) {
                redisUtil.lLeftPush("comTypes", type);
            }
            return i;
        } catch (Exception e) {
            return comTypeMapper.addType(map);
        }
    }

    @Override
    public String getTypeIdByName(String tyName) {
        return comTypeMapper.getTypeIdByName(tyName);
    }

    @Override
    public String getTypeNameById(String id) {
        return comTypeMapper.getTypeNameById(id);
    }

    @Override
    public int revType(Map<String, Object> map) {
        try {
            int i = comTypeMapper.revType(map);//添加新注册的用户
            List<ComType> comTypes = comTypeMapper.selType();//更新redis信息
            redisUtil.delete("comTypes");//删除原来的list
            List<ComType> comType = comTypeMapper.selType();
            for (ComType type : comType) {
                redisUtil.lLeftPush("comTypes", type);
            }
            return i;
        } catch (Exception e) {
            return comTypeMapper.revType(map);
        }
    }

    @Override
    public int delType(String id) {
        try {
            // 先删除所有外键
            List<String> fictions = fictionService.getFictionsByType(id);
            System.out.println(fictions);
            for (String fid : fictions) {
                fictionService.delFiction(fid);
            }
            int i = comTypeMapper.delType(id);//id删除分区类型
            List<ComType> comTypes = comTypeMapper.selType();//更新redis信息
            redisUtil.delete("comTypes");//删除原来的list
            List<ComType> comType = comTypeMapper.selType();
            for (ComType type : comType) {
                redisUtil.lLeftPush("comTypes", type);
            }
            return i;
        } catch (Exception e) {
            return comTypeMapper.delType(id);
        }
    }

    @Override
    public int noShowType(String id) {
        return comTypeMapper.noShowType(id);
    }

    @Override
    public int showType(String id) {
        return comTypeMapper.showType(id);
    }
}
