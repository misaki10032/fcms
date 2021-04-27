package com.cxy.fcms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxy.fcms.pojo.SysAdminInfo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface SysAdminInfoMapper extends BaseMapper<SysAdminInfo> {
    /**
     * 根据id查询用户信息
     */
    SysAdminInfo selAdmin(String id);

    /**
     * 修改信息
     */
    int revAdmin(Map<String, Object> map);

    /**
     * 添加
     */
    int addAdmin(Map<String, Object> map);


}
