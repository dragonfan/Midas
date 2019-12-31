package com.xnad.sdk.config;

/**
 * Desc:错误码
 * <p>
 * Author: AnYaBo
 * Date: 2019/12/28
 * Copyright: Copyright (c) 2016-2022
 * Company: @小牛科技
 * Email:anyabo@xiaoniu.com
 * Update Comments:
 *
 * @author anyabo
 */
public enum  ErrorCode {
    HTTP_RESPONSE_SUCCESS_CODE(200,"http通信协议响应成功"),
    HTTP_RESPONSE_IO_EXCEPTION_CODE(201,"http通信IO异常"),
    API_RESPONSE_NOT_ARRIVED_EXCEPTION(10001,"api响应未到达异常"),
    API_DATA_PARSE_EXCEPTION(10002,"api数据解析异常"),
    STRATEGY_DATA_EMPTY(10003,"策略数据返回为空"),
    STRATEGY_CONFIG_EXCEPTION(10004,"策略配置获取异常"),
    CSJ_AD_DATA_EMPTY(10005,"穿山甲平台广告返回为空"),

    ;


    public int errorCode;
    public String errorMsg;

    ErrorCode(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

}
