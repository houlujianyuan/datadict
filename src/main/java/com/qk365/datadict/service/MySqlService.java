package com.qk365.datadict.service;

import com.qk365.datadict.po.TableInfo;

import java.util.List;
import java.util.Map;

public interface MySqlService {

    /**
     * 查询数据库表信息
     * @return
     */
    List<Map<String,Object>> findTableName(String dbKey,String dbName);

    /**
     * 查询表字段信息
     * @param id
     * @return
     */
    List<TableInfo> findTableInfo(String dbKey , String tableName);

    /**
     * 修改表说明信息
     * @param tableName
     * @param explain
     * @return
     */
    void editTableExplain(String tableName, String explain, String dbKey);

    void addTableExplain(String tableName, String explain, String dbKey);
    /**
     * 修改列说明信息
     * @param tableName
     * @param explain
     * @param columnName
     * @return
     */
    void editColumnExplain(String tableName, String explain, String columnName, String dbKey);

    void addColumnExplain(String tableName, String explain, String columnName, String dbKey);

    /**
     * 查询表说明
     * @param id
     * @return
     */
    List<Map<String,Object>>  findTableNameExplain(Long id, String dbKey);

    void insertEditTableInfo(String tableName, String oldVal, String newVal, String column, String type, String dbKey);

    String generateEntityClasses(List<TableInfo> list, String tableName, String dbKey);
}
