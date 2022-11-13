package com.example.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.dto.ContractDto;
import com.example.common.entity.Contract;
import com.example.common.response.Result;
import com.example.common.vo.ContractVo;

import java.util.List;

public interface ContractService extends IService<Contract> {

    Result removeSingle(Integer id);

    Result applyC2E(ContractDto contractDto);

    Result applyE2C(ContractDto contractDto);

    Result listContracts(Integer status);

    Result accept(Integer id);

    Result cancelByC(Integer id);

    Result cancelApproveByC(Integer id);

    Result cancelRefuseByC(Integer id);

    Result cancelApproveByE(Integer id);

    Result cancelRefuseByE(Integer id);

    Result cancelByE(Integer id);
}
