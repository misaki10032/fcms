package com.cxy.fcms.mapper;

import com.cxy.fcms.pojo.SysAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈新予
 * @since 2021-04-15
 */
@Component
public interface SysAdminMapper extends BaseMapper<SysAdmin> {

    int addAdmin(HashMap<String, String> map);

    List<SysAdmin> getAllAdmins();

    void revAdminAuthorById(HashMap<String, String> map);

    void delAdmin(HashMap<String, String> map);

    void revAdminPassword(HashMap<String, String> map);

}
