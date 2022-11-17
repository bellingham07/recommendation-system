package com.example.common.service.serviceImpl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.dao.EShopDao;
import com.example.common.dto.LoginDto;
import com.example.common.entity.EShop;
import com.example.common.entity.LoginEShop;
import com.example.common.response.Result;
import com.example.common.service.EShopService;
import com.example.common.utils.JwtUtil;
import com.example.common.utils.bean.BeanCopyUtils;
import com.example.common.utils.cache.RedisCache;
import com.example.common.vo.UserInfoVo;
import com.example.common.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.example.common.utils.constant.RedisConstant.ESHOP_LOGIN_KEY;

@Service
public class EShopServiceImpl extends ServiceImpl<EShopDao, EShop> implements EShopService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Result login(LoginDto loginDto) {
        // 1.进行认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 2.判断是否认证通过
        if (ObjectUtil.isNull(authentication)) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 3.获取id，生成token
        LoginEShop loginEShop = (LoginEShop) authentication.getPrincipal();
        String id = loginEShop.getEShop().getId().toString();
        String jwt = JwtUtil.createJWT(id);
        // 4.把用户信息存入redis
        redisCache.setMapCache(ESHOP_LOGIN_KEY + id, loginEShop);
        // 5.把token和CelebrityInfo封装到CelebrityLoginVo中返回
        // 5.1.把Celebrity转换为CelebrityInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copy(loginEShop.getEShop(), UserInfoVo.class);
        UserLoginVo vo = new UserLoginVo(jwt, userInfoVo);
        return Result.success(vo);
    }

    @Override
    public Result logout() {
        // 1.获取token，解析获取id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginEShop loginEShop = (LoginEShop) authentication.getPrincipal();
        // 2.获取id
        Long id = loginEShop.getEShop().getId();
        // 3.删除redis中的用户信息
        redisCache.removeCache(ESHOP_LOGIN_KEY + id);
        return null;
    }
}
