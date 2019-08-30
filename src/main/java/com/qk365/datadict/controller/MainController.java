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
    private DataSourceListMapper dataSourceListMapper;

    @RequestMapping("/")
    public String main(Model model,HttpServletRequest request) {
            //查询datasource
            List<DataSourceList> dataSourceLists = dataSourceListMapper.selectAll();
            if (dataSourceLists != null && dataSourceLists.size() > 0) {
                //跳转默认第一个数据源
                model.addAttribute("dbKey", dataSourceLists.get(0).getDatabaseName());
                model.addAttribute("dataSourceLists", dataSourceLists);
                return "main/main";

            } else {
                //跳转添加数据源页面
                return "dataSourceList/addDataSource";
            }
    }

}
