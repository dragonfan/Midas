package com.xnad.sdk.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.xnad.sdk.ad.cache.AdContainerWrapper;
import com.xnad.sdk.ad.cache.wrapper.WrapperInterstitialADListener;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.entity.MidasInteractionAd;
import com.xnad.sdk.ad.entity.MidasNativeTemplateAd;
import com.xnad.sdk.ad.outlistener.AdInteractionListener;
import com.xnad.sdk.ad.outlistener.AdOutChargeListener;
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
public class ListenerUtils {

    /**
     * 设置插屏广告监听
     *
     * @param activity
     * @param adContainerWrapper
     * @param adListener
     */
    public static void setListenerAndShow(Activity activity, AdContainerWrapper adContainerWrapper, AdInteractionListener adListener) {
        AdInfo info = adContainerWrapper.getAdInfo();
        if (info.getMidasAd() == null) {
            return;
        }
        //判断广告类型
        if (TextUtils.equals(info.getAdType(), Constants.AdType.INTERACTION_TYPE)) {
            if (info.getMidasAd() instanceof MidasInteractionAd) {
                MidasInteractionAd interactionAd = (MidasInteractionAd) info.getMidasAd();
                if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.ChuanShanJia)) {
                    TTNativeExpressAd ttNativeExpressAd = interactionAd.getTtNativeExpressAd();
                    setCsjInteractionListener(activity, info, ttNativeExpressAd, adListener);
                } else if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.YouLiangHui)) {
                    UnifiedInterstitialAD unifiedInterstitialAD = interactionAd.getUnifiedInterstitialAD();
                    setYlhInteractionListener(activity, adContainerWrapper, unifiedInterstitialAD, adListener);
                }
            }
        }
    }

    /**
     * 设置穿山甲监听
     *
     * @param activity
     * @param info
     * @param adView
     * @param adListener
     */
    private static void setCsjInteractionListener(Activity activity, AdInfo info, TTNativeExpressAd adView, AdInteractionListener adListener) {
        //设置监听
        adView.setExpressInteractionListener(new TTNativeExpressAd.AdInteractionListener() {
            @Override
            public void onAdClicked(View view, int i) {
                if (adListener != null) {
                    adListener.adClicked(info);
                }
            }

            @Override
            public void onAdShow(View view, int i) {
                if (adListener != null) {
                    adListener.adExposed(info);
                }
            }

            @Override
            public void onRenderFail(View view, String s, int i) {
                if (adListener != null) {
                    adListener.adError(info, i, s);
                }
            }

            @Override
            public void onRenderSuccess(View view, float v, float v1) {
                if (adListener != null) {
                    adListener.adSuccess(info);
                }
                //在渲染成功回调时展示广告，提升体验
                adView.showInteractionExpressAd(activity);
            }

            @Override
            public void onAdDismiss() {
                if (adListener != null) {
                    adListener.adClose(info);
                }
            }
        });
        adView.render();
    }

    /**
     * 设置优良汇监听
     *
     * @param activity
     * @param adContainer
     * @param unifiedInterstitialAD
     * @param outListener
     */
    public static void setYlhInteractionListener(Activity activity, AdContainerWrapper adContainer, UnifiedInterstitialAD unifiedInterstitialAD, AdInteractionListener outListener) {
        if (adContainer.getListener() != null) {
            WrapperInterstitialADListener listener = (WrapperInterstitialADListener) adContainer.getListener();
            listener.setOutListener(outListener);
        }
        unifiedInterstitialAD.showAsPopupWindow(activity);
    }

}
