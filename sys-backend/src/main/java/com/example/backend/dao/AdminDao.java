package com.example.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminDao extends BaseMapper<Admin> {
}
