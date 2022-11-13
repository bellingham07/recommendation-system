package com.example.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.dto.OrderDto;
import com.example.common.entity.Order;
import com.example.common.response.Result;

public interface OrderService extends IService<Order> {

    Result buy(Long id);

    Result save2cart(Long goodId);

    Result listOrders(Integer status);

    Result makeOrder(OrderDto orderDto);

    Result cancelByC(Long id);

    Result cancelByE(Long id);

    Result consignCheck(Long id);

    Result takeCheck(Long id);

    Result removeBatch();

    Result refund(Long id);

    Result refundApprove(Long id);

    Result refundRefuse(Long id);
}
