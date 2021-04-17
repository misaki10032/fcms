package com.cxy.fcms;

import com.cxy.fcms.mapper.ComFictionMapper;
import com.cxy.fcms.mapper.ComTypeMapper;
import com.cxy.fcms.pojo.ComLink;
import com.cxy.fcms.service.impl.LinkServiceImpl;
import com.cxy.fcms.service.impl.TypeServiceImpl;
import com.cxy.fcms.util.IDUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
class FcmsApplicationTests {
    @Autowired
    ComFictionMapper fictionMapper;
    @Autowired
    ComTypeMapper typeMapper;
    @Autowired
    LinkServiceImpl linkService;
    @Test
    void insertFic() {
        HashMap<String,String> map = new HashMap<>();
        map.put("id",IDUtil.getID());
        map.put("ficName","刀剑神域");
        map.put("ficAuthor",null);
        fictionMapper.addFiction(map);
    }
    @Test
    void inserttype() {
        HashMap<String,String> map = new HashMap<>();
        map.put("id",IDUtil.getID());
        map.put("tyName","后宫");
        typeMapper.addType(map);
        map.put("id",IDUtil.getID());
        map.put("tyName","中二病");
        typeMapper.addType(map);
        map.put("id",IDUtil.getID());
        map.put("tyName","古典");
        typeMapper.addType(map);
    }
    @Test
    void addFicData(){
        String text = "文章内容=========================================";
        fictionMapper.addFictionData(IDUtil.getID(),"5e760d25a6054213b21fbec1783d4c23",text);
    }
    @Test
    void addFicType(){
        String typeId = typeMapper.getTypeIdByName("轻小说");
        fictionMapper.addFictionType(IDUtil.getID(),"5e760d25a6054213b21fbec1783d4c23",typeId);
    }

    @Test
    public  void fa(){
        List<ComLink> i = linkService.searchLinks();
        i.forEach(System.out::println);
    }

}
