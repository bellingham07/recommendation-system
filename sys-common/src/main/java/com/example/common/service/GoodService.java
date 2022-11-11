package com.example.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.entity.Good;

import java.util.List;

public interface GoodService extends IService<Good> {

//    根据网红主要代理类型获得几个商品（分页，暂定）
    List<Good> getByCelebrityType(String type, int start);

//    取每个展示的店的四个商品展示
    List<Good> get4(String account);

    List<Good> getAll(String account, Integer start);

//    获得该店的上架的前十位个商品
    List<String> get15Names(String account);

//    通过网红的主要代理类型获得6个商品展示给网红（分页，暂定）
    List<Good> get6Goods(String type, Integer start);

    //插入商品信息
    int addGoods(Good good);

    int updateStatus(int status,int goodId);

    List<Integer> getGoodId(String type);

    Good getGoodById(int goodId);
}
