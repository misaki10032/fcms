package com.cxy.fcms.util;

import com.cxy.fcms.mapper.ComFictionMapper;
import com.cxy.fcms.pojo.ComFiction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SelectRedis
 * @Author 陈新予(blank)
 * @Date 2021/4/30
 * @Version 1.0
 */
@Component
public class SelectRedis {
    /**
     * redis查询列表的泛用抽象
     *
     * @param keyName    要查询的keyName
     * @param mapper     mapper
     * @param invokeName mapper的方法
     * @return 返回object类型的List集合
     * @throws NoSuchMethodException     异常
     * @throws IllegalAccessException    异常
     * @throws InstantiationException    异常
     * @throws InvocationTargetException 异常
     */
    public static List selectRedis(RedisUtil redisUtil, String keyName, Object mapper, String invokeName)
            throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        try {
            List redisFic = redisUtil.lRange(keyName, 0, -1);
            if (!redisFic.isEmpty()) {
                return redisFic;
            } else {
                Method method = mapper.getClass().getDeclaredMethod(invokeName);
                List<Object> invoke = Collections.singletonList(method.invoke(mapper.getClass().newInstance()));
                for (Object fiction : invoke) {
                    redisUtil.lLeftPush(keyName, fiction);
                }
                redisUtil.expire(keyName, TimeOutSetting.REDIS_TIME_OUT, TimeUnit.SECONDS);
                return invoke;
            }
        } catch (Exception e) {
            return Collections.singletonList(mapper.getClass().getDeclaredMethod(invokeName).invoke(mapper.getClass().newInstance()));
        }

    }
}
