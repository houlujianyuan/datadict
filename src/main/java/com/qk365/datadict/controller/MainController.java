package com.qk365.datadict.controller;

import com.baomidou.dynamic.datasource.DynamicDataSourceCreator;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.druid.DruidConfig;
import com.qk365.datadict.dao.DataSourceListMapper;
import com.qk365.datadict.po.DataSourceList;
import com.qk365.datadict.po.Users;
import com.qk365.datadict.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.util.List;

/**
 * @author zhaoge
 */
@Controller
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private DataSourceListMapper dataSourceListMapper;
    @Autowired
    private DynamicDataSourceCreator dynamicDataSourceCreator;

    @RequestMapping("/")
    public String index() {
        return "index";
    }
    @RequestMapping("/login")
    public String login() {
        return "index";
    }
    @RequestMapping("/main")
    public String login(Model model, Users users, HttpServletRequest request) {
        if (null == users.getUsername()){
            return "index";
        }
        List<Users> list = userService.selectList(users, "");
        if (list != null && list.size() > 0) {
            HttpSession session = request.getSession();
            session.setAttribute("user", list.get(0));
            //查询datasource
            DataSourceList dataSourceList1 = new DataSourceList();
            dataSourceList1.setUserId(list.get(0).getId());
            List<DataSourceList> dataSourceLists = dataSourceListMapper.select(dataSourceList1);
            if (dataSourceLists != null && dataSourceLists.size() > 0) {
                //将所有数据源添加至进程
                dataSourceLists.stream().forEach(dataSourceList -> {
                            DynamicRoutingDataSource dd = (DynamicRoutingDataSource) dataSource;
                            DataSourceProperty t = new DataSourceProperty();
                            t.setDriverClassName(dataSourceList.getDriverName());
                            t.setPollName(dataSourceList.getDatabaseName());
                            t.setUsername(dataSourceList.getUsername());
                            t.setPassword(dataSourceList.getPassword());
                            t.setUrl(dataSourceList.getUrl());
                            t.setDruid(new DruidConfig());
                            dd.addDataSource(dataSourceList.getDatabaseName(), dynamicDataSourceCreator.createDataSource(t));
                        }
                );
                //跳转默认第一个数据源
                model.addAttribute("dbKey", dataSourceLists.get(0).getDatabaseName());
                model.addAttribute("dataSourceLists", dataSourceLists);
                return "main/main";

            } else {
                //跳转添加数据源页面
                return "dataSourceList/addDataSource";
            }
        }
        model.addAttribute("msg", 1);
        return "index";
    }

    @RequestMapping("/api/dataIndex")
    public String login(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Users users = (Users) session.getAttribute("user");
        //查询datasource
        DataSourceList dataSourceList1 = new DataSourceList();
        dataSourceList1.setUserId(users.getId());
        List<DataSourceList> dataSourceLists = dataSourceListMapper.select(dataSourceList1);
        if (dataSourceLists != null && dataSourceLists.size() > 0) {
            //将所有数据源添加至进程
            dataSourceLists.stream().forEach(dataSourceList -> {
                        DynamicRoutingDataSource dd = (DynamicRoutingDataSource) dataSource;
                        DataSourceProperty t = new DataSourceProperty();
                        t.setDriverClassName(dataSourceList.getDriverName());
                        t.setPollName(dataSourceList.getDatabaseName());
                        t.setUsername(dataSourceList.getUsername());
                        t.setPassword(dataSourceList.getPassword());
                        t.setUrl(dataSourceList.getUrl());
                        t.setDruid(new DruidConfig());
                        dd.addDataSource(dataSourceList.getDatabaseName(), dynamicDataSourceCreator.createDataSource(t));
                    }
            );
            //跳转默认第一个数据源
            model.addAttribute("dbKey", dataSourceLists.get(0).getDatabaseName());
            model.addAttribute("dataSourceLists", dataSourceLists);
            return "main/main";

        } else {
            //跳转添加数据源页面
            return "dataSourceList/addDataSource";
        }
}


    @RequestMapping("/toRegister")
    public String toRegister(Model model) {

        return "register";
    }

    @RequestMapping("/register")
    public String register(Model model, Users users, HttpServletRequest request) {
        Users users1 = new Users();
        BeanUtils.copyProperties(users, users1);
        users1.setPassword(null);
        List<Users> list = userService.selectList(users1, "");
        if (list != null && list.size() > 0) {
            model.addAttribute("msg", 1);
            return "register";
        }
        userService.insertUser(users, "");
        HttpSession session = request.getSession();
        session.setAttribute("user", users);
        return "dataSourceList/addDataSource";
    }
    @RequestMapping("/exit")
    public String exit(Model model,HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "index";
    }

}
