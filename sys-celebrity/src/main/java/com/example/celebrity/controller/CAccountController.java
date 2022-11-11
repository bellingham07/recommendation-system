package com.example.celebrity.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.entity.User;
import com.example.common.service.UserService;
import com.example.common.utils.oss.OssUtil;
import com.example.common.response.Result;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
@RequestMapping("/celebrity")
public class CAccountController {

    @Resource
    private UserService userService;;

    @Resource
    private OssUtil ossUtil;

    @PostMapping("/register")
    public Result register(User user) {

        user.setAccount(user.getAccount().trim());
        if (!Objects.equals(user.getAccount(), "")) {
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
            user.setRole(2);
            return Result.test(userService.save(user));
        }
        return Result.error("账号不能为空或全为空格！");
    }

    @PostMapping("/registerPic")
    public Result register(User user, @RequestParam("_pic") MultipartFile pic) {
        try {
            String url = ossUtil.upload(pic, user.getUsername());
            user.setHeadPic(url);
        } catch (Exception e) {
            return Result.error("图片有误！");
        }

        user.setAccount(user.getAccount().trim());
        if (!Objects.equals(user.getAccount(), "")) {
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
            user.setRole(2);
            return Result.test(userService.save(user));
        }
        return Result.error("账号不能为空或全为空格！");
    }

    @PostMapping("/validate")
    public Result registerValidate(@RequestParam("account") String account,
                                                    @RequestParam("tel") String tel) {

        LambdaQueryWrapper<User> qw1 = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> qw2 = new LambdaQueryWrapper<>();

        qw1.eq(User::getAccount, account);
        qw2.eq(User::getTel, tel);

        User acc = userService.getOne(qw1);
        User tel2 = userService.getOne(qw2);

        if (acc == null && tel2 == null) return Result.error("账号及手机号可用");
        else if (acc != null && tel2 == null) return Result.error("账号已存在");
        else if (acc == null) return Result.error("手机号已存在");

        return Result.error("账号已存在，手机号已存在");
    }
}
