package com.example.common.service;

import com.example.common.entity.Recommend;

public interface RecommendService {
    int save(String account);

    Recommend getRecommend(String account);

    int saveRecommend(Recommend recommend);
}
