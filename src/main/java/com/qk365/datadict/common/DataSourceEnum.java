package com.qk365.datadict.common;

/**
 * @author zhaoge
 */
public enum DataSourceEnum {
    DATA_DRIVER_MYSQL(1,"com.mysql.cj.jdbc.Driver"),
    DATA_DRIVER_SQLSERVER(2,"com.microsoft.sqlserver.jdbc.SQLServerDriver"),
    DATA_DRIVER_SQLITE(3,"org.sqlite.JDBC");

    public static DataSourceEnum[] dataSource_CheckStatusList = {
            DATA_DRIVER_MYSQL,
            DATA_DRIVER_SQLSERVER,
            DATA_DRIVER_SQLITE
    };
    private DataSourceEnum(Integer status, String statusName){
        this.status = status;
        this.statusName = statusName;
    }


    private Integer status;
    private String statusName;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    public static String getDriverNameById(Integer id){
        for (DataSourceEnum bit : DataSourceEnum.values()) {
            if(id.equals(bit.getStatus())){

                return bit.getStatusName();
            }
        }
        return "";
    }
}
