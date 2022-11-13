package com.example.eshop.controller;

import com.example.common.dto.AddressDto;
import com.example.common.response.Result;
import com.example.common.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("address")
public class AddressController {


    @Autowired
    private AddressService addressService;

    // TODO 获取addresstList
    @GetMapping("list")
    private Result list() {
        return addressService.listAddress();
    }

    // TODO 添加地址
    @PostMapping
    public Result add(AddressDto addressDto) {
        return addressService.add(addressDto);
    }

   // 获取单条
    @GetMapping("{id}")
    public Result getOne(@PathVariable("id") Long id) {
        return Result.test(addressService.getById(id));
    }

    // TODO 更新地址
    @PostMapping("address")
    public Result update(AddressDto addressDto) {
        return addressService.update(addressDto);
    }

    // 删除地址
    @DeleteMapping("address/{id}")
    public Result remove(@PathVariable("id") Integer id) {
        return Result.test(addressService.removeById(id));
    }
}
