package com.example.common.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.dao.GoodDao;
import com.example.common.dao.OrderDao;
import com.example.common.dto.OrderDto;
import com.example.common.entity.Order;
import com.example.common.response.Result;
import com.example.common.service.OrderService;
import com.example.common.utils.threadHolder.CelebrityHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static com.example.common.utils.constant.SystemConstant.*;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {

    @Autowired
    private GoodDao goodDao;

    public Result updateStatus(Long orderId, Integer status) {
        return Result.test(update()
                .setSql("status = " + status)
                .eq("order_id", orderId)
                .update());
    }

    @Override
    public Result buy(Long id) {
        Order order = new Order();
        order.setId(id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        order.setCelebrity(CelebrityHolder.getUser().getId());
        order.setEshop(goodDao.selectById(order.getGood()).getEshop());
        order.setPayTime(sdf.format(new Date()));
        order.setStatus(ORDER_STATUS_PAYED);
        return Result.test(saveOrUpdate(order));
    }

    @Override
    public Result save2cart(Long goodId) {
        Order order = new Order();
        order.setGood(goodId);
        order.setCelebrity(CelebrityHolder.getUser().getId());
        order.setStatus(ORDER_STATUS_CART);
        order.setEshop(goodDao.selectById(goodId).getEshop());
        return Result.test(save(order));
    }

    @Override
    public Result listOrders(Integer status) {
        return null;
    }

    @Override
    public Result makeOrder(OrderDto orderDto) {
        return null;
    }

    @Override
    public Result cancelByC(Long id) {
        return null;
    }

    @Override
    public Result cancelByE(Long id) {
        return null;
    }

    @Override
    public Result consignCheck(Long id) {
        return null;
    }

    @Override
    public Result takeCheck(Long id) {
        return null;
    }

    @Override
    public Result removeBatch() {
        return null;
    }

    @Override
    public Result refund(Long id) {
        return null;
    }

    @Override
    public Result refundApprove(Long id) {
        return null;
    }

    @Override
    public Result refundRefuse(Long id) {
        return null;
    }
}
