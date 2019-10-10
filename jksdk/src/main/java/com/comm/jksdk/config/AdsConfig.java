package com.comm.jksdk.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.comm.jksdk.api.ConfigService;
import com.comm.jksdk.bean.ConfigBean;
import com.comm.jksdk.bean.PositionInfo;
import com.comm.jksdk.constant.Constants;
import com.comm.jksdk.http.OkHttpWrapper;
import com.comm.jksdk.http.base.BaseResponse;
import com.comm.jksdk.http.utils.AppInfoUtils;
import com.comm.jksdk.http.utils.LogUtils;
import com.comm.jksdk.utils.SpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author liupengbing
 * @date 2019/9/27
 */
public class AdsConfig {
    private static Context mContext;
    protected final String TAG = "GeekAdSdk-->";
    private static AdsConfig mAdsConfig = null;
    private Gson mGson = new Gson();
    private String mConfigInfo;
    private ConfigBean.AdListBean mConfigInfoBean;
    private ArrayList<PositionInfo> posInfoList;

    private AdsConfig() {

    }

    public static AdsConfig getInstance(Context context) {
        mContext = context;
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
    @SuppressLint("CheckResult")
    public void requestConfig() {

        getConfigInfo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<ConfigBean>>() {
                    @Override
                    public void accept(BaseResponse<ConfigBean> ConfigInfoBean) {
                        if (ConfigInfoBean == null) {
                            return;
                        }
                        if (!ConfigInfoBean.isSuccess()) {
                            LogUtils.d(TAG, "accept->配置信息请求失败:" + ConfigInfoBean.getCode()
                                    +ConfigInfoBean.getMsg());
                            return;
                        }

                        LogUtils.d(TAG, "accept->配置信息请求成功 ");
                        ConfigBean configBean = ConfigInfoBean.getData();
                        if (configBean == null) {
                            LogUtils.d(TAG, "accept->配置信息为空 ");
                            return;
                        }

                        List<ConfigBean.AdListBean> configList = configBean.getAdList();
                        if (configList == null || configList.size() == 0) {
                            LogUtils.d(TAG, "accept->配置信息为空 ");
                            return;
                        }
                        for (int i = 0; i < configList.size(); i++) {
                            // "isChange": 0,//是否变更：0 - 无  1 - 有
                            if (configList.get(i).getIsChange() == 1) {
                                //更新数据
                                //对象转json保存到sp
                                String adPosition = configList.get(i).getAdPosition();
                                if (TextUtils.isEmpty(adPosition)) {
                                    return;
                                }

                                String configInfo = mGson.toJson(configList.get(i));
                                SpUtils.putString(adPosition, configInfo);
                            }

                        }
                        //对象转json保存到sp
                        //保存总json
                        String configInfo = mGson.toJson(ConfigInfoBean);
                        SpUtils.putString(Constants.SPUtils.CONFIG_INFO, configInfo);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        LogUtils.d(TAG, "accept->配置信息请求失败" + throwable.getMessage());
                    }
                });
        return;
    }


    /**
     * 发请求
     *
     * @return
     */
    private Observable<BaseResponse<ConfigBean>> getConfigInfo() {
        Map<String, Object> requestParams = new HashMap<>();
        RequestBody requestBody = null;
        requestParams.put("bid", Constants.bid);
        requestParams.put("productName", Constants.productName);
        requestParams.put("marketName", Constants.marketName);
        requestParams.put("versionCode", AppInfoUtils.getVerCode(mContext));
        requestParams.put("osSystem", 1);
        requestParams.put("userActive", Constants.userActive);
        requestParams.put("ts", System.currentTimeMillis());
        requestParams.put("latitude", Constants.latitude);
        requestParams.put("longitude", Constants.longitude);
        requestParams.put("province", Constants.province);
        requestParams.put("city", Constants.city);
        requestParams.put("modelVersion", "");
        requestParams.put("sdkVersion", 1);

        Boolean posInfosBoolean=getPositionInfos();
        if (posInfosBoolean) {
            requestParams.put("positionInfos", posInfoList);
        }
        requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), mGson.toJson(requestParams));
        return OkHttpWrapper.getInstance().getRetrofit().create(ConfigService.class).getConfig(requestBody);
    }

    /**
     * 获取所有位置对于信息
     * @return
     */
    private  Boolean getPositionInfos() {
        posInfoList = new ArrayList<PositionInfo>();
        posInfoList.clear();
        Boolean positionInfos=false;
        // 从sp获取配置信息
        mConfigInfo = SpUtils.getString(Constants.SPUtils.CONFIG_INFO, "");
        if (!TextUtils.isEmpty(mConfigInfo)) {
            BaseResponse<ConfigBean> mConfigInfoBean = mGson.fromJson(mConfigInfo, new TypeToken<BaseResponse<ConfigBean>>() {
            }.getType());
            if (mConfigInfoBean != null) {
                if (mConfigInfoBean.getData() != null) {
                    if (mConfigInfoBean.getData().getAdList() != null && mConfigInfoBean.getData().getAdList().size() > 0) {
                        for (int i = 0; i < mConfigInfoBean.getData().getAdList().size(); i++) {
                            PositionInfo posInfo = new PositionInfo();
                            posInfo.adPosition = mConfigInfoBean.getData().getAdList().get(i).getAdPosition();
                            posInfo.adVersion = mConfigInfoBean.getData().getAdList().get(i).getAdVersion();
                            posInfo.productId = mConfigInfoBean.getData().getAdList().get(i).getProductId();
                            posInfoList.add(posInfo);
                        }
                        positionInfos=true;
                    }
                }
            }

        }
        return positionInfos;

    }

    /**
     * 获取本地保存配置信息
     */
    public ConfigBean.AdListBean getConfig(String defaultConfigKey, String cmsConfigKey) {
        // 从sp获取配置信息
        if (!TextUtils.isEmpty(cmsConfigKey)) {
            mConfigInfo = SpUtils.getString(cmsConfigKey, "");
            mConfigInfoBean = mGson.fromJson(mConfigInfo, new TypeToken<ConfigBean.AdListBean>() {}.getType());
        }
        if (TextUtils.isEmpty(mConfigInfo)) {
            // 获取默认配置（客户端）
            if (!TextUtils.isEmpty(defaultConfigKey)) {
                String allConfigInfo = SpUtils.getString(defaultConfigKey, "");
                BaseResponse<ConfigBean> allConfigInfoBean = mGson.fromJson(allConfigInfo, new TypeToken<BaseResponse<ConfigBean>>() {
                }.getType());
                if (allConfigInfoBean != null) {
                    if (allConfigInfoBean.getData() != null) {
                        if (allConfigInfoBean.getData().getAdList() != null && allConfigInfoBean.getData().getAdList().size() > 0) {
                            for (int i = 0; i < allConfigInfoBean.getData().getAdList().size(); i++) {
                                String adPosition = allConfigInfoBean.getData().getAdList().get(i).getAdPosition();
                                if (!TextUtils.isEmpty(adPosition)) {
                                    if (cmsConfigKey.equals(adPosition)) {
                                        mConfigInfoBean = allConfigInfoBean.getData().getAdList().get(i);
                                        LogUtils.w(TAG,"DATA：客户端默认配置信息");
                                    }
                                }

                            }
                        }
                    }
                }
            }else {
                LogUtils.w(TAG,"默认defaultConfigKey为空");
            }

        }else{
            LogUtils.w(TAG,"DATA：cms上次请求配置信息");
        }

        return mConfigInfoBean;
    }

}
