package com.example.common.service.serviceImpl.security;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.dao.CelebrityDao;
import com.example.common.entity.Celebrity;
import com.example.common.entity.LoginCelebrity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.common.utils.constant.SecurityConstant.LOGIN_NO_EXIST;

@Service
public class CelebrityDetailServiceImpl implements UserDetailsService {

    @Autowired
    private CelebrityDao celebrityDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.根据账号密码查询用户是否存在
        LambdaQueryWrapper<Celebrity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Celebrity::getUsername, username);
        Celebrity celebrity = celebrityDao.selectOne(queryWrapper);
        if (ObjectUtil.isNull(celebrity)) {
            throw new RuntimeException(LOGIN_NO_EXIST);
        }
        // 返回用户信息
        return new LoginCelebrity(celebrity);
    }
}
