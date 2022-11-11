package com.example.common.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//登录的dao层
@Mapper
public interface UserDao extends BaseMapper<User> {

    User getUserInfo(@Param("account") String account);

    int updateUserInfo(@Param("account") String account,@Param("username") String username,@Param("sex") String sex,@Param("age") int age);

    int addAddress(@Param("account") String addr_uid,@Param("user_name") String user_name,@Param("user_phone") String user_phone,@Param("province") String province,@Param("city") String city,@Param("area") String area,@Param("address_detail") String address_detail);

    List<User> getByCelebrityType(@Param("type") String type, @Param("start") int start);

    int setIsLike(@Param("account") String account);
}
