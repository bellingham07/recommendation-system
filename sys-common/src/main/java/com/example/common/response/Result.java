package com.example.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.common.utils.constant.SystemConstant.*;

//结果类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code;

    private String msg;

    private Object data;

    public static Result success() {
        Result r = new Result();
        r.data = null;
        r.code = CODE_SUCCESS;
        r.msg = OPERATE_SUCCESS;
        return r;
    }

    public static Result success(String msg) {
        Result r = new Result();
        r.data = null;
        r.code = CODE_SUCCESS;
        r.msg = msg;
        return r;
    }

    public static Result success(Object data) {
        Result r = new Result();
        r.data = data;
        r.code = CODE_SUCCESS;
        r.msg = OPERATE_SUCCESS;
        return r;
    }

    public static Result success(String msg, Object data) {
        Result r = new Result();
        r.data = data;
        r.code = CODE_SUCCESS;
        r.msg = msg;
        return r;
    }

    public static Result error() {
        Result r = new Result();
        r.data = null;
        r.code = CODE_ERROR;
        r.msg = OPERATE_FAIL;
        return r;
    }

    public static Result error(String msg) {
        Result r = new Result();
        r.data = null;
        r.code = CODE_ERROR;
        r.msg = msg;
        return r;
    }

    public static Result test(boolean success) {
        Result r = new Result();
        r.data = null;
        if (success) {
            r.msg = OPERATE_SUCCESS;
            r.code = CODE_SUCCESS;
            return r;
        }
        r.msg = OPERATE_FAIL;
        r.code = CODE_ERROR;
        return r;
    }

    public static Result test(Object data) {
        Result r = new Result();
        if (data != null) {
            r.msg = OPERATE_SUCCESS;
            r.code = CODE_SUCCESS;
            r.data = data;
            return r;
        }
        r.data = null;
        r.msg = OPERATE_FAIL;
        r.code = CODE_ERROR;
        return r;
    }
}
