package com.example.eshop.controller;

import com.example.common.response.Result;
import com.example.common.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.example.common.utils.constant.SystemConstant.BELONG_CELEBRITY;

@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // TODO 获取订单列表（根据状态）
    @GetMapping("list/{status}")
    public Result list(@PathVariable("status") Integer status) {
        return orderService.listOrders(status, BELONG_CELEBRITY);
    }

    // TODO 获取单条
    @GetMapping("{id}")
    public Result getOne(@PathVariable("id") Long id) {
        return Result.test(orderService.getById(id));
    }

    // 取消订单
    @PutMapping("operate/{id}")
    public Result cancel(@PathVariable("id") Long id) {
        return orderService.cancel(id);
    }

    // TODO 发货
    @GetMapping("operate/{id}")
    public Result consignCheck(@PathVariable("id") Long id) {
        return orderService.consignCheck(id);
    }

    // TODO 同意退款
    @GetMapping("operate/refund/{id}")
    public Result refundApprove(@PathVariable("id") Long id) {
        return orderService.refundApprove(id);
    }

    // TODO 拒绝退款
    @PutMapping("operate/refund/{id}")
    public Result refundRefuse(@PathVariable("id") Long id) {
        return orderService.refundRefuse(id);
    }

    // 单条订单删除，购物车移除
    @DeleteMapping("operate/{id}")
    public Result removeSingle(@PathVariable("id") Long id) {
        return Result.test(orderService.removeById(id));
    }

    // TODO 批量订单删除
    @PostMapping("operate/remove")
    public Result removeBatch() {
        return orderService.removeBatch();
    }


}
