package com.comm.jksdk.config;

import android.content.Context;
import android.text.TextUtils;

import com.comm.jksdk.MidasAdSdk;
import com.comm.jksdk.api.ConfigService;
import com.comm.jksdk.bean.MidasConfigBean;
import com.comm.jksdk.config.listener.ConfigListener;
import com.comm.jksdk.constant.Constants;
import com.comm.jksdk.http.OkHttpWrapper;
import com.comm.jksdk.http.base.BaseResponse;
import com.comm.jksdk.http.exception.DataParseException;
import com.comm.jksdk.http.exception.HttpRequestException;
import com.comm.jksdk.http.utils.LogUtils;
import com.comm.jksdk.utils.CodeFactory;
import com.comm.jksdk.utils.CollectionUtils;
import com.comm.jksdk.utils.JsonUtils;
import com.comm.jksdk.utils.SpUtils;
import com.comm.jksdk.utils.StatisticUtils;
import com.google.gson.Gson;

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
                    if (cacheConfigBean != null){
                        return Observable.just(cacheConfigBean);
                    }else {
                        throw new DataParseException(CodeFactory.CONFIG_DATA_EMPTY,
                                CodeFactory.getError(CodeFactory.CONFIG_DATA_EMPTY));
                    }
                }
                String configBeanStr = null;
                try {
                    configBeanStr = new Gson().toJson(configBean);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("configBean to json string error");
                    throw new DataParseException(CodeFactory.CONFIG_PARSE_EXCEPTION,
                            CodeFactory.getError(CodeFactory.CONFIG_PARSE_EXCEPTION));
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
                                listener.adError(200,1, "获取配置信息失败");
                            }
                            return;
                        }
                        if (listener != null) {
                            listener.adSuccess(midasConfigBean);
                        }
                    }
                }, throwable -> {
                    int httpCode = CodeFactory.HTTP_EXCEPTION;
                    int errorCode = CodeFactory.HTTP_IO_EXCEPTION;
                    String errorMsg = CodeFactory.getError(errorCode);
                    if (throwable != null){
                        if (throwable instanceof HttpRequestException){
                            HttpRequestException exception = (HttpRequestException) throwable;
                            if (exception != null){
                                httpCode = exception.getCode();
                            }
                        }else if(throwable instanceof DataParseException){
                            DataParseException exception = (DataParseException) throwable;
                            if (exception != null){
                                errorCode = exception.getCode();
                                errorMsg = exception.getMsg();
                            }
                        }
                    }
                    LogUtils.e("get midas config error, "+throwable.getMessage());
                    if (listener != null) {
                        listener.adError(httpCode, errorCode,errorMsg);
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
        String uuid = StatisticUtils.getNiuDateUUID();
        LogUtils.d("uuid = "+uuid);
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
        String requstData=mGson.toJson(requestParams);
        LogUtils.d(TAG, "requstData->"+requstData);

        requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),requstData);
        return OkHttpWrapper.getInstance().getRetrofit().create(ConfigService.class).getConfig(requestBody);
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

}
