package com.example.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.dto.LoginDto;
import com.example.common.entity.EShop;
import com.example.common.response.Result;

public interface EShopService extends IService<EShop> {

    Result login(LoginDto loginDto);

    Result logout();
}
