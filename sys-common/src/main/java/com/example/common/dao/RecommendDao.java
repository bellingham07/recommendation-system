package com.example.common.dao;

import com.example.common.entity.Recommend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecommendDao {
    int save(@Param("account") String account);//在注册时为当前用户插入一条数据

    Recommend getRecommend(@Param("account") String account);

    int saveRecommend(@Param("recommend") Recommend recommend);
}
