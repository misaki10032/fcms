package com.cxy.fcms.service;

import com.cxy.fcms.pojo.ComFiction;
import com.cxy.fcms.pojo.SysAdmin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ServiceTest {
    @Autowired
    LoginService loginService;
    @Autowired
    TypeService service;
    @Autowired
    FictionService fictionService;
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
    @Test
    void getFictions(){
        List<ComFiction> fiction = fictionService.getFiction();
        System.out.println(fiction);
    }

    @Test
    void getFictionsToJSON() throws JSONException {

    }
}
