package com.cxy.fcms.service;

import com.cxy.fcms.pojo.ComFicDate;
import com.cxy.fcms.pojo.ComFiction;

import java.util.HashMap;
import java.util.List;

public interface FictionService {

    /**
     * 添加一个新书
     */
    List<ComFiction> getFiction();

    void addFiction(HashMap<String, String> ficmap, String id, String text, String type);

    List<ComFiction> getFictionOrderByTime();

    List<ComFiction> getFictionsOrderByHost();

    void delFiction(String id);

    List<String> getFictionsByType(String id);

    ComFicDate getFictionDataById(String id);
}
