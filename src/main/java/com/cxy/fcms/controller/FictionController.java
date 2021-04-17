package com.cxy.fcms.controller;

import com.cxy.fcms.pojo.ComFiction;
import com.cxy.fcms.service.FictionService;

import com.cxy.fcms.util.LayuiReplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FictionController {
    @Autowired
    FictionService fictionService;
    @GetMapping("/getfictions")
    public Object  getAllFictions() throws JSONException {
        List<ComFiction> fiction = fictionService.getFiction();
        return new LayuiReplay<ComFiction>(0, "OK", fiction.size(), fiction);
    }

}
