package com.example.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Celebrity {

    @TableId(value = "id")
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private Integer name;
    private String sex;
    private Integer age;
    private String phonenumber;
    private String email;
    private String avatar;
    private String intro;
    private String realName;
    private String platform;
    private String platformUrl;
    private Integer category;
    private Integer creditPoint;
    private Integer like;
}
