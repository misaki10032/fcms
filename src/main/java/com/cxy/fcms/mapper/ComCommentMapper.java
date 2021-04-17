package com.cxy.fcms.mapper;

import com.cxy.fcms.pojo.ComComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 陈新予
 * @since 2021-04-15
 */
@Component
public interface ComCommentMapper extends BaseMapper<ComComment> {
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
     */
    int addComment(Map<String, Object> map);

    /**
     * 删除评论
     */
    int delComment(String id);

    /**
     * 不显示评论
     */
    int noShowComment(String id);

    /**
     * 显示评论
     */
    int showComment(String id);
}
