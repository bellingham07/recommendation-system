package com.example.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.entity.Contract;
import com.example.common.vo.ContractVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ContractDao extends BaseMapper<Contract> {

    @Select("select c.*, g.name from contract c, goods g where c.good = g.good_id and c.celebrity = #{account}")
    List<ContractVo> findContractsByCelebrity(@Param("account") String account);

    List<ContractVo> eshopSelectOrders(@Param("account") String account, @Param("start") Integer start);

    List<ContractVo> eshopSelectOrdersByStatus(@Param("account") String account, @Param("start") Integer start, @Param("status") Integer status);

    List<ContractVo> celebritySelectOrders(@Param("account") String account, @Param("start") Integer start);

    List<ContractVo> celebritySelectOrdersByStatus(@Param("account") String account, @Param("start") Integer start, @Param("status") Integer status);

    int insertContract(Contract c);
}
