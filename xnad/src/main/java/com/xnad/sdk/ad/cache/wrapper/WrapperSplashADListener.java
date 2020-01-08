package com.xnad.sdk.ad.cache.wrapper;

import android.view.ViewGroup;

import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.interstitial2.UnifiedInterstitialADListener;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;
import com.xnad.sdk.ad.cache.ADTool;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.entity.MidasInteractionAd;
import com.xnad.sdk.ad.entity.MidasSplashAd;
import com.xnad.sdk.ad.listener.AdRequestListener;
import com.xnad.sdk.ad.outlistener.AdInteractionListener;
import com.xnad.sdk.ad.outlistener.AdSplashListener;
import com.xnad.sdk.utils.AppUtils;
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
public class WrapperSplashADListener implements SplashADListener {
    /**
     * 加载监听
     */
    AdRequestListener adRequestListener;
    /**
     * 对外的监听
     */
    AdSplashListener outListener;
    /**
     * 广告信息
     */
    AdInfo adInfo;

    /**
     * 是否曝光过
     */
    boolean isADExposure;

    @Override
    public void onADDismissed() {

        LogUtils.d( "YLH onADDismissed:");
        if (outListener != null) {
            outListener.adClose(adInfo);
        }
    }

    @Override
    public void onNoAD(AdError adError) {
        LogUtils.d( "YLH onNoAD:");

        //添加到缓存
        ADTool.getInstance().remove( adInfo);
        //优量汇广告加载失败
        if (adRequestListener != null) {
            adRequestListener.adError(adInfo, adError.getErrorCode(), adError.getErrorMsg());
        }
    }

    //广告成功展示时调用，成功展示不等于有效展示（比如广告容器高度不够）
    @Override
    public void onADPresent() {
        //优量汇成功展示的时候代表请求成功
        //请求成功回调
        if (adRequestListener != null) {
            adRequestListener.adSuccess(adInfo);
        }

        if (outListener != null) {
            outListener.adSuccess(adInfo);
        }

    }

    @Override
    public void onADClicked() {
        LogUtils.d("YLH onADClicked:");
        if (outListener != null) {
            outListener.adClicked(adInfo);
        }
    }

    //倒计时回调，返回广告还将被展示的剩余时间，单位是 ms
    @Override
    public void onADTick(long l) {

    }

    //广告曝光时调用，此处的曝光不等于有效曝光（如展示时长未满足）
    @Override
    public void onADExposure() {
        LogUtils.d( "YLH onADClicked:");
        if (outListener != null) {
            outListener.adExposed(adInfo);
        }
        if (!isADExposure) {
            isADExposure = true;
            //缓存展示次数
            AppUtils.putAdCount(adInfo.getMidasAd().getAdId());
        }

    }



    public void setLoadListener(AdRequestListener listener) {
        adRequestListener = listener;

    }

    public void setOutListener(AdSplashListener listener) {
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
