package com.cxy.fcms.service.impl;

import com.cxy.fcms.mapper.ComTypeMapper;
import com.cxy.fcms.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    ComTypeMapper comTypeMapper;
    @Override
    public int addType(HashMap<String, String> map) {
        return comTypeMapper.addType(map);
    }

    @Override
    public String getTypeIdByName(String tyName) {
        return comTypeMapper.getTypeIdByName(tyName);
    }

    @Override
    public String getTypeNameById(String id) {
        return comTypeMapper.getTypeNameById(id);
    }

    @Override
    public int revType(Map<String, Object> map) {
        return comTypeMapper.revType(map);
    }

    @Override
    public int delType(String id) {
        return comTypeMapper.delType(id);
    }

    @Override
    public int noShowType(String id) {
        return comTypeMapper.noShowType(id);
    }

    @Override
    public int showType(String id) {
        return comTypeMapper.showType(id);
    }
}
