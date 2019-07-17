package cn.zx.controller;

import cn.zx.model.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhaoxiang
 * @date 2019/6/20 23:15
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public ModelAndView Index(){
        Msg msg = new Msg("测试标题","测试内容","欢迎来到hello页面,您拥有 ROLE_ADMIN权限");
        ModelAndView mv = new ModelAndView();
        mv.addObject(msg);
        mv.setViewName("hello");
        return mv;
    }

    @RequestMapping("/admin")
    public String hello(Model model){
//        Msg msg = new Msg("测试标题","测试内容","欢迎来到home页面,您拥有 ROLE_ADMIN权限");
//        model.addAttribute("msg",msg);
        return "home";
    }
}
