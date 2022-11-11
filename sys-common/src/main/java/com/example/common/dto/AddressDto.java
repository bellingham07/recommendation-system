package com.example.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private String human;//收件人或发货人姓名（乱起名了）
    private String tel;//收件人电话
    private String province;//一级地址
    private String city;//二级地址
    private String area;//三级地址
    private String addressDetail;//详细地址
    private String type;
    private String post;
}
