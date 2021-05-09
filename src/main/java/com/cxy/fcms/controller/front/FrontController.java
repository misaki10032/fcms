package com.cxy.fcms.controller.front;

import com.cxy.fcms.pojo.ComFicDate;
import com.cxy.fcms.pojo.ComFiction;
import com.cxy.fcms.service.FictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName FrontController
 * @Description TODO
 * @Author 码农天宇
 * @Date 2021/4/20 15:18
 * @Version 1.0
 */

@Controller
public class FrontController {

    @Autowired
    FictionService fictionService;

    // 跳转到单本书籍页面
    @RequestMapping("info")
    public String info() {
        return "front/fiction/info";
    }

    // 跳转到单本书籍详细页面
    @RequestMapping("toContentPage")
    public String toContent(String bid, Model model) {
        ComFicDate data = fictionService.getFictionDataById(bid);
        List<ComFiction> fiction = fictionService.getFiction();
        ComFiction ff = null;
        for (ComFiction comFiction : fiction) {
            if (comFiction.getId().equals(bid)) {
                ff = comFiction;
                break;
            }
        }
        if (ff != null) {
            model.addAttribute("fiction", ff);
        } else {
            model.addAttribute("fiction", new ComFiction());
        }
        model.addAttribute("data", data);
        return "front/fiction/content";
    }

}
