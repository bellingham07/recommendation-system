package com.example.common.service.serviceImpl;


import com.example.common.dao.RecommendDao;
import com.example.common.entity.Recommend;
import com.example.common.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    private RecommendDao recommendDao;


    @Override
    public int save(String account) {
        return recommendDao.save(account);
    }

    @Override
    public Recommend getRecommend(String account) {
        return recommendDao.getRecommend(account);
    }

    @Override
    public int saveRecommend(Recommend recommend) {
        return recommendDao.saveRecommend(recommend);
    }
}
