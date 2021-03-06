package com.xnad.sdk.config;

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
         * banner广告
         */
        String BANNER_TYPE = "1";
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

    /**
     * 本地存储常量
     */
    public interface SpUtils {
        /**
         * midas广告前缀
         */
        String MIDAS_PREFIX = "MIDAS_";
        /**
         * 一个项目只能上报两次IMEI，第一次上报为空，第二次上报为真是IMEI
         * 首次上报IMEI
         */
        String FIRST_REPORT_IMEI = "first_report_imei";
        /**
         * 第二次上报IMEI
         */
        String AGAIN_REPORT_IMEI = "again_report_imei";
        /**
         * xnId存储
         */
        String XN_ID = "xn_id";
        /**
         * 大数据标识老用户
         */
        String BIG_DATA_MARK_OLD_USER = "big_data_mark_old_user";
    }


}
