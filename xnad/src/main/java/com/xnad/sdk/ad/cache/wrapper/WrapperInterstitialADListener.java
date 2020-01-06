package com.xnad.sdk.ad.cache.wrapper;

import android.content.Context;

import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.interstitial2.UnifiedInterstitialADListener;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;
import com.xnad.sdk.ad.cache.ADTool;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.entity.MidasAd;
import com.xnad.sdk.ad.entity.MidasInteractionAd;
import com.xnad.sdk.ad.listener.AdRequestListener;
import com.xnad.sdk.ad.outlistener.AdInteractionListener;
import com.xnad.sdk.utils.AppUtils;

import java.util.List;

/**
 * Desc: 优量汇插屏广告监听
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
public class WrapperInterstitialADListener implements UnifiedInterstitialADListener {
    /**
     * 加载监听
     */
    AdRequestListener loadListener;
    /**
     * 对外的监听
     */
    AdInteractionListener outListener;
    /**
     * 广告信息
     */
    AdInfo info;
    /**
     * 是否曝光
     */
    boolean isExposed = false;

    //插屏2.0广告加载完毕，此回调后才可以调用 show 方法
    @Override
    public void onADReceive() {
        //请求成功回调
        if (loadListener != null) {
            loadListener.adSuccess(info);
        }
        //资源加载到
        if (!isExposed && loadListener != null) {
            loadListener.adLoad(info);
        }
        if (loadListener==null||loadListener.adShow(info)) {
            UnifiedInterstitialAD iad = ((MidasInteractionAd) info.getMidasAd()).getUnifiedInterstitialAD();
            //广告加载成功
            if (iad != null) {
                iad.close();
                iad.showAsPopupWindow();
            }
            if (outListener != null) {
                outListener.adSuccess(info);
            }
        }else{
            //添加到缓存
            ADTool.getInstance().cacheAd(this, info);
        }
    }

    @Override
    public void onNoAD(AdError adError) {
        if (loadListener != null) {
            loadListener.adError(info, adError.getErrorCode(), adError.getErrorMsg());
        }
    }

    @Override
    public void onVideoCached() {

    }

    /**
     * 插屏2.0广告展开时回调
     */
    @Override
    public void onADOpened() {

    }

    //插屏2.0广告曝光时回调
    @Override
    public void onADExposure() {
        if (outListener != null) {
            outListener.adExposed(info);
        }

        if (!isExposed) {
            isExposed = true;
            AppUtils.getAdCount(info.getMidasAd().getAdId());
        }
    }

    //插屏2.0广告点击时回调
    @Override
    public void onADClicked() {
        if (outListener != null) {
            outListener.adClicked(info);
        }
    }

    //插屏2.0广告点击离开应用时回调
    @Override
    public void onADLeftApplication() {

    }

    //插屏2.0广告关闭时回调
    @Override
    public void onADClosed() {
        if (outListener != null) {
            outListener.adClose(info);
        }
    }

    public void setLoadListener(AdRequestListener listener) {
        loadListener = listener;

    }

    public void setOutListener(AdInteractionListener listener) {
        //防止内存泄漏
        if (loadListener != null && outListener != null) {
            loadListener = null;
            outListener = null;
        }

        outListener = listener;
    }

    public void setAdInfo(AdInfo info) {
        this.info = info;
    }
}
