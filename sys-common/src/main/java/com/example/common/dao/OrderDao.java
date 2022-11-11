package com.example.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.entity.Order;
import com.example.common.vo.OrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDao extends BaseMapper<Order> {
    List<OrderVo> getOrders(@Param("account") String account, @Param("start") Integer start,@Param("status") Integer status);

    List<OrderVo> getAllOrders(@Param("account") String account, @Param("start") Integer start);
}
