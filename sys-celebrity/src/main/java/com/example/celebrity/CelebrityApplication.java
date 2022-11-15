package com.example.celebrity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(scanBasePackages = {"com.example.common", "com.example.celebrity"})
@MapperScan("com.example.common.dao")
@EnableWebSecurity
@EnableGlobalAuthentication
public class CelebrityApplication {
    public static void main(String[] args) {
        SpringApplication.run(CelebrityApplication.class, args);
    }
}
