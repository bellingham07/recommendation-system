package com.example.common.service.serviceImpl;

import com.example.common.dao.UserBehaviorDao;
import com.example.common.entity.UserBehavior;
import com.example.common.service.UserBehaviorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBehaviorImpl implements UserBehaviorService {
    @Autowired
    private UserBehaviorDao userBehaviorDao;

    @Override
    public int insertHobby(String hobby1, String hobby2,String account) {
        return userBehaviorDao.insertHobby(hobby1,hobby2,account);
    }

    @Override
    public int save(String account) {
        return userBehaviorDao.save(account);
    }

    @Override
    public int saveBehavior(UserBehavior userBehavior) {
        return userBehaviorDao.saveBehavior(userBehavior);
    }

    @Override
    public UserBehavior all(String account) {
        return userBehaviorDao.all(account);
    }
}
