package com.xnad.sdk.http;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.xnad.sdk.http.Api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Desc:
 * <p>
 * Author: ZhouTao
 * Date: 2019/12/26
 * Copyright: Copyright (c) 2016-2020
 * Company: @小牛科技
 * Email:zhoutao@xiaoniu.com
 * Update Comments:
 *
 * @author zhoutao
 */
public class OkHttpHelp {
    private static final OkHttpHelp ourInstance = new OkHttpHelp();

    OkHttpClient mOkHttpClient;
    /**
     * 请求域名
     */
    String baseUrl = Api.URL_TEST.APP_WEATHER_DOMAIN;

    public static OkHttpHelp getInstance() {
        return ourInstance;
    }

    private OkHttpHelp() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    private Handler mHandler = new Handler();

    /**
     * 发送 POST JSON 请求
     *
     * @param requestUrl  请求路径
     * @param requestJson 请求json 参数
     * @param callback    回调
     */

    public void postJson(String requestUrl, String requestJson, HttpCallBack callback) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestJson);

        Request request = new Request.Builder()
                .url(baseUrl + requestUrl)
                .post(requestBody)
                .build();
        //创建/Call
        Call call = mOkHttpClient.newCall(request);
        //加入队列 异步操作
        if (callback != null) {
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mHandler.post(() -> {
                        callback.onFailure(500);
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (response.code() == 200) {
                            try {
                                String responseData = response.body().string();
                                mHandler.post(() -> {
                                    callback.onResponse(responseData);
                                });

                                return;
                            } catch (IOException e) {
                            } catch (Exception e) {
                            }
                        }
                        mHandler.post(() -> {
                            callback.onFailure(response.code());
                        });
                    } catch (Exception e) {

                        mHandler.post(() -> {
                            callback.onFailure(response.code());
                        });

                    }
                }
            });
        }
    }

    public interface HttpCallBack {
        /**
         * 请求失败
         *
         * @param errorCode
         */
        void onFailure(int errorCode);

        /**
         * 请求成功
         *
         * @param responseData
         */
        void onResponse(String responseData);
    }
}
