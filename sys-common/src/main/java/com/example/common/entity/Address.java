package com.example.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_address")
public class Address implements Serializable {

    @TableId("id")
    private Long id;
    private Long user; //网红或商家id
    private Integer belong; // 网红-1，电商1
    private String name; //收件人或发货人姓名（乱起名了）
    private String phonenumber; //收件人电话
    private String province; //一级地址
    private String city; //二级地址
    private String area; //三级地址
    private String detail; //详细地址
    private String type;
    private String post;
}
