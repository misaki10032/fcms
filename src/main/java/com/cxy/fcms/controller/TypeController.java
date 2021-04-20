package com.cxy.fcms.controller;

import com.cxy.fcms.mapper.ComTypeMapper;
import com.cxy.fcms.pojo.ComType;
import com.cxy.fcms.pojo.ComUser;
import com.cxy.fcms.service.TypeService;
import com.cxy.fcms.util.IDUtil;
import com.cxy.fcms.util.LayuiReplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

@Controller
public class TypeController {
    @Autowired
    TypeService typeService;

    @GetMapping("/toTypes")
    public String toTypePage() {
        return "back/type/typelist";
    }

    @GetMapping("/getTypes")
    @ResponseBody
    public Object getTypeJSON() {
        List<ComType> comTypes = typeService.selType();
        return new LayuiReplay<ComType>(0, "OK", comTypes.size(), comTypes);
    }

    @GetMapping("/deltype")
    public void DelType(String id, HttpServletResponse rep) {
        typeService.delType(id);
        PrintWriter out = null;
        try {
            out = rep.getWriter();
            typeService.delType(id);
            out.print("1");
        } catch (IOException e) {
            System.err.println("删除失败!");
        }
    }

    @GetMapping("/addType")
    public void AddType(String tyname, HttpServletResponse rep) {
        HashMap<String, String> map = new HashMap<>();
        map.put("tyName", tyname);
        map.put("id", IDUtil.getID());
        PrintWriter out = null;
        try {
            out = rep.getWriter();
            typeService.addType(map);
            out.print("1");
        } catch (IOException e) {
            System.err.println("删除失败!");
        }
    }

    @GetMapping("/updateTypeName")
    public void updateTypeName(String id, String name, HttpServletResponse rep) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("tyName", name);
        map.put("id", id);
        PrintWriter out = null;
        try {
            out = rep.getWriter();
            typeService.revType(map);
            out.print("1");
        } catch (IOException e) {
            System.err.println("删除失败!");
        }
    }
}
