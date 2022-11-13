package com.example.celebrity.controller;

import com.example.common.dto.AddressDto;
import com.example.common.entity.Celebrity;
import com.example.common.response.Result;
import com.example.common.service.AddressService;
import com.example.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("info")
public class InfoController {

    @Autowired
    private UserService userService;

    @PostMapping("avatar")
    public Result updateAvatar(@RequestParam("avatar") MultipartFile avatar) {
        return userService.updateAvatar(avatar);
    }

    @PostMapping("update")
    public Result update(Celebrity celebrity) {
        return userService.updateInfo(celebrity);
    }


}
