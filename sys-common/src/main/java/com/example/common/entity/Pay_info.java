package com.example.common.entity;

import lombok.Data;

import java.io.Serializable;

@Data

public class Pay_info implements Serializable {
    private int pay_id;
    private int p_uid;
    private String pay_platform;
    private String pay_number;
    private int pay_status;
}
