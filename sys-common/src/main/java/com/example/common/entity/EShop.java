package com.example.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EShop {

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
