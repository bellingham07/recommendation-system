package com.example.celebrity.controller;

import com.example.common.dto.RegisterDto;
import com.example.common.response.Result;
import com.example.common.service.CelebrityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    private CelebrityService celebrityService;

    @PostMapping("/register")
    public Result register(RegisterDto registerDto) {
        return celebrityService.Register(registerDto);
    }
}
