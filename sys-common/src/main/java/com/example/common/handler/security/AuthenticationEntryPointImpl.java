package com.example.common.handler.security;

import com.alibaba.fastjson.JSON;
import com.example.common.response.Result;
import com.example.common.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.common.utils.constant.SecurityConstant.AUTHENTICATION_OR_AUTHORIZATION_FAIL;
import static com.example.common.utils.constant.SecurityConstant.LOGIN_INFO_ERROR;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();
        Result result = null;
        if (e instanceof BadCredentialsException) {
            result = Result.error(LOGIN_INFO_ERROR);
        } else if (e instanceof InsufficientAuthenticationException) {
            result = Result.error(AUTHENTICATION_OR_AUTHORIZATION_FAIL);
        }
        //响应给前端
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
