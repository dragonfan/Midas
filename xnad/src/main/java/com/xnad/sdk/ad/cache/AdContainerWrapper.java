package com.xnad.sdk.ad.cache;

import android.text.TextUtils;

import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.config.Constants;

/**
 * Desc:
 * <p>
 * Author: ZhouTao
 * Date: 2020/1/2
 * Copyright: Copyright (c) 2016-2020
 * Company: @小牛科技
 * Email:zhoutao@xiaoniu.com
 * Update Comments:
 *
 * @author zhoutao
 */
public class AdContainerWrapper {
    //广告渠道
    private String adChannel;
    //广告类型
    private String adType;
    /**
     * 模板广告,插屏广告
     */
    private AdInfo adInfo;
    /**
     * 开始缓存时间
     */
    private long receiveTime;
    /**
     * 有效时间
     */
    private long validTime;
    /**
     * 监听
     */
    private Object listener;

    /**
     * 添加一个广告
     *
     * @param adInfo
     * @param adSource
     * @param adType
     */
    public void addView(AdInfo adInfo, String adSource, String adType) {
        this.adInfo = adInfo;
        this.adChannel = adSource;
        this.adType = adType;
    }

    public void setReceiveTime() {
        this.receiveTime = System.currentTimeMillis();
    }

    public void setValidTime(long validTime) {
        this.validTime = 3 * 60 * 60 * 1000;
    }

    /**
     * 是否是有效的广告
     *
     * @return
     */
    public boolean isValid() {
        //当前时间 - 缓存时间 小于 有效时间 这个广告才能使用
        return (System.currentTimeMillis() - receiveTime) < validTime;
    }

    public long getReceiveTime() {
        return receiveTime;
    }

    public String getAdChannel() {
        return adChannel;
    }

    public String getAdType() {
        return adType;
    }

    public AdInfo getAdInfo() {
        return adInfo;
    }

    public void addListener(Object listener) {
        this.listener = listener;
    }

    public Object getListener() {
        return listener;
    }
}
