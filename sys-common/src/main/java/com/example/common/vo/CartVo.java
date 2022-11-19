package com.example.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartVo {
    private Long id; // 订单编号
    private Long eshop; // 发货商家的account
    private Long celebrity; // 收货网红的account
    private Long good; // 商品
    private String createTime;

    // good
    private String goodName;
    private String goodIntro;
    private String goodCategory;
    private String goodImg;
}
