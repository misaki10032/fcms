package com.cxy.fcms.redis;

import com.cxy.fcms.mapper.ComFictionMapper;
import com.cxy.fcms.pojo.SysAdmin;
import com.cxy.fcms.util.RedisUtil;
import com.cxy.fcms.util.SelectRedis;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@SpringBootTest
public class RedisTest {
    @Autowired
    @Qualifier("redisTemplates")
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    ComFictionMapper fictionMapper;

    @Test
    void redisPing() {
        redisUtil.set("blank", "111");
        Object blank = redisUtil.get("blank");
        System.out.println(blank);
    }

    @Test
    void rrtt() {
        try {
            List<Object> objects = SelectRedis.selectRedis(redisUtil, "fictions", fictionMapper, "getFictions");
            System.out.println(objects);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
