package com.comm.jksdk.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.comm.jksdk.api.ConfigService;
import com.comm.jksdk.bean.ConfigBean;
import com.comm.jksdk.constant.Constants;
import com.comm.jksdk.http.Constant;
import com.comm.jksdk.http.OkHttpWrapper;
import com.comm.jksdk.http.base.BaseResponse;
import com.comm.jksdk.http.utils.AppInfoUtils;
import com.comm.jksdk.http.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jk.adsdkdemo.utils.SPUtils;

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
    private  static Context mContext;
    protected  final String TAG = "GeekAdSdk-->";
    private static AdsConfig mAdsConfig=null;
    private Gson mGson = new Gson();
    private  String mConfigInfo;

    private AdsConfig(){

    }
    public static AdsConfig getInstance(Context context){
        mContext=context;
        if(mAdsConfig==null){
            synchronized (AdsConfig.class){
                if(mAdsConfig==null){
                    mAdsConfig=new AdsConfig();
                }
            }
        }
        return mAdsConfig;
    }
    /**
     * 从cms请求广告配置
     */
    @SuppressLint("CheckResult")
    public   void requestConfig() {
        getConfigInfo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<ConfigBean>>() {
                    @Override
                    public void accept(BaseResponse<ConfigBean> ConfigInfoBean)  {
                        if(ConfigInfoBean==null){
                            return;
                        }
                        if(!ConfigInfoBean.isSuccess()){
                            return;
                        }
                        LogUtils.d(TAG, "accept->配置信息请求成功 ");
                        //对象转json保存到sp
                        String configInfo = mGson.toJson(ConfigInfoBean);
                        SPUtils.putString(Constants.SPUtils.CONFIG_INFO,configInfo);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        LogUtils.d(TAG, "accept->配置信息请求失败"+throwable.getMessage());
                    }
                });
    }

    /**
     * 发请求
     * @return
     */
    private Observable<BaseResponse<ConfigBean>> getConfigInfo() {
        Map<String, Object> requestParams = new HashMap<>();
        RequestBody requestBody = null;
        requestParams.put("bid", Constants.bid);
        requestParams.put("productName", Constants.productName);
        requestParams.put("marketName", Constants.marketName);
        requestParams.put("versionCode", AppInfoUtils.getVerCode(mContext));
        requestParams.put("osSystem", 0);
        requestParams.put("userActive", Constants.userActive);
        requestParams.put("ts", System.currentTimeMillis());
        requestParams.put("latitude", Constants.latitude);
        requestParams.put("longitude", Constants.longitude);
        requestParams.put("province", Constants.province);
        requestParams.put("city", Constants.city);
        requestParams.put("modelVersion", "");
        requestParams.put("sdkVersion",1);
        requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), mGson.toJson(requestParams));
        return OkHttpWrapper.getInstance().getRetrofit().create(ConfigService.class).getConfig(requestBody);
    }
    /**
     * 获取本地保存配置信息
     */
    public  BaseResponse<ConfigBean> getConfig(String defaultConfigKey){
        // 从sp获取配置信息
        mConfigInfo = SPUtils.getString(Constants.SPUtils.CONFIG_INFO, "");
        if(TextUtils.isEmpty(mConfigInfo)){
            // 获取默认配置（客户端）
            if(!TextUtils.isEmpty(defaultConfigKey)){
                mConfigInfo= SPUtils.getString(defaultConfigKey,"");
            }else{
                LogUtils.w(TAG, "默认defaultConfigKey为空，请检查");
            }

        }
        BaseResponse<ConfigBean> mConfigInfoBean = mGson.fromJson(mConfigInfo, new TypeToken<BaseResponse<ConfigBean>>(){}.getType());

      return mConfigInfoBean;
    }

}
