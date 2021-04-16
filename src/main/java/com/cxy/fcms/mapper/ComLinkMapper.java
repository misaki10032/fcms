package com.cxy.fcms.mapper;

import com.cxy.fcms.pojo.ComLink;
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
public interface ComLinkMapper extends BaseMapper<ComLink> {
    int addLink(HashMap<String,String> map);

}
