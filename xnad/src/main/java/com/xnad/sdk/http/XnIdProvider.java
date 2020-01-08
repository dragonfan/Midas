package com.xnad.sdk.http;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.xiaoniu.statistic.NiuDataAPI;
import com.xnad.sdk.MidasAdSdk;
import com.xnad.sdk.config.Constants;
import com.xnad.sdk.config.ErrorCode;
import com.xnad.sdk.http.protocol.Protocal;
import com.xnad.sdk.utils.AppUtils;
import com.xnad.sdk.utils.MacUtils;
import com.xnad.sdk.utils.SpUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Desc:
 * <p>
 * Author: AnYaBo
 * Date: 2020/1/8
 * Copyright: Copyright (c) 2016-2022
 * Company: @小牛科技
 * Email:anyabo@xiaoniu.com
 * Update Comments:
 *
 * @author anyabo
 */
public class XnIdProvider {
    private static XnIdProvider sInstance = new XnIdProvider();

    public static XnIdProvider getInstance(){
        return sInstance;
    }

    private XnIdProvider(){
    }

    /**
     * 激活后获取xnId的延长时间 5s
     */
    private static final long ACTIVE_OBTAIN_XN_ID_DELAY_TIME = 5000;
    /**
     * 请求xnId失败轮训间隔
     */
    private static final long FAILED_REQUEST_XN_ID_DELAY_TIME = 2000;

    private static final long MAX_REQUEST_COUNT = 3;

    private int mRequestCount = 0;
    private String mLocalXnId = "";
    private boolean mLocalUserMark = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();

    /**
     * 激活5秒后请求xnId接口
     */
    public void requestDelayFiveSecondAfterActive() {
        Log.e("requestXnId", "requestDelayFiveSecondAfterActive");
        mHandler.postDelayed(() -> requestXnId(true), ACTIVE_OBTAIN_XN_ID_DELAY_TIME);
    }

    /**
     * 保存xnId到本地
     * @param xnId 小牛ID
     */
    public void saveXnIdToLocal(String xnId) {
        if (!TextUtils.isEmpty(xnId)) {
            SpUtils.putString(Constants.SpUtils.XN_ID, xnId);
        }
    }

    /**
     * 从本地缓存或内存缓存中取xnId
     */
    public String getXnIdFromLocal() {
        if (TextUtils.isEmpty(mLocalXnId)){
            mLocalXnId = SpUtils.getString(Constants.SpUtils.XN_ID, "");
        }
        return mLocalXnId;
    }


    /**
     * 标识用户为老用户
     */
    public void markUserWithOldUser() {
        SpUtils.putBoolean(Constants.SpUtils.BIG_DATA_MARK_OLD_USER, true);
    }

    /**
     * 从本地缓存或内存缓存获取用户标识,是否用户标识为老用户
     */
    public boolean isUserMarkToOld(){
        if (!mLocalUserMark){
            mLocalUserMark = SpUtils.getBoolean(Constants.SpUtils.BIG_DATA_MARK_OLD_USER, false);
        }
        return mLocalUserMark;
    }


    /**
     * 请求xnId
     *  @param isNeedLoop 是否轮训请求
     */
    public void requestXnId(boolean isNeedLoop) {
        try {
            Log.e("requestXnId", "requestXnId" + isNeedLoop);
            if (isNeedLoop && mRequestCount >= MAX_REQUEST_COUNT){
                return;
            }
            Log.e("requestXnId", "requestXnId" + mRequestCount);
            JSONObject requestJson = new JSONObject();
            String oaId = !TextUtils.isEmpty(NiuDataAPI.getOaid()) ? NiuDataAPI.getOaid() : "";
            requestJson.put("imei", AppUtils.getIMEI());
            requestJson.put("mac", MacUtils.getMacFromHardware(AppUtils.getContext()));
            requestJson.put("oaid", oaId);
            requestJson.put("android_id", AppUtils.getAndroidID());
            FormBody formBody = new FormBody.Builder()
                    .add("data", requestJson.toString())
                    .build();
            Request request = new Request.Builder()
                    .url(getBigDataBaseUrl() + "pk/id")
                    .post(formBody)
                    .build();
            Call call = mOkHttpClient.newCall(request);


            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (isNeedLoop && mRequestCount < MAX_REQUEST_COUNT) {
                        mRequestCount++;
                        mHandler.postDelayed(() -> requestXnId(true),FAILED_REQUEST_XN_ID_DELAY_TIME);
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (response.code() == ErrorCode.HTTP_RESPONSE_SUCCESS_CODE.errorCode) {
                            try {
                                String jsonStr = response.body().string();
                                Log.e("onResponse", "jsonStr : " + jsonStr);

                                JSONObject jsonObject = new JSONObject(jsonStr);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    String xnId = jsonObject.getString("xnid");
                                    if (!TextUtils.isEmpty(xnId)) {
                                        saveXnIdToLocal(xnId);
                                    }
                                }else{
                                    onFailure(call, new IOException());
                                }
                                //非轮训模式下，并且未获取到xnId
                                if (!isNeedLoop && TextUtils.isEmpty(getXnIdFromLocal())){
                                    //标识用户为老用户
                                    markUserWithOldUser();
                                }
                            } catch (Exception e) {
                                onFailure(call, new IOException());
                            }
                        }
                    } catch (Exception e) {
                        onFailure(call, new IOException());
                    }
                }
            });
        } catch ( Exception e) {
            Log.e("onResponse", "Exception : " + e.getMessage());
        }
    }

    /**
     * 获取大数据域名地址
     *  业务端控制使用测试环境还是生产环境
     */
    private String getBigDataBaseUrl() {
        return MidasAdSdk.isFormal() ? Protocal.XN_ID_SOURCE_HOST_PRODUCT
                : Protocal.XN_ID_SOURCE_HOST_TEST;
    }



}
