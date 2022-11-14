package com.example.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.dto.PasswordDto;
import com.example.common.entity.Celebrity;
import com.example.common.response.Result;
import com.example.common.dto.LoginDto;
import com.example.common.dto.ValidateDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

public interface CelebrityService extends IService<Celebrity> {
    
    Result login(LoginDto loginDto, HttpSession session);

    Result updatePassword(PasswordDto passwordDto);

    Result validate(ValidateDto validateDto);

    Result updateInfo(Celebrity celebrity);

    Result updateAvatar(MultipartFile avatar);
}
