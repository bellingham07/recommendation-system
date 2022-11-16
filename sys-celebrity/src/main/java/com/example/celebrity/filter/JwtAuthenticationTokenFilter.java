package com.example.celebrity.filter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.example.common.entity.LoginCelebrity;
import com.example.common.response.Result;
import com.example.common.utils.JwtUtil;
import com.example.common.utils.WebUtils;
import com.example.common.utils.cache.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.example.common.utils.constant.RedisConstant.CELEBRITY_LOGIN_KEY;
import static com.example.common.utils.constant.RedisConstant.CELEBRITY_LOGIN_TTL;
import static com.example.common.utils.constant.SecurityConstant.LOGIN_HAS_EXPIRED;
import static com.example.common.utils.constant.SecurityConstant.LOGIN_NEEDED;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1.获取请求头中的token
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            // token为空，放行
            filterChain.doFilter(request, response);
            return;
        }
        // 2.解析获取网红Id
        Claims claims;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            // 2.1.token超时 token非法
            // 2.2.响应告诉前端需要重新登录
            Result result = Result.error(LOGIN_NEEDED);
            WebUtils.renderString(response, JSONUtil.toJsonStr(result));
            return;
        }
        String id = claims.getSubject();
        // 3.从redis中获取用户信息
        Map<Object, Object> celebrityCacheMap = redisCache.getMapCache(CELEBRITY_LOGIN_KEY + id);
        LoginCelebrity celebrity = BeanUtil.fillBeanWithMap(celebrityCacheMap, new LoginCelebrity(), true);
        // 3.1.如果获取不到
        if (ObjectUtil.isNull(celebrity)) {
            // 3.2.说明登录过期 提示重新登录
            Result result = Result.error(LOGIN_HAS_EXPIRED);
            WebUtils.renderString(response, JSONUtil.toJsonStr(result));
            return;
        }
        // 4.存入securityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(celebrity, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 5.刷新token有效期
        redisCache.expire(CELEBRITY_LOGIN_KEY + token, CELEBRITY_LOGIN_TTL, TimeUnit.HOURS);
        filterChain.doFilter(request, response);
    }
}
