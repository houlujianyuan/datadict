package com.qk365.datadict.common;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.qk365.datadict.dao.EditTableInfoMapper;
import com.qk365.datadict.dao.SqlServerMapper;
import com.qk365.datadict.po.EditTableInfo;
import com.qk365.datadict.po.TableInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author zhaoge sqlserver
 */
@Component("datasource_2")
public class DataSource_2 implements DataSourceItem {
    @Autowired
    private SqlServerMapper sqlServerMapper;
    @Autowired
    private EditTableInfoMapper editTableInfoMapper;



    @Override
    @DS("#dbKey")
    public List<Map<String, Object>> findTableName(String dbKey, String dbName) {
        return sqlServerMapper.findTableName();
    }

    @Override
    @DS("#dbKey")
    public List<TableInfo> findTableInfo(String tableName,String dbKey) {
        return sqlServerMapper.findTableInfo(tableName);
    }


    @Override
    @DS("#dbKey")
    public void editTableExplain(String tableName, String explain,String dbKey,String oldValue) {
        if (oldValue.equals("请输入表说明")){
            sqlServerMapper.addTableExplain(tableName,explain);
        }else {
            sqlServerMapper.editTableExplain(tableName, explain);
        }

    }

    @Override
    @DS("#dbKey")
    public void addTableExplain(String tableName, String explain,String dbKey) {
        sqlServerMapper.addTableExplain(tableName,explain);
    }

    @Override
    @DS("#dbKey")
    public void editColumnExplain(String tableName, String explain, String columnName,String dbKey,String oldVal) {
        if (oldVal.equals("")){
            sqlServerMapper.addColumnExplain(tableName,explain,columnName);
        }else {
            sqlServerMapper.editColumnExplain(tableName,explain,columnName);
        }

    }

    @Override
    @DS("#dbKey")
    public void addColumnExplain(String tableName, String explain, String columnName,String dbKey) {
        sqlServerMapper.addColumnExplain(tableName,explain,columnName);
    }

    @Override
    @DS("#dbKey")
    public List<Map<String, Object>> findTableNameExplain(Long id,String dbKey) {
        return sqlServerMapper.findTableNameExplain(id);
    }

    @Override
    @DS("#dbKey")
    public void insertEditTableInfo(String tableName, String oldVal, String newVal, String column, String type,String dbKey) {
        EditTableInfo info = new EditTableInfo();
        info.setColumnName(column);
        info.setCreateTime(new Date());
        info.setNewVal(newVal);
        info.setOldVal(oldVal);
        info.setTableName(tableName);
        info.setType(type);
        editTableInfoMapper.insertSelective(info);
    }

    @Override
    @DS("#dbKey")
    public String generateEntityClasses(List<TableInfo> list, String tableName,String dbKey) {
        //import 部分
        Set<String> importSet=new HashSet<String>();
        importSet.add("import javax.persistence.Column;\n");
        importSet.add("import javax.persistence.Id;\n");
        importSet.add("import javax.persistence.Table;\n");
        importSet.add("import lombok.Data;\n");
        importSet.add("import lombok.ToString;\n");

        List<String> classSet=new ArrayList<String>();
        classSet.add("@Data\n");
        classSet.add("@ToString\n");
        classSet.add("@Table(name = \""+tableName+"\")\n");

        String className= this.getClassNameByTableName(tableName);
        classSet.add("public class "+className+" {\n");

        StringBuffer buf = new StringBuffer();

        list.forEach(l->{
            buf.append("\t\t").append("/**").append(l.getExplain()).append("**/\n");
            if("1".equals(l.getPk())){
                buf.append("\t\t").append("@Id").append("\n");
            }

            buf.append("\t\t").append("@Column(name = \""+l.getColumnName()+"\")").append("\n");

            String javaType=this.getJavaDataType(l.getDataType(),importSet);

            buf.append("\t\t").append("private "+javaType+" "+ this.toLowerCaseFirstOne(
                    this.getClassNameByTableName(l.getColumnName()) )+";").append("\n");

            buf.append("\n");
        });


        StringBuffer classBuf = new StringBuffer();
        classBuf.append("\n");
        importSet.forEach(i->{
            classBuf.append(i);
        });

        classSet.forEach(c->{
            classBuf.append(c);
        });

        classBuf.append(buf);

        classBuf.append("}");

        return classBuf.toString();
    }

    private String getJavaDataType(String dataType, Set<String> importSet) {

        if("int".equals(dataType)){
            return "Integer";
        }

        if("varchar".equals(dataType)){
            return "String";
        }

        if("datetime".equals(dataType)){
            importSet.add("import java.sql.Timestamp;\n");
            return "Timestamp";
        }

        if("bigint".equals(dataType)){
            return "Long";
        }

        if("nvarchar".equals(dataType)){
            return "String";
        }

        if("decimal".equals(dataType)){
            importSet.add("import java.math.BigDecimal;\n");
            return "BigDecimal";
        }

        return dataType;
    }


    public String getClassNameByTableName(String tableName) {
        String[] strs = tableName.split("_");
        StringBuffer buf = new StringBuffer();
        for(int i=0;i<strs.length;i++){
            buf.append(this.toUpperCaseFirstOne(strs[i].toLowerCase()));
        }
        return buf.toString();
    }

    //首字母转大写
    public  String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }
    //首字母转小写
    public  String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

}
