package com.example.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.entity.Celebrity;
import org.apache.ibatis.annotations.Mapper;

//登录的dao层
@Mapper
public interface CelebrityDao extends BaseMapper<Celebrity> {

}
