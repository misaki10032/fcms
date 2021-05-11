package com.cxy.fcms.service;

import com.cxy.fcms.pojo.SysAdmin;
import org.apache.shiro.crypto.hash.Hash;

import java.util.HashMap;
import java.util.List;

/**
 * @InterfaceName AdminService
 * @Author 陈新予(blank)
 * @Date 2021/5/11
 * @Version 1.0
 */
public interface AdminService {

    List<SysAdmin> getAdmins();

    void revAdmin(HashMap<String, String> map);

    void delAdmin(HashMap<String, String> map);

}
