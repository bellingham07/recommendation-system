package com.example.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.dto.AddressDto;
import com.example.common.entity.Address;
import com.example.common.response.Result;

public interface AddressService extends IService<Address> {

    Result add(AddressDto addressDto);

    Result listAddress();

    Result update(AddressDto addressDto);
}
