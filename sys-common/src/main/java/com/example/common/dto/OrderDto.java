package com.example.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    /**
     * 1.从购物车购买，只需传id，type，remark，receiveAddress，consignee
     * 2.直接购买，需要传good，eshop，type，remark，receiveAddress，consignee
     * 3.保存到购物车，需要传good，eshop
     */
    private Long id; // 1
    private Long receiveAddress; // 1,2
    private String consignee; // 1,2
    private Long good; // 2
    private Long eshop; // 2
    private String type; // 1,2
    private String remark; // 1,2
}
