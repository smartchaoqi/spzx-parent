package com.aqiu.spzx.manager;

import com.aqiu.spzx.manager.properties.MinIoProperties;
import com.aqiu.spzx.manager.properties.UserAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.aqiu.spzx"})
@EnableConfigurationProperties(value = {UserAuthProperties.class, MinIoProperties.class})
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
