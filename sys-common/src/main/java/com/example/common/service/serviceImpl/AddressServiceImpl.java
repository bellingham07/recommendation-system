package com.example.common.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.dao.AddressDao;
import com.example.common.dto.AddressDto;
import com.example.common.entity.Address;
import com.example.common.response.Result;
import com.example.common.service.AddressService;
import com.example.common.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressDao, Address> implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Override
    public List<Address> getUserAddress(String account, Integer current) {
        return addressDao.findAddress(account, current);
    }

    @Override
    public Result updateAddress(Address address) {
        // 1.从threadLocal中获得用户
        address.setAccount(UserHolder.getUser().getAccount());
        // 2.更新地址并判断是否成功
        return Result.test(updateById(address));
    }

    @Override
    public Result addAddress(AddressDto addressDto) {
        // 1.判断除id以外的地址信息是否都已填写
        if (!BeanUtil.isNotEmpty(addressDto)) {
            // 1.1.有空则返回错误信息
            return Result.error("请填写所有地址信息！");
        }
        // 2.dto转实体类
        Address address = BeanUtil.copyProperties(addressDto, Address.class);
        // 3.设置发货人
        address.setAccount(UserHolder.getUser().getAccount());
        // 4.保存
        return Result.test(save(address));
    }

    @Override
    public Result listAddress() {
        String account = UserHolder.getUser().getAccount();
        return Result.test(query().eq("account", account).list());
    }
}
