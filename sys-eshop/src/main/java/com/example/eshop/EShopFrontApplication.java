package com.example.eshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.common"})
@MapperScan("com.example.common.dao")
public class EShopFrontApplication {
    //dfafa
    public static void main(String[] args) {
        SpringApplication.run(EShopFrontApplication.class, args);
    }
}
