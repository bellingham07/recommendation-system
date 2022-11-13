package com.example.celebrity.controller;

import com.example.common.dto.OrderDto;
import com.example.common.response.Result;
import com.example.common.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // TODO 获取订单列表（根据状态）
    @GetMapping("list/{status}")
    public Result list(@PathVariable("status") Integer status) {
        return orderService.listOrders(status);
    }

    // TODO 获取单条
    @GetMapping("{id}")
    public Result getOne(@PathVariable("id") Long id) {
        return Result.test(orderService.getById(id));
    }

    @PostMapping
    public Result makeOrder(OrderDto orderDto) {
        return orderService.makeOrder(orderDto);
    }

    // 加入购物车
    @GetMapping("cart/{id}")
    public Result save2cart(@PathVariable("id") Long goodId) {
        return orderService.save2cart(goodId);
    }

    // 购买
    @GetMapping("buy/{id}")
    public Result buy(@PathVariable("id") Long id) {
        return orderService.buy(id);
    }

    // 取消订单
    @PutMapping("operate/{id}")
    public Result cancel(@PathVariable("id") Long id) {
        return orderService.cancelByC(id);
    }

    // TODO 收货
    @GetMapping("operate/{id}")
    public Result takeCheck(@PathVariable("id") Long id) {
        return orderService.takeCheck(id);
    }

    // TODO 发起退款
    @PutMapping("operate/refund/{id}")
    public Result refund(@PathVariable("id") Long id) {
        return orderService.refund(id);
    }

    // 单条订单删除，购物车移除
    @DeleteMapping("operate/{id}")
    public Result removeSingle(@PathVariable("id") Long id) {
        return Result.test(orderService.removeById(id));
    }

    // TODO 批量订单删除，购物车移除
    @PostMapping("operate/remove")
    public Result removeBatch() {
        return orderService.removeBatch();
    }
}
