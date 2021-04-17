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
    @Autowired
    TypeService service;
    @Test
    void getAdminsText(){
        List<SysAdmin> admins = loginService.getAdmins();
        System.out.println(admins);
    }
    @Test
    void get(){
        String typeNameById = service.getTypeNameById("015cdf73928f4ffaba1bf0b8de09c529");
        System.out.println(typeNameById);

    }
}
