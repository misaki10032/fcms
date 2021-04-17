package com.cxy.fcms.service.impl;

import com.cxy.fcms.mapper.ComCommentMapper;
import com.cxy.fcms.pojo.ComCollect;
import com.cxy.fcms.pojo.ComComment;
import com.cxy.fcms.service.CommentService;
import com.cxy.fcms.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    ComCommentMapper comCommentMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public ComComment selById(String id) {
        return comCommentMapper.selById(id);
    }

    @Override
    public List<ComComment> selCommentsByUserId(String userId) {
        try {
            List<Object> commentByUID = redisUtil.lRange("commentByUID", 0, -1);
            System.out.println("===================尝试redis读取数据=========================");
            //不为空则读取,为空则查询数据库
            if (!commentByUID.isEmpty()) {
                System.out.println("=====================从Redis中读取数据=======================");
                List<ComComment> collects = new ArrayList<>();
                for (Object comment : commentByUID) {
                    collects.add((ComComment) comment);
                }
                return collects;
            } else {
                System.out.println("===================== Redis 中没有数据=======================");
                System.out.println("=====================从MySQL中读取数据=======================");
                List<ComComment> comments = comCommentMapper.selCommentsByUserId(userId);
                System.out.println("=====================向 Redis 中存数据=======================");
                for (ComComment comComment : comments) {
                    redisUtil.lLeftPush("commentByUID", comComment);
                }
                return comments;
            }
        } catch (Exception e) {
            System.out.println("=====================  Redis 宕机   =======================");
            System.out.println("=====================从MySQL中读取数据=======================");
            return comCommentMapper.selCommentsByUserId(userId);
        }
    }

    @Override
    public List<ComComment> selCommentsByFicId(String ficId) {
        try {
            List<Object> commentByFID = redisUtil.lRange("commentByFID", 0, -1);
            System.out.println("===================尝试redis读取数据=========================");
            //不为空则读取,为空则查询数据库
            if (!commentByFID.isEmpty()) {
                System.out.println("=====================从Redis中读取数据=======================");
                List<ComComment> collects = new ArrayList<>();
                for (Object comment : commentByFID) {
                    collects.add((ComComment) comment);
                }
                return collects;
            } else {
                System.out.println("===================== Redis 中没有数据=======================");
                System.out.println("=====================从MySQL中读取数据=======================");
                List<ComComment> comments = comCommentMapper.selCommentsByFicId(ficId);
                System.out.println("=====================向 Redis 中存数据=======================");
                for (ComComment comComment : comments) {
                    redisUtil.lLeftPush("commentByFID", comComment);
                }
                return comments;
            }
        } catch (Exception e) {
            System.out.println("=====================  Redis 宕机   =======================");
            System.out.println("=====================从MySQL中读取数据=======================");
            return comCommentMapper.selCommentsByFicId(ficId);
        }
    }

    @Override
    public int addComment(Map<String, Object> map) {
        try {
            int i = comCommentMapper.addComment(map);//添加新注册的用户
            ComComment comComment = comCommentMapper.selById((String) map.get("id"));
            redisUtil.delete("commentByUID");//删除原来的list
            redisUtil.delete("commentByFID");//删除原来的list
            System.out.println("=====================从MySQL中读取数据=======================");
            List<ComComment> comments = comCommentMapper.selCommentsByUserId(comComment.getUserId());//更新redis信息
            List<ComComment> comments1 = comCommentMapper.selCommentsByFicId(comComment.getFicId());//更新redis信息
            System.out.println("=====================向 Redis 中存数据=======================");
            for (ComComment collect1 : comments) {
                redisUtil.lLeftPush("commentByUID", collect1);
            }
            for (ComComment collect1 : comments1) {
                redisUtil.lLeftPush("commentByFID", collect1);
            }
            return i;
        } catch (Exception e) {
            return comCommentMapper.addComment(map);
        }
    }

    @Override
    public int delComment(String id) {
        try {
            int i = comCommentMapper.delComment(id);//添加新注册的用户
            ComComment comComment = comCommentMapper.selById(id);
            redisUtil.delete("commentByUID");//删除原来的list
            redisUtil.delete("commentByFID");//删除原来的list
            System.out.println("=====================从MySQL中读取数据=======================");
            List<ComComment> comments = comCommentMapper.selCommentsByUserId(comComment.getUserId());//更新redis信息
            List<ComComment> comments1 = comCommentMapper.selCommentsByFicId(comComment.getFicId());//更新redis信息
            System.out.println("=====================向 Redis 中存数据=======================");
            for (ComComment collect1 : comments) {
                redisUtil.lLeftPush("commentByUID", collect1);
            }
            for (ComComment collect1 : comments1) {
                redisUtil.lLeftPush("commentByFID", collect1);
            }
            return i;
        } catch (Exception e) {
            return comCommentMapper.delComment(id);
        }
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
