package com.example.common.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.dao.GoodDao;
import com.example.common.dao.OrderDao;
import com.example.common.dto.OrderDto;
import com.example.common.entity.Order;
import com.example.common.response.Result;
import com.example.common.service.OrderService;
import com.example.common.utils.UserHolder;
import com.example.common.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.example.common.utils.constant.SystemConstant.*;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private GoodDao goodDao;

    @Override
    public List<OrderVo> selectAllOrders(String account, Integer start) {
        return orderDao.getAllOrders(account, start);
    }

    @Override
    public List<OrderVo> selectOrders(String account, Integer start, Integer status) {
        return orderDao.getOrders(account, start, status);
    }

    @Override
    public Result updateStatus(Integer orderId, Integer status) {
        return Result.test(update()
                .setSql("status = " + status)
                .eq("order_id", orderId)
                .update());
    }

    @Override
    public Result buy(OrderDto orderDto) {
        Order order = BeanUtil.copyProperties(orderDto, Order.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        order.setCelebrity(UserHolder.getUser().getAccount());
        order.setEshop(goodDao.selectById(order.getGood()).getEshopAccount());
        order.setPayTime(sdf.format(new Date()));
        order.setStatus(ORDER_STATUS_PAYED);
        return Result.test(save(order));
    }

    @Override
    public Result save2cart(Integer goodId) {
        Order order = new Order();
        order.setGood(goodId);
        order.setCelebrity(UserHolder.getUser().getAccount());
        order.setStatus(ORDER_STATUS_CART);
        order.setEshop(goodDao.selectById(goodId).getEshopAccount());
        return Result.test(save(order));
    }

    @Override
    public Result buyFromCart(Integer id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Order order = getById(id);
        order.setPayTime(sdf.format(new Date()));
        order.setStatus(ORDER_STATUS_PAYED);
        return Result.test(updateById(order));
    }

    @Override
    public Result consign(Integer id) {
        if (!Objects.equals(getById(id).getStatus(), ORDER_STATUS_PAYED)) {
            return Result.error("该网红未支付");
        }
        return Result.test(updateStatus(id, ORDER_STATUS_SENT));
    }

    @Override
    public Result cancelOrder(Integer orderId) {
        Order order = getById(orderId);
        Integer status = order.getStatus();
        if (Objects.equals(status, ORDER_STATUS_SENT)) {
            return updateStatus(orderId, ORDER_STATUS_CANCELLED);
        }
        return Result.error("该订单目前不能取消！");
    }
}
