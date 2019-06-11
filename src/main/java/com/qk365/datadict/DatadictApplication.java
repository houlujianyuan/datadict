package com.qk365.datadict;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@ComponentScan({"com.qk365"})
@ImportResource({ "classpath*:spring/spring-root.xml" })
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class DatadictApplication {
    public static void main(String[] args) {
        SpringApplication.run(DatadictApplication.class, args);
    }

}
