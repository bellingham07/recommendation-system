package com.example.celebrity.controller;

import com.example.common.dto.OrderDto;
import com.example.common.response.Result;
import com.example.common.service.OrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.example.common.utils.constant.SystemConstant.BELONG_CELEBRITY;

@Controller
@RequestMapping("order")
@Api(tags = "订单相关controller")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // TODO 获取订单列表（根据状态）
    @GetMapping("{status}")
    public Result list(@PathVariable("status") Integer status) {
        return orderService.listOrders(status, BELONG_CELEBRITY);
    }

    // TODO 获取单条
    @GetMapping("{id}")
    public Result getOne(@PathVariable("id") Long id) {
        return orderService.get1(id);
    }

    // 加入购物车
    @PostMapping("cart")
    public Result save2cart(OrderDto orderDto) {
        return orderService.save2cart(orderDto);
    }

    // 购买
    @PostMapping("buy")
    public Result buy(OrderDto orderDto) {
        return orderService.buy(orderDto);
    }

    // TODO 收货
    @GetMapping("operate/{id}")
    public Result receiveCheck(@PathVariable("id") Long id) {
        return orderService.receiveCheck(id);
    }

    // TODO 发起退款
    @PutMapping("operate/{id}")
    public Result refund(@PathVariable("id") Long id) {
        return orderService.refund(id);
    }

    // 单条订单删除，购物车移除
    @DeleteMapping("operate/{id}")
    public Result removeSingle(@PathVariable("id") Long id) {
        return Result.test(orderService.removeById(id));
    }

    // TODO 批量订单删除，购物车移除
    @PostMapping("operate")
    public Result removeBatch() {
        return orderService.removeBatch();
    }
}
