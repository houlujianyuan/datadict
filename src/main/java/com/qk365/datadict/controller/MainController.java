package com.qk365.datadict.controller;

import com.baomidou.dynamic.datasource.DynamicDataSourceCreator;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.druid.DruidConfig;
import com.qk365.datadict.dao.DataSourceListMapper;
import com.qk365.datadict.po.DataSourceList;
import com.qk365.datadict.po.Users;
import com.qk365.datadict.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/main")
    public String login(Model model, Users users) {
        List<Users> list = userService.selectList(users, "");
        if (list != null && list.size() > 0) {
            //查询datasource
            List<DataSourceList> dataSourceLists = dataSourceListMapper.selectAll();
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
                model.addAttribute("dataSourceLists",dataSourceLists);
                return "main/main";

            } else {
                //跳转添加数据源页面
                return "dataSourceList/addDataSource";
            }
        }
        model.addAttribute("msg", 1);
        return "index";
    }

    @RequestMapping("/toRegister")
    public String toRegister(Model model) {

        return "register";
    }

    @RequestMapping("/register")
    public String register(Model model, Users users) {
        users.setPassword(null);
        List<Users> list = userService.selectList(users, "");
        if (list != null && list.size() > 0) {
            model.addAttribute("msg", 1);
            return "register";
        }
        userService.insertUser(users, "");
        return "index";
    }


}
