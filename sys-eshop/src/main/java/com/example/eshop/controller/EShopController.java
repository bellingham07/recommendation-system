package com.example.eshop.controller;

import com.example.common.dto.LoginDto;
import com.example.common.response.Result;
import com.example.common.service.EShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EShopController {

    @Autowired
    private EShopService eShopService;

    @PostMapping("/login")
    public Result login(LoginDto loginDto) {
        return eShopService.login(loginDto);
    }
}
