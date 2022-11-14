package com.example.celebrity.controller;

import com.example.common.service.CelebrityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录相关controller")
public class LoginController {

    @Autowired
    private CelebrityService celebrityService;;


}
