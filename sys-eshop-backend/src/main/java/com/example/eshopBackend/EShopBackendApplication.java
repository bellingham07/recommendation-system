package com.example.eshopBackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.common"})
@MapperScan("com.example.common.dao")
public class EShopBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(EShopBackendApplication.class, args);
    }
}
