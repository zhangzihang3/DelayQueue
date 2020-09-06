package com.zzh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
//开启定时器
@EnableScheduling
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class start {

    public static void main(String[] args) {

        SpringApplication.run(start.class, args);
    }

}
