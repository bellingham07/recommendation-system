package com.example.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.dto.PasswordDto;
import com.example.common.entity.Celebrity;
import com.example.common.response.Result;
import com.example.common.dto.LoginDto;
import com.example.common.dto.ValidateDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService extends IService<Celebrity> {
    
    Result<Object> login(LoginDto loginDto, HttpSession session);

    Result<Object> updatePassword(PasswordDto passwordDto);

    Result<Object> validate(ValidateDto validateDto);

    Result updateInfo(Celebrity celebrity);

    Result updateAvatar(MultipartFile avatar);
}
