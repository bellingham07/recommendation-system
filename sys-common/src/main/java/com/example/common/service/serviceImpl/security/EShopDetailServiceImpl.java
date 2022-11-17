package com.example.common.service.serviceImpl.security;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.example.common.dao.EShopDao;
import com.example.common.entity.EShop;
import com.example.common.entity.LoginEShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static com.example.common.utils.constant.SecurityConstant.LOGIN_NO_EXIST;

@Component("eshopUserDetailsService")
public class EShopDetailServiceImpl implements UserDetailsService {

    @Autowired
    private EShopDao eShopDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EShop eShop = eShopDao.selectOne(new QueryChainWrapper<>(eShopDao)
                .eq("username", username)
                .select());
        if (ObjectUtil.isNull(eShop)) {
            throw new RuntimeException(LOGIN_NO_EXIST);
        }
        return new LoginEShop(eShop);
    }
}
