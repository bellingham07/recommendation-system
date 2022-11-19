package com.example.common.utils.constant;

public class SystemConstant {

    /**
     * 返回信息
     */
    public static final Integer CODE_SUCCESS = 1;
    public static final Integer CODE_ERROR = 0;
    public static final String OPERATE_SUCCESS = "成功";
    public static final String OPERATE_FAIL = "操作失败";

    /**
     * contract状态：
     */
    public static final Integer CONTRACT_STATUS_C2E = 0; //合约（网红向商家申请）
    public static final Integer CONTRACT_STATUS_E2C = 2; //合约（商家向网红申请）
    public static final Integer CONTRACT_STATUS_UNEXPIRED = 3; //未到期合约
    public static final Integer CONTRACT_STATUS_EXPIRED = 4; //到期合约（完成）
    public static final Integer CONTRACT_STATUS_CANCELLED = 6; //合约取消

    /**
     * order状态：C加入购物车0，C下单并支付1（C可退款，E可直接取消），E发货2（C可退款），
     * C完成收货即完成订单3（C可退款），C发起退款4，E确认退款5，E取消6
     */
    public static final Integer ORDER_STATUS_CART = 0;
    public static final Integer ORDER_STATUS_PAYED = 1; // 已下单，已支付
    public static final Integer ORDER_STATUS_CONSIGNED = 2; // 已发货
    public static final Integer ORDER_STATUS_COMPLETE = 3; // 订单收货完成
    public static final Integer ORDER_STATUS_REFUND = 4; // 网红发起退款
    public static final Integer ORDER_STATUS_REFUNDED = 5; // 电商同意退款
    public static final Integer ORDER_STATUS_CANCELLED = 6; // 电商取消订单

    /**
     * 网红或商家标记
     */
    public static final Integer BELONG_CELEBRITY = -1;
    public static final Integer BELONG_ESHOP = 1;

}
