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
    @Autowired
    ComFictionMapper fictionMapper;

    @Test
    void addAdmin() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", IDUtil.getID());
        map.put("num", "blanker");
        map.put("pwd", "111");
        map.put("name", "陈新予");
        map.put("phone", "15667650616");
        adminMapper.addAdmin(map);
    }

    @Test
    void getAdmin() {
        List<SysAdmin> allAdmins = adminMapper.getAllAdmins();
        System.out.println(allAdmins);
    }

    @Test
    void delFic() {

        fictionMapper.delFicData("f7a1a9fa267f4eb0bc152fe64517e475");
        fictionMapper.delFicType("f7a1a9fa267f4eb0bc152fe64517e475");

        fictionMapper.delFiction("f7a1a9fa267f4eb0bc152fe64517e475");
    }

    @Test
    void getTTT2() {
        List<String> aa = fictionMapper.getFictionByTypeId("d7a93c109c444140b1e0b8799e7a1a96");
        System.out.println(aa);
    }
}
