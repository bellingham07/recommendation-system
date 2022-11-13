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
@TableName("tb_contract")
public class Contract implements Serializable {

    @TableId("id")
    private Long id;
    private Long celebrity;
    private Long eshop;
    private Long good;
    private String createTime;
    private String startTime;
    private String endTime;
    private String cRemark;
    private String eRemark;

    /**
     * 状态：C向E申请代理1，E向C申请合作2，C/E双方接受3，C/E合约到期失效4，C向E发起取消-1，E向C发起取消-2，取消-3
     */
    private Integer status;
    private Integer preStatus; // 保存取消时的状态
}
