package com.example.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.dto.AddressDto;
import com.example.common.entity.Address;
import com.example.common.response.Result;

import java.util.List;

public interface AddressService extends IService<Address> {

//    获取当前网红用户的十个收货地址，分页
    List<Address> getUserAddress(String account, Integer current);

    Result<Object> updateAddress(Address address);

    Result<Object> addAddress(AddressDto addressDto);

    Result listAddress();
}
