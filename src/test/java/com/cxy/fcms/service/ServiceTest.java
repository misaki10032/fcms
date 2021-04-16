package com.cxy.fcms.service;

import com.cxy.fcms.pojo.SysAdmin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ServiceTest {
    @Autowired
    LoginService loginService;
    @Test
    void getAdminsText(){
        List<SysAdmin> admins = loginService.getAdmins();
        System.out.println(admins);
    }
}
