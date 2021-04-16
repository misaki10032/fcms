package com.cxy.fcms.mapper;

import com.cxy.fcms.pojo.ComCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈新予
 * @since 2021-04-15
 */
@Component
public interface ComCollectMapper extends BaseMapper<ComCollect> {
        int collect();
        int uncollect(String id);
}
