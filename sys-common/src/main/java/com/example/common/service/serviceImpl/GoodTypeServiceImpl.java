package com.example.common.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.dao.GoodTypeDao;
import com.example.common.entity.GoodCategory;
import com.example.common.service.GoodTypeService;
import org.springframework.stereotype.Service;

@Service
public class GoodTypeServiceImpl extends ServiceImpl<GoodTypeDao, GoodCategory> implements GoodTypeService {
}
