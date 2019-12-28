package com.xnad.sdk.http;

import android.os.Handler;

import com.xnad.sdk.BuildConfig;

import java.io.IOException;
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
public class OkHttpHelp {
    private static final OkHttpHelp ourInstance = new OkHttpHelp();
    OkHttpClient mOkHttpClient;

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
     * @param suffix  请求地址后缀
     * @param requestJson 请求json 参数
     * @param callback    回调
     */

    public void postJson(String suffix, String requestJson, HttpCallBack callback) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestJson);
        Request request = new Request.Builder()
                .url(BuildConfig.API_DOMAIN_HOST + suffix)
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
