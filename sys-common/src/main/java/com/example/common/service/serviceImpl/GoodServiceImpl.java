package com.example.common.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.dao.GoodDao;
import com.example.common.entity.Good;
import com.example.common.service.GoodService;
import org.springframework.stereotype.Service;

@Service
public class GoodServiceImpl extends ServiceImpl<GoodDao, Good> implements GoodService {



}
