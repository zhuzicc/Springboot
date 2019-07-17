package cn.zx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhaoxiang
 * @date 2019/7/9 17:29
 */
@Controller
public class ErrorPageController {

    @RequestMapping("/403")
    public String toPage403(){
        return "403";
    }

    @RequestMapping("/404")
    public String toPage404(){
        return "404";
    }

    @RequestMapping("/500")
    public String toPage500(){
        return "500";
    }
}
