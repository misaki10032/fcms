package com.cxy.fcms;

import com.cxy.fcms.mapper.ComFictionMapper;
import com.cxy.fcms.mapper.ComTypeMapper;
import com.cxy.fcms.util.IDUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class FcmsApplicationTests {
    @Autowired
    ComFictionMapper fictionMapper;
    @Autowired
    ComTypeMapper typeMapper;
    @Test
    void insertFic() {
        HashMap<String,String> map = new HashMap<>();
        map.put("id",IDUtil.getID());
        map.put("ficName","从零开始的异世界生活");
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

}
