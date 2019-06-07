package com.qk365.datadict;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@ComponentScan({"com.qk365"})
@SpringBootApplication
@ImportResource({ "classpath*:spring/spring-root.xml" })
public class DatadictApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatadictApplication.class, args);
    }

}
