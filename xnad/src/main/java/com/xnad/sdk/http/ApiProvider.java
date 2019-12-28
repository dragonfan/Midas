package com.xnad.sdk.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.xnad.sdk.BuildConfig;
import com.xnad.sdk.MidasAdSdk;
import com.xnad.sdk.bean.MidasConfigBean;
import com.xnad.sdk.config.ErrorCode;
import com.xnad.sdk.config.Constants;
import com.xnad.sdk.http.callback.HttpCallback;
import com.xnad.sdk.http.utils.HttpHelp;
import com.xnad.sdk.utils.SpUtils;
import com.xnad.sdk.utils.StatisticUtils;

import org.json.JSONObject;

/**
 * Desc:
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
public class ApiProvider {

    private static Gson mGson = new Gson();

    /**
     * 获取广告策略信息
     *
     * @param adPositionId 广告位id
     * @param httpCallback 接口回调
     * @param <T>          回调配置信息实体类
     */
    public static <T> void getStrategyInfo(String adPositionId, HttpCallback<T> httpCallback) {
        JSONObject requestJson = new JSONObject();
        try {
            String uuid = StatisticUtils.getNiuDateUUID();
            //牛数uuid
            requestJson.put("uuid", uuid);
            //业务线ID
            requestJson.put("appid", MidasAdSdk.getAppId());
            //广告位置ID
            requestJson.put("adpostId", adPositionId);
            //sdk版本号
            requestJson.put("sdkVersion", BuildConfig.VERSION_CODE);
            //设备唯一标识:可为空
            requestJson.put("primary_id", "");
        } catch (Exception e) {
        }
        HttpHelp.getInstance().postJson("pizarroadsenseapi/ads/strategyInfo",
                requestJson.toString(), new HttpCallback<MidasConfigBean>() {
                    @Override
                    public void onFailure(int httpResponseCode,
                                          int errorCode, String message) {
                        String beforeConfigInfoJson = SpUtils.getString(
                                Constants.SpUtils.MIDAS_PREFIX + adPositionId, "");
                        if (!TextUtils.isEmpty(beforeConfigInfoJson)) {
                            try {
                                MidasConfigBean configBean = mGson.fromJson(
                                        beforeConfigInfoJson, MidasConfigBean.class);
                                httpCallback.onSuccess(httpResponseCode, (T) configBean);
                            } catch (Exception e) {
                                httpCallback.onFailure(httpResponseCode, ErrorCode.API_DATA_PARSE_EXCEPTION.
                                        errorCode, ErrorCode.API_DATA_PARSE_EXCEPTION.errorMsg);
                            }
                        }else {
                            httpCallback.onFailure(httpResponseCode, ErrorCode.STRATEGY_DATA_EMPTY.
                                    errorCode, ErrorCode.STRATEGY_DATA_EMPTY.errorMsg);
                        }
                    }

                    @Override
                    public void onSuccess(int httpResponseCode, MidasConfigBean configBean) {
                        if (configBean != null) {
                            httpCallback.onSuccess(httpResponseCode, (T) configBean);
                            SpUtils.putString(Constants.SpUtils.MIDAS_PREFIX + adPositionId,
                                    mGson.toJson(configBean));
                        } else {
                            onFailure(httpResponseCode, ErrorCode.STRATEGY_DATA_EMPTY.
                                    errorCode, ErrorCode.STRATEGY_DATA_EMPTY.errorMsg);
                        }
                    }
                });
    }


}
