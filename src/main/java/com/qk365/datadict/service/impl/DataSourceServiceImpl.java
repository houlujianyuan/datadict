package com.qk365.datadict.service.impl;

import com.qk365.datadict.common.DataSourceItem;
import com.qk365.datadict.common.ServiceFactory;
import com.qk365.datadict.dao.DataSourceListMapper;
import com.qk365.datadict.po.DataSourceList;
import com.qk365.datadict.po.TableInfo;
import com.qk365.datadict.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 6154876
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {
    @Autowired
    private DataSourceListMapper dataSourceListMapper;
    @Override
    public List<Map<String, Object>> left(String dbKey) {

        DataSourceItem item = ServiceFactory.createItem("datasource_" + findDbType(dbKey));

        return item.findTableName(dbKey, dbKey);
    }

    @Override
    public List<Map<String, Object>> findTableName(String dbKey, String dbName) {
        DataSourceItem item = ServiceFactory.createItem("datasource_" + findDbType(dbKey));


        return item.findTableName(dbKey,dbName);
    }

    @Override
    public List<TableInfo> findTableInfo(String dbName,String dbKey) {
        DataSourceItem item = ServiceFactory.createItem("datasource_" + findDbType(dbKey));

        return item.findTableInfo(dbName,dbKey);
    }

    @Override
    public void editTableExplain(String tableName, String explain, String oldVal, String dbKey) {
        DataSourceItem item = ServiceFactory.createItem("datasource_" + findDbType(dbKey));
        item.editTableExplain(tableName,explain,dbKey,oldVal);
    }

    @Override
    public void editColumnExplain(String tableName, String explain, String claName, String oldVal, String dbKey) {
        DataSourceItem item = ServiceFactory.createItem("datasource_" + findDbType(dbKey));
        item.editColumnExplain(tableName,explain,claName,dbKey,oldVal);
    }

    @Override
    public String generateEntityClasses(List<TableInfo> list, String dbName, String dbKey) {
        DataSourceItem item = ServiceFactory.createItem("datasource_" + findDbType(dbKey));

        return item.generateEntityClasses(list,dbName,dbKey);
    }


    private Integer findDbType(String dbKey) {
        //根据dbkey查询数据库类型
        DataSourceList dataSourceList = new DataSourceList();
        dataSourceList.setDatabaseName(dbKey);
        DataSourceList dataSourceList1 = dataSourceListMapper.selectOne(dataSourceList);
        return dataSourceList1.getType();
    }
}
