package com.example.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.dto.LoginDto;
import com.example.common.dto.PasswordDto;
import com.example.common.entity.Celebrity;
import com.example.common.response.Result;
import org.springframework.web.multipart.MultipartFile;

public interface CelebrityService extends IService<Celebrity> {
    
    Result login(LoginDto loginDto);

    Result updateInfo(Celebrity celebrity);

    Result updateAvatar(MultipartFile avatar);

    Result logout();

    Result validatePasswordAndUpdate(PasswordDto passwordDto);

//    Result register(RegisterDto registerDto);
}
