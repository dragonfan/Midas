package com.comm.jksdk.constant;

import android.content.Context;

public class Constants {

    /**
     * APP内 使用到广告类型
     */
    public interface AdType {
        /**
         * 穿山甲
         */
        String ChuanShanJia = "chuanshanjia";

        /**
         * 优量汇
         */
        String YouLiangHui = "youlianghui";
    }

    /**
     * 广告样式
     */
    public interface AdStyle {

        /**
         * 开屏
         */
        String SplashAd = "OPEN_ADS";

        /**
         * 大图
         */
        String BigImg = "BigImg";

        /**
         * 大图默认样式
         */
        String BigImgNormal = "BIG_IMG_NORMAL";
        /**
         * 大图标题居中样式
         */
        String BigImgCenter = "BIG_IMG_CENTER";

        /**
         * 左图右边两行文字
         */
        String LeftImgRightTwoText = "LeftImgRightTwoText";
        /**
         * 左图右边两行文字
         */
        String All = "All";
    }


    public interface SPUtils {
        /**
         * 配置信息
         */
        String CONFIG_INFO = "AD_SDK_CONFIG_INFO";
        /**
         * 第一次请求广告系统时间
         */
        String FIRST_REQUEST_AD_TIME = "AD_SDK_FIRST_REQUEST_AD_TIME";
        /**
         * bid
         */
        String BID = "AD_SDK_BID";
        /**
         * 用户激活时间
         */
        String USER_ACTIVE = "AD_SDK_USER_ACTIVE";
        /**
         * 经度
         */
        String LONGITUDE = "AD_SDK_LONGITUDE";

        /**
         * 纬度
         */
        String LATITUDE = "AD_SDK_LATITUDE";
        /**
         * 优量汇广告APPID
         */
        String YLH_APPID = "AD_SDK_YLH_APPID";
        /**
         * 穿山甲广告APPID
         */
        String CHJ_APPID = "AD_SDK_CHJ_APPID";

        /**
         * 优量汇广告APPNAME
         */
        String YLH_APPNAME = "AD_SDK_YLH_APPNAME";

        /**
         * 穿山甲广告APPNAME
         */
        String CHJ_APPNAME = "AD_SDK_CHJ_APPNAME";
    }

    /**
     * 优量汇广告APPID
     */
    public static String YLH_APPID = "";

    /**
     * 穿山甲广告APPID
     */
    public static String CHJ_APPID = "";

    /**
     * 优量汇广告APPNAME
     */
    public static String YLH_APPNAME = "";

    /**
     * 穿山甲广告APPNAME
     */
    public static String CHJ_APPNAME = "";

    /**
     * 客户端随机参数(第一次安装的时候产生)
     */
    public static int bid = -1;

    /**
     * 业务线标识
     */
    public static String productName = "";

    /**
     * 渠道
     */
    public static String marketName = "";
    /**
     * 渠道
     */
    public static String adPositionId = "";
    /**
     * 用户激活时间
     */
    public static long userActive = -1;
    /**
     * 纬度
     */
    public static String latitude = "";
    /**
     * 经度
     */
    public static String longitude = "";
    /**
     * 省份
     */
    public static String province = "";
    /**
     * 城市
     */
    public static String city = "";
    /**
     * 全局context
     */
    public static Context mContext;
}
