package com.cxy.fcms.service;

import com.cxy.fcms.pojo.ComComment;

import java.util.List;
import java.util.Map;

public interface CommentService {
    /**
     * 用户查询自己评论
     */
    List<ComComment> selCommentsByUserId(String userId);

    /**
     * 管理员查询某本书评论
     */
    List<ComComment> selCommentsByFicId(String ficId);
    /**
     * 添加评论
     * */
    int addComment(Map<String,Object> map);
    /**
     * 删除评论
     * */
    int delComment(String id);
    /**
     * 不显示评论
     * */
    int noShowComment(String id);

    /**
     * 显示评论
     * */
    int showComment(String id);
}
