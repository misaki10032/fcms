package com.cxy.fcms.mapper;

import com.cxy.fcms.pojo.ComCollect;
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
public interface ComCollectMapper extends BaseMapper<ComCollect> {
    /**
     * 用户根据收藏id 查询收藏信息
     */
    ComCollect selCollectById(String id);

    /**
     * 用户根据userid 查询收藏
     */
    List<ComCollect> selCollectsByUserId(String userid);

    /**
     * 管理员根据ficId 查询收藏
     */
    List<ComCollect> selCollectsByFicId(String ficid);

    /**
     * 删除/取消收藏
     */
    int delCollect(String id);

    /**
     * 添加收藏
     */
    int addCollect(Map<String, Object> map);

    /**
     * 不显示收藏
     */
    int noShow(String id);

    /**
     * 显示收藏
     */
    int show(String id);


}
