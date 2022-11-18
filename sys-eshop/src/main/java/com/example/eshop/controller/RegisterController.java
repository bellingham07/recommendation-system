package com.example.eshop.controller;

import com.example.common.dto.RegisterDto;
import com.example.common.response.Result;
import com.example.common.service.EShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    private EShopService eShopService;

    @PostMapping("register")
    public Result register(RegisterDto registerDto){
        return eShopService.register(registerDto);
    }
}
