package com.example.common.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user_behavior")
public class UserBehavior implements Serializable {
    private int id;
    private String account;
    private String like1;
    private String like2;
    private String browse1;
    private String browse2;
    private String browse3;
    private String browse4;

}
