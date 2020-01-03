package com.xnad.sdk.ad.cache.wrapper;

import com.qq.e.ads.nativ.NativeADUnifiedListener;
import com.qq.e.ads.nativ.NativeUnifiedADData;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;
import com.qq.e.comm.util.AdError;
import com.xnad.sdk.ad.cache.ADTool;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.entity.MidasRewardVideoAd;
import com.xnad.sdk.ad.entity.MidasSelfRenderAd;
import com.xnad.sdk.ad.listener.AdRequestListener;
import com.xnad.sdk.ad.outlistener.AdRewardVideoListener;
import com.xnad.sdk.ad.outlistener.AdSelfRenderListener;

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
public class WrapperRewardVideoAdListener implements RewardVideoADListener {
    /**
     * 加载监听
     */
    AdRequestListener adRequestListener;
    /**
     * 对外的监听
     */
    AdRewardVideoListener outListener;
    /**
     * 广告信息
     */
    AdInfo adInfo;



    //广告加载成功，可在此回调后进行广告展示，此时广告过期时间确定，可通过RewardVideoAD.getExpireTimestamp()获取
    @Override
    public void onADLoad() {
        //请求成功回调
        if (adRequestListener != null) {
            adRequestListener.adSuccess(adInfo);
        }
        //添加到缓存
        ADTool.getInstance().cacheAd(this, adInfo);
        MidasRewardVideoAd midasRewardVideoAd = (MidasRewardVideoAd) adInfo.getMidasAd();
        RewardVideoAD rewardVideoAD = midasRewardVideoAd.getRewardVideoAD();
        //广告加载成功标志
        rewardVideoAD.showAD();
        if (outListener != null) {
            outListener.adSuccess(adInfo);
        }
    }

    @Override
    public void onVideoCached() {
        //视频素材缓存成功，可在此回调后进行广告展示

    }

    //激励视频广告页面展示，此后RewardVideoAD.hasShown()返回true
    @Override
    public void onADShow() {

    }

    //激励视频广告曝光
    @Override
    public void onADExpose() {
        if (outListener != null) {
            outListener.adExposed(adInfo);
        }
    }

    //激励视频广告激励发放
    @Override
    public void onReward() {
        if (outListener != null) {
            MidasRewardVideoAd midasRewardVideoAd = (MidasRewardVideoAd) adInfo.getMidasAd();
            int rewardAmount = midasRewardVideoAd.getRewardAmount();
            String rewardName = midasRewardVideoAd.getRewardName();
            RewardVideoAD rewardVideoAD = midasRewardVideoAd.getRewardVideoAD();
            outListener.onVideoRewardVerify(adInfo, true, rewardAmount, rewardName);
        }
    }

    @Override
    public void onADClick() {
        if (outListener != null) {
            outListener.adClicked(adInfo);
        }
    }

    //广告视频素材播放完毕
    @Override
    public void onVideoComplete() {
        if (outListener != null) {
            outListener.onVideoComplete(adInfo);
        }
    }

    @Override
    public void onADClose() {
        if (outListener != null) {
            outListener.adClose(adInfo);
        }
    }

    @Override
    public void onError(AdError adError) {
        if (outListener != null) {
            outListener.adError(adInfo, adError.getErrorCode(), adError.getErrorMsg());
        }
    }



    public void setLoadListener(AdRequestListener listener) {
        adRequestListener = listener;

    }

    public void setOutListener(AdRewardVideoListener listener) {
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
