package com.example.common.service;

import com.example.common.entity.UserBehavior;

public interface UserBehaviorService {
    int insertHobby(String hbooy1,String hobby2,String account);

    int save(String account);

    int saveBehavior(UserBehavior userBehavior);

    UserBehavior all(String account);
}
