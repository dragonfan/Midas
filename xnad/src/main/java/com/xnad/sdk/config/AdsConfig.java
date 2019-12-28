package com.xnad.sdk.config;

import android.content.Context;
import android.text.TextUtils;

import com.xnad.sdk.BuildConfig;
import com.xnad.sdk.MidasAdSdk;
import com.xnad.sdk.http.bean.BaseResponse;
import com.xnad.sdk.bean.MidasConfigBean;
import com.xnad.sdk.config.listener.ConfigListener;
import com.xnad.sdk.constant.Constants;
import com.xnad.sdk.http.Api;
import com.xnad.sdk.http.OkHttpHelp;
import com.xnad.sdk.utils.LogUtils;
import com.xnad.sdk.utils.StatisticUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;


/**
 * @author liupengbing
 * @date 2019/9/27
 */
public class AdsConfig {
    protected final String TAG = "MidasAdSdk-->";
    private static AdsConfig mAdsConfig = null;
    private Gson mGson = new Gson();

    private AdsConfig() {

    }

    public static AdsConfig getInstance(Context context) {
        if (mAdsConfig == null) {
            synchronized (AdsConfig.class) {
                if (mAdsConfig == null) {
                    mAdsConfig = new AdsConfig();
                }
            }
        }
        return mAdsConfig;
    }


    /**
     * 从cms请求广告配置
     */
    public void requestConfig(String adpostId, ConfigListener listener) {
        Map<String, Object> requestParams = new HashMap<>();
        String uuid = StatisticUtils.getNiuDateUUID();
        LogUtils.d("uuid = " + uuid);
        //设备ID
        requestParams.put("uuid", uuid);
        //业务线ID
        requestParams.put("appid", MidasAdSdk.getAppId());
        //广告位置ID
        requestParams.put("adpostId", adpostId);
        //sdk版本号
        requestParams.put("sdkVersion", BuildConfig.VERSION_CODE);
        //设备唯一标识:可为空
        requestParams.put("primary_id", "");
        String requstData = mGson.toJson(requestParams);
        LogUtils.d(TAG, "requstData->" + requstData);

        OkHttpHelp.getInstance().postJson(Api.STRATEGY_INFO, requstData, new OkHttpHelp.HttpCallBack() {
            @Override
            public void onFailure(int errorCode) {

            }

            @Override
            public void onResponse(String responseData) {
                try {
                    MidasConfigBean cacheConfigBean;
                    //解析base
                    Gson gson = new Gson();
                    BaseResponse<MidasConfigBean> baseBean = gson.fromJson(responseData, new TypeToken<BaseResponse<MidasConfigBean>>() {
                    }.getType());

                    if (baseBean == null) {
                        cacheConfigBean = getCacheMidasConfigBean(adpostId);
                    } else {
                        if (baseBean.getData() == null) {
                            cacheConfigBean = getCacheMidasConfigBean(adpostId);
                        } else {
                            cacheConfigBean = baseBean.getData();
                        }
                    }

                    if (cacheConfigBean != null) {
                        String configBeanStr = gson.toJson(cacheConfigBean);
                        if (!TextUtils.isEmpty(configBeanStr)) {
                            com.xnad.sdk.utils.SpUtils.putString(Constants.SpUtils.MIDAS_PREFIX + adpostId, configBeanStr);
                        }
                    }

                    if (cacheConfigBean == null) {
                        if (listener != null) {
                            listener.adError(200, 1, "获取配置信息失败");
                        }
                    } else {
                        if (listener != null) {
                            listener.adSuccess(cacheConfigBean);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取缓存的配置信息
     *
     * @param adpostId
     * @return
     */
    private MidasConfigBean getCacheMidasConfigBean(String adpostId) {
        try {
            String midasConfigBean = com.xnad.sdk.utils.SpUtils.getString(Constants.SpUtils.MIDAS_PREFIX + adpostId, "");
            if (TextUtils.isEmpty(midasConfigBean)) {
                return null;
            }
            MidasConfigBean configBean = new Gson().fromJson(midasConfigBean, MidasConfigBean.class);
            return configBean;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("getCacheMidasConfigBean error " + e);
        }
        return null;
    }


}
