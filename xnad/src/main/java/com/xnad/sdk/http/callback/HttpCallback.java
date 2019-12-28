package com.xnad.sdk.http.callback;

/**
 * Desc:接口响应回调
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
public abstract class HttpCallback<T> {
    /**
     * 接口请求开始
     *  UI Thread
     */
    public void onStart() {
    }

    /**
     * 请求失败
     * @param httpResponseCode  http通信协议响应code
     * @param errorCode 接口错误码
     * @param message   错误消息
     *   UI Thread
     */
    public abstract void onFailure(int httpResponseCode,
                                   int errorCode, String message);

    /**
     * 请求成功
     * @param httpResponseCode  http通信协议响应code
     * @param result    返回实体类
     *    UI Thread
     */
    public abstract void onSuccess(int httpResponseCode, T result);
}
