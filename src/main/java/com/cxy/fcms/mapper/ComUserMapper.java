package com.cxy.fcms.mapper;

import com.cxy.fcms.pojo.ComUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxy.fcms.pojo.SysAdminInfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈新予
 * @since 2021-04-15
 */
@Component
public interface ComUserMapper extends BaseMapper<ComUser> {
    /**
     * 添加用户
     */
    int addUser(HashMap<String, String> map);

    /**
     * 禁用用户
     */
    int delUser(String id);

    /**
     * 解封用户
     */
    int unDelUser(String id);

    /**
     * 修改用户
     */
    int revUser(Map<String, Object> map);

    /**
     * 登录注册验证
     */
    ComUser logUser(Map<String, Object> map);

    /**
     * 查询所有用户
     */
    List<ComUser> selUser();

    /**
     * 根据电话号查询用户信息
     */
    ComUser selUserByPhone(String userPhone);

    void revUserPwd(Map<String, String> map);
}
