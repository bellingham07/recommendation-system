package com.example.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AddressDao extends BaseMapper<Address> {
}
