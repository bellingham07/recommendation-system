package com.example.common.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("recommend")
public class Recommend implements Serializable {
    private int id;
    private String account;
    private int re1;
    private int re2;
    private int re3;
    private int re4;
    private int re5;
    private int re6;
}
