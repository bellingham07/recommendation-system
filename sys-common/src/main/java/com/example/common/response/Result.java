package com.example.common.response;

import com.example.common.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.common.utils.constant.SystemConstant.*;

//结果类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private Integer code;

    private String msg;

    private T data;

    public static Result success() {
        Result r = new Result();
        r.data = null;
        r.code = SUCCESS_CODE;
        r.msg = SUCCESS_OPERATE;
        return r;
    }

    public static Result success(String msg) {
        Result<Address> r = new Result();
        r.data = null;
        r.code = SUCCESS_CODE;
        r.msg = msg;
        return r;
    }

    public static <T> Result success(T data) {
        Result r = new Result();
        r.data = data;
        r.code = SUCCESS_CODE;
        r.msg = SUCCESS_OPERATE;
        return r;
    }

    public static <T> Result success(String msg, T data) {
        Result r = new Result();
        r.data = data;
        r.code = SUCCESS_CODE;
        r.msg = msg;
        return r;
    }

    public static Result error() {
        Result r = new Result();
        r.data = null;
        r.code = ERROR_CODE;
        r.msg = FAIL_OPERATE;
        return r;
    }

    public static <T> Result error(String msg) {
        Result r = new Result();
        r.data = null;
        r.code = ERROR_CODE;
        r.msg = msg;
        return r;
    }

    public static Result test(boolean success) {
        Result r = new Result();
        r.data = null;
        if (success) {
            r.msg = SUCCESS_OPERATE;
            r.code = SUCCESS_CODE;
            return r;
        }
        r.msg = FAIL_OPERATE;
        r.code = ERROR_CODE;
        return r;
    }

    public static <T> Result test(T data) {
        Result r = new Result();
        if (data != null) {
            r.msg = SUCCESS_OPERATE;
            r.code = SUCCESS_CODE;
            r.data = data;
            return r;
        }
        r.data = null;
        r.msg = FAIL_OPERATE;
        r.code = ERROR_CODE;
        return r;
    }
}
