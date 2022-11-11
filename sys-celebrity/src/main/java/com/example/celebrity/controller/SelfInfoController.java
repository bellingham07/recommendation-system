package com.example.celebrity.controller;

import com.example.common.dto.AddressDto;
import com.example.common.entity.Address;
import com.example.common.entity.User;
import com.example.common.response.Result;
import com.example.common.service.AddressService;
import com.example.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("celebrity/selfInfo")
public class SelfInfoController {
    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @PostMapping("picUpdate")
    public Result headPicUpdate(HttpSession session, @RequestParam("_pic") MultipartFile pic) {
        return userService.updatePic(session, pic);
    }

    @PostMapping("update")
    public Result update(User user, HttpSession session) {
        return userService.updateInfo(user, session);
    }

    @PostMapping("addAddress")
    public Result addAddress(AddressDto addressDto) {
        return addressService.addAddress(addressDto);
    }

    @GetMapping("updateAddress")
    public Result getAddress(@RequestParam("addressId") Integer addressId) {
        return Result.test(addressService.getById(addressId));
    }

    @PostMapping("updateAddress")
    public Result updateAddress(Address address) {
        return addressService.updateAddress(address);
    }

    @GetMapping("removeAddress")
    public Result removeAddress(@RequestParam("addressId") Integer addressId) {
        return Result.test(addressService.removeById(addressId));
    }
}
