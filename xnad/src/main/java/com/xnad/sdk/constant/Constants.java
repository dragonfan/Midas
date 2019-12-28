package com.xnad.sdk.constant;

public class Constants {

    /**
     * 广告类型
     */
    public interface AdType {
        /**
         * 开屏广告类型
         */
        String SPLASH_TYPE = "0";
        /**
         * 插屏广告类型
         */
        String INTERACTION_TYPE = "2";
        /**
         * 全屏视频广告类型
         */
        String FULL_SCREEN_VIDEO_TYPE = "3";
        /**
         * 激励视频广告类型
         */
        String REWARD_VIDEO_TYPE = "4";
        /**
         * 原生模板广告类型
         */
        String NATIVE_TEMPLATE = "5";
        /**
         * 自渲染广告
         */
        String SELF_RENDER = "6";
        /**
         * banner广告类型
         */
        String BANNER_TYPE = "BANNER_TYPE";
        /**
         * 原生广告类型
         */
        String NATIVE_TYPE = "NATIVE_TYPE";
        /**
         * 信息流广告类型
         */
        String FEED_TYPE = "FEED_TYPE";
        /**
         * 竖版视频广告类型
         */
        String DRAW_VIDEO = "DRAW_VIDEO";
    }
    /**
     * APP内 使用到广告源
     */
    public interface AdSourceType {
        /**
         * 穿山甲
         */
        String ChuanShanJia = "chuanshanjia";

        /**
         * 优量汇
         */
        String YouLiangHui = "youlianghui";
    }

    public interface SPUtils {
        /**
         * midas广告前缀
         */
        String MIDAS_PREFIX = "MIDAS_";
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
     * sdk版本code
     */
    public static int version_code = 1;


    /**
     * 一个项目只能上报两次IMEI，第一次上报为空，第二次上报为真是IMEI
     * 首次上报IMEI
     */
    public static final String FIRST_REPORT_IMEI = "first_report_imei";
    /**
     * 第二次上报IMEI
     */
    public static final String AGAIN_REPORT_IMEI = "again_report_imei";
}