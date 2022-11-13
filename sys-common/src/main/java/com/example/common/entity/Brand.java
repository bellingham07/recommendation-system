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
@TableName("tb_brand")
public class Brand implements Serializable {

    @TableId("id")
    private Long id;
    private String chineseName;
    private String englishName;
    private String letter;
    private String img;
    private String createTime;
    private String updateTime;
}
