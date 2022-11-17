package com.example.eshop.controller;

import com.example.common.service.EShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EShopController {

    @Autowired
    private EShopService eShopService;


}
