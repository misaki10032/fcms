package com.cxy.fcms.mapper;

import com.cxy.fcms.pojo.ComType;
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
public interface ComTypeMapper extends BaseMapper<ComType> {
    /**
     * 获取所有的类型
     */
    List<ComType> selType();
    /**
     * 添加类型
     */
    int addType(HashMap<String, String> map);

    /**
     * 通过tyName获取类型id
     */
    String getTypeIdByName(String tyName);

    /**
     * 通过类型id获取tyName
     */
    String getTypeNameById(String id);

    /**
     * 修改类型
     */
    int revType(Map<String, Object> map);

    /**
     * 删除类型
     */
    int delType(String id);

    /**
     * 不显示类型
     */
    int noShowType(String id);

    /**
     * 显示类型
     */
    int showType(String id);
}
