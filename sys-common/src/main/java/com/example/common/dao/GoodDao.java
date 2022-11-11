package com.example.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.entity.Good;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodDao extends BaseMapper<Good> {
    List<Good> getByCelebrityType(@Param("type") String type, @Param("start") int start);

    List<Good> get4(@Param("account") String account);

    List<Good> getAll(@Param("account") String account, @Param("start") Integer start);

    @Select("select name from goods where eshop_account = #{account} limit 0, 15")
    List<String> get15Names(String account);

    @Select("select g.*, u.username as eshopName from goods g, users u where type = #{type} and g.eshop_account = u.account order by g.brand desc, g.good_id desc limit #{start}, 6")
    List<Good> get6GoodsForRecommendation(@Param("type") String type, @Param("start") Integer start);

    //向数据库添加商品
    int addGoods(Good good);

    int updateStatus(@Param("status") int status,@Param("goodId") int goodId);

    List<Integer> getGoodId(@Param("type") String type);

    Good getGoodById(@Param("goodId") int goodId);
}
