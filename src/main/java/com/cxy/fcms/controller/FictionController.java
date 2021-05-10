package com.cxy.fcms.controller;

import com.cxy.fcms.mapper.ComFictionMapper;
import com.cxy.fcms.mapper.ComTypeMapper;
import com.cxy.fcms.pojo.ComFicDate;
import com.cxy.fcms.pojo.ComFiction;
import com.cxy.fcms.pojo.ComType;
import com.cxy.fcms.service.FictionService;

import com.cxy.fcms.util.IDUtil;
import com.cxy.fcms.util.LayuiReplay;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @ClassName UserController
 * @Author 陈新予
 * @Date 2021/4/29 18:11
 * @Version 1.0
 */
@Controller
public class FictionController {
    @Autowired
    FictionService fictionService;
    @Autowired
    ComTypeMapper typeMapper;

    @GetMapping("getfictions")
    @ResponseBody//数据请求接口
    public Object getAllFictions() {
        List<ComFiction> fiction = fictionService.getFiction();
        return new LayuiReplay<ComFiction>(0, "OK", fiction.size(), fiction);
    }

    @GetMapping("addfiction")
    public void addFiction(HttpServletResponse resp, String title, String author, String type, String isdel, String data) throws IOException {
        PrintWriter out = resp.getWriter();
        try {
            HashMap<String, String> map = new HashMap<>();
            String ficid = IDUtil.getID();
            map.put("id", ficid);
            map.put("ficName",title);
            if (author != null && !author.equals("")) {
                map.put("ficAuthor", author);
            } else {
                map.put("ficAuthor", null);
            }
            fictionService.addFiction(map,ficid,data,type);
            out.print("ok");
        }catch (Exception e){
            out.print("no");
        }
    }

    @GetMapping("addfictionWindow")//打开添加的浮动窗口
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

    @GetMapping("tofictionxx")
    public String toFictionXX(String index, Model model) {
        List<ComFiction> fictions = fictionService.getFictionOrderByTime();
        for (ComFiction fiction : fictions) {
            System.out.println(fiction);
        }

        int index2 = Integer.parseInt(index);
        ComFiction comFiction = fictions.get(index2 - 1);
        //发送数据
        model.addAttribute("ficBook", comFiction);
        //查询内容信息
        ComFicDate data = fictionService.getFictionDataById(comFiction.getId());
        model.addAttribute("data", data);
        return "front/fiction/info";
    }

    @GetMapping("tofictionhost")
    public String toFictionHost(String index, Model model) {
        List<ComFiction> fictions = fictionService.getFictionsOrderByHost();
        for (ComFiction fiction : fictions) {
            System.out.println(fiction);
        }
        int index2 = Integer.parseInt(index);
        ComFiction comFiction = fictions.get(index2 - 1);
        //发送数据
        model.addAttribute("ficBook", comFiction);
        //查询内容信息
        ComFicDate data = fictionService.getFictionDataById(comFiction.getId());
        model.addAttribute("data", data);
        return "front/fiction/info";
    }

    @GetMapping("delfic")
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

    @GetMapping("getfictionslimit")
    @ResponseBody//数据请求接口
    public Object getAllFictions2(int page, int limit) {
        List<ComFiction> fiction = fictionService.getFiction();
        List<ComFiction> fiction3 = new ArrayList<>();
        for (int i = (page - 1) * limit; i < fiction.size(); i++) {
            fiction3.add(fiction.get(i));
            if (fiction3.size() == limit) {
                break;
            }
        }
        return new LayuiReplay<ComFiction>(0, "OK", fiction.size(), fiction3);
    }

    @GetMapping("getfictionSearch")
    public String getfictionSearch(String searchMSG, Model model) {
        List<ComFiction> fictions = fictionService.SearchFiction(searchMSG);
        int pages;
        if (fictions.size() % 5 == 0) {
            pages = fictions.size() / 5;
        } else {
            pages = fictions.size() / 5 + 1;
        }
        ArrayList<String> fictionNames = new ArrayList<>();
        ArrayList<String> fictionTypes = new ArrayList<>();
        ArrayList<String> imges = new ArrayList<>();
        ArrayList<Integer> fictionHosts = new ArrayList<>();
        ArrayList<String> fictionDatas = new ArrayList<>();
        for (ComFiction fiction : fictions) {
            fictionNames.add(fiction.getFicName());
            fictionTypes.add(fiction.getFicType());
            imges.add(fiction.getFicImg());
            fictionHosts.add(fiction.getFicHost());
            fictionDatas.add(fiction.getGmtCreate());
        }
        model.addAttribute("fictionNames", fictionNames);
        model.addAttribute("fictionTypes", fictionTypes);
        model.addAttribute("imges", imges);
        model.addAttribute("fictionHosts", fictionHosts);
        model.addAttribute("fictionData", fictionDatas);
        model.addAttribute("pages", pages);
        return "search/titleSearch";
    }

    /**
     * RESTful风格的搜索
     *
     * @param searchMSG 搜索信息
     * @return 返回json
     */
    @GetMapping("getfictionSearch/{searchMSG}")
    @ResponseBody//数据请求接口
    public Object getfictionSearchREST(@PathVariable("searchMSG") String searchMSG) {
        List<ComFiction> fictions = fictionService.SearchFiction(searchMSG);
        return new LayuiReplay<ComFiction>(0, "OK", fictions.size(), fictions);
    }

    /**
     * 热门榜单
     *
     * @return 返回json
     */
    @GetMapping("getfictionTopHost")
    @ResponseBody
    public Object toHostTop() {
        List<ComFiction> fictions = fictionService.getFictionsOrderByHost();
        return new LayuiReplay<ComFiction>(0, "OK", fictions.size(), fictions);
    }

    @GetMapping("tofictionInfo")
    public String ficInfo(String name, Model model) {
        ComFiction fiction = fictionService.getFictionByName(name);

        //发送数据
        model.addAttribute("ficBook", fiction);
        //查询内容信息
        ComFicDate data = fictionService.getFictionDataById(fiction.getId());
        model.addAttribute("data", data);
        return "front/fiction/info";
    }

}
