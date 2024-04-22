package com.aqiu.spzx.manager;

import com.aqiu.spzx.manager.properties.MinIoProperties;
import com.aqiu.spzx.manager.properties.UserAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {"com.aqiu.spzx"})
@EnableConfigurationProperties(value = {UserAuthProperties.class, MinIoProperties.class})
@EnableTransactionManagement
@EnableScheduling
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
