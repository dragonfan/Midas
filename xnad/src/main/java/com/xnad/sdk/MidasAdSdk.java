package com.xnad.sdk;

import android.content.Context;
import android.text.TextUtils;

import com.xnad.sdk.ad.factory.MidasAdManagerFactory;
import com.xnad.sdk.ad.admanager.AdManager;
import com.xnad.sdk.config.ADConfigBuild;
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

    private static boolean mIsInit = false;

    private static Context mContext;
    private static String mAppId;

    private static boolean mIsFormal;

    /**
     * 聚合广告sdk初始化
     *
     * @param context       上下文
     * @param adConfigBuild 初始化配置信息
     */
    public static void init(Context context, ADConfigBuild adConfigBuild) {

        long beginTime = System.currentTimeMillis();
        mContext = context.getApplicationContext();
        mIsFormal = adConfigBuild.isIsFormal();
        mAppId = adConfigBuild.getAppId();
        //初始化基本配置信息
        //强烈建议在应用对应的Application#onCreate()方法中调用，避免出现content为null的异常
        AppUtils.init(mContext);
        TTAdManagerHolder.init(context, adConfigBuild.getCsjAppId());
        mIsInit = true;
        LogUtils.d("Midas sdk init time=" + (System.currentTimeMillis() - beginTime));
        //初始化牛数
        StatisticUtils.init(context, adConfigBuild.getChannel(), adConfigBuild.getProductId(), adConfigBuild.getServerUrl());
        //首次上报imei
        boolean isReport = com.xnad.sdk.utils.SpUtils.getBoolean(Constants.SpUtils.FIRST_REPORT_IMEI, false);
        if (!isReport) {
            StatisticUtils.setImei(AppUtils.getIMEI());
            com.xnad.sdk.utils.SpUtils.putBoolean(Constants.SpUtils.FIRST_REPORT_IMEI, true);
        }
        LogUtils.d("Midas and niuDate sdk init time=" + (System.currentTimeMillis() - beginTime));
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

    public static Context getContext() {
        return mContext;
    }


    public static String getAppId() {
        return mAppId;
    }


    /**
     * 检测是否初始化
     */
    private static void checkInit() {
        if (!mIsInit) {
            throw new RuntimeException("MidasAdSdk should  be init");
        }
    }


    public static boolean isFormal() {
        return mIsFormal;
    }
}
