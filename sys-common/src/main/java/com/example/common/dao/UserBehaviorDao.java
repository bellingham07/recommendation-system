package com.example.common.dao;

import com.example.common.entity.UserBehavior;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserBehaviorDao {
    int insertHobby(@Param("hobby1") String hobby1,@Param("hobby2") String hobby2,@Param("account") String account);

    int save(String account);

    int saveBehavior(@Param("userBehavior") UserBehavior userBehavior);

    UserBehavior all(@Param("account") String account);
}
