package com.example.common.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.dao.AddressDao;
import com.example.common.dto.AddressDto;
import com.example.common.entity.Address;
import com.example.common.response.Result;
import com.example.common.service.AddressService;
import com.example.common.utils.bean.BeanCopyUtils;
import com.example.common.utils.threadHolder.CelebrityHolder;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressDao, Address> implements AddressService {

    @Override
    public Result add(AddressDto addressDto) {
        // 1.判断除id以外的地址信息是否都已填写
        if (!BeanUtil.isNotEmpty(addressDto)) {
            // 1.1.有空则返回错误信息
            return Result.error("请填写所有地址信息！");
        }
        // 2.dto转实体类
        Address address = BeanCopyUtils.copy(addressDto, Address.class);
        // 3.设置发货人
        address.setUser(CelebrityHolder.getUser().getId());
        // 4.保存
        return Result.test(save(address));
    }

    @Override
    public Result listAddress() {
        return null;
    }

    @Override
    public Result update(AddressDto addressDto) {
        return null;
    }
}
