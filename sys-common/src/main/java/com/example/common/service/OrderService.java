package com.example.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.dto.OrderDto;
import com.example.common.entity.Order;
import com.example.common.response.Result;

public interface OrderService extends IService<Order> {

    Result buy(OrderDto id);

    Result save2cart(OrderDto goodId);

    Result listOrders(Integer status, Integer belong);

    Result cancel(Long id);

    Result consignCheck(Long id);

    Result receiveCheck(Long id);

    Result removeBatch();

    Result refund(Long id);

    Result refundApprove(Long id);

    Result refundRefuse(Long id);

    Result get1(Long id);
}
