package com.qk365.datadict.service;

import com.qk365.datadict.po.TableInfo;

import java.util.List;
import java.util.Map;

public interface DataSourceService {

    List<Map<String, Object>> left(String dbKey );


    List<Map<String, Object>> findTableName(String dbKey,String dbName);
    List<TableInfo> findTableInfo(String dbName,String dbKey);

    void editTableExplain(String tableName,String explain, String oldVal, String dbKey);
    void editColumnExplain(String tableName,String explain,String claName, String oldVal, String dbKey);

    String generateEntityClasses(List<TableInfo> list,String dbName,String dbKey);
}
