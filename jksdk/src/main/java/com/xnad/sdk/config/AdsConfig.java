package com.xnad.sdk.config;

import android.content.Context;
import android.text.TextUtils;

import com.xnad.sdk.MidasAdSdk;
import com.xnad.sdk.bean.BaseResponse;
import com.xnad.sdk.bean.MidasConfigBean;
import com.xnad.sdk.config.listener.ConfigListener;
import com.xnad.sdk.constant.Constants;
import com.xnad.sdk.http.Api;
import com.xnad.sdk.http.OkHttpHelp;
import com.xnad.sdk.utils.LogUtils;
import com.xnad.sdk.utils.SpUtils;
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
        requestParams.put("sdkVersion", Constants.version_code);
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
                            SpUtils.putString(Constants.SPUtils.MIDAS_PREFIX + adpostId, configBeanStr);
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
            String midasConfigBean = SpUtils.getString(Constants.SPUtils.MIDAS_PREFIX + adpostId, "");
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




    /**
     * 获取用户激活时间
     *
     * @return
     */
    public static long getUserActive() {
        if (Constants.userActive > 0) {
            return Constants.userActive;
        }
        Constants.userActive = SpUtils.getLong(Constants.SPUtils.USER_ACTIVE, -1);
        return Constants.userActive;
    }

    /**
     * 设置用户激活时间
     *
     * @param userActive
     */
    public static void setUserActive(long userActive) {
        SpUtils.putLong(Constants.SPUtils.USER_ACTIVE, userActive);
        Constants.userActive = userActive;
    }

}
