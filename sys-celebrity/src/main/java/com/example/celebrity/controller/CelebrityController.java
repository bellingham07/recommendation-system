package com.example.celebrity.controller;

import com.example.common.dto.LoginDto;
import com.example.common.dto.PasswordDto;
import com.example.common.response.Result;
import com.example.common.service.CelebrityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "登录相关controller")
public class CelebrityController {

    @Autowired
    private CelebrityService celebrityService;;

    @PostMapping("login")
    public Result login(LoginDto loginDto) {
        System.out.println(loginDto);
        return celebrityService.login(loginDto);
    }

    @GetMapping("logout")
    public Result logout() {
        return celebrityService.logout();
    }

    @PostMapping("password")
    public Result updateByRawPassword(PasswordDto passwordDto) {
        return celebrityService.validatePasswordAndUpdate(passwordDto);
    }

//    @PostMapping("register")
//    public Result register(RegisterDto registerDto) {
//        return celebrityService.register(registerDto);
//    }
}
