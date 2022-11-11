package com.example.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("tb_order")
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    @TableId("id")
    private Integer id; // 订单编号
    private String eshop; // 发货商家的account
    private String celebrity;// 收货网红的account
    private Integer deliverAddress; // 发货地址id
    private Integer receiveAddress; // 收货地址id
    private Integer good; // 商品
    private String remark; // 订单备注
    private String type; // 支付方式
    private Integer status; // 状态：C加入购物车7，C支付商家未发货1，E发货2，C完成收货即完成订单3，C/E取消的订单6
    private String payTime; // 支付时间
    private String consignmentTime; // 发货时间
    private String doneTime; // 收货时间
}
