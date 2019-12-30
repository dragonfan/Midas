package com.xnad.sdk.http.utils;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.xnad.sdk.BuildConfig;
import com.xnad.sdk.MidasAdSdk;
import com.xnad.sdk.config.ErrorCode;
import com.xnad.sdk.http.callback.HttpCallback;
import com.xnad.sdk.http.model.BaseResponse;
import com.xnad.sdk.utils.LogUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Desc:网络请求工具类
 * <p>
 * Author: ZhouTao
 * Date: 2019/12/26
 * Copyright: Copyright (c) 2016-2020
 * Company: @小牛科技
 * Email:zhoutao@xiaoniu.com
 * te Comments:
 * @author zhoutao
 */
public class HttpHelp {
    private static final HttpHelp ourInstance = new HttpHelp();
    OkHttpClient mOkHttpClient;
    private Gson mGson;

    public static HttpHelp getInstance() {
        return ourInstance;
    }

    private HttpHelp() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        mGson = new Gson();
    }

    private Handler mHandler = new Handler();

    /**
     * 发送 POST JSON 请求
     * @param suffix  请求地址后缀
     * @param requestJson 请求json 参数
     * @param callback    回调
     */
    public <T> void postJson(String suffix, String requestJson, HttpCallback<T> callback) {
        RequestBody requestBody = RequestBody.create(MediaType.parse
                ("application/json; charset=utf-8"), requestJson);
        Request request = new Request.Builder()
                .url(getBaseUrl() + suffix)
                .post(requestBody)
                .build();
        if (callback != null){
            mHandler.post(() -> callback.onStart());
        }
        callback.onStart();
        //创建/Call
        Call call = mOkHttpClient.newCall(request);
        //加入队列 异步操作
        if (callback != null) {
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mHandler.post(() -> callback.onFailure(
                            ErrorCode.HTTP_RESPONSE_IO_EXCEPTION_CODE.errorCode,
                            ErrorCode.API_RESPONSE_NOT_ARRIVED_EXCEPTION.errorCode,
                            ErrorCode.HTTP_RESPONSE_IO_EXCEPTION_CODE.errorMsg));
                }

                @Override
                public void onResponse(Call call, Response response) {
                    try {
                        if (response.code() ==
                                ErrorCode.HTTP_RESPONSE_SUCCESS_CODE.errorCode) {
                            try {
                                String jsonStr = response.body().string();

                                Type dataType = getSuperclassTypeParameter(callback);
                                Type resultType = $Gson$Types.newParameterizedTypeWithOwner(
                                        null, BaseResponse.class, dataType);
                                BaseResponse apiResult = mGson.fromJson(jsonStr, resultType);
                                LogUtils.i(jsonStr);
                                if (apiResult != null && apiResult.isSuccess()){
                                    mHandler.post(() -> callback.onSuccess(response.code(),
                                            (T) apiResult.data));
                                }else {
                                    mHandler.post(() -> callback.onFailure(response.code(),
                                            apiResult.code,apiResult.msg));
                                }
                                return;
                            } catch (Exception e) {
                                mHandler.post(() -> callback.onFailure(
                                        ErrorCode.API_DATA_PARSE_EXCEPTION.errorCode,
                                        ErrorCode.API_RESPONSE_NOT_ARRIVED_EXCEPTION.errorCode,
                                        ErrorCode.API_DATA_PARSE_EXCEPTION.errorMsg));
                            }
                        }
                    } catch (Exception e) {
                    }
                    mHandler.post(() -> callback.onFailure(
                            response.code(),
                            ErrorCode.API_RESPONSE_NOT_ARRIVED_EXCEPTION.errorCode,
                            response.message()));
                }
            });
        }
    }

    private static Type getSuperclassTypeParameter(Object object) {
        Type superclass = object.getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
            return Object.class;
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public static String getBaseUrl(){
        if (MidasAdSdk.isFormal()) {
            return BuildConfig.API_DOMAIN_HOST_PRODUCT;
        }
        return BuildConfig.API_DOMAIN_HOST_TEST;
    }
}
