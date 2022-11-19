package com.example.common.service.serviceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.dao.CelebrityDao;
import com.example.common.dto.LoginDto;
import com.example.common.dto.PasswordDto;
import com.example.common.dto.RegisterDto;
import com.example.common.entity.Celebrity;
import com.example.common.entity.LoginCelebrity;
import com.example.common.response.Result;
import com.example.common.service.CelebrityService;
import com.example.common.utils.JwtUtil;
import com.example.common.utils.bean.BeanCopyUtils;
import com.example.common.utils.cache.RedisCache;
import com.example.common.utils.oss.OssUtil;
import com.example.common.utils.regex.RegexUtils;
import com.example.common.vo.UserInfoVo;
import com.example.common.vo.UserLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.example.common.utils.constant.RedisConstant.CELEBRITY_LOGIN_KEY;

@Slf4j
@Service
public class CelebrityServiceImpl extends ServiceImpl<CelebrityDao, Celebrity> implements CelebrityService {

    @Autowired
    private OssUtil ossUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Result login(LoginDto loginDto) {
        // 1.进行认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 2.判断是否认证通过
        if (ObjectUtil.isNull(authentication)) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 3.获取id，生成token
        LoginCelebrity loginCelebrity = (LoginCelebrity) authentication.getPrincipal();
        String id = loginCelebrity.getCelebrity().getId().toString();
        String jwt = JwtUtil.createJWT(id);
        // 4.把用户信息存入redis
        redisCache.setMapCache(CELEBRITY_LOGIN_KEY + id, loginCelebrity);
        // 5.把token和CelebrityInfo封装到CelebrityLoginVo中返回
        // 5.1.把Celebrity转换为CelebrityInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copy(loginCelebrity.getCelebrity(), UserInfoVo.class);
        UserLoginVo vo = new UserLoginVo(jwt, userInfoVo);
        return Result.success(vo);
    }

    @Override
    public Result logout() {
        // 1.获取token，解析获取id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginCelebrity loginCelebrity = (LoginCelebrity) authentication.getPrincipal();
        // 2.获取id
        Long id = loginCelebrity.getCelebrity().getId();
        // 3.删除redis中的用户信息
        redisCache.removeCache(CELEBRITY_LOGIN_KEY + id);
        return null;
    }

    @Override
    public Result validatePasswordAndUpdate(PasswordDto passwordDto) {
        String password1 = passwordDto.getPassword1();
        String password2 = passwordDto.getPassword2();
        // 1.后端再次验证密码是否为空
        if (StrUtil.isBlankIfStr(password1)) {
            return Result.error("不能输入空密码！");
        }
        if (StrUtil.isBlankIfStr(password2)) {
            return Result.error("不能输入空密码！");
        }
        // 2.去除前后空格
        passwordDto.setPassword1(StrUtil.trim(password1));
        passwordDto.setPassword2(StrUtil.trim(password2));
        // 3.判断密码是否一致
        if (!StrUtil.equals(password1, password2)) {
            return Result.error("两次输入的密码不一致！");
        }
        // 4.从SecurityContextHolder中获取id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginCelebrity loginCelebrity = (LoginCelebrity) authentication.getPrincipal();
        Long id = loginCelebrity.getCelebrity().getId();
        // 5.通过空值判断网红是通过那种方式来修改密码的（如果原密码为空，即忘记原密码）
        // 5.1.通过手机号验证
        if (StrUtil.isBlank(passwordDto.getPasswordRaw())) {
            return updateByPhonenumber(passwordDto, id);
        }
        // 5.2.通过原密码验证
        return updateByRawPassword(passwordDto, id);
    }

    // 先通过原密码验证，再更新
    public Result updateByRawPassword(PasswordDto passwordDto, Long id) {
        // 1.查询数据库，判断id，原密码是否匹配
        boolean success = update()
                .set("password", passwordDto.getPassword2())
                .eq("id", id)
                .eq("password", passwordDto.getPasswordRaw())
                .update();
        if (!success) return Result.error("原密码错误！");
        return Result.success();
    }

    // 先通过手机号验证，再更新
    public Result updateByPhonenumber(PasswordDto passwordDto, Long id) {
        // 1.查询数据库，判断信息是否匹配
        boolean success = update()
                .set("password", passwordDto.getPassword2())
                .eq("id", id)
                .eq("phonenumber", passwordDto.getPhonenumber())
                .update();
        if (!success) return Result.error("手机号有误！");
        return null;
    }

    @Override
    public Result register(RegisterDto registerDto) {
        Celebrity celebrity = BeanCopyUtils.copy(registerDto, Celebrity.class);
        if (RegexUtils.isPhoneInvalid(celebrity.getPhonenumber())) {
            return Result.error("请输入正确手机号！");
        }
        return Result.test(save(celebrity));
    }

    // TODO
    @Override
    public Result updateInfo(Celebrity celebrity) {
        return null;
    }

    // TODO
    @Override
    public Result updateAvatar(MultipartFile avatar) {
        return null;
    }


}
