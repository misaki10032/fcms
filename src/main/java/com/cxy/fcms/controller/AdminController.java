package com.cxy.fcms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/toFicList")
    public String toFicList(){
        return "back/fiction/booklist";
    }
}
