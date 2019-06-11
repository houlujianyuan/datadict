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

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.List;

/**
 * @author zhaoge
 */
@Controller
public class DataSourceListController {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private DataSourceListMapper dataSourceListMapper;
    @Autowired
    private DynamicDataSourceCreator dynamicDataSourceCreator;

    @RequestMapping("/toAddDs")
    public String toAddDs(Model model) {

        return "dataSourceList/addDataSource";
    }

    @RequestMapping("/addDs")
    public String addDs(Model model, DataSourceList dataSourceList) {
        if (dataSourceList.getType() == 1){
            dataSourceList.setDriverName("com.mysql.cj.jdbc.Driver");
        }else if (dataSourceList.getType() == 2){
            dataSourceList.setDriverName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }else if (dataSourceList.getType() == 3){
            dataSourceList.setDriverName("org.sqlite.JDBC");
        }

        dataSourceListMapper.insertSelective(dataSourceList);
        //将数据源添加至进程
        DynamicRoutingDataSource dd = (DynamicRoutingDataSource) dataSource;
        DataSourceProperty t = new DataSourceProperty();
        t.setDriverClassName(dataSourceList.getDriverName());
        /*t.setPollName("zxtest");*/
        t.setUsername(dataSourceList.getUsername());
        t.setPassword(dataSourceList.getPassword());
        t.setUrl(dataSourceList.getUrl());
        t.setDruid(new DruidConfig());
        dd.addDataSource(dataSourceList.getDatabaseName(), dynamicDataSourceCreator.createDataSource(t));

        //查询用户所有数据源
        DataSourceList dataSourceList2= new DataSourceList();
        dataSourceList2.setUserId(3);
        List<DataSourceList> dataSourceLists = dataSourceListMapper.select(dataSourceList2);
        model.addAttribute("dataSourceLists", dataSourceLists);
        return "dataSourceList/dataSourceList";
    }

    @RequestMapping("/dataSourceList")
    public String dataSourceList(Model model) {
        //查询用户所有数据源
        DataSourceList dataSourceList = new DataSourceList();
        dataSourceList.setUserId(3);
        List<DataSourceList> dataSourceLists = dataSourceListMapper.select(dataSourceList);
        model.addAttribute("dataSourceLists", dataSourceLists);
        return "dataSourceList/dataSourceList";
    }

    @RequestMapping("/changeDbKey")
    public String changeDbKey(Model model, HttpServletRequest request) {
        //查询用户所有数据源
        DataSourceList dataSourceList2= new DataSourceList();
        dataSourceList2.setUserId(3);
        List<DataSourceList> dataSourceLists = dataSourceListMapper.select(dataSourceList2);
        model.addAttribute("dataSourceLists", dataSourceLists);
        String dbKey = request.getParameter("dbKey");
        model.addAttribute("dbKey", dbKey);
        return "main/main";
    }

}
