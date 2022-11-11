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
    private Integer id;
    private String name;
    private String brand;
    private String img;
    private String eshopAccount;
    private String type;
    private Integer marketPrice;
    private Integer celebrityPrice;
    private String intro;
    private Integer agentNum;
    private String goodUrl;
    private Integer status; //默认为1启用状态，禁用为0

    @TableField(exist = false)
    private String eshopName;
}
