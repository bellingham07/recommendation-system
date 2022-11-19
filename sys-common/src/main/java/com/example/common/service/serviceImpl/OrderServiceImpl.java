package com.example.common.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.dao.CelebrityDao;
import com.example.common.dao.EShopDao;
import com.example.common.dao.GoodDao;
import com.example.common.dao.OrderDao;
import com.example.common.dto.OrderDto;
import com.example.common.entity.*;
import com.example.common.response.Result;
import com.example.common.service.OrderService;
import com.example.common.utils.bean.BeanCopyUtils;
import com.example.common.utils.cache.RedisCache;
import com.example.common.vo.CartVo;
import com.example.common.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.common.utils.constant.RedisConstant.*;
import static com.example.common.utils.constant.SystemConstant.*;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {

    @Autowired
    private CelebrityDao celebrityDao;

    @Autowired
    private EShopDao eShopDao;

    @Autowired
    private GoodDao goodDao;

    @Autowired
    private RedisCache redisCache;

    public Result updateStatus(Long id, Integer status) {
        return Result.test(update()
                .setSql("status = " + status)
                .eq("id", id)
                .update());
    }

    @Override
    public Result buy(OrderDto orderDto) {
        // 1.从securityContextHolder中获取id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long celebrityId = ((LoginCelebrity) authentication.getPrincipal()).getCelebrity().getId();
        Order order;
        // 2.判断是直接购买还是从购物车购买
        // 2.1.id非空，则是从购物车购买
        Long id = orderDto.getId();
        if (id != null) {
            // 2.2.dto转entity
            order = BeanCopyUtils.copy(orderDto, Order.class);
            // 2.3.更新状态值
            order.setStatus(ORDER_STATUS_PAYED);
            return Result.test(updateById(order));
        }
        // 3.直接够买
        order = BeanCopyUtils.copy(orderDto, Order.class);
        order.setCelebrity(celebrityId);
        order.setStatus(ORDER_STATUS_PAYED);
        return Result.test(save(order));
    }

    @Override
    public Result save2cart(OrderDto orderDto) {
        // 1.从securityContextHolder中获取id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = ((LoginCelebrity) authentication.getPrincipal()).getCelebrity().getId();
        Order order = BeanCopyUtils.copy(orderDto, Order.class);
        order.setCelebrity(id);
        return Result.test(save(order));
    }

    @Override
    public Result listOrders(Integer status, Integer belong) {
        // 1.从securityContextHolder中获取id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((LoginCelebrity) authentication.getPrincipal()).getCelebrity().getId();
        List<Order> orders;
        // 2.判断是网红还是电商在操作
        if (belong.equals(BELONG_CELEBRITY)) {
            // 2.1.搜索属于该网红的order
            orders = query()
                    .eq("celebrity", userId)
                    .eq("status", status)
                    .list();
            // 2.2.如果是网红查看自己的购物车，那就返回购物车vo
            if (status.equals(ORDER_STATUS_CART)) {

                List<CartVo> cartVos = BeanCopyUtils.copyList(orders, CartVo.class);
                cartVos = cartVos.parallelStream()
                        .map(cartVo -> {
                            Good good = selectGoodById(cartVo.getGood());
                            cartVo.setGoodName(good.getName());
                            cartVo.setGoodCategory(good.getCategory());
                            cartVo.setGoodImg(good.getImg());
                            cartVo.setGoodIntro(good.getIntro());
                            return cartVo;
                        })
                        .sorted(Comparator.comparing(CartVo::getCreateTime).reversed())
                        .collect(Collectors.toList());
                return Result.success(cartVos);
            }
            return toVo(orders);
        } else {
            // 2.3.搜索属于该电商的order
            orders = query()
                    .eq("eshop", userId)
                    .eq("status", status)
                    .list();
        }
        return toVo(orders);
    }

    public Result toVo(List<Order> orders) {
        List<OrderVo> orderVos = BeanCopyUtils.copyList(orders, OrderVo.class);
        // 2.3.查库封装vo
        orderVos = orderVos.parallelStream()
                .map(orderVo -> {
                    Good good = selectGoodById(orderVo.getGood());
                    orderVo.setGoodName(good.getName());
                    orderVo.setGoodImg(good.getImg());
                    orderVo.setGoodCategory(good.getCategory());
                    orderVo.setGoodIntro(good.getIntro());
                    orderVo.setCelebrityPrice(good.getCelebrityPrice());
                    EShop eShop = selectEShopById(orderVo.getEshop());
                    orderVo.setEshopName(eShop.getName());
                    orderVo.setSeller(eShop.getSeller());
                    Celebrity celebrity = selectCelebrityById(orderVo.getCelebrity());
                    orderVo.setCelebrityName(celebrity.getName());
                    return orderVo;
                })
                .collect(Collectors.toList());
        return Result.success(orderVos);
    }

    @Override
    public Result cancel(Long id) {
        return null;
    }

    @Override
    public Result consignCheck(Long id) {
        return null;
    }

    @Override
    public Result receiveCheck(Long id) {
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

    @Override
    public Result get1(Long id) {
        // 1.拼接key
        String key = ORDER_KEY + id;
        // 2.从redis中拿
        Map<Object, Object> orderCacheMap = redisCache.getMapCache(key);
        // 3.判断是否在redis中
        if (orderCacheMap == null) {
            // 3.1.redis中没有，先查询数据库
            Order order = getById(id);
            if (order == null) {
                // 3.2.数据库也没有，设空值
                redisCache.setMapCache(key, new Address(""));
                return Result.error("没有此订单！");
            }
            return Result.success(order);
        }
        // 4.在redis中，判断是否为空值
        if (StrUtil.isBlank((String) orderCacheMap.get("redisFlag"))) {
            return Result.error("没有此订单！");
        }
        // 5.非空则数据存在
        Order order = BeanUtil.fillBeanWithMap(orderCacheMap, new Order(), true);
        return Result.success(order);
    }

    public Good selectGoodById(Long id) {
        String key = GOOD_KEY + id;
        Map<Object, Object> goodCache = redisCache.getMapCache(key);
        if (goodCache == null) {
            return goodDao.selectById(id);
        }
        return BeanUtil.fillBeanWithMap(goodCache, new Good(), true);
    }

    public Celebrity selectCelebrityById(Long id) {
        String key = CELEBRITY_LOGIN_KEY + id;
        Map<Object, Object> celebrityCache = redisCache.getMapCache(key);
        if (celebrityCache == null) {
            return celebrityDao.selectById(id);
        }
        return BeanUtil.fillBeanWithMap(celebrityCache, new Celebrity(), true);
    }

    public EShop selectEShopById(Long id) {
        String key = ESHOP_LOGIN_KEY + id;
        Map<Object, Object> eshopCache = redisCache.getMapCache(key);
        if (eshopCache == null) {
            return eShopDao.selectById(id);
        }
        return BeanUtil.fillBeanWithMap(eshopCache, new EShop(), true);
    }
}
