package com.xnad.sdk.config;

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
public class ADConfigBuild {

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

    public ADConfigBuild setAppId(String mAppId) {
        this.mAppId = mAppId;
        return this;
    }

    public String getChannel() {
        return mChannel;
    }

    public ADConfigBuild setChannel(String mChannel) {
        this.mChannel = mChannel;
        return this;
    }

    public String getProductId() {
        return mProductId;
    }

    public String getCsjAppId() {
        return csjAppId;
    }

    public ADConfigBuild setCsjAppId(String csjAppId) {
        this.csjAppId = csjAppId;
        return this;
    }

    public ADConfigBuild setProductId(String ProductId) {
        this.mProductId = mProductId;
        return this;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public ADConfigBuild setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
        return this;
    }

    public boolean isIsFormal() {
        return mIsFormal;
    }

    public ADConfigBuild setIsFormal(boolean mIsFormal) {
        this.mIsFormal = mIsFormal;
        return this;
    }
}