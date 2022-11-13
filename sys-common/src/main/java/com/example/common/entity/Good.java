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
@TableName("tb_good")
public class Good implements Serializable {

    @TableId("id")
    private Long id;
    private String name;
    private String brand;
    private String img;
    private Long category;
    private Long eshop;
    private Double marketPrice;
    private Double celebrityPrice;
    private String intro;
    private Integer proxy;
    private String goodUrl;
    private Integer status; //默认为1启用状态，禁用为0
    private String createTime;
    private String updateTime;
}
