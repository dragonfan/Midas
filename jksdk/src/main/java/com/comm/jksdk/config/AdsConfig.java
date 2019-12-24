package com.comm.jksdk.config;

import android.content.Context;
import android.text.TextUtils;

import com.comm.jksdk.MidasAdSdk;
import com.comm.jksdk.api.ConfigService;
import com.comm.jksdk.bean.ConfigBean;
import com.comm.jksdk.bean.MidasConfigBean;
import com.comm.jksdk.bean.PositionInfo;
import com.comm.jksdk.config.listener.ConfigListener;
import com.comm.jksdk.constant.Constants;
import com.comm.jksdk.http.OkHttpWrapper;
import com.comm.jksdk.http.base.BaseResponse;
import com.comm.jksdk.http.utils.AppInfoUtils;
import com.comm.jksdk.http.utils.LogUtils;
import com.comm.jksdk.utils.CollectionUtils;
import com.comm.jksdk.utils.JsonUtils;
import com.comm.jksdk.utils.SpUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author liupengbing
 * @date 2019/9/27
 */
public class AdsConfig {
    private static Context mContext;
    protected final String TAG = "MidasAdSdk-->";
    private static AdsConfig mAdsConfig = null;
    private Gson mGson = new Gson();
    private String mConfigInfo;
    private ArrayList<PositionInfo> posInfoList;

    //本地的配置信息
    private static List<ConfigBean.AdListBean> adsInfoslist = new ArrayList();

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
    public void requestConfig(String adpostId, ConfigListener listener) {
        getMidasConfigInfo(adpostId).flatMap(new Function<BaseResponse<MidasConfigBean>, ObservableSource<MidasConfigBean>>() {
            @Override
            public ObservableSource<MidasConfigBean> apply(BaseResponse<MidasConfigBean> midasConfigBeanBaseResponse) throws Exception {
                if (midasConfigBeanBaseResponse == null) {
                    LogUtils.e("request config error, midasConfigBeanBaseResponse is null");
                    MidasConfigBean cacheConfigBean = getCacheMidasConfigBean(adpostId);
                    return Observable.just(cacheConfigBean);
                }
                if (!midasConfigBeanBaseResponse.isSuccess()) {
                    LogUtils.e("request config error, code is " + midasConfigBeanBaseResponse.getCode());
                    MidasConfigBean cacheConfigBean = getCacheMidasConfigBean(adpostId);
                    return Observable.just(cacheConfigBean);
                }
                MidasConfigBean configBean = midasConfigBeanBaseResponse.getData();
                if (configBean == null) {
                    LogUtils.e("request config error, MidasConfigBean is null");
                    MidasConfigBean cacheConfigBean = getCacheMidasConfigBean(adpostId);
                    return Observable.just(cacheConfigBean);
                }
                String configBeanStr = null;
                try {
                    configBeanStr = new Gson().toJson(configBean);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("configBean to json string error");
                }
                if (!TextUtils.isEmpty(configBeanStr)) {
                    SpUtils.putString(Constants.SPUtils.MIDAS_PREFIX + adpostId, configBeanStr);
                }
                return Observable.just(configBean);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MidasConfigBean>() {
                    @Override
                    public void accept(MidasConfigBean midasConfigBean) throws Exception {
                        if (midasConfigBean == null) {
                            if (listener != null) {
                                listener.adError(1, "获取配置信息失败");
                            }
                            return;
                        }
                        if (listener != null) {
                            listener.adSuccess(midasConfigBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e("get midas config error, "+throwable.getMessage());
                        if (listener != null) {
                            listener.adError(1, "获取配置信息失败");
                        }
                    }
                });
    }

    /**
     * 获取缓存的配置信息
     * @param adpostId
     * @return
     */
    private MidasConfigBean getCacheMidasConfigBean(String adpostId){
        try {
            String midasConfigBean = SpUtils.getString(Constants.SPUtils.MIDAS_PREFIX + adpostId, "");
            if (TextUtils.isEmpty(midasConfigBean)) {
                return null;
            }
            MidasConfigBean configBean = JsonUtils.decode(midasConfigBean, MidasConfigBean.class);
            return configBean;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("getCacheMidasConfigBean error " + e);
        }
        return null;
    }

    /**
     * 发请求
     *
     * @return
     */
    private Observable<BaseResponse<MidasConfigBean>> getMidasConfigInfo(String adpostId) {
        Map<String, Object> requestParams = CollectionUtils.createMap();
        RequestBody requestBody = null;
//        int bid = AdsConfig.getBid();
//        if (bid > 0) {
//            requestParams.put("bid", bid);
//        }
        String uuid = MidasAdSdk.getUUID();
        requestParams.put("uuid", uuid);
        requestParams.put("sessionid", uuid+System.currentTimeMillis());
        requestParams.put("appid", MidasAdSdk.getAppId());
        requestParams.put("appversion", AppInfoUtils.getVerCode(MidasAdSdk.getContext()));
        requestParams.put("adpostId", adpostId);
        requestParams.put("sdkVersion", Constants.version_code);
//        String productName = MidasAdSdk.getRroductName();
//        requestParams.put("productName", productName);
//        String marketName = MidasAdSdk.getChannel();
//        requestParams.put("marketName", marketName);
//        requestParams.put("osSystem", Constants.bid);
//        long userActive = AdsConfig.getUserActive();
//        if (userActive < 0) {
//            userActive = System.currentTimeMillis();
//            AdsConfig.setUserActive(userActive);
//        }
//        requestParams.put("userActive", userActive);
//        requestParams.put("ts", System.currentTimeMillis());
//        String latitude = AdsConfig.getLatitude();
//        if (!TextUtils.isEmpty(latitude)) {
//            requestParams.put("latitude", latitude);
//        }
//        String longitude = AdsConfig.getLongitude();
//        if (!TextUtils.isEmpty(longitude)) {
//            requestParams.put("longitude", longitude);
//        }
//        requestParams.put("province", Constants.province);
//        requestParams.put("city", Constants.city);
//        requestParams.put("modelVersion", "");

//        Boolean posInfosBoolean=getPositionInfos();
//        if (posInfosBoolean) {
//            requestParams.put("positionInfos", posInfoList);
//        }
        String requstData=mGson.toJson(requestParams);
        LogUtils.d(TAG, "requstData->"+requstData);

        requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),requstData);
        return OkHttpWrapper.getInstance().getRetrofit().create(ConfigService.class).getConfig(requestBody);
    }

    /**
     * 获取本地保存配置信息
     */
    public ConfigBean.AdListBean getConfig(String postion) {
        if (TextUtils.isEmpty(postion)) {
            return null;
        }
        List<ConfigBean.AdListBean> adListBeans = getAdsInfoslist();
        if (CollectionUtils.isEmpty(adListBeans)) {
            return null;
        }
        for (ConfigBean.AdListBean adListBean : adListBeans) {
            if (postion.equals(adListBean.getAdPosition())) {
                return adListBean;
            }
        }
        return null;
    }

    /**
     * 获取bid
     * @return
     */
    public static int getBid(){
        if (Constants.bid > 0) {
            return Constants.bid;
        }
        Constants.bid = SpUtils.getInt(Constants.SPUtils.BID, -1);
        return Constants.bid;
    }

    /**
     * 设置bid
     * @return
     */
    public static void setBid(int bid){
        SpUtils.putInt(Constants.SPUtils.BID, bid);
        Constants.bid = bid;
    }

    /**
     *获取用户激活时间
     * @return
     */
    public static long getUserActive(){
        if (Constants.userActive > 0) {
            return Constants.userActive;
        }
        Constants.userActive = SpUtils.getLong(Constants.SPUtils.USER_ACTIVE, -1);
        return Constants.userActive;
    }

    /**
     * 设置用户激活时间
     * @param userActive
     */
    public static void setUserActive(long userActive){
        SpUtils.putLong(Constants.SPUtils.USER_ACTIVE, userActive);
        Constants.userActive = userActive;
    }


    /**
     * 根据业务线获取app name
     * @return
     */
    public static String getProductAppName(){
        if (TextUtils.isEmpty(MidasAdSdk.getRroductId())) {
            return "未知";
        }
        String appName = "";
        switch (MidasAdSdk.getRroductId()) {
            case "12":
                appName = "吉日历";
                break;
            case "13":
                appName = "即刻天气";
                break;
            case "131":
                appName = "知心天气";
                break;
            case "17":
                appName = "玲珑视频";
                break;
            case "18":
                appName = "悟空清理";
                break;
            case "181":
                appName = "清理管家极速版";
                break;
            case "19":
                appName = "最来电";
                break;
        }
        return appName;
    }

    /**
     * 获取本地配置信息
     * @return
     */
    public static List<ConfigBean.AdListBean> getAdsInfoslist(){
        if (!CollectionUtils.isEmpty(adsInfoslist)) {
            return adsInfoslist;
        }
        String adInfo = SpUtils.getString(Constants.SPUtils.CONFIG_INFO, "");
        if (TextUtils.isEmpty(adInfo)) {
            return adsInfoslist;
        }
        ConfigBean configBean = JsonUtils.decode(adInfo, ConfigBean.class);
        adsInfoslist = configBean.getAdList();
        return adsInfoslist;
    }

}
