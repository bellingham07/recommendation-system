package com.example.celebrity.controller;

import com.example.common.dto.LoginDto;
import com.example.common.response.Result;
import com.example.common.service.CelebrityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录相关controller")
public class LoginController {

    @Autowired
    private CelebrityService celebrityService;;

    @PostMapping("login")
    public Result login(LoginDto loginDto) {
        return celebrityService.login(loginDto);
    }

    @GetMapping("logout")
    public Result logout() {
        return celebrityService.logout();
    }
}
