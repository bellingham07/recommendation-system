package com.example.common.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class GoodParams implements Serializable {
    private String name;
    private MultipartFile img;
    private String type;
    private Integer marketPrice;
    private Integer celebrityPrice;
    private String intro;
    private String goodUrl;
}
