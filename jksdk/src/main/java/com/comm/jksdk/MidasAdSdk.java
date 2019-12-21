package com.comm.jksdk;

import android.content.Context;

import com.comm.jksdk.ad.factory.MidasAdManagerFactory;
import com.comm.jksdk.ad.listener.AdManager;
import com.comm.jksdk.config.AdsConfig;
import com.comm.jksdk.config.InitBaseConfig;
import com.comm.jksdk.config.listener.ConfigListener;

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
    public static String mChannel;
    public static String mUUID;
    public static boolean mIsFormal;

//    /**
//     * 聚合广告sdk初始化
//     * @param context 上下文
//     * @param productName 业务线名称 12：日历；13：即刻天气 18：悟空清理
//     * @param channel 渠道名称
//     * @param isFormal 是否是正式环境 true对应生产环境
//     */
//    public static void init(Context context, String productName, String csjAppId, String channel, boolean isFormal){
//        mContext = context.getApplicationContext();
//        mRroductName = productName;
//        mChannel = channel;
//        mIsFormal = isFormal;
//        //初始化基本配置信息
//        InitBaseConfig.getInstance().init(mContext);
//        InitBaseConfig.getInstance().initChjAd(mContext, csjAppId);
//        AdsConfig.setProductName(mRroductName);
//        mIsInit = true;
//    }

    /**
     * 聚合广告sdk初始化
     * @param context 上下文
     * @param appid 业务线id 大数据提供
     * @param csjAppId 业务线唯一标识 大数据提供
     * @param uuid 设备唯一标识
     * @param isFormal 是否是正式环境 true对应生产环境
     */
    public static void init(Context context, String appid, String csjAppId, String uuid, boolean isFormal){
        mContext = context.getApplicationContext();
        mRroductId = appid;
        mUUID = uuid;
        mIsFormal = isFormal;
        //初始化基本配置信息
        InitBaseConfig.getInstance().init(mContext);
        InitBaseConfig.getInstance().initChjAd(mContext, csjAppId);
        AdsConfig.setProductName(mRroductName);
        mIsInit = true;
    }

    /**
     * 请求广告配置信息
     * @param listener 回调
     */
    public static void requestConfig(ConfigListener listener){
        checkInit();
//        AdsConfig.getInstance(mContext).requestConfig(listener);
    }

    public static void requestConfig(){
        checkInit();
//        AdsConfig.getInstance(mContext).requestConfig(null);
    }

    /**
     * 获取广告管理类
     * @return
     */
    public static AdManager getAdsManger(){
        return new MidasAdManagerFactory().produce();
    }

    /**
     * 设置经度、纬度
     * @param longitude
     * @param latitude
     */
    public static void setLocation(String longitude, String latitude){
        AdsConfig.setLongitude(longitude);
        AdsConfig.setLatitude(latitude);
    }

    /**
     * 设置bid
     * @param bid
     */
    public static void setBid(int bid){
        AdsConfig.setBid(bid);
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

    public static String getUUID() {
        return mUUID;
    }

    public static void setUUID(String mUUID) {
        MidasAdSdk.mUUID = mUUID;
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
