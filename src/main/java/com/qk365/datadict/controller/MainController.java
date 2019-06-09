package com.qk365.datadict.controller;

import com.qk365.datadict.po.Users;
import com.qk365.datadict.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author zhaoge
 */
@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(){
        return "index";
    }
    @RequestMapping("/main")
    public String login(Model model, Users users){
        List<Users> list = userService.selectList(users);
        if (list !=null && list.size() >0 ){
            model.addAttribute("msg",0);
            return "main/table";
        }

        model.addAttribute("msg",1);

        return "index";
    }
    @RequestMapping("/toRegister")
    public String toRegister(Model model){

        return "register";
    }
    @RequestMapping("/register")
    public String register(Model model, Users users){
        users.setPassword(null);
        List<Users> list = userService.selectList(users);
        if (list !=null && list.size() >0 ){
            model.addAttribute("msg",1);
            return "register";
        }
        userService.insertUser(users);
        return "index";
    }
}
