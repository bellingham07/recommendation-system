package com.example.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
    private Long consignAddress; // 发货地址id
    private Long receiveAddress; // 收货地址id
    private Long good; // 商品
    private String remark; // 订单备注
    private String type; // 支付方式
    private String createTime;
    private String consignmentTime; // 发货时间
    private String doneTime; // 收货时间
    private String consignee; // 轻度冗余，减少查库
    private String consignor; // 轻度冗余，减少查库

    /**
     * 状态：C加入购物车0，C下单并支付1（C可退款，E可直接取消），E发货2（C可退款），
     * C完成收货即完成订单3（C可退款），C发起退款4，E确认退款5，E取消6
     */
    private Integer status;
    private Integer preStatus; // 退款时的状态


    @TableField(exist = false)
    private String redisFlag;

    public Order(String redisFlag) {
        this.redisFlag = redisFlag;
    }
}
