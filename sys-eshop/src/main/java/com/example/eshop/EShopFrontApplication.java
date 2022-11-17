package com.example.eshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(scanBasePackages = {"com.example.common", "com.example.eshop"})
@MapperScan("com.example.common.dao")
@EnableWebSecurity
public class EShopFrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(EShopFrontApplication.class, args);
    }
}
