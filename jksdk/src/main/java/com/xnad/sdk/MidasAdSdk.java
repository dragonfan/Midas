package com.xnad.sdk;

import android.content.Context;

import com.xnad.sdk.ad.factory.MidasAdManagerFactory;
import com.xnad.sdk.ad.listener.AdManager;
import com.xnad.sdk.config.TTAdManagerHolder;
import com.xnad.sdk.constant.Constants;
import com.xnad.sdk.utils.AppUtils;
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

    public static Context mContext;
    public static String mRroductName;
    public static String mRroductId;
    public static String mAppId;
    public static String mChannel;
    public static boolean mIsFormal;


    /**
     * 聚合广告sdk初始化
     * @param context 上下文
     * @param appid 广告业务线id 大数据提供
     * @param csjAppId
     * @param isFormal 是否是正式环境 true对应生产环境
     * @param productId 业务线id 大数据提供 (初始化牛数和初始化穿山甲sdk用到)
     * @param channel 渠道名称 (初始化牛数用到)
     * @param serverUrl 需指定上传地址，并传入大数据给定的url
     */
    public static void init(Context context, String appid, String productId, String channel, String csjAppId, String serverUrl, boolean isFormal){
        mContext = context.getApplicationContext();
        mRroductId = productId;
        mAppId = appid;
        mChannel = channel;
        mIsFormal = isFormal;
        //初始化基本配置信息
        //强烈建议在应用对应的Application#onCreate()方法中调用，避免出现content为null的异常
        TTAdManagerHolder.init(context, csjAppId);
        mIsInit = true;
        //初始化牛数
        StatisticUtils.init(context, channel, productId, serverUrl);

        //首次上报imei
        boolean isReport = SpUtils.getBoolean(Constants.FIRST_REPORT_IMEI, false);
        if (!isReport) {
            StatisticUtils.setImei(AppUtils.getIMEI(mContext));
            SpUtils.putBoolean(Constants.FIRST_REPORT_IMEI, true);
        }
    }

    /**
     * 获取广告管理类
     * @return
     */
    public static AdManager getAdsManger(){
        return new MidasAdManagerFactory().produce();
    }

    /**
     * 设置imei
     * @param imei
     */
    public static void setImei(String imei) {
        boolean isReport = SpUtils.getBoolean(Constants.AGAIN_REPORT_IMEI, false);
        if (!isReport) {
            StatisticUtils.setImei(imei);
            SpUtils.putBoolean(Constants.AGAIN_REPORT_IMEI, true);
        }
    }

    public static boolean isInit() {
        return mIsInit;
    }

    public static Context getContext() {
        return mContext;
    }

    public static String getRroductName() {
        return mRroductName;
    }

    public static String getChannel() {
        return mChannel;
    }

    public static boolean isFormal() {
        return mIsFormal;
    }

    public static String getRroductId() {
        return mRroductId;
    }

    public static void setRroductId(String mRroductId) {
        MidasAdSdk.mRroductId = mRroductId;
    }

    public static String getAppId() {
        return mAppId;
    }

    public static void setAppId(String mAppId) {
        MidasAdSdk.mAppId = mAppId;
    }

    public static String getmChannel() {
        return mChannel;
    }

    public static void setmChannel(String mChannel) {
        MidasAdSdk.mChannel = mChannel;
    }

    /**
     * 检测是否初始化
     */
    private static void checkInit() {
        if (!mIsInit) {
            throw new RuntimeException("MidasAdSdk should  be init");
        }
    }
}
