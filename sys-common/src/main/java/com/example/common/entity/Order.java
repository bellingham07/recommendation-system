package com.example.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_order")
public class Order implements Serializable {

    @TableId("id")
    private Long id; // 订单编号
    private Long eshop; // 发货商家的account
    private Long celebrity; // 收货网红的account
    private Long deliverAddress; // 发货地址id
    private Long receiveAddress; // 收货地址id
    private Long good; // 商品
    private String remark; // 订单备注
    private Integer type; // 支付方式
    private String createTime;
    private String payTime; // 支付时间
    private String consignmentTime; // 发货时间
    private String doneTime; // 收货时间

    /**
     * 状态：C加入购物车0，C下单未支付1，C支付E未发货2，E发货3，C完成收货即完成订单4，C退款5，E确认退款6，C/E取消-1
     */
    private Integer status;
    private Integer preStatus; // 退款时的状态
}
