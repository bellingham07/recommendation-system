package com.example.common.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.dao.ContractDao;
import com.example.common.dto.ContractDto;
import com.example.common.dto.UserDto;
import com.example.common.entity.Contract;
import com.example.common.response.Result;
import com.example.common.service.ContractService;
import com.example.common.utils.UserHolder;
import com.example.common.vo.ContractVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

import static com.example.common.utils.constant.SystemConstant.*;

@Service
public class ContractServiceImpl extends ServiceImpl<ContractDao, Contract> implements ContractService {

    @Autowired
    private ContractDao contractDao;

    @Override
    public List<ContractVo> getContractsByCelebrity(String account) {
        return contractDao.findContractsByCelebrity(account);
    }

    @Override
    public List<ContractVo> getContractsCelebrity(String account, Integer start, Integer status) {
        List<ContractVo> contracts;
        if (status != 0) {
            contracts = contractDao.celebritySelectOrdersByStatus(account, start, status);

        } else {
            contracts = contractDao.celebritySelectOrders(account, start);
        }
        return contracts;
    }

    @Override
    public List<ContractVo> getContractsEshop(String account, Integer start, Integer status) {
        List<ContractVo> contracts;
        if (status != 0) {
            contracts = contractDao.eshopSelectOrdersByStatus(account, start, status);
        } else {
            contracts = contractDao.eshopSelectOrders(account, start);
        }
        return contracts;
    }

    @Override
    public int insertContract(Contract contract) {
        return contractDao.insertContract(contract);
    }

    // 接受合约
    @Override
    public Result acceptContract(Integer id) {
        // 判断role是否和status一致，一致说明有误
        // （此处逻辑：当网红向电商申请状态为0，则需要电商接受，电商role为2，所以判断需不等，反之亦然）
        Integer role = UserHolder.getUser().getRole();
        if (Objects.equals(role, getById(id).getStatus())) {
            return Result.error("该合约信息有误！");
        }
        return Result.test(updateStatus(id, CONTRACT_STATUS_UNEXPIRED));
    }

    // 某些情况可通用的更新status方法
    public Result updateStatus(Integer id, Integer status) {
        return Result.test(update()
                .setSql("status = " + status)
                .eq("contract_id", id)
                .update());
    }

    @Override
    public Result cancelContract(Integer id) {
        Contract contract = getById(id);
        Integer status = contract.getStatus();
        if (!Objects.equals(status, CONTRACT_STATUS_EXPIRED)) {
            return updateStatus(id, CONTRACT_STATUS_CANCELLED);
        }
        return Result.error("该合约目前不能取消！");
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
        contract.setCelebrity(UserHolder.getUser().getAccount());
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
        contract.setEshop(UserHolder.getUser().getAccount());
        contract.setStartTime(sdf.format(contractDto.getStartTime()));
        contract.setEndTime(contractDto.getEndTime());
        contract.setStatus(CONTRACT_STATUS_E2C);
        return Result.test(save(contract));
    }

    //
    @Override
    public Result listContracts(Integer status) {
        // 从threadLocal中获取用户，判断是商家还是网红
        UserDto operator = UserHolder.getUser();
        if (operator.getRole() == 0) {
            // 是电商
            return listContractsByStatus("eshop", operator, status);
        }
        // 是网红
        return listContractsByStatus("celebrity", operator, status);
    }

    // TODO 从status判断要取的合约类型
    private Result listContractsByStatus(String user, UserDto userDto, Integer status) {
        String account = userDto.getAccount();
        if (status == 0) {
            return Result.test(null);
        }
        return Result.test(null);
    }
}
