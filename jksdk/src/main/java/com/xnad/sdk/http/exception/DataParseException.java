package com.xnad.sdk.http.exception;

/**
 * Desc:
 * <p>
 * Author: AnYaBo
 * Date: 2019/12/24
 * Copyright: Copyright (c) 2016-2022
 * Company: @小牛科技
 * Email:anyabo@xiaoniu.com
 * Update Comments:
 *
 * @author anyabo
 */
public class DataParseException extends Exception {
    private int code;
    private String msg;

    public DataParseException(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
