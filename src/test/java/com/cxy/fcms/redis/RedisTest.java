package com.cxy.fcms.redis;

import com.cxy.fcms.pojo.SysAdmin;
import com.cxy.fcms.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@SpringBootTest
public class RedisTest {
    @Autowired
    @Qualifier("redisTemplates")
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Test
    void redisPing(){
        List<Object> allAdmins = redisUtil.lRange("allAdmins", 0, -1);
        for (Object admin:allAdmins) {
            System.out.println((SysAdmin)admin);
        }
    }

}
