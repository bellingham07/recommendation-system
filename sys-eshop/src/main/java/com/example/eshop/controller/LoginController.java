package com.example.eshop.controller;

import com.example.common.dto.LoginDto;
import com.example.common.response.Result;
import com.example.common.service.EShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private EShopService eShopService;

    @PostMapping("login")
    public Result login(LoginDto loginDto) {
        return eShopService.login(loginDto);
    }

    @GetMapping("logout")
    public Result logout() {
        return eShopService.logout();
    }
}
