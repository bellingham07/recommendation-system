package com.example.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.dto.ContractDto;
import com.example.common.entity.Contract;
import com.example.common.response.Result;
import com.example.common.vo.ContractVo;

import java.util.List;

public interface ContractService extends IService<Contract> {

//    获取网红用户的代言签约合同
    List<ContractVo> getContractsByCelebrity(String account);

    List<ContractVo> getContractsCelebrity(String account, Integer start, Integer status);

    List<ContractVo> getContractsEshop(String account, Integer start, Integer status);

    int insertContract(Contract contract);

    Result cancelContract(Integer id);

    Result removeSingle(Integer id);

    Result applyC2E(ContractDto contractDto);

    Result applyE2C(ContractDto contractDto);

    Result listContracts(Integer status);

    Result acceptContract(Integer id);
}
