package com.xnad.sdk.ad.cache.wrapper;

import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;
import com.xnad.sdk.ad.cache.ADTool;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.listener.AdRequestListener;
import com.xnad.sdk.ad.outlistener.AdBannerListener;
import com.xnad.sdk.ad.outlistener.AdSplashListener;
import com.xnad.sdk.utils.LogUtils;

/**
 * Desc: 优量汇开屏广告监听
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
public class WrapperBannerADListener implements UnifiedBannerADListener {
    /**
     * 加载监听
     */
    AdRequestListener adRequestListener;
    /**
     * 对外的监听
     */
    AdBannerListener outListener;
    /**
     * 广告信息
     */
    AdInfo adInfo;

    @Override
    public void onNoAD(AdError adError) {
        if (adRequestListener != null) {
            adRequestListener.adError(adInfo, adError.getErrorCode(), adError.getErrorMsg());
        }
    }

    @Override
    public void onADReceive() {
        if (adRequestListener != null) {
            adRequestListener.adSuccess(adInfo);
        }

        if (outListener != null) {
            outListener.adSuccess(adInfo);
        }

        //添加到缓存
        ADTool.getInstance().cacheAd(this, adInfo);
    }

    @Override
    public void onADExposure() {
        if (outListener != null) {
            outListener.onAdShow(adInfo);
        }
    }

    @Override
    public void onADClosed() {
        if (outListener != null) {
            outListener.adClose(adInfo);
        }
        adInfo.getAdParameter().getViewContainer().removeAllViews();
    }

    @Override
    public void onADClicked() {
        if (outListener != null) {
            outListener.onAdClicked(adInfo);
        }
    }

    @Override
    public void onADLeftApplication() {
    }

    @Override
    public void onADOpenOverlay() {
    }

    @Override
    public void onADCloseOverlay() {
    }

    public void setLoadListener(AdRequestListener listener) {
        adRequestListener = listener;

    }

    public void setOutListener(AdBannerListener listener) {
        //防止内存泄漏
        if (adRequestListener != null && outListener != null) {
            adRequestListener = null;
            outListener = null;
        }

        outListener = listener;
    }

    public void setAdInfo(AdInfo info) {
        this.adInfo = info;
    }
}
