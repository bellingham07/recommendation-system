package com.example.celebrity.controller;

import com.example.common.dto.ContractDto;
import com.example.common.response.Result;
import com.example.common.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("contract")
public class ContractController {

    @Autowired
    private ContractService contractService;

    // TODO 获取contractList
    @GetMapping("list/{status}")
    public Result list(@PathVariable("status") Integer status) {
        return contractService.listContracts(status);
    }

    // 获取单条
    @GetMapping("{id}")
    public Result getOne(@PathVariable("id") Long id) {
        return Result.test(contractService.getById(id));
    }

    // 网红向电商申请合约
    @PostMapping("operate")
    public Result apply(ContractDto contractDto) {
        return contractService.applyC2E(contractDto);
    }

    // 接受合约
    @GetMapping("operate/{id}")
    public Result accept(@PathVariable("id") Integer id) {
        return contractService.accept(id);
    }

    // 发起取消
    @PutMapping("operate/{id}")
    public Result cancel(@PathVariable("id") Integer id) {
        return contractService.cancelByE(id);
    }

    // TODO 确认取消
    @GetMapping("operate/cancel/{id}")
    public Result cancelApprove(@PathVariable("id") Integer id) {
        return contractService.cancelApproveByE(id);
    }

    // TODO 拒绝取消
    @PutMapping("operate/cancel/{id}")
    public Result cancelRefuse(@PathVariable("id") Integer id) {
        return contractService.cancelRefuseByE(id);
    }

    // 删除合约
    @DeleteMapping("operate/{id}")
    public Result removeSingle(@PathVariable("id") Integer id) {
        return contractService.removeSingle(id);
    }

    // TODO 批量删除合约
    @PostMapping("operate/remove")
    public Result removeBatch() {
        return null;
    }
}
