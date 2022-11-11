package com.example.common.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.dao.UserDao;
import com.example.common.dto.LoginDto;
import com.example.common.dto.PasswordDto;
import com.example.common.dto.UserDto;
import com.example.common.dto.ValidateDto;
import com.example.common.entity.User;
import com.example.common.response.Result;
import com.example.common.service.UserService;
import com.example.common.utils.oss.OssUtil;
import com.example.common.utils.UserHolder;
import com.example.common.utils.cache.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.common.utils.constant.RedisConstant.USER_LOGIN_KEY;
import static com.example.common.utils.constant.RedisConstant.USER_LOGIN_TTL;
import static com.example.common.utils.constant.SystemConstant.FAIL_OPERATE;
import static com.example.common.utils.constant.SystemConstant.SUCCESS_OPERATE;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Resource
    private UserDao userDao;

    @Autowired
    private OssUtil ossUtil;

    @Autowired
    private RedisClient redisClient;

    @Override
    public List<User> getByCelebrityType(String type, int start) {
        return userDao.getByCelebrityType(type, start);
    }

    @Override
    public Result updatePic(HttpSession session, MultipartFile pic) {
        User user = (User) session.getAttribute("user");
        try {
            String url = ossUtil.upload(pic, user.getUsername());
            user.setHeadPic(url);
        } catch (Exception e) {
            return Result.error("图片有误！");
        }

        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getAccount, user.getAccount());
        int t = userDao.update(user, qw);
        if (t == 1) {
            session.removeAttribute("user");
            session.setAttribute("user", getById(user.getAccount()));
            return Result.success(SUCCESS_OPERATE);
        }
        return Result.error("保持成功！联系臭卷宝。");
    }

    @Override
    public Result updateInfo(User user, HttpSession session) {
        user.setAccount(((User) session.getAttribute("user")).getAccount());
        boolean t = updateById(user);
        if (t) {
            session.setAttribute("user", getById(user.getAccount()));
            return Result.success(SUCCESS_OPERATE);
        }
        return Result.error(FAIL_OPERATE);
    }

    @Override
    public int setIsLike(String account) {
        return userDao.setIsLike(account);
    }

    @Override
    public Result login(LoginDto loginDto, HttpSession session) {
        // 密码加密
        String password = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes(StandardCharsets.UTF_8));
        // 查询用户
        User user = query()
                .eq("account", loginDto.getAccount())
                .eq("password", password)
                .one();
        if (user == null) {
            return Result.error("账号或密码错误！");
        }
        // 成功
        UserDto userDto = BeanUtil.copyProperties(user, UserDto.class);
        // 将用户保存到当前线程的UserHolder中
        UserHolder.saveUser(userDto);
        log.info("loginService里的" + UserHolder.getUser().getAccount());
        // 将用户保存到redis，并且设置过期时间
        redisClient.setMapCache(USER_LOGIN_KEY, userDto, USER_LOGIN_TTL, TimeUnit.HOURS);
        session.setAttribute("user", user);
        if (user.getRole() == 0) return Result.success("电商登录");
        return Result.success("网红登录");
    }

    @Override
    public Result updatePassword(PasswordDto passwordDto) {
        if (!StrUtil.equals(passwordDto.getPassword1(), passwordDto.getPassword2())) {
            return Result.error("两次输入的密码不一致！");
        }
        boolean success = update()
                .set("password", StrUtil.trim(passwordDto.getPassword2()))
                .eq("account", UserHolder.getUser().getAccount())
                .update();
        if (!success) return Result.error(FAIL_OPERATE);
        return Result.success(SUCCESS_OPERATE);
    }

    @Override
    public Result validate(ValidateDto validateDto) {
        User user = query()
                .eq("account", validateDto.getAccount())
                .eq("tel", validateDto.getTel())
                .one();
        if (user == null) return Result.error("账号或手机号有误！");
        return Result.success(SUCCESS_OPERATE);
    }
}
