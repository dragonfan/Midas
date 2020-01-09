package com.xnad.sdk.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.qq.e.ads.banner2.UnifiedBannerView;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.nativ.MediaView;
import com.qq.e.ads.nativ.NativeADEventListener;
import com.qq.e.ads.nativ.NativeADMediaListener;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeUnifiedADData;
import com.qq.e.ads.nativ.widget.NativeAdContainer;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.comm.util.AdError;
import com.xnad.sdk.R;
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
import com.xnad.sdk.ad.entity.MidasSelfRenderAd;
import com.xnad.sdk.ad.entity.MidasSplashAd;
import com.xnad.sdk.ad.listener.AdBasicListener;
import com.xnad.sdk.ad.listener.AdRequestListener;
import com.xnad.sdk.ad.outlistener.AdBannerListener;
import com.xnad.sdk.ad.outlistener.AdFullScreenVideoListener;
import com.xnad.sdk.ad.outlistener.AdInteractionListener;
import com.xnad.sdk.ad.outlistener.AdNativeTemplateListener;
import com.xnad.sdk.ad.outlistener.AdRewardVideoListener;
import com.xnad.sdk.ad.outlistener.AdSelfRenderListener;
import com.xnad.sdk.ad.outlistener.AdSplashListener;
import com.xnad.sdk.config.AdParameter;
import com.xnad.sdk.config.Constants;

import java.util.ArrayList;
import java.util.List;

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
     * @param activity           上下文
     * @param adContainerWrapper 广告缓存
     * @param adRequestListener  请求监听
     * @param adListener         对外监听
     */
    public static void setListenerAndShow(Activity activity, AdContainerWrapper adContainerWrapper, AdRequestListener adRequestListener, AdBasicListener adListener) {
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
                    setCsjInteractionListener(activity, info, csjAd, getInteractionListener((AdInteractionListener) adListener));
                } else if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.YouLiangHui)) {
                    UnifiedInterstitialAD ylhAd = ad.getUnifiedInterstitialAD();
                    setYlhInteractionListener(activity, adContainerWrapper, ylhAd, getInteractionListener((AdInteractionListener) adListener));
                }
            }
            //自渲染广告
        } else if (TextUtils.equals(info.getAdType(), Constants.AdType.SELF_RENDER)) {
            AdSelfRenderListener outAdListener = (AdSelfRenderListener) adListener;
            if (outAdListener != null) {
                outAdListener.adSuccess(info);
            }

            showSelfRenderView(activity, info, ListenerUtils.getAdSelfRenderListener(outAdListener));
            //开屏广告
        } else if (TextUtils.equals(info.getAdType(), Constants.AdType.SPLASH_TYPE)) {
            if (info.getMidasAd() instanceof MidasSplashAd) {
                MidasSplashAd ad = (MidasSplashAd) info.getMidasAd();
                //判断广告渠道
                if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.ChuanShanJia)) {
                    TTSplashAd csjAd = ad.getTtSplashAd();
                    setCsjSplashListener(info, csjAd, getAdSplashListener((AdSplashListener) adListener));
                } else if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.YouLiangHui)) {
                    SplashAD ylhAd = ad.getSplashAD();
                    setYlhSplashListener(adContainerWrapper, ylhAd, getAdSplashListener((AdSplashListener) adListener));
                }
            }
            //激励视频
        } else if (TextUtils.equals(info.getAdType(), Constants.AdType.REWARD_VIDEO_TYPE)) {
            if (info.getMidasAd() instanceof MidasRewardVideoAd) {
                MidasRewardVideoAd ad = (MidasRewardVideoAd) info.getMidasAd();
                //判断广告渠道
                if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.ChuanShanJia)) {
                    TTRewardVideoAd csjAd = ad.getTtRewardVideoAd();
                    setCsjRewardVideoListener(activity, info, csjAd, getRewardVideoAdListener((AdRewardVideoListener) adListener));
                } else if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.YouLiangHui)) {
                    RewardVideoAD ylhAd = ad.getRewardVideoAD();
                    setYlhRewardVideoListener(adContainerWrapper, ylhAd, getRewardVideoAdListener((AdRewardVideoListener) adListener));
                }
            }
            //全屏 视频
        } else if (TextUtils.equals(info.getAdType(), Constants.AdType.FULL_SCREEN_VIDEO_TYPE)) {
            if (info.getMidasAd() instanceof MidasFullScreenVideoAd) {
                MidasFullScreenVideoAd ad = (MidasFullScreenVideoAd) info.getMidasAd();
                //判断广告渠道
                if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.ChuanShanJia)) {
                    TTFullScreenVideoAd csjAd = ad.getTtFullScreenVideoAd();
                    setCsjFullVideoListener(activity, info, csjAd, getFullScreenVideoAdListener((AdFullScreenVideoListener) adListener));
                }
//                else if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.YouLiangHui)) {
//                    if (listener != null) {
//                        listener.adError(info, 3, "优量汇暂不支持全屏视频");
//                    }
//                }
            }
            //本地模板
        } else if (TextUtils.equals(info.getAdType(), Constants.AdType.NATIVE_TEMPLATE)) {
            if (info.getMidasAd() instanceof MidasNativeTemplateAd) {
                MidasNativeTemplateAd ad = (MidasNativeTemplateAd) info.getMidasAd();
                //判断广告渠道
                if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.ChuanShanJia)) {
                    TTNativeExpressAd csjAd = ad.getTtNativeExpressAd();
                    setCsjNativeTemplateListener(info, csjAd, getNativeTemplateAdChargeListener((AdNativeTemplateListener) adListener));
                } else if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.YouLiangHui)) {
                    NativeExpressAD ylhAd = ad.getNativeExpressAD();
                    setYlhNativeTemplateListener(adContainerWrapper, ylhAd, getNativeTemplateAdChargeListener((AdNativeTemplateListener) adListener));
                }
            }
            //Banner 广告
        } else if (TextUtils.equals(info.getAdType(), Constants.AdType.BANNER_TYPE)) {
            if (info.getMidasAd() instanceof MidasBannerAd) {
                MidasBannerAd ad = (MidasBannerAd) info.getMidasAd();
                //判断广告渠道
                if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.ChuanShanJia)) {
                    TTNativeExpressAd csjAd = ad.getTTBannerAd();
                    setCsjBannerListener(activity, info, csjAd, getAdBannerListener((AdBannerListener) adListener));
                } else if (TextUtils.equals(info.getMidasAd().getAdSource(), Constants.AdSourceType.YouLiangHui)) {
                    UnifiedBannerView ylhAd = ad.getUnifiedBannerView();
                    setYlhBannerListener(adContainerWrapper, ylhAd, getAdBannerListener((AdBannerListener) adListener));
                }
            }

        }
    }

    /**
     * 优量汇 banner 广告
     *
     * @param adContainer 广告缓存
     * @param ylhAd       广告
     * @param adListener  广告监听
     */
    private static void setYlhBannerListener(AdContainerWrapper adContainer, UnifiedBannerView ylhAd, AdBannerListener adListener) {
        //设置自定义监听
        if (adContainer.getListener() != null) {
            WrapperBannerADListener listener = (WrapperBannerADListener) adContainer.getListener();
            listener.setOutListener(adListener);
        }
        ylhAd.loadAD();
    }

    /**
     * 穿山甲监听
     *
     * @param activity          上下文
     * @param adInfo            广告信息
     * @param ttNativeExpressAd 穿山甲广告
     * @param adBannerListener  广告监听
     */
    private static void setCsjBannerListener(Activity activity, AdInfo adInfo, TTNativeExpressAd ttNativeExpressAd, AdBannerListener adBannerListener) {


        ViewGroup viewContainer = adInfo.getAdParameter().getViewContainer();
        int screenWidth = AppUtils.getScreenWidth();
        ttNativeExpressAd.setSlideIntervalTime(30 * 1000);


        ttNativeExpressAd.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
            @Override
            public void onAdClicked(View view, int type) {
                if (adBannerListener != null) {
                    adBannerListener.onAdClicked(adInfo);
                }
            }

            @Override
            public void onAdShow(View view, int type) {
                if (adBannerListener != null) {
                    adBannerListener.onAdShow(adInfo);
                }
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                if (adBannerListener != null) {
                    adBannerListener.adError(adInfo, code, msg);
                }
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                //返回view的宽高 单位 dp
                viewContainer.removeAllViews();
                viewContainer.addView(view, new ViewGroup.
                        LayoutParams(screenWidth, Math.round(screenWidth / 6.4F)));
            }
        });
        ttNativeExpressAd.render();

        ttNativeExpressAd.setDislikeCallback(activity, new TTAdDislike.DislikeInteractionCallback() {
            @Override
            public void onSelected(int position, String value) {
                //用户选择不喜欢原因后，移除广告展示
                viewContainer.removeAllViews();
            }

            @Override
            public void onCancel() {

            }
        });
        if (ttNativeExpressAd.getInteractionType() !=
                TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
            return;
        }
        ttNativeExpressAd.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
            }

            @Override
            public void onInstalled(String fileName, String appName) {
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
            }
        });
        if (adBannerListener != null) {
            adBannerListener.adSuccess(adInfo);
        }
        //------------------------------------------------------------------------------------------------------------

    }

    /**
     * 优量汇 本地模板广告
     *
     * @param adContainer 广告容器
     * @param ylhAd       广告
     * @param adListener  监听
     */
    private static void setYlhNativeTemplateListener(AdContainerWrapper adContainer, NativeExpressAD ylhAd, AdNativeTemplateListener adListener) {
        //设置自定义监听
        if (adContainer.getListener() != null) {
            WrapperNativeTemplateAdListener listener = (WrapperNativeTemplateAdListener) adContainer.getListener();
            listener.setOutListener(adListener);
        }
        //  显示激励视频广告
        ylhAd.loadAD(1);
    }

    /**
     * 穿山甲 本地模板广告
     *
     * @param info       广告信息
     * @param ttNativeAd 广告
     * @param adListener 外部监听
     */
    private static void setCsjNativeTemplateListener(AdInfo info, TTNativeExpressAd ttNativeAd, AdNativeTemplateListener adListener) {
        ttNativeAd.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
            @Override
            public void onAdClicked(View view, int type) {
                if (adListener != null) {
                    adListener.adClicked(info);
                }
            }

            @Override
            public void onAdShow(View view, int type) {
                if (adListener != null) {
                    adListener.adExposed(info);
                }
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
//                Log.e("ExpressView","render fail:"+(System.currentTimeMillis() - startTime));
                if (adListener != null) {
                    adListener.adError(info, code, msg);
                }
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                MidasNativeTemplateAd midasNativeTemplateAd = (MidasNativeTemplateAd) info.getMidasAd();
                midasNativeTemplateAd.setAddView(view);
                ViewGroup viewContainer = info.getAdParameter().getViewContainer();
                if (viewContainer!=null) {
                    viewContainer.removeAllViews();
                    viewContainer.addView(view);
                }
                if (adListener != null) {
                    adListener.adSuccess(info);
                }
            }
        });

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
        ttNativeAd.render();
    }

    /**
     * 穿山甲 全屏广告
     *
     * @param activity   上下文
     * @param info       广告信息
     * @param csjAd      广告缓存
     * @param adListener 监听
     */
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

    /**
     * 优量汇 激励视频
     *
     * @param adContainer        激励视频广告缓存
     * @param midasRewardVideoAd 广告
     * @param adListener         外部监听
     */
    private static void setYlhRewardVideoListener(AdContainerWrapper adContainer, RewardVideoAD midasRewardVideoAd, AdRewardVideoListener adListener) {
        //设置自定义监听
        if (adContainer.getListener() != null) {
            WrapperRewardVideoAdListener listener = (WrapperRewardVideoAdListener) adContainer.getListener();
            listener.setOutListener(adListener);
        }
        //  显示激励视频广告
        midasRewardVideoAd.showAD();
    }

    /**
     * 穿山甲激励视频
     *
     * @param activity              上下文
     * @param adInfo                广告信息
     * @param mttRewardVideoAd      激励视频
     * @param adRewardVideoListener 外部监听
     */
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


    /**
     * 优量汇监听广告
     *
     * @param adContainer 广告信息
     * @param ylhAd       开屏广告
     * @param adListener  开屏对外监听
     */
    private static void setYlhSplashListener(AdContainerWrapper adContainer, SplashAD ylhAd, AdSplashListener adListener) {
        //设置自定义监听

        if (adContainer.getListener() != null) {
            WrapperSplashADListener listener = (WrapperSplashADListener) adContainer.getListener();
            listener.setOutListener(adListener);
        }

        if (adListener != null) {
            ViewGroup viewGroup = adContainer.getAdInfo().getAdParameter().getViewContainer();
            if (viewGroup != null) {
                ylhAd.fetchAndShowIn(viewGroup);
            }
        }
    }

    /**
     * 设置穿山甲 开屏广告 监听
     *
     * @param adInfo     广告信息
     * @param csjAd      穿山甲开屏广告
     * @param adListener 开屏广告对外监听
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
                try {
                    adInfo.getAdParameter().getViewContainer().removeAllViews();
                } catch (Exception e) {
                }
                if (adListener != null) {
                    adListener.adClose(adInfo);
                }

            }

            //倒计时结束
            @Override
            public void onAdTimeOver() {
                try {
                    adInfo.getAdParameter().getViewContainer().removeAllViews();
                } catch (Exception e) {
                }
                if (adListener != null) {
                    adListener.adClose(adInfo);
                }
            }
        });
        if (adListener != null) {
            adListener.adSuccess(adInfo);
        }
        //把广告添加到容器中
        adInfo.getAdParameter().getViewContainer().removeAllViews();
        adInfo.getAdParameter().getViewContainer().addView(csjAd.getSplashView());
    }


    /**
     * 设置穿山甲 插屏广告 监听
     *
     * @param activity   上下文
     * @param adInfo     广告信息
     * @param csjAd      穿山甲广告对象
     * @param adListener 对外监听
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
     * @param activity              上下文
     * @param adContainer           广告缓存对象
     * @param unifiedInterstitialAd 优量汇广告对象
     * @param outListener           对外监听
     */
    private static void setYlhInteractionListener(Activity activity, AdContainerWrapper adContainer, UnifiedInterstitialAD unifiedInterstitialAd, AdInteractionListener outListener) {
        if (adContainer.getListener() != null) {
            WrapperInterstitialADListener listener = (WrapperInterstitialADListener) adContainer.getListener();
            listener.setOutListener(outListener);
        }
        unifiedInterstitialAd.show(activity);


    }


    //#########################################################################################################################################################################################################################

    /**
     * 原生模板广告回调中间层（埋点可以埋到这里）
     *
     * @return 返回一个对外的监听
     * @param adListener
     */
    public static AdNativeTemplateListener getNativeTemplateAdChargeListener(AdNativeTemplateListener adListener) {
        return new AdNativeTemplateListener() {
            @Override
            public void adRenderSuccess(AdInfo adInfo) {
                if (adListener!=null) {
                    adListener.adRenderSuccess(adInfo);
                }
            }

            /**
             * 时间间隔
             * 记录填充到展示，展示到点击间隔
             */
            private long intervalTime = 0L;

            @Override
            public void adClose(AdInfo info) {
                if (adListener!=null) {
                    adListener.adClose(info);
                }
                StatisticUtils.advertisingClose(info, intervalTime);
            }

            @Override
            public void adSuccess(AdInfo info) {
                if (adListener!=null) {
                    adListener.adSuccess(info);
                }

                intervalTime = System.currentTimeMillis();
            }

            @Override
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                if (adListener!=null) {
                    adListener.adError(info, errorCode, errorMsg);
                }

            }

            @Override
            public void adExposed(AdInfo info) {
                if (adListener!=null) {
                    adListener.adExposed(info);
                }

                advertisingOfferShow(info);
            }

            @Override
            public void adClicked(AdInfo info) {
                if (adListener!=null) {
                    adListener.adClicked(info);
                }
                StatisticUtils.advertisingClick(info, intervalTime);
            }

            /**
             * 广告offer展示
             * @param adInfo    广告信息
             */
            private void advertisingOfferShow(AdInfo adInfo) {
                StatisticUtils.advertisingOfferShow(adInfo, intervalTime);
                intervalTime = System.currentTimeMillis();
            }
        };
    }

    /**
     * 开屏广告回调中间层（埋点可以埋到这里）
     *
     * @param listener 对外监听
     * @return 返回对外监听, 中间层用来埋点
     */
    public static AdSplashListener getAdSplashListener(AdSplashListener listener) {
        return new AdSplashListener() {
            long intervalTime = 0L;

            @Override
            public void adClose(AdInfo info) {
                if (listener != null) {
                    listener.adClose(info);
                }
                StatisticUtils.advertisingClose(info, intervalTime);
            }

            @Override
            public void adSuccess(AdInfo info) {
                if (listener != null) {
                    listener.adSuccess(info);
                }
                intervalTime = System.currentTimeMillis();
            }

            @Override
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                if (listener != null) {
                    listener.adError(info, errorCode, errorMsg);
                }
            }

            @Override
            public void adExposed(AdInfo info) {
                if (listener != null) {
                    listener.adExposed(info);
                }

                advertisingOfferShow(info);
            }

            @Override
            public void adClicked(AdInfo info) {
                if (listener != null) {
                    listener.adClicked(info);
                }
                StatisticUtils.advertisingClick(info, intervalTime);
            }


            /**
             * 广告offer展示
             * @param adInfo    广告信息
             */
            private void advertisingOfferShow(AdInfo adInfo) {
                StatisticUtils.advertisingOfferShow(adInfo, intervalTime);
                intervalTime = System.currentTimeMillis();
            }
        };
    }

    /**
     * 激励视频广告回调中间层（埋点可以埋到这里）
     *
     * @param listener 对外监听
     * @return 返回对外监听, 中间层用来埋点
     */
    public static AdRewardVideoListener getRewardVideoAdListener(AdRewardVideoListener listener) {

        return new AdRewardVideoListener() {
            long intervalTime = 0L;

            @Override
            public void adClose(AdInfo info) {
                if (listener != null) {
                    listener.adClose(info);
                }
                if (TextUtils.equals(Constants.AdType.REWARD_VIDEO_TYPE, info.getAdType())) {
                    StatisticUtils.advertisingRewardedClose(info, intervalTime);
                } else {
                    StatisticUtils.advertisingClose(info, intervalTime);
                }
            }

            @Override
            public void adSuccess(AdInfo info) {
                if (listener != null) {
                    listener.adSuccess(info);
                }
                intervalTime = System.currentTimeMillis();
            }

            @Override
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                if (listener != null) {
                    listener.adError(info, errorCode, errorMsg);
                }
            }

            @Override
            public void adExposed(AdInfo info) {
                if (listener != null) {
                    listener.adExposed(info);
                }
                advertisingOfferShow(info);
            }

            @Override
            public void adClicked(AdInfo info) {
                if (listener != null) {
                    listener.adClicked(info);
                }
                StatisticUtils.advertisingClick(info, intervalTime);
            }


            @Override
            public void onVideoRewardVerify(AdInfo info, boolean rewardVerify, int rewardAmount, String rewardName) {
                if (listener != null) {
                    listener.onVideoRewardVerify(info, rewardVerify, rewardAmount, rewardName);
                }
                if (TextUtils.equals(Constants.AdType.REWARD_VIDEO_TYPE, info.getAdType())) {
                    StatisticUtils.advertisingRewarded(info, intervalTime);
                }
            }

            @Override
            public void onVideoComplete(AdInfo info) {
                if (listener != null) {
                    listener.onVideoComplete(info);
                }
            }

            /**
             * 广告offer展示
             * @param adInfo    广告信息
             */
            private void advertisingOfferShow(AdInfo adInfo) {
                StatisticUtils.advertisingOfferShow(adInfo, intervalTime);
                intervalTime = System.currentTimeMillis();
            }
        };
    }

    /**
     * 全屏视频广告回调中间层（埋点可以埋到这里）
     *
     * @param listener 对外监听
     * @return 返回对外监听, 中间层用来埋点
     */
    public static AdFullScreenVideoListener getFullScreenVideoAdListener(AdFullScreenVideoListener listener) {
        return new AdFullScreenVideoListener() {
            long intervalTime = 0L;

            @Override
            public void adClose(AdInfo info) {
                if (listener != null) {
                    listener.adClose(info);
                }
                if (TextUtils.equals(Constants.AdType.REWARD_VIDEO_TYPE, info.getAdType())) {
                    StatisticUtils.advertisingRewardedClose(info, intervalTime);
                } else {
                    StatisticUtils.advertisingClose(info, intervalTime);
                }
            }

            @Override
            public void adSuccess(AdInfo info) {
                if (listener != null) {
                    listener.adSuccess(info);
                }
                intervalTime = System.currentTimeMillis();
            }

            @Override
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                if (listener != null) {
                    listener.adError(info, errorCode, errorMsg);
                }
            }

            @Override
            public void adExposed(AdInfo info) {
                if (listener != null) {
                    listener.adExposed(info);
                }
                advertisingOfferShow(info);
            }

            @Override
            public void adClicked(AdInfo info) {
                if (listener != null) {
                    listener.adClicked(info);
                }
                StatisticUtils.advertisingClick(info, intervalTime);
            }

            @Override
            public void adVideoComplete(AdInfo info) {
                if (listener != null) {
                    listener.adVideoComplete(info);
                }
            }

            @Override
            public void adSkippedVideo(AdInfo info) {
                if (listener != null) {
                    listener.adSkippedVideo(info);
                }
            }

            /**
             * 广告offer展示
             * @param adInfo    广告信息
             */
            private void advertisingOfferShow(AdInfo adInfo) {
                StatisticUtils.advertisingOfferShow(adInfo, intervalTime);
                intervalTime = System.currentTimeMillis();
            }
        };
    }

    /**
     * 插屏广告回调中间层（埋点可以埋到这里）
     *
     * @param listener 对外监听
     * @return 返回对外监听, 中间层用来埋点
     */
    public static AdInteractionListener getInteractionListener(AdInteractionListener listener) {
        return new AdInteractionListener() {

            /**
             * 时间间隔
             * 记录填充到展示，展示到点击间隔
             */
            private long intervalTime = 0L;

            @Override
            public void adClose(AdInfo info) {
                if (listener != null) {
                    listener.adClose(info);
                }
                StatisticUtils.advertisingClose(info, intervalTime);
            }

            @Override
            public void adSuccess(AdInfo info) {
                if (listener != null) {
                    listener.adSuccess(info);
                }
                intervalTime = System.currentTimeMillis();
            }

            @Override
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                if (listener != null) {
                    listener.adError(info, errorCode, errorMsg);
                }
            }

            @Override
            public void adExposed(AdInfo info) {
                if (listener != null) {
                    listener.adExposed(info);
                }
                advertisingOfferShow(info);
            }

            @Override
            public void adClicked(AdInfo info) {
                if (listener != null) {
                    listener.adClicked(info);
                }
                StatisticUtils.advertisingClick(info, intervalTime);
            }

            /**
             * 广告offer展示
             * @param adInfo    广告信息
             */
            private void advertisingOfferShow(AdInfo adInfo) {
                StatisticUtils.advertisingOfferShow(adInfo, intervalTime);
                intervalTime = System.currentTimeMillis();
            }
        };
    }

    /**
     * banner广告回调中间层（埋点可以埋到这里）
     *
     * @param listener 对外监听
     * @return 返回对外监听, 中间层用来埋点
     */
    public static AdBannerListener getAdBannerListener(AdBannerListener listener) {
        return new AdBannerListener() {

            /**
             * 时间间隔
             * 记录填充到展示，展示到点击间隔
             */
            private long intervalTime = 0L;


            @Override
            public void onAdShow(AdInfo info) {
                if (listener != null) {
                    listener.onAdShow(info);
                }
                advertisingOfferShow(info);
            }

            @Override
            public void adSuccess(AdInfo info) {
                if (listener != null) {
                    listener.adSuccess(info);
                }
                intervalTime = System.currentTimeMillis();
            }

            @Override
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                if (listener != null) {
                    listener.adError(info, errorCode, errorMsg);
                }
            }

            @Override
            public void onAdClicked(AdInfo info) {
                if (listener != null) {
                    listener.onAdClicked(info);
                }
                StatisticUtils.advertisingClick(info, intervalTime);
            }

            @Override
            public void adClose(AdInfo info) {
                if (listener != null) {
                    listener.adClose(info);
                }
                StatisticUtils.advertisingClose(info, intervalTime);
            }

            /**
             * 广告offer展示
             * @param adInfo    广告信息
             */
            private void advertisingOfferShow(AdInfo adInfo) {
                StatisticUtils.advertisingOfferShow(adInfo, intervalTime);
                intervalTime = System.currentTimeMillis();
            }
        };
    }

    /**
     * 自渲染广告回调中间层
     *
     * @param listener 对外监听
     * @return 返回对外监听, 中间层用来埋点
     */
    public static AdSelfRenderListener getAdSelfRenderListener(AdSelfRenderListener listener) {
        return new AdSelfRenderListener() {
            /**
             * 时间间隔
             * 记录填充到展示，展示到点击间隔
             */
            private long intervalTime = 0L;
            boolean isExposed = false;

            @Override
            public void callbackView(View view) {
                if (listener != null) {
                    listener.callbackView(view);
                }
            }

            @Override
            public void adSuccess(AdInfo adInfo) {
                if (listener != null) {
                    listener.adSuccess(adInfo);
                }
                intervalTime = System.currentTimeMillis();
            }

            @Override
            public void adError(AdInfo adInfo, int errorCode, String errorMsg) {
                if (listener != null) {
                    listener.adError(adInfo, errorCode, errorMsg);
                }
            }

            @Override
            public void adExposed(AdInfo adInfo) {
                if (listener != null) {
                    listener.adExposed(adInfo);
                }
                if (!isExposed) {
                    isExposed = true;
                    AppUtils.getAdCount(adInfo.getMidasAd().getAdId());
                }
                advertisingOfferShow(adInfo);
            }

            @Override
            public void adClicked(AdInfo adInfo) {
                if (listener != null) {
                    listener.adClicked(adInfo);
                }
                StatisticUtils.advertisingClick(adInfo, intervalTime);
            }


            @Override
            public void adClose(AdInfo adInfo) {
                if (listener != null) {
                    listener.adClose(adInfo);
                }
                StatisticUtils.advertisingClose(adInfo, intervalTime);
            }

            /**
             * 广告offer展示
             * @param adInfo    广告信息
             */
            private void advertisingOfferShow(AdInfo adInfo) {
                StatisticUtils.advertisingOfferShow(adInfo, intervalTime);
                intervalTime = System.currentTimeMillis();
            }
        };
    }

    /**
     * 显示自渲染视图
     *
     * @param adInfo 广告实体
     */
    public static void showSelfRenderView(Activity activity, AdInfo adInfo, AdSelfRenderListener selfRenderListener) {
        try {
            MidasSelfRenderAd midasSelfRenderAd = (MidasSelfRenderAd) adInfo.getMidasAd();
            AdParameter adParameter = adInfo.getAdParameter();
            ViewGroup viewContainer = adParameter.getViewContainer();
            if (viewContainer == null) {

                return;
            }
            if (adParameter.getLayoutId() == 0) {

                return;
            }
            View view = LayoutInflater.from(activity).
                    inflate(adParameter.getLayoutId(), viewContainer, false);
            //小图标
            ImageView adSmallLogoIv = view.findViewById(R.id.ivAdIcon);
            String iconUrl = midasSelfRenderAd.getIconUrl();
            if (!TextUtils.isEmpty(iconUrl) && adSmallLogoIv != null) {
                Glide.with(activity).load(iconUrl).into(adSmallLogoIv);
            }
            //标题
            TextView adTitleTv = view.findViewById(R.id.tvAdTitle);
            if (adTitleTv != null) {
                adTitleTv.setText(midasSelfRenderAd.getTitle());
            }
            //描述
            TextView adDescTv = view.findViewById(R.id.tvAdDesc);
            if (adDescTv != null) {
                adDescTv.setText(midasSelfRenderAd.getDescription());
            }
            //大图片
            ImageView adImgIv = view.findViewById(R.id.ivAdImage);
            //视频播放view
            MediaView mediaView = view.findViewById(R.id.adMediaView);
            //详情大按钮
            TextView tvBigButton = view.findViewById(R.id.tvBigButton);

            List<View> clickViewList = new ArrayList<>();
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.getChildCount() > 0) {
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    clickViewList.add(viewGroup.getChildAt(i));
                }
            }
            //触发创意广告的view（点击下载或拨打电话）
            List<View> creativeViewList = new ArrayList<>();
            try {
                View smallBtnView = view.findViewById(R.id.tvSmallButton);
                View bigBtnView = view.findViewById(R.id.tvBigButton);
                if (smallBtnView != null) {
                    creativeViewList.add(smallBtnView);
                    clickViewList.add(smallBtnView);
                }
                if (bigBtnView != null) {
                    creativeViewList.add(bigBtnView);
                    clickViewList.add(bigBtnView);
                }
            } catch (Exception e) {
            }

            NativeAdContainer inflateView = new NativeAdContainer(AppUtils.getContext());
            inflateView.addView(view, new ViewGroup.LayoutParams(-1, -1));
            viewContainer.removeAllViews();
            viewContainer.addView(inflateView);

            if (Constants.AdSourceType.ChuanShanJia.equals(adInfo.getMidasAd().getAdSource())) {
                if (midasSelfRenderAd.getMidasAdPatternType() == 2) {
                    //视频广告
//                    if (adImgIv != null) {
//                        adImgIv.setVisibility(View.GONE);
//                    }
                    List<String> imageList = midasSelfRenderAd.getImageList();
                    if (imageList != null && imageList.size() > 0) {
                        if (adImgIv != null) {
                            Glide.with(activity).load(imageList.get(0))
                                    .into(adImgIv);
                        }
                    }
                    LogUtils.e("midasSelfRenderAd","穿山甲视频"+ imageList.get(0));
                } else {
//                    adImgIv.setVisibility(View.VISIBLE);
                    List<String> imageList = midasSelfRenderAd.getImageList();
                    if (imageList != null && imageList.size() > 0) {
                        if (adImgIv != null) {
                            Glide.with(activity).load(imageList.get(0))
                                    .into(adImgIv);
                        }
                    }
                    LogUtils.e("midasSelfRenderAd","穿山甲图片" + imageList.get(0));
                }
                TTFeedAd ttFeedAd = midasSelfRenderAd.getTtFeedAd();
                if (ttFeedAd == null) {
                    return;
                }
                ttFeedAd.registerViewForInteraction(viewContainer, clickViewList, creativeViewList, new TTNativeAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, TTNativeAd ttNativeAd) {
                        if (selfRenderListener != null) {
                            selfRenderListener.adClicked(adInfo);
                        }
                    }

                    @Override
                    public void onAdCreativeClick(View view, TTNativeAd ttNativeAd) {
                        if (selfRenderListener != null) {
                            selfRenderListener.adClicked(adInfo);
                        }
                    }

                    @Override
                    public void onAdShow(TTNativeAd ttNativeAd) {
                        if (selfRenderListener != null) {
                            selfRenderListener.adExposed(adInfo);
                            selfRenderListener.callbackView(view);
                        }
                    }
                });
                //可根据广告类型，为交互区域设置不同提示信息
                if (ttFeedAd.getInteractionType() == TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
                    //兼容下载类广告，使用activity申请权限
                    ttFeedAd.setActivityForDownloadApp(activity);
                    if (tvBigButton != null) {
                        tvBigButton.setText("立即下载");
                    }
                    // 注册下载监听
                    bindDownloadListener(tvBigButton, ttFeedAd);
                } else {
                    if (tvBigButton != null) {
                        tvBigButton.setText("查看详情");
                    }
                }
            } else if (Constants.AdSourceType.YouLiangHui.equals(adInfo.getMidasAd().getAdSource())) {
                NativeUnifiedADData nativeUnifiedAdData = midasSelfRenderAd.getNativeUnifiedADData();
                if (nativeUnifiedAdData == null) {
                    return;
                }


                nativeUnifiedAdData.bindAdToView(activity, inflateView, null,
                        clickViewList);
                updateAdAction(tvBigButton, nativeUnifiedAdData);
                if (selfRenderListener != null) {
                    selfRenderListener.callbackView(view);
                }
                nativeUnifiedAdData.setNativeAdEventListener(new NativeADEventListener() {
                    @Override
                    public void onADExposed() {
                        if (selfRenderListener != null) {
                            selfRenderListener.adExposed(adInfo);
                        }
                    }

                    @Override
                    public void onADClicked() {
                        if (selfRenderListener != null) {
                            selfRenderListener.adClicked(adInfo);
                        }
                    }

                    @Override
                    public void onADError(AdError adError) {
                        if (selfRenderListener != null) {
                            selfRenderListener.adError(adInfo, adError.getErrorCode(), adError.getErrorMsg());
                        }
                    }

                    @Override
                    public void onADStatusChanged() {
                        updateAdAction(tvBigButton, nativeUnifiedAdData);
                    }
                });
                //视频广告
                if (midasSelfRenderAd.getMidasAdPatternType() == 2) {
                    LogUtils.e("midasSelfRenderAd","优量汇视频");
                    if (adImgIv != null) {
                        adImgIv.setVisibility(View.GONE);
                    }
                    if (mediaView != null) {
                        ADTool.getInstance().cachePlaySelfRenderAd(activity.getClass().getSimpleName(),nativeUnifiedAdData);
                        mediaView.setVisibility(View.VISIBLE);
                        VideoOption videoOption = new VideoOption.Builder()
                                //总是自动播放
                                .setAutoPlayPolicy(VideoOption.AutoPlayPolicy.ALWAYS)
                                //静音
                                .setAutoPlayMuted(true)
                                //显示封面
                                .setNeedCoverImage(true)
                                //是否显示播放进度条
                                .setNeedProgressBar(false)
                                //点击按钮是否跳转详情
                                .setEnableDetailPage(true)
                                //点击视频是否停止or播放,false点击会onADClicked回调
                                .setEnableUserControl(false)
                                .build();
                        nativeUnifiedAdData.bindMediaView(mediaView, videoOption, new NativeADMediaListener() {
                            @Override
                            public void onVideoInit() {
                            }

                            @Override
                            public void onVideoLoading() {
                            }

                            @Override
                            public void onVideoReady() {
                            }

                            @Override
                            public void onVideoLoaded(int videoDuration) {
                            }

                            @Override
                            public void onVideoStart() {
                            }

                            @Override
                            public void onVideoPause() {
                            }

                            @Override
                            public void onVideoResume() {
                            }

                            @Override
                            public void onVideoCompleted() {
                                nativeUnifiedAdData.startVideo();
                            }

                            @Override
                            public void onVideoError(AdError error) {
                                LogUtils.e("AdError", "error" + error.getErrorMsg());
                            }

                            @Override
                            public void onVideoStop() {
                            }

                            @Override
                            public void onVideoClicked() {
                            }
                        });
                    }
                } else {
                    LogUtils.e("midasSelfRenderAd","优量汇图片" + midasSelfRenderAd.getImageUrl());
                    if (adImgIv != null) {
                        adImgIv.setVisibility(View.VISIBLE);
                        Glide.with(activity).load(midasSelfRenderAd.getImageUrl()).into(adImgIv);
                    }
                    if (mediaView != null) {
                        mediaView.setVisibility(View.GONE);
                    }
                }

            } else {

            }
        } catch (Exception e) {
            LogUtils.e("SdkRequestManager", "" + e.getMessage());
        }
    }


    /**
     * 根据下载状态更新底部按钮文字
     *
     * @param textView 文本控件
     * @param ad       优量汇广告对象
     */
    private static void updateAdAction(TextView textView, NativeUnifiedADData ad) {
        if (textView == null) {
            return;
        }
        if (!ad.isAppAd()) {
            textView.setText("查看详情");
            return;
        }
        textView.setText("立即下载");
        switch (ad.getAppStatus()) {
            //点击下载
            case 0:
                textView.setText("立即下载");
                break;
            //点击打开
            case 1:
                textView.setText("点击打开");
                break;
            //点击更新
            case 2:
                textView.setText("立即下载");
                break;
            // 特别注意：当进度小于0时，不要使用进度来渲染界面
            case 4:
                textView.setText("下载中" + (ad.getProgress() > 0 ? ad.getProgress() : 0) + "%");
                break;
            //点击安装
            case 8:
                textView.setText("点击安装");
                break;
            //点击重试
            case 16:
                textView.setText("重新下载");
                break;
            //继续下载
            default:
                textView.setText("继续下载" + (ad.getProgress() > 0 ? ad.getProgress() : 0) + "%");
                break;
        }
    }

    /**
     * 根据下载状态更新底部按钮文字
     *
     * @param textView 文本控件
     * @param ad       穿山甲 广告对象
     */
    private static void bindDownloadListener(TextView textView, TTNativeAd ad) {
        if (textView == null) {
            return;
        }
        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
                textView.setText("立即下载");
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                if (totalBytes > 0) {
                    if (currBytes < 0) {
                        currBytes = 0;
                    }
                    textView.setText("下载中" + (currBytes * 100 / totalBytes) + "%");
                }
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                if (totalBytes > 0) {
                    if (currBytes < 0) {
                        currBytes = 0;
                    }
                    textView.setText("继续下载" + (currBytes * 100 / totalBytes) + "%");
                }
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                textView.setText("重新下载");
            }

            @Override
            public void onInstalled(String fileName, String appName) {
                textView.setText("点击打开");
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                textView.setText("点击安装");
            }
        });
    }


}
