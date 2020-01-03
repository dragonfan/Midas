package com.xnad.sdk.utils;

import android.app.Activity;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTBannerAd;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.qq.e.ads.banner2.UnifiedBannerView;
import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.splash.SplashAD;
import com.xnad.sdk.ad.cache.ADTool;
import com.xnad.sdk.ad.cache.AdContainerWrapper;
import com.xnad.sdk.ad.cache.wrapper.WrapperBannerADListener;
import com.xnad.sdk.ad.cache.wrapper.WrapperInterstitialADListener;
import com.xnad.sdk.ad.cache.wrapper.WrapperNativeTemplateAdListener;
import com.xnad.sdk.ad.cache.wrapper.WrapperRewardVideoAdListener;
import com.xnad.sdk.ad.cache.wrapper.WrapperSplashADListener;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.entity.MidasBannerAd;
import com.xnad.sdk.ad.entity.MidasFullScreenVideoAd;
import com.xnad.sdk.ad.entity.MidasInteractionAd;
import com.xnad.sdk.ad.entity.MidasNativeTemplateAd;
import com.xnad.sdk.ad.entity.MidasRewardVideoAd;
import com.xnad.sdk.ad.entity.MidasSplashAd;
import com.xnad.sdk.ad.listener.AdBasicListener;
import com.xnad.sdk.ad.outlistener.AdBannerListener;
import com.xnad.sdk.ad.outlistener.AdFullScreenVideoListener;
import com.xnad.sdk.ad.outlistener.AdInteractionListener;
import com.xnad.sdk.ad.outlistener.AdNativeTemplateListener;
import com.xnad.sdk.ad.outlistener.AdOutChargeListener;
import com.xnad.sdk.ad.outlistener.AdRewardVideoListener;
import com.xnad.sdk.ad.outlistener.AdSplashListener;
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
    private static final String TAG = "ListenerUtils";

    /**
     * 设置插屏广告监听
     *
     * @param activity
     * @param adContainerWrapper
     * @param adListener
     */
    public static void setListenerAndShow(Activity activity, AdContainerWrapper adContainerWrapper, AdBasicListener adListener) {
        AdInfo info = adContainerWrapper.getAdInfo();
        if (info.getMidasAd() == null) {
            return;
        }
        //判断广告类型
        //是插屏广告
        if (TextUtils.equals(info.getAdType(), Constants.AdType.INTERACTION_TYPE)) {
            if (info.getMidasAd() instanceof MidasInteractionAd) {
                MidasInteractionAd ad = (MidasInteractionAd) info.getMidasAd();
                //判断广告渠道
                if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.ChuanShanJia)) {
                    TTNativeExpressAd csjAd = ad.getTtNativeExpressAd();
                    setCsjInteractionListener(activity, info, csjAd, (AdInteractionListener) adListener);
                } else if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.YouLiangHui)) {
                    UnifiedInterstitialAD ylhAD = ad.getUnifiedInterstitialAD();
                    setYlhInteractionListener(activity, adContainerWrapper, ylhAD, (AdInteractionListener) adListener);
                }
            }
            //自渲染广告
        } else if (TextUtils.equals(info.getAdType(), Constants.AdType.SELF_RENDER)) {
            if (adListener != null) {
                adListener.adSuccess(info);
            }
            //开屏广告
        } else if (TextUtils.equals(info.getAdType(), Constants.AdType.SPLASH_TYPE)) {
            if (info.getMidasAd() instanceof MidasSplashAd) {
                MidasSplashAd ad = (MidasSplashAd) info.getMidasAd();
                //判断广告渠道
                if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.ChuanShanJia)) {
                    TTSplashAd csjAd = ad.getTtSplashAd();
                    setCsjSplashListener(info, csjAd, (AdSplashListener) adListener);
                } else if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.YouLiangHui)) {
                    SplashAD ylhAd = ad.getSplashAD();
                    setYlhSplashListener(adContainerWrapper, ylhAd, (AdSplashListener) adListener);
                }
            }
            //激励视频
        } else if (TextUtils.equals(info.getAdType(), Constants.AdType.REWARD_VIDEO_TYPE)) {
            if (info.getMidasAd() instanceof MidasRewardVideoAd) {
                MidasRewardVideoAd ad = (MidasRewardVideoAd) info.getMidasAd();
                //判断广告渠道
                if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.ChuanShanJia)) {
                    TTRewardVideoAd csjAd = ad.getTtRewardVideoAd();
                    setCsjRewardVideoListener(activity, info, csjAd, (AdRewardVideoListener) adListener);
                } else if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.YouLiangHui)) {
                    RewardVideoAD ylhAd = ad.getRewardVideoAD();
                    setYlhRewardVideoListener(adContainerWrapper, ylhAd, (AdRewardVideoListener) adListener);
                }
            }
            //全屏 视频
        } else if (TextUtils.equals(info.getAdType(), Constants.AdType.FULL_SCREEN_VIDEO_TYPE)) {
            if (info.getMidasAd() instanceof MidasFullScreenVideoAd) {
                MidasFullScreenVideoAd ad = (MidasFullScreenVideoAd) info.getMidasAd();
                //判断广告渠道
                if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.ChuanShanJia)) {
                    TTFullScreenVideoAd csjAd = ad.getTtFullScreenVideoAd();
                    setCsjFullVideoListener(activity, info, csjAd, (AdFullScreenVideoListener) adListener);
                } else if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.YouLiangHui)) {
//                    if (listener != null) {
//                        listener.adError(info, 3, "优量汇暂不支持全屏视频");
//                    }
                }
            }
            //本地模板
        } else if (TextUtils.equals(info.getAdType(), Constants.AdType.NATIVE_TEMPLATE)) {
            if (info.getMidasAd() instanceof MidasNativeTemplateAd) {
                MidasNativeTemplateAd ad = (MidasNativeTemplateAd) info.getMidasAd();
                //判断广告渠道
                if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.ChuanShanJia)) {
                    TTNativeExpressAd csjAd = ad.getTtNativeExpressAd();
                    setCsjNativeTemplateFullVideoListener(info, csjAd, (AdNativeTemplateListener) adListener);
                } else if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.YouLiangHui)) {
                    NativeExpressAD ylhAd = ad.getNativeExpressAD();
                    setYlhNativeTemplateFullVideoListener(adContainerWrapper, ylhAd, (AdNativeTemplateListener) adListener);
                }
            }
        //Banner 广告
        } else if (TextUtils.equals(info.getAdType(), Constants.AdType.BANNER_TYPE)) {
            if (info.getMidasAd() instanceof MidasBannerAd) {
                MidasBannerAd ad = (MidasBannerAd) info.getMidasAd();
                //判断广告渠道
                if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.ChuanShanJia)) {
                    TTBannerAd csjAd = ad.getTTBannerAd();
                    setCsjBannerListener(info, csjAd, (AdBannerListener) adListener);
                } else if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.YouLiangHui)) {
                    UnifiedBannerView ylhAd = ad.getUnifiedBannerView();
                    setYlhBannerFullVideoListener(adContainerWrapper, ylhAd, (AdBannerListener) adListener);
                }
            }

        }
    }

    private static void setYlhBannerFullVideoListener(AdContainerWrapper adContainer, UnifiedBannerView ylhAd, AdBannerListener adListener) {
        //设置自定义监听
        if (adContainer.getListener() != null) {
            WrapperBannerADListener listener = (WrapperBannerADListener) adContainer.getListener();
            listener.setOutListener(adListener);
        }
        ylhAd.loadAD();
    }

    private static void setCsjBannerListener(AdInfo adInfo, TTBannerAd csjAd, AdBannerListener adBannerListener) {
        if (adBannerListener != null){
            adBannerListener.adSuccess(adInfo);
        }
        ViewGroup viewContainer = adInfo.getAdParameter().getViewContainer();

        View bannerView = csjAd.getBannerView();

        Point screenSize = new Point();
        adInfo.getAdParameter().getActivity().getWindowManager().
                getDefaultDisplay().getSize(screenSize);

        //设置轮播的时间间隔  间隔在30s到120秒之间的值，不设置默认不轮播
        csjAd.setSlideIntervalTime(30 * 1000);
        viewContainer.removeAllViews();
        viewContainer.addView(bannerView,new ViewGroup.
                LayoutParams(screenSize.x,  Math.round(screenSize.x / 6.4F)));
        //设置广告互动监听回调
        csjAd.setBannerInteractionListener(new TTBannerAd.AdInteractionListener() {
            @Override
            public void onAdClicked(View view, int type) {
                if (adBannerListener != null){
                    adBannerListener.onAdClicked(adInfo);
                }
            }
            @Override
            public void onAdShow(View view, int type) {
                if (adBannerListener != null){
                    adBannerListener.onAdShow(adInfo);
                }
            }
        });
        //（可选）设置下载类广告的下载监听
        AdUtils.bindBannerDownloadLinstener(csjAd);
        //在banner中显示网盟提供的dislike icon，有助于广告投放精准度提升
        csjAd.setShowDislikeIcon(new TTAdDislike.DislikeInteractionCallback() {
            @Override
            public void onSelected(int position, String value) {
                //用户选择不喜欢原因后，移除广告展示
                viewContainer.removeAllViews();
                if (adBannerListener != null){
                    adBannerListener.adClose(adInfo);
                }
            }
            @Override
            public void onCancel() {
            }
        });
    }

    private static void setYlhNativeTemplateFullVideoListener(AdContainerWrapper adContainer, NativeExpressAD ylhAd, AdNativeTemplateListener adListener) {
        //设置自定义监听
        if (adContainer.getListener() != null) {
            WrapperNativeTemplateAdListener listener = (WrapperNativeTemplateAdListener) adContainer.getListener();
            listener.setOutListener(adListener);
        }
        //  显示激励视频广告
        ylhAd.loadAD(1);
    }

    private static void setCsjNativeTemplateFullVideoListener(AdInfo info, TTNativeExpressAd ttNativeAd, AdNativeTemplateListener adListener) {
        AdOutChargeListener adOutChargeListener = getNativeTemplateAdChargeListener();
        ttNativeAd.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
            @Override
            public void onAdClicked(View view, int type) {
                if (adOutChargeListener != null) {
                    adOutChargeListener.adClicked(info);
                }
            }

            @Override
            public void onAdShow(View view, int type) {
                if (adOutChargeListener != null) {
                    adOutChargeListener.adExposed(info);
                }
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
//                Log.e("ExpressView","render fail:"+(System.currentTimeMillis() - startTime));
                if (adOutChargeListener != null) {
                    adOutChargeListener.adError(info, code, msg);
                }
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                MidasNativeTemplateAd midasNativeTemplateAd = (MidasNativeTemplateAd) info.getMidasAd();
                midasNativeTemplateAd.setAddView(view);
                if (adOutChargeListener != null) {
                    adOutChargeListener.adSuccess(info);
                }
            }
        });
        //dislike设置
//        bindDislike(ttNativeExpressAd, false);
        if (ttNativeAd.getInteractionType() == TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
            ttNativeAd.setDownloadListener(new TTAppDownloadListener() {
                @Override
                public void onIdle() {
//                TToast.show(NativeExpressActivity.this, "点击开始下载", Toast.LENGTH_LONG);
                }

                @Override
                public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
//                if (!mHasShowDownloadActive) {
//                    mHasShowDownloadActive = true;
//                    TToast.show(NativeExpressActivity.this, "下载中，点击暂停", Toast.LENGTH_LONG);
//                }
                }

                @Override
                public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
//                TToast.show(NativeExpressActivity.this, "下载暂停，点击继续", Toast.LENGTH_LONG);
                }

                @Override
                public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
//                TToast.show(NativeExpressActivity.this, "下载失败，点击重新下载", Toast.LENGTH_LONG);
                }

                @Override
                public void onInstalled(String fileName, String appName) {
//                TToast.show(NativeExpressActivity.this, "安装完成，点击图片打开", Toast.LENGTH_LONG);
                }

                @Override
                public void onDownloadFinished(long totalBytes, String fileName, String appName) {
//                TToast.show(NativeExpressActivity.this, "点击安装", Toast.LENGTH_LONG);
                }
            });
        }

        if (adListener != null) {
            adListener.adSuccess(info);
        }
    }


    private static void setCsjFullVideoListener(Activity activity, AdInfo info, TTFullScreenVideoAd csjAd, AdFullScreenVideoListener adListener) {
        csjAd.setFullScreenVideoAdInteractionListener(new TTFullScreenVideoAd.FullScreenVideoAdInteractionListener() {

            @Override
            public void onAdShow() {
                if (adListener != null) {
                    adListener.adExposed(info);
                }
            }

            //广告下载bar点击回调
            @Override
            public void onAdVideoBarClick() {
                if (adListener != null) {
                    adListener.adClicked(info);
                }
            }

            @Override
            public void onAdClose() {
                if (adListener != null) {
                    adListener.adClose(info);
                }
            }

            //广告播放完成回调
            @Override
            public void onVideoComplete() {
                if (adListener != null) {
                    adListener.adVideoComplete(info);
                }
            }

            //广告跳过视频播放回调
            @Override
            public void onSkippedVideo() {
                if (adListener != null) {
                    adListener.adSkippedVideo(info);
                }
            }
        });

        //添加到缓存
        ADTool.getInstance().cacheAd(csjAd, info);
        //step6:在获取到广告后展示
        csjAd.showFullScreenVideoAd(activity);
        if (adListener != null) {
            adListener.adSuccess(info);
        }

    }

    private static void setYlhRewardVideoListener(AdContainerWrapper adContainer, RewardVideoAD midasRewardVideoAd, AdRewardVideoListener adListener) {
        //设置自定义监听
        if (adContainer.getListener() != null) {
            WrapperRewardVideoAdListener listener = (WrapperRewardVideoAdListener) adContainer.getListener();
            listener.setOutListener(adListener);
        }
        //  显示激励视频广告
        midasRewardVideoAd.showAD();
    }

    private static void setCsjRewardVideoListener(Activity activity, AdInfo adInfo, TTRewardVideoAd mttRewardVideoAd, AdRewardVideoListener adRewardVideoListener) {
        mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {
            @Override
            public void onAdShow() {
                if (adRewardVideoListener != null) {
                    adRewardVideoListener.adExposed(adInfo);
                }
            }

            @Override
            public void onAdVideoBarClick() {
                LogUtils.d(TAG, "rewardVideoAd bar click");
                if (adRewardVideoListener != null) {
                    adRewardVideoListener.adClicked(adInfo);
                }
            }

            @Override
            public void onAdClose() {
                LogUtils.d(TAG, "rewardVideoAd close");
                if (adRewardVideoListener != null) {
                    adRewardVideoListener.adClose(adInfo);
                }
            }

            //视频播放完成回调
            @Override
            public void onVideoComplete() {
                LogUtils.d(TAG, "rewardVideoAd complete");
                if (adRewardVideoListener != null) {
                    adRewardVideoListener.onVideoComplete(adInfo);
                }
            }

            @Override
            public void onVideoError() {
                LogUtils.d(TAG, "rewardVideoAd error");
                if (adRewardVideoListener != null) {
                    adRewardVideoListener.adError(adInfo, 1, "rewardVideoAd error");
                }
            }

            //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
            @Override
            public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                LogUtils.d(TAG, "verify:" + rewardVerify + " amount:" + rewardAmount + " name:" + rewardName);
                if (adRewardVideoListener != null) {
                    adRewardVideoListener.onVideoRewardVerify(adInfo, rewardVerify, rewardAmount, rewardName);
                }
            }

            @Override
            public void onSkippedVideo() {

            }
        });
        mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {

            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                LogUtils.d(TAG, "下载中，点击下载区域暂停");

            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                LogUtils.d(TAG, "下载暂停，点击下载区域继续");
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                LogUtils.d(TAG, "下载失败，点击下载区域重新下载");
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                LogUtils.d(TAG, "下载完成，点击下载区域重新下载");
            }

            @Override
            public void onInstalled(String fileName, String appName) {
                LogUtils.d(TAG, "安装完成，点击下载区域打开");
            }
        });
        mttRewardVideoAd.showRewardVideoAd(activity);
        if (adRewardVideoListener != null) {
            adRewardVideoListener.adSuccess(adInfo);
        }
    }

    private static void setYlhSplashListener(AdContainerWrapper adContainer, SplashAD ylhAd, AdSplashListener adListener) {
        //设置自定义监听

        if (adContainer.getListener() != null) {
            WrapperSplashADListener listener = (WrapperSplashADListener) adContainer.getListener();
            listener.setOutListener(adListener);
        }

        if (adListener != null) {
            ViewGroup viewGroup = adListener.getViewGroup();
            if (viewGroup != null) {
                ylhAd.fetchAndShowIn(viewGroup);
            }
        }
    }

    /**
     * 设置穿山甲 开屏广告 监听
     *
     * @param adInfo
     * @param csjAd
     * @param adListener
     */
    private static void setCsjSplashListener(AdInfo adInfo, TTSplashAd csjAd, AdSplashListener adListener) {
        csjAd.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
            @Override
            public void onAdClicked(View view, int i) {
                if (adListener != null) {
                    adListener.adClicked(adInfo);
                }
            }

            @Override
            public void onAdShow(View view, int i) {
                if (adListener != null) {
                    adListener.adExposed(adInfo);
                }
            }

            //开屏广告跳过
            @Override
            public void onAdSkip() {
                if (adListener != null) {
                    adListener.adClose(adInfo);
                }
            }

            //倒计时结束
            @Override
            public void onAdTimeOver() {
                if (adListener != null) {
                    adListener.adClose(adInfo);
                }
            }
        });

        if (adListener != null) {
            adListener.adSuccess(adInfo);
        }
    }

    /**
     * 设置穿山甲 插屏广告 监听
     *
     * @param activity
     * @param adInfo
     * @param csjAd
     * @param adListener
     */
    private static void setCsjInteractionListener(Activity activity, AdInfo adInfo, TTNativeExpressAd csjAd, AdInteractionListener adListener) {
        //设置监听
        csjAd.setExpressInteractionListener(new TTNativeExpressAd.AdInteractionListener() {
            @Override
            public void onAdClicked(View view, int i) {
                if (adListener != null) {
                    adListener.adClicked(adInfo);
                }
            }

            @Override
            public void onAdShow(View view, int i) {
                if (adListener != null) {
                    adListener.adExposed(adInfo);
                }
            }

            @Override
            public void onRenderFail(View view, String s, int i) {
                if (adListener != null) {
                    adListener.adError(adInfo, i, s);
                }
            }

            @Override
            public void onRenderSuccess(View view, float v, float v1) {
                if (adListener != null) {
                    adListener.adSuccess(adInfo);
                }
                //在渲染成功回调时展示广告，提升体验
                csjAd.showInteractionExpressAd(activity);
            }

            @Override
            public void onAdDismiss() {
                if (adListener != null) {
                    adListener.adClose(adInfo);
                }
            }
        });
        csjAd.render();
    }

    /**
     * 设置优良汇 插屏 监听
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


    /**
     * 原生模板广告回调中间层（埋点可以埋到这里）
     *
     * @return
     */
    private static AdOutChargeListener getNativeTemplateAdChargeListener() {
        return new AdOutChargeListener<AdInfo>() {
            /**
             * 时间间隔
             * 记录填充到展示，展示到点击间隔
             */
            private long intervalTime = 0L;

            @Override
            public void adClose(AdInfo info) {
                if (((MidasNativeTemplateAd) info.getMidasAd()).getAdOutChargeListener() != null) {
                    ((MidasNativeTemplateAd) info.getMidasAd()).getAdOutChargeListener().adClose(info);
                }
                StatisticUtils.advertisingClose(info, intervalTime);
            }

            @Override
            public void adSuccess(AdInfo info) {
                if (((MidasNativeTemplateAd) info.getMidasAd()).getAdOutChargeListener() != null) {
                    ((MidasNativeTemplateAd) info.getMidasAd()).getAdOutChargeListener().adSuccess(info);
                }

            }

            @Override
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                if (((MidasNativeTemplateAd) info.getMidasAd()).getAdOutChargeListener() != null) {
                    ((MidasNativeTemplateAd) info.getMidasAd()).getAdOutChargeListener().adError(info, errorCode, errorMsg);
                }
            }

            @Override
            public void adExposed(AdInfo info) {
                if (((MidasNativeTemplateAd) info.getMidasAd()).getAdOutChargeListener() != null) {
                    ((MidasNativeTemplateAd) info.getMidasAd()).getAdOutChargeListener().adExposed(info);
                }
                advertisingOfferShow(info);
            }

            @Override
            public void adClicked(AdInfo info) {
                if (((MidasNativeTemplateAd) info.getMidasAd()).getAdOutChargeListener() != null) {
                    ((MidasNativeTemplateAd) info.getMidasAd()).getAdOutChargeListener().adClicked(info);
                }
                StatisticUtils.advertisingClick(info, intervalTime);
            }

            /**
             * 广告offer展示
             * @param adInfo    广告信息
             */
            private void advertisingOfferShow(AdInfo adInfo) {
                StatisticUtils.advertisingOfferShow(adInfo, 0);
                intervalTime = System.currentTimeMillis();
            }
        };
    }

}