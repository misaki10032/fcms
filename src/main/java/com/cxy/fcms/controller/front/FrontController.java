package com.cxy.fcms.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName FrontController
 * @Description TODO
 * @Author 码农天宇
 * @Date 2021/4/20 15:18
 * @Version 1.0
 */

@Controller
public class FrontController {


    // 跳转到单本书籍页面
    @RequestMapping("/info")
    public String info() {
        return "/front/fiction/info";
    }

    // 跳转到单本书籍详细页面
    @RequestMapping("/toContentPage")
    public String toContent(){
        return "front/fiction/content";
    }

}
