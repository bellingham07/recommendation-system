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
@TableName("tb_good_category")
public class GoodCategory implements Serializable {

    @TableId("id")
    private Long id;
    private String name;
    private Long parentId;
    private Integer sort;
    private String createTime;
    private String updateTime;
}
