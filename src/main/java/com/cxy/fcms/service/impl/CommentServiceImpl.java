package com.cxy.fcms.service.impl;

import com.cxy.fcms.mapper.ComCommentMapper;
import com.cxy.fcms.pojo.ComComment;
import com.cxy.fcms.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class CommentServiceImpl implements CommentService {
    @Autowired
    ComCommentMapper comCommentMapper;

    @Override
    public List<ComComment> selCommentsByUserId(String userId) {
        return comCommentMapper.selCommentsByUserId(userId);
    }

    @Override
    public List<ComComment> selCommentsByFicId(String ficId) {
        return comCommentMapper.selCommentsByFicId(ficId);
    }

    @Override
    public int addComment(Map<String, Object> map) {
        return comCommentMapper.addComment(map);
    }

    @Override
    public int delComment(String id) {
        return comCommentMapper.delComment(id);
    }

    @Override
    public int noShowComment(String id) {
        return comCommentMapper.noShowComment(id);
    }

    @Override
    public int showComment(String id) {
        return comCommentMapper.delComment(id);
    }
}
