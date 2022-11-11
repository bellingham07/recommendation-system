package com.example.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {

    private Integer id;//订单编号
    private String eshop;//发货商家的account
    private String celebrity;//收货网红的account
    private Integer deliverAddress; //发货地址id
    private Integer receiveAddress; //收货地址id
    private Integer good; //商品
    private String remark; // 订单备注
    private String type; //支付方式
    private Integer status; //状态：创建订单未付款1，支付商家未接单2，接单未发货3，发货4，完成收货即完成订单5，取消的订单6
    private String payTime;//支付时间
    private String consignmentTime;//发货时间
    private String doneTime;//收获时间

    private String payment; //付款总金额
    private String eshopName;
    private String celebrityName;
    private Integer goodId;
    private String goodName;
    private String goodIntro;
    private String goodType;
    private String goodImg;
    private String seller;
    private String consignee;
    private String consignor;
}