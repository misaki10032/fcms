package com.cxy.fcms.service;

import com.cxy.fcms.pojo.SysAdmin;

import java.util.HashMap;
import java.util.List;

public interface LoginService {

    List<SysAdmin> getAdmins();

    void addAdmin(HashMap<String,String> map);

}
