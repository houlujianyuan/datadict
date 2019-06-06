package com.qk365.datadict;

import com.qk365.datadict.utils.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }
    @RequestMapping("/login")
    public String login(Model model,String username,String password){
        if (username!=null && password!=null &&username.equals("admin") && password.equals("123456")){
            return "main/table";
        }
        model.addAttribute("msg","账号或密码错误");

        return "index";
    }
}
