package com.example.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.entity.GoodCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodTypeDao extends BaseMapper<GoodCategory> {
}
