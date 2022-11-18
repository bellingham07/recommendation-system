package com.example.celebrity.controller;

import com.example.common.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("good")
public class GoodController {

    @Autowired
    private GoodService goodService;
}
