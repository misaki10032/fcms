package com.cxy.fcms.controller;

import com.cxy.fcms.pojo.ComFiction;
import com.cxy.fcms.service.FictionService;

import com.cxy.fcms.util.LayuiReplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

@Controller
public class FictionController {

    @Autowired
    FictionService fictionService;

    @GetMapping("/getfictions")
    @ResponseBody
    public Object getAllFictions() throws JSONException {
        List<ComFiction> fiction = fictionService.getFiction();
        return new LayuiReplay<ComFiction>(0, "OK", fiction.size(), fiction);
    }

    @GetMapping("/addfictionWindow")
    public String toAddFictionWindow() {
        return "back/fiction/addFiction";
    }

}
