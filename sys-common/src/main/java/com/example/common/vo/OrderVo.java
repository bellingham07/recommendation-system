package com.example.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {

    private Long id; // 订单编号
    private Long eshop; // 发货商家的account
    private Long celebrity; // 收货网红的account
    private Long consignAddress; // 发货地址id
    private Long receiveAddress; // 收货地址id
    private Long good; // 商品
    private String remark; // 订单备注
    private Integer type; // 支付方式
    private String createTime;
    private String consignmentTime; // 发货时间
    private String doneTime; // 收货时间
    private Integer status;
    private Integer preStatus; // 退款时的状态
    private String consignee;
    private String consignor;

    // celebrity
    private String celebrityName;

    // eshop
    private String eshopName;
    private String seller;

    // good
    private Double celebrityPrice; // 付款总金额
    private String goodName;
    private String goodIntro;
    private String goodCategory;
    private String goodImg;


}