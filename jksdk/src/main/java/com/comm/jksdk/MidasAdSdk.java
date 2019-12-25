package com.comm.jksdk;

import android.content.Context;

import com.comm.jksdk.ad.factory.MidasAdManagerFactory;
import com.comm.jksdk.ad.listener.AdManager;
import com.comm.jksdk.config.AdsConfig;
import com.comm.jksdk.config.InitBaseConfig;
import com.comm.jksdk.config.listener.ConfigListener;
import com.comm.jksdk.http.utils.AppInfoUtils;
import com.comm.jksdk.http.utils.LogUtils;
import com.comm.jksdk.utils.StatisticUtils;

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
    public static String mUUID;
    public static boolean mIsFormal;


    /**
     * 聚合广告sdk初始化
     * @param context 上下文
     * @param appid 广告业务线id 大数据提供
     * @param csjAppId
     * @param uuid 设备唯一标识
     * @param isFormal 是否是正式环境 true对应生产环境
     * @param productId 业务线id 大数据提供 (初始化牛数和初始化穿山甲sdk用到)
     * @param channel 渠道名称 (初始化牛数用到)
     */
    public static void init(Context context, String appid, String productId, String channel, String csjAppId, String uuid, boolean isFormal){
        mContext = context.getApplicationContext();
        mRroductId = productId;
        mAppId = appid;
        mUUID = uuid;
        mChannel = channel;
        mIsFormal = isFormal;
        //初始化基本配置信息
        InitBaseConfig.getInstance().init(mContext);
        InitBaseConfig.getInstance().initChjAd(mContext, csjAppId);
        mIsInit = true;
        int appVersionCode = AppInfoUtils.getVerCode(MidasAdSdk.getContext());
        LogUtils.e("appVersionCode=="+appVersionCode);
        //初始化牛数
        StatisticUtils.init(context, channel, productId);
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
    public static void setImei(String imei){

        // TODO: 2019/12/23 下面设置imei给牛数
        StatisticUtils.setImei(imei);
    }


    /**
     * 设置第一次激活时间
     */
    public static void setActivationTime(long time){
        AdsConfig.setUserActive(time);
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
