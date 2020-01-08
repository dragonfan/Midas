package com.xnad.sdk;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.qq.e.ads.cfg.MultiProcessFlag;
import com.qq.e.ads.nativ.NativeUnifiedADData;
import com.xnad.sdk.ad.cache.ADTool;
import com.xnad.sdk.ad.factory.MidasAdManagerFactory;
import com.xnad.sdk.ad.admanager.AdManager;
import com.xnad.sdk.config.AdConfig;
import com.xnad.sdk.config.TTAdManagerHolder;
import com.xnad.sdk.config.Constants;
import com.xnad.sdk.utils.AppUtils;
import com.xnad.sdk.utils.LogUtils;
import com.xnad.sdk.utils.SpUtils;
import com.xnad.sdk.utils.StatisticUtils;


/**
 * @ProjectName: MidasAdSdk
 * @Package: com.comm.jksdk
 * @ClassName: MidasAdSdk
 * @Description: java类作用描述
 * @Author: fanhailong
 * @CreateDate: 2019/11/11 17:13
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/11 17:13
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public final class MidasAdSdk {

    /**
     * SDK是否初始化
     */
    private static boolean mIsInit = false;

    /**
     * 业务 appId
     */
    private static String mAppId;
    /**
     * 是否是正式环境 true对应生产环境
     */
    private static boolean mIsFormal;

    /**
     * 聚合广告sdk初始化
     *
     * @param context       上下文
     * @param adConfig 初始化配置信息
     */
    public static void init(Context context, AdConfig adConfig) {
        AppUtils.init(context);
        long beginTime = System.currentTimeMillis();
        mIsFormal = adConfig.isIsFormal();
        mAppId = adConfig.getAppId();
        //初始化基本配置信息
        //强烈建议在应用对应的Application#onCreate()方法中调用，避免出现content为null的异常
        TTAdManagerHolder.init(context, adConfig.getCsjAppId());

        MultiProcessFlag.setMultiProcess(true);

        mIsInit = true;
        LogUtils.d("Midas sdk init time=" + (System.currentTimeMillis() - beginTime));
        //初始化牛数
        StatisticUtils.init(context, adConfig.getChannel(), adConfig.getProductId(), adConfig.getServerUrl());
        //首次上报imei
        boolean isReport = SpUtils.getBoolean(Constants.SpUtils.FIRST_REPORT_IMEI, false);
        if (!isReport) {
            StatisticUtils.setImei(AppUtils.getIMEI());
            SpUtils.putBoolean(Constants.SpUtils.FIRST_REPORT_IMEI, true);
        }
        LogUtils.d("Midas and niuDate sdk init time=" + (System.currentTimeMillis() - beginTime));


        registerActivityLifecycleCallbacks(context);
    }

    private static void registerActivityLifecycleCallbacks(Context context) {
        Application mApplication = (Application) context;
        mApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (activity!=null) {
                    String activityName = activity.getClass().getSimpleName();
                    NativeUnifiedADData ad = ADTool.getInstance().getSelfRenderAdCache(activityName);
                    if (ad!=null) {
                        ad.resume();
                    }
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (activity!=null) {
                    String activityName = activity.getClass().getSimpleName();
                    NativeUnifiedADData ad = ADTool.getInstance().getSelfRenderAdCache(activityName);
                    if (ad!=null) {
                        ad.destroy();
                    }
                }
            }
        });
    }

    /**
     * 获取广告管理类
     *
     * @return
     */
    public static AdManager getAdsManger() {
        return new MidasAdManagerFactory().produce();
    }

    /**
     * 设置imei
     */
    public static void setImei() {
        String imei = AppUtils.getIMEI();
        boolean isReport = SpUtils.getBoolean(Constants.SpUtils.AGAIN_REPORT_IMEI, false);
        if (!isReport && !TextUtils.isEmpty(imei)) {
            StatisticUtils.setImei(imei);
            SpUtils.putBoolean(Constants.SpUtils.AGAIN_REPORT_IMEI, true);
        }
    }

    public static boolean isInit() {
        return mIsInit;
    }

    public static String getAppId() {
        return mAppId;
    }

    public static boolean isFormal() {
        return mIsFormal;
    }

}
