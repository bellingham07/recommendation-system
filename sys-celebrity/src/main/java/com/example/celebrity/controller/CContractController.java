package com.example.celebrity.controller;

import com.example.common.dto.ContractDto;
import com.example.common.response.Result;
import com.example.common.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("celebrity/contract")
public class CContractController {

    @Autowired
    private ContractService contractService;

    // TODO 获取contractList
    @GetMapping("list/{status}")
    public Result list(@PathVariable("status") Integer status) {
        return contractService.listContracts(status);
    }

    // 网红向电商申请合约
    @PostMapping("")
    public Result apply(ContractDto contractDto) {
        return contractService.applyC2E(contractDto);
    }

    // 接受合约
    @GetMapping("{id}")
    public Result accept(@PathVariable("id") Integer id) {
        return contractService.acceptContract(id);
    }

    // 取消合约
    @PutMapping("{id}")
    public Result cancel(@PathVariable("id") Integer id) {
        return contractService.cancelContract(id);
    }

    // 删除合约
    @DeleteMapping("{id}")
    public Result removeSingle(@PathVariable("id") Integer id) {
        return contractService.removeSingle(id);
    }

    // TODO 批量删除合约
    @PostMapping("remove")
    public Result removeBatch() {
        return null;
    }
}
