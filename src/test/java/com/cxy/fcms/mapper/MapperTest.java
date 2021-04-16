package com.cxy.fcms.mapper;

import com.cxy.fcms.pojo.SysAdmin;
import com.cxy.fcms.util.IDUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class MapperTest {
    @Autowired
    SysAdminMapper adminMapper;
    @Test
    void addAdmin(){
        HashMap<String,String> map = new HashMap<>();
        map.put("id", IDUtil.getID());
        map.put("num","blanker");
        map.put("pwd","111");
        map.put("name","陈新予");
        map.put("phone","15667650616");
        adminMapper.addAdmin(map);
    }
    @Test
    void getAdmin(){
        List<SysAdmin> allAdmins = adminMapper.getAllAdmins();
        System.out.println(allAdmins);

    }
}
