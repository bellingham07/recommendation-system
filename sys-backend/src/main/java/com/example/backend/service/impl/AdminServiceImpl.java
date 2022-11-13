package com.example.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend.dao.AdminDao;
import com.example.backend.entity.Admin;
import com.example.backend.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminDao, Admin> implements AdminService {
}
