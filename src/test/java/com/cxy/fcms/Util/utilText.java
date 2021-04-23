package com.cxy.fcms.Util;

import com.cxy.fcms.util.ShiroMd5Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class utilText {


    @Test
    void ShiroUtil1() {
        String num = "15667650616";
        String pwd = "111";
        String s = ShiroMd5Util.toPwdMd5(num, pwd);
        System.out.println(s);
    }
}
