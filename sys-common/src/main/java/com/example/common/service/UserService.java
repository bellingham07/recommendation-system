package com.example.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.dto.PasswordDto;
import com.example.common.entity.User;
import com.example.common.response.Result;
import com.example.common.dto.LoginDto;
import com.example.common.dto.PasswordDto;
import com.example.common.dto.ValidateDto;
import com.example.common.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService extends IService<User> {

//    根据网红主要代理类型获得几个商家（分页，暂定）
    List<User> getByCelebrityType(String type, int start);

    Result<Object> updatePic(HttpSession session, MultipartFile pic);

    Result<Object> updateInfo(User user, HttpSession session);

    int setIsLike(String account);

    Result<Object> login(LoginDto loginDto, HttpSession session);

    Result<Object> updatePassword(PasswordDto passwordDto);

    Result<Object> validate(ValidateDto validateDto);
}
