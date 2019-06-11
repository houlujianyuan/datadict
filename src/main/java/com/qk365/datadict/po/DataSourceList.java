package com.qk365.datadict.po;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "data_source_list")
@Data
@ToString
@NoArgsConstructor
public class DataSourceList {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "type")
    private Integer type;
    
    @Column(name = "url")
    private String url;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "database_name")
    private String databaseName;

    @Column(name = "driver_name")
    private String driverName;

}
