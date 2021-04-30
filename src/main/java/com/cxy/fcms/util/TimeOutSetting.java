package com.cxy.fcms.util;

import org.springframework.stereotype.Component;

/**
 * @ClassName UserController
 * @Author 陈新予
 * @Date 2021/4/25 20:21
 * @Version 1.0
 */
@Component
public class TimeOutSetting {
    //redis key过期时间
    public static int REDIS_TIME_OUT = 3600;
}
