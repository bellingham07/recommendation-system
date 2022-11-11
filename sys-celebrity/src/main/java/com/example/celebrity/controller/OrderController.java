package com.example.celebrity.controller;

import com.example.common.dto.OrderDto;
import com.example.common.response.Result;
import com.example.common.service.AddressService;
import com.example.common.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.example.common.utils.constant.SystemConstant.*;


@Controller
@RequestMapping("celebrity/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressService addressService;


    // 加入购物车
    @GetMapping("cart/{id}")
    public Result cart(@PathVariable("id") Integer goodId) {
        return orderService.save2cart(goodId);
    }

    // 从购物车购买
    @PutMapping("cart/{id}")
    public Result buyFromCart(@PathVariable("id") Integer orderId) {
        return orderService.buyFromCart(orderId);
    }

    // 直接购买
    @PostMapping("buy")
    public Result buy(OrderDto orderDto) {
        return orderService.buy(orderDto);
    }

    // 收货
    @GetMapping("{id}")
    public Result receiveCheck(@PathVariable("id") Integer id) {
        return orderService.updateStatus(id, ORDER_STATUS_COMPLETE);
    }

    // 取消订单
    @PutMapping("{id}")
    public Result cancel(@PathVariable("id") Integer id) {
        return orderService.cancelOrder(id);
    }

    // TODO 单条订单删除，购物车移除（网红逻辑删除）
    @DeleteMapping("{id}")
    public Result removeSingle(@PathVariable("id") Integer id) {
        return null;
    }

    // TODO 批量订单删除，购物车移除（网红逻辑删除）
    @PostMapping("remove")
    public Result removeBatch() {
        return null;
    }

    // TODO 加载收货地址
    @GetMapping("address")
    public Result listAddress() {
        return addressService.listAddress();
    }
}
