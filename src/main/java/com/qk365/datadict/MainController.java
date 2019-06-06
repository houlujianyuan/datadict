package com.qk365.datadict;

import com.qk365.datadict.utils.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("date", DateUtils.dateToStringByFormat(DateUtils.FORMAT3,new Date()));
        return "index";
    }
    @RequestMapping("/login")
    public String login(Model model){


        return "index";
    }
}
