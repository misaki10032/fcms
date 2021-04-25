package com.cxy.fcms.controller;

import com.cxy.fcms.mapper.ComTypeMapper;
import com.cxy.fcms.pojo.ComFiction;
import com.cxy.fcms.pojo.ComType;
import com.cxy.fcms.service.FictionService;

import com.cxy.fcms.util.IDUtil;
import com.cxy.fcms.util.LayuiReplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

@Controller
public class FictionController {
    @Autowired
    FictionService fictionService;
    @Autowired
    ComTypeMapper typeMapper;
    
    
    @GetMapping("/getfictions")
    @ResponseBody//数据请求接口
    public Object  getAllFictions() throws JSONException {
        List<ComFiction> fiction = fictionService.getFiction();
        return new LayuiReplay<ComFiction>(0, "OK", fiction.size(), fiction);
    }
    @GetMapping("/addfiction")
    public void addFiction(HttpServletResponse resp, String title, String author, String type, String isdel, String data) throws IOException {
        PrintWriter out = resp.getWriter();
        try{
            HashMap<String,String> map = new HashMap<>();
            String ficid = IDUtil.getID();
            map.put("id", ficid);
            map.put("ficName",title);
            if(author!=null&&!author.equals("")){
                map.put("ficAuthor",author);
            }
            map.put("ficAuthor",null);
            fictionService.addFiction(map,ficid,data,type);
            out.print("ok");
        }catch (Exception e){
            out.print("no");
        }
    }

    @GetMapping("/addfictionWindow")//打开添加的浮动窗口
    public String toAddFictionWindow(Model model){
        List<ComType> comTypes = typeMapper.selectList(null);
        String[] types = new String[comTypes.size()];
        int i = 0;
        for (ComType type : comTypes) {
            types[i] = type.getTyName();
            i++;
        }
        model.addAttribute("types", types);
        return "back/fiction/addFiction";
    }

    @GetMapping("/tofictionxx")
    @ResponseBody
    public String toFictionXX(String index) {
        List<ComFiction> fictionOrderByTime = fictionService.getFictionOrderByTime();
        int index2 = Integer.parseInt(index);
        return fictionOrderByTime.get(index2 - 1).toString();
    }

    @GetMapping("/tofictionhost")
    @ResponseBody
    public String toFictionHost(String index) {
        List<ComFiction> fictions = fictionService.getFictionsOrderByHost();
        for (ComFiction fiction : fictions) {
            System.out.println(fiction);
        }
        int index2 = Integer.parseInt(index);
        return fictions.get(index2 - 1).toString();
    }

    @GetMapping("/delfic")
    public void delFiction(String id, HttpServletResponse rep) {
        PrintWriter out = null;
        try {
            out = rep.getWriter();
            fictionService.delFiction(id);
            out.print("1");
        } catch (IOException e) {
            System.err.println("删除失败!");
        }
    }

}
