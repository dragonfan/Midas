package com.xnad.sdk.ad.cache.wrapper;

import com.qq.e.ads.nativ.NativeADUnifiedListener;
import com.qq.e.ads.nativ.NativeUnifiedADData;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;
import com.xnad.sdk.ad.cache.ADTool;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.entity.MidasSelfRenderAd;
import com.xnad.sdk.ad.listener.AdRequestListener;
import com.xnad.sdk.ad.outlistener.AdSelfRenderListener;
import com.xnad.sdk.ad.outlistener.AdSplashListener;
import com.xnad.sdk.utils.LogUtils;

import java.util.List;

/**
 * Desc: 优量汇 自渲染 广告 监听
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
public class WrapperSelfRenderAdListener implements NativeADUnifiedListener {
    /**
     * 加载监听
     */
    AdRequestListener adRequestListener;
    /**
     * 对外的监听
     */
    AdSelfRenderListener outListener;
    /**
     * 广告信息
     */
    AdInfo adInfo;
    @Override
    public void onADLoaded(List<NativeUnifiedADData> list) {
        if (list == null || list.size() == 0) {
            if (adRequestListener != null) {
                adRequestListener.adError(adInfo, 3, "没广告");
            }
            return;
        }
        NativeUnifiedADData nativeUnifiedADData = list.get(0);
        if (nativeUnifiedADData == null) {
            if (adRequestListener != null) {
                adRequestListener.adError(adInfo, 3, "没广告");
            }
            return;
        }
        MidasSelfRenderAd midasSelfRenderAd = (MidasSelfRenderAd) adInfo.getMidasAd();
        midasSelfRenderAd.setNativeUnifiedADData(nativeUnifiedADData);

        //如果需要显示才回调监听,否则直接缓存起来
        if (adRequestListener.adShow(adInfo)) {
            if (outListener != null) {
                outListener.adSuccess(adInfo);
            }
        }else{
            //添加到缓存
            ADTool.getInstance().cacheAd(this,adInfo);
        }


        //请求成功回调
        if (adRequestListener != null) {
            adRequestListener.adSuccess(adInfo);
        }


    }

    @Override
    public void onNoAD(AdError adError) {
        if (adRequestListener != null) {
            adRequestListener.adError(adInfo, adError.getErrorCode(), adError.getErrorMsg());
        }
    }


    public void setLoadListener(AdRequestListener listener) {
        adRequestListener = listener;

    }

    public void setOutListener(AdSelfRenderListener listener) {
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
