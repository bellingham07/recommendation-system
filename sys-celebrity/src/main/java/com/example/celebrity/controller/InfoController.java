package com.example.celebrity.controller;

import com.example.common.entity.Celebrity;
import com.example.common.response.Result;
import com.example.common.service.CelebrityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("info")
@Api(tags = "网红信息相关controller")
public class InfoController {

    @Autowired
    private CelebrityService celebrityService;

    @PostMapping("avatar")
    public Result updateAvatar(@RequestParam("avatar") MultipartFile avatar) {
        return celebrityService.updateAvatar(avatar);
    }

    @PostMapping("update")
    public Result update(Celebrity celebrity) {
        return celebrityService.updateInfo(celebrity);
    }


}
