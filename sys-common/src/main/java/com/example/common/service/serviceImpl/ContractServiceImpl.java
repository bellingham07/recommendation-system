package com.example.common.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.dao.ContractDao;
import com.example.common.dto.ContractDto;
import com.example.common.dto.UserDto;
import com.example.common.entity.Contract;
import com.example.common.response.Result;
import com.example.common.service.ContractService;
import com.example.common.utils.threadHolder.CelebrityHolder;
import com.example.common.vo.ContractVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

import static com.example.common.utils.constant.SystemConstant.*;

@Service
public class ContractServiceImpl extends ServiceImpl<ContractDao, Contract> implements ContractService {

    // 接受合约
    @Override
    public Result accept(Integer id) {
        // 判断role是否和status一致，一致说明有误

        return Result.test(updateStatus(id, CONTRACT_STATUS_UNEXPIRED));
    }

    @Override
    public Result cancelByC(Integer id) {
        return null;
    }

    @Override
    public Result cancelApproveByC(Integer id) {
        return null;
    }

    @Override
    public Result cancelRefuseByC(Integer id) {
        return null;
    }

    @Override
    public Result cancelApproveByE(Integer id) {
        return null;
    }

    @Override
    public Result cancelRefuseByE(Integer id) {
        return null;
    }

    @Override
    public Result cancelByE(Integer id) {
        return null;
    }

    // 某些情况可通用的更新status方法
    public Result updateStatus(Integer id, Integer status) {
        return Result.test(update()
                .setSql("status = " + status)
                .eq("contract_id", id)
                .update());
    }

    @Override
    public Result removeSingle(Integer id) {
        Contract contract = getById(id);
        Integer status = contract.getStatus();
        if (!Objects.equals(status, CONTRACT_STATUS_UNEXPIRED)) {
            return Result.error("合约生效中，不能删除！");
        }
        return Result.test(removeById(id));
    }

    // 网红celebrity向电商eshop申请
    @Override
    public Result applyC2E(ContractDto contractDto) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Contract contract = BeanUtil.copyProperties(contractDto, Contract.class);
        contract.setGood(contractDto.getGood());
        contract.setCelebrity(CelebrityHolder.getUser().getId());
        contract.setStartTime(sdf.format(contractDto.getStartTime()));
        contract.setEndTime(contractDto.getEndTime());
        contract.setStatus(CONTRACT_STATUS_C2E);
        return Result.test(save(contract));
    }

    // 电商eshop向申请网红celebrity
    @Override
    public Result applyE2C(ContractDto contractDto) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Contract contract = BeanUtil.copyProperties(contractDto, Contract.class);
        contract.setGood(contractDto.getGood());
        contract.setEshop(CelebrityHolder.getUser().getId());
        contract.setStartTime(sdf.format(contractDto.getStartTime()));
        contract.setEndTime(contractDto.getEndTime());
        contract.setStatus(CONTRACT_STATUS_E2C);
        return Result.test(save(contract));
    }

    //
    @Override
    public Result listContracts(Integer status) {
        return null;
    }
}
