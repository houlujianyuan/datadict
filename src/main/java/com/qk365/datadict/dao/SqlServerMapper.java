package com.qk365.datadict.dao;

import com.qk365.datadict.po.TableInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SqlServerMapper {
    /**
     * 查询数据库表信息
     * @return
     */
    List<Map<String,Object>> findTableName();

    /**
     * 查询表字段信息
     * @param id
     * @return
     */
    List<TableInfo> findTableInfo(@Param("name") String name);

    /**
     * 修改表说明信息
     * @param tableName
     * @param explain
     * @return
     */
    void editTableExplain(@Param("tableName") String tableName, @Param("explain") String explain);

    void addTableExplain(@Param("tableName") String tableName, @Param("explain") String explain);
    /**
     * 修改列说明信息
     * @param tableName
     * @param explain
     * @param columnName
     * @return
     */
    void editColumnExplain(@Param("tableName")String tableName, @Param("explain") String explain,@Param("columnName")  String columnName);

    void addColumnExplain(@Param("tableName")String tableName, @Param("explain") String explain,@Param("columnName")  String columnName);

    /**
     * 查询表说明
     * @param id
     * @return
     */
    List<Map<String,Object>>  findTableNameExplain(@Param("id") Long id);

}
