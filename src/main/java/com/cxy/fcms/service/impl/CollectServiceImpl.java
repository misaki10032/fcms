package com.cxy.fcms.service.impl;

import com.cxy.fcms.mapper.ComCollectMapper;
import com.cxy.fcms.pojo.ComCollect;
import com.cxy.fcms.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    ComCollectMapper comCollectMapper;

    @Override
    public List<ComCollect> selCollectsByUserId(String userid) {
        return comCollectMapper.selCollectsByUserId(userid);
    }

    @Override
    public List<ComCollect> selCollectsByFicId(String ficid) {
        return comCollectMapper.selCollectsByFicId(ficid);
    }

    @Override
    public int delCollect(String id) {
        return comCollectMapper.delCollect(id);
    }

    @Override
    public int addCollect(Map<String, Object> map) {
        return comCollectMapper.addCollect(map);
    }

    @Override
    public int noShow(String id) {
        return comCollectMapper.noShow(id);
    }

    @Override
    public int show(String id) {
        return comCollectMapper.show(id);
    }
}
