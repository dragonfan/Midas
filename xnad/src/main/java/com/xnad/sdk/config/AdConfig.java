package com.xnad.sdk.config;

import android.os.Build;

/**
 * Desc:
 * <p>
 * Author: zhoutao
 * Date: 2019/12/28
 * Copyright: Copyright (c) 2016-2022
 * Company: @小牛科技
 * Email:zhoutao@xiaoniu.com
 * Update Comments:
 *
 * @author zhoutao
 */
public class AdConfig {

    /**
     * 应用appId
     */
    private String mAppId;
    /**
     * App渠道
     */
    private String mChannel;
    /**
     * 是否是正式环境 true对应生产环境
     */
    private boolean mIsFormal;
    /**
     * 穿山甲 appId
     */
    private String csjAppId;
    /**
     * 牛数 id
     */
    private String mProductId;
    /**
     * 牛数埋点地址
     */
    private String serverUrl;

    /**
     * 根据Build 创建一个广告配置
     * @param build
     */
    public AdConfig(Build build) {
        super();
        mAppId = build.mAppId;
        mChannel = build.mChannel;
        mIsFormal = build.mIsFormal;
        csjAppId = build.csjAppId;
        mProductId = build.mProductId;
        serverUrl = build.serverUrl;
    }

    /**
     * 获取应用 ID
     * @return 应用 ID
     */
    public String getAppId() {
        return mAppId;
    }

    /**
     * 获取应用渠道号
     * @return 应用渠道号
     */
    public String getChannel() {
        return mChannel;
    }

    /**
     * 是否是正式环境
     * @return true对应生产环境
     */
    public boolean isIsFormal() {
        return mIsFormal;
    }

    /**
     * 获取穿山甲ID
     * @return 穿山甲ID
     */
    public String getCsjAppId() {
        return csjAppId;
    }

    /**
     * 获取产品ID
     * @return 产品ID
     */
    public String getProductId() {
        return mProductId;
    }

    /**
     * 获取服务器地址
     * @return 小牛埋点 服务器地址
     */
    public String getServerUrl() {
        return serverUrl;
    }

    public static class Build {

        /**
         * 应用appId
         */
        private String mAppId;
        /**
         * App渠道
         */
        private String mChannel;
        /**
         * 是否是正式环境 true对应生产环境
         */
        private boolean mIsFormal;
        /**
         * 穿山甲 appId
         */
        private String csjAppId;
        /**
         * 牛数 id
         */
        private String mProductId;
        /**
         * 牛数埋点地址
         */
        private String serverUrl;


        public String getAppId() {
            return mAppId;
        }

        public Build setAppId(String mAppId) {
            this.mAppId = mAppId;
            return this;
        }

        public String getChannel() {
            return mChannel;
        }

        public Build setChannel(String mChannel) {
            this.mChannel = mChannel;
            return this;
        }

        public String getCsjAppId() {
            return csjAppId;
        }

        public Build setCsjAppId(String csjAppId) {
            this.csjAppId = csjAppId;
            return this;
        }
        /**
         * 设置产品ID
         * @return 产品ID
         */
        public Build setProductId(String productId) {
            this.mProductId = productId;
            return this;
        }

        /**
         * 设置服务器地址
         * @return 小牛埋点 服务器地址
         */
        public Build setServerUrl(String serverUrl) {
            this.serverUrl = serverUrl;
            return this;
        }

        public boolean isIsFormal() {
            return mIsFormal;
        }

        public Build setIsFormal(boolean mIsFormal) {
            this.mIsFormal = mIsFormal;
            return this;
        }

        public AdConfig build(){
            return new AdConfig(this);
        }
    }
}