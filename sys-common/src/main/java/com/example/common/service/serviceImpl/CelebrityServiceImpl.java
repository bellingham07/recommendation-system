package com.example.common.service.serviceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.dao.CelebrityDao;
import com.example.common.dto.LoginDto;
import com.example.common.dto.PasswordDto;
import com.example.common.dto.ValidateDto;
import com.example.common.entity.Celebrity;
import com.example.common.entity.LoginCelebrity;
import com.example.common.response.Result;
import com.example.common.service.CelebrityService;
import com.example.common.utils.JwtUtil;
import com.example.common.utils.bean.BeanCopyUtils;
import com.example.common.utils.cache.RedisCache;
import com.example.common.utils.oss.OssUtil;
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
import static com.example.common.utils.constant.SystemConstant.OPERATE_FAIL;
import static com.example.common.utils.constant.SystemConstant.OPERATE_SUCCESS;

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

    // TODO
    @Override
    public Result updatePassword(PasswordDto passwordDto) {
        if (!StrUtil.equals(passwordDto.getPassword1(), passwordDto.getPassword2())) {
            return Result.error("两次输入的密码不一致！");
        }
        boolean success = update()
                .set("password", StrUtil.trim(passwordDto.getPassword2()))
                .eq("", SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .update();
        if (!success) return Result.error(OPERATE_FAIL);
        return Result.success(OPERATE_SUCCESS);
    }

    // TODO
    @Override
    public Result validate(ValidateDto validateDto) {
        Celebrity celebrity = query()
                .eq("id", validateDto.getId())
                .eq("phonenumber", validateDto.getPhonenumber())
                .one();
        if (celebrity == null) return Result.error("账号或手机号有误！");
        return Result.success(OPERATE_SUCCESS);
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
