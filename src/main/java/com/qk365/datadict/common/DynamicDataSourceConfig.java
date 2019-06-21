package com.qk365.datadict.common;

import com.baomidou.dynamic.datasource.DynamicDataSourceCreator;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.druid.DruidConfig;
import com.qk365.datadict.dao.DataSourceListMapper;
import com.qk365.datadict.po.DataSourceList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
@Service
public class DynamicDataSourceConfig {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private DataSourceListMapper dataSourceListMapper;
    @Autowired
    private DynamicDataSourceCreator dynamicDataSourceCreator;

    @PostConstruct
    public void init() {
        System.out.println("@PostConstruct方法被调用");
        DataSourceList dataSourceList1 = new DataSourceList();
        /*   dataSourceList1.setUserId(list.get(0).getId());*/
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
        }

    }


}
