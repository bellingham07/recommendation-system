package com.example.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractVo {
    private Integer id;
    private String celebrity;
    private String eshop;
    private Integer good;
    private String createTime;
    private String startTime;
    private String endTime;
    private Integer status; //状态：网红向商家申请代理0，商家向网红申请合作2，双方接受并合约生效在期限3，合约到期失效4，合约取消6

    private String celebrityName;
    private String eshopName;
    private String goodName; //商品名
    private String goodImg;
    private String seller;
    private String goodType;
    private String brand;
}
