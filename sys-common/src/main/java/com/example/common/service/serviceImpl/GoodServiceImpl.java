package com.example.common.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.dao.GoodDao;
import com.example.common.entity.Good;
import com.example.common.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodServiceImpl extends ServiceImpl<GoodDao, Good> implements GoodService {

    @Autowired
    private GoodDao goodDao;

    @Override
    public List<Good> getByCelebrityType(String type, int start) {
        return goodDao.getByCelebrityType(type, start);
    }

    @Override
    public List<Good> get4(String account) {
        return goodDao.get4(account);
    }

    @Override
    public List<Good> getAll(String account, Integer start) {
        return goodDao.getAll(account, start);
    }

    @Override
    public List<String> get15Names(String account) {
        return goodDao.get15Names(account);
    }

    @Override
    public List<Good> get6Goods(String type, Integer start) {
        return goodDao.get6GoodsForRecommendation(type, start);
    }

    @Override
    public int addGoods(Good good) {
        return goodDao.addGoods(good);
    }

    @Override
    public int updateStatus(int status, int goodId) {
        return goodDao.updateStatus(status,goodId);
    }

    @Override
    public List<Integer> getGoodId(String type) {
        return goodDao.getGoodId(type);
    }

    @Override
    public Good getGoodById(int goodId) {
        return goodDao.getGoodById(goodId);
    }


}
