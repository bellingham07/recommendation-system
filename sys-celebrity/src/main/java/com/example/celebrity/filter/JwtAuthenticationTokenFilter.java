package com.example.celebrity.filter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.example.celebrity.dto.LoginCelebrity;
import com.example.celebrity.utils.JwtUtil;
import com.example.common.response.Result;
import com.example.common.utils.WebUtils;
import com.example.common.utils.cache.RedisClient;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisClient redisClient;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中的token
        String token = httpServletRequest.getHeader("token");
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        // 解析获取网红Id
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            // token超时 token非法
            // 响应告诉前端需要重新登录
            Result result = Result.error("需要登录后操作！");
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }
        String id = claims.getSubject();
        // 从redis中获取用户信息
        Map celebrityCacheMap = redisClient.getMapCache("celebrity:" + id);
        LoginCelebrity celebrity = BeanUtil.fillBeanWithMap(celebrityCacheMap, new LoginCelebrity(), true);
        // 如果获取不到
        if (ObjectUtil.isNull(celebrity)) {
            // 说明登录过期 提示重新登录
            Result result = Result.error("登录信息过期，请重新登录！");
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }
        // 存入securityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(celebrity, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
