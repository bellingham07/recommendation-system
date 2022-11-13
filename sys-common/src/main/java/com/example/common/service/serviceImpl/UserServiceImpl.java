package com.example.common.service.serviceImpl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.dao.CelebrityDao;
import com.example.common.dto.LoginDto;
import com.example.common.dto.PasswordDto;
import com.example.common.dto.ValidateDto;
import com.example.common.entity.Celebrity;
import com.example.common.response.Result;
import com.example.common.service.UserService;
import com.example.common.utils.oss.OssUtil;
import com.example.common.utils.threadHolder.CelebrityHolder;
import com.example.common.utils.cache.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static com.example.common.utils.constant.SystemConstant.OPERATE_FAIL;
import static com.example.common.utils.constant.SystemConstant.OPERATE_SUCCESS;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<CelebrityDao, Celebrity> implements UserService {

    @Autowired
    private OssUtil ossUtil;

    @Autowired
    private RedisClient redisClient;

    @Override
    public Result login(LoginDto loginDto, HttpSession session) {
        // 密码加密
//        String password = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes(StandardCharsets.UTF_8));
//        // 查询用户
//        Celebrity celebrity = query()
//                .eq("account", loginDto.getAccount())
//                .eq("password", password)
//                .one();
//        if (celebrity == null) {
//            return Result.error("账号或密码错误！");
//        }
//        // 成功
//        UserDto userDto = BeanUtil.copyProperties(celebrity, UserDto.class);
//        // 将用户保存到当前线程的UserHolder中
//        UserHolder.saveUser(userDto);
//        log.info("loginService里的" + UserHolder.getUser().getAccount());
//        // 将用户保存到redis，并且设置过期时间
//        redisClient.setMapCache(USER_LOGIN_KEY, userDto, USER_LOGIN_TTL, TimeUnit.HOURS);
//        session.setAttribute("user", user);
        return null;
    }

    @Override
    public Result updatePassword(PasswordDto passwordDto) {
        if (!StrUtil.equals(passwordDto.getPassword1(), passwordDto.getPassword2())) {
            return Result.error("两次输入的密码不一致！");
        }
        boolean success = update()
                .set("password", StrUtil.trim(passwordDto.getPassword2()))
                .eq("account", CelebrityHolder.getUser().getAccount())
                .update();
        if (!success) return Result.error(OPERATE_FAIL);
        return Result.success(OPERATE_SUCCESS);
    }

    @Override
    public Result validate(ValidateDto validateDto) {
        Celebrity celebrity = query()
                .eq("account", validateDto.getAccount())
                .eq("tel", validateDto.getTel())
                .one();
        if (celebrity == null) return Result.error("账号或手机号有误！");
        return Result.success(OPERATE_SUCCESS);
    }
}
