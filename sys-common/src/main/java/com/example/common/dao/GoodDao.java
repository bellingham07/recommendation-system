package com.example.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.entity.Good;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodDao extends BaseMapper<Good> {

}
