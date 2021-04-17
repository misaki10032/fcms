package com.cxy.fcms.mapper;

import com.cxy.fcms.pojo.ComLink;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 陈新予
 * @since 2021-04-15
 */
@Component
public interface ComLinkMapper extends BaseMapper<ComLink> {
    /**
     * 查询所有链接
     */
    List<ComLink> searchLinks();

    /**
     * 查询可显示的链接
     */
    List<ComLink> searchShowLinks();

    /**
     * 增加链接
     */
    int addLink(HashMap<String, Object> map);

    /**
     * 删除链接
     */
    int delLink(String id);

    /**
     * 修改链接
     */
    int revLink(HashMap<String, Object> map);

    /**
     * 不显示链接
     * id
     */
    int noShowLink(String id);

    /**
     * 显示链接
     * id
     */
    int showLink(String id);

}
