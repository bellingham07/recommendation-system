package com.example.common.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.dao.AddressDao;
import com.example.common.dto.AddressDto;
import com.example.common.entity.Address;
import com.example.common.entity.Celebrity;
import com.example.common.entity.LoginCelebrity;
import com.example.common.response.Result;
import com.example.common.service.AddressService;
import com.example.common.utils.bean.BeanCopyUtils;
import com.example.common.utils.cache.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.example.common.utils.constant.RedisConstant.ADDRESS_KEY;
import static com.example.common.utils.constant.RedisConstant.ADDRESS_TLL;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressDao, Address> implements AddressService {

    @Autowired
    private RedisCache redisCache;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        address.setUser(((LoginCelebrity) authentication.getPrincipal()).getCelebrity().getId());
        // 4.保存
        return Result.test(save(address));
    }

    @Override
    public Result listAddress() {
        // 1.从SecurityContextHolder中获取id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginCelebrity loginCelebrity = (LoginCelebrity) authentication.getPrincipal();
        Long id = loginCelebrity.getCelebrity().getId();
        // 2.查询
        List<Address> addressList = query()
                .eq("id", id)
                .list();
        return Result.success(addressList);
    }

    @Override
    public Result update(AddressDto addressDto) {
        // 1.转换bean
        Address address = BeanCopyUtils.copy(addressDto, Address.class);
        // 2.拼接key
        String key = ADDRESS_KEY + address.getId();
        // 3.此处保证唯一性先不更新，而先删除，需要再加载进redis
        String cache = redisCache.getCache(key);
        if (cache != null) {
            redisCache.removeCache(key);
        }
        return Result.test(updateById(address));
    }

    // 查询单条地址（解决缓存穿透问题）
    @Override
    public Result get1(Long id) {
        // 1.拼接key
        String key = ADDRESS_KEY + id;
        // 2.从redis中拿
        Map<Object, Object> addressCacheMap = redisCache.getMapCache(key);
        // 3.判断是否在redis中
        if (addressCacheMap == null) {
            // 3.1.redis中没有，先查询数据库
            Address address = getById(id);
            if (address == null) {
                // 3.2.数据库也没有，设空值
                redisCache.setMapCache(key, new Address(""));
                return Result.error("没有该地址！");
            }
            return Result.success(address);
        }
        // 4.在redis中，判断是否为空值
        if (StrUtil.isBlank((String) addressCacheMap.get("redisFlag"))) {
            return Result.error("没有该地址！");
        }
        // 5.非空则数据存在
        Address address = BeanUtil.fillBeanWithMap(addressCacheMap, new Address(), true);
        return Result.success(address);
    }
}
