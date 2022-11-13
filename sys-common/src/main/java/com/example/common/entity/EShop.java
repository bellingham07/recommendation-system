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
@TableName("tb_eshop")
public class EShop implements Serializable {

    @TableId(value = "id")
    private Long id;
    private String username;
    private String password;
    private String name;
    private Integer age;
    private String tel;
    private String email;
    private String avatar;
    private String intro;
    private String seller;
    private String platform;
    private String platformUrl;
    private Integer category;
    private Integer creditPoint;
    private Integer like;
}
