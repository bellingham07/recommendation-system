package com.example.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_user")
public class User implements Serializable {

    @TableId(value = "id")
    private Long id;
    private String username;
    private String password;
    private Integer role;
    private Integer crePoints;
    private String sex;
    private Integer age;
    private String tel;
    private String headPic;
    private String briefIntro;
    private String realName;
    private String platform;
    private String platform2;
    private String shopUrl;
    private String goodType;
    private String goodType2;
    private int isLike;//用户是否填写问卷
}
