package com.example.common.utils.constant;

public class SystemConstant {
    public static final Integer SUCCESS_CODE = 1;
    public static final Integer ERROR_CODE = 0;
    public static final String SUCCESS_OPERATE = "成功";
    public static final String FAIL_OPERATE = "操作失败";
    public static final Integer CONTRACT_STATUS_C2E = 0; //合约（网红向商家申请）
    public static final Integer CONTRACT_STATUS_E2C = 2; //合约（商家向网红申请）
    public static final Integer CONTRACT_STATUS_UNEXPIRED = 3; //未到期合约
    public static final Integer CONTRACT_STATUS_EXPIRED = 4; //到期合约（完成）
    public static final Integer CONTRACT_STATUS_CANCELLED = 6; //合约取消
    public static final Integer ORDER_STATUS_PAYED = 1; //未接单
    public static final Integer ORDER_STATUS_SENT = 2; //已发货
    public static final Integer ORDER_STATUS_COMPLETE = 3; //订单完成
    public static final Integer ORDER_STATUS_CANCELLED = 6; //订单取消
    public static final Integer ORDER_STATUS_CART = 7; //物品加入购物车（创建订单（暂定？））

}
