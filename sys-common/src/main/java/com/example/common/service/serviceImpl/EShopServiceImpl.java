package com.example.common.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.dao.EShopDao;
import com.example.common.dto.LoginDto;
import com.example.common.entity.EShop;
import com.example.common.response.Result;
import com.example.common.service.EShopService;
import com.example.common.utils.cache.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EShopServiceImpl extends ServiceImpl<EShopDao, EShop> implements EShopService {

    @Autowired
    private RedisClient redisClient;

    @Override
    public Result login(LoginDto loginDto) {
        EShop eShop = query()
                .eq("username", loginDto.getUsername())
                .eq("password", loginDto.getUsername())
                .one();
        if (eShop == null) {
            return Result.error("账户或密码错误！");
        }
        return Result.success();
    }
}
