package com.aqiu.spzx.pay;

import com.aqiu.spzx.common.annotation.EnableUserWebMvcConfiguration;
import com.aqiu.spzx.pay.utils.AlipayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableUserWebMvcConfiguration
@EnableConfigurationProperties(AlipayProperties.class)
@EnableFeignClients(basePackages = "com.aqiu.spzx")
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
