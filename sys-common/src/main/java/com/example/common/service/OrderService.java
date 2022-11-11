package com.example.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.dto.OrderDto;
import com.example.common.entity.Order;
import com.example.common.response.Result;
import com.example.common.vo.OrderVo;

import java.util.List;

public interface OrderService extends IService<Order> {

    //查询商品信息
    List<OrderVo> selectAllOrders(String account, Integer start);

    List<OrderVo> selectOrders(String account, Integer start, Integer status);

    Result<Object> cancelOrder(Integer orderId);

    Result<Object> updateStatus(Integer orderId, Integer status);

    Result buy(OrderDto orderDto);

    Result save2cart(Integer goodId);

    Result buyFromCart(Integer orderId);

    Result consign(Integer id);
}
