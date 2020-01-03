package com.xnad.sdk.ad.admanager;

import android.app.Activity;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.entity.MidasNativeTemplateAd;
import com.xnad.sdk.ad.entity.MidasSelfRenderAd;
import com.xnad.sdk.ad.listener.AdBasicListener;
import com.xnad.sdk.ad.listener.AdRequestListener;
import com.xnad.sdk.ad.outlistener.AdBannerListener;
import com.xnad.sdk.ad.outlistener.AdFullScreenVideoListener;
import com.xnad.sdk.ad.outlistener.AdInteractionListener;
import com.xnad.sdk.ad.outlistener.AdNativeTemplateListener;
import com.xnad.sdk.ad.outlistener.AdOutChargeListener;
import com.xnad.sdk.ad.outlistener.AdRewardVideoListener;
import com.xnad.sdk.ad.outlistener.AdSelfRenderListener;
import com.xnad.sdk.ad.outlistener.AdSplashListener;
import com.xnad.sdk.config.Constants;
import com.xnad.sdk.utils.StatisticUtils;

/**
 * @ProjectName: MidasAdSdk
 * @Package: com.comm.jksdk.ad.admanager
 * @ClassName: SdkRequestManager
 * @Description: sdk广告请求
 * @Author: fanhailong
 * @CreateDate: 2019/12/2 18:18
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/2 18:18
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public abstract class SdkRequestManager implements AdRequestManager {
    protected final String TAG = "MidasAdSdk-->";

    /**
     * 时间间隔
     * 记录填充到展示，展示到点击间隔
     */
    private long intervalTime = 0L;

    @Override
    public void requestAd(Activity activity, AdInfo adInfo, AdRequestListener listener, AdBasicListener adListener) {
        if (Constants.AdType.SPLASH_TYPE.equals(adInfo.getAdType())) {
            requestSplashAd(activity, adInfo, listener, getAdSplashListener((AdSplashListener) adListener));
        } else if (Constants.AdType.REWARD_VIDEO_TYPE.equals(adInfo.getAdType())) {
            requestRewardVideoAd(activity, adInfo, listener, getRewardVideoAdListener((AdRewardVideoListener) adListener));
        } else if (Constants.AdType.FULL_SCREEN_VIDEO_TYPE.equals(adInfo.getAdType())){
            requestFullScreenVideoAd(activity, adInfo, listener, getFullScreenVideoAdListener((AdFullScreenVideoListener) adListener));
        } else if (Constants.AdType.SELF_RENDER.equals(adInfo.getAdType())){
            requestSelfRenderAd(activity, adInfo, listener, (AdSelfRenderListener)adListener);
            bindSelfRenderAdListener(adInfo);
        } else if (Constants.AdType.INTERACTION_TYPE.equals(adInfo.getAdType())){
            requestInteractionAd(activity, adInfo, listener, getInteractionListener((AdInteractionListener) adListener));
        } else if (Constants.AdType.NATIVE_TEMPLATE.equals(adInfo.getAdType())) {
            requestNativeTemplateAd(activity, adInfo, listener, (AdNativeTemplateListener) adListener, getNativeTemplateAdChargeListener());
        } else if(Constants.AdType.BANNER_TYPE.equals(adInfo.getAdType())){
            requestBannerAd(adInfo,listener, getAdBannerListener((AdBannerListener) adListener));
        } else{
            if (listener != null) {
                listener.adError(adInfo, 3, "没有该广告类型 ");
            }
        }
    }


    protected abstract void requestNativeTemplateAd(Activity activity, AdInfo info, AdRequestListener adRequestListener, AdNativeTemplateListener adNativeTemplateListener, AdOutChargeListener adOutChargeListener);

    protected abstract void requestInteractionAd(Activity activity, AdInfo info, AdRequestListener listener, AdInteractionListener adListener);

    protected abstract void requestSelfRenderAd(Activity activity, AdInfo info, AdRequestListener listener, AdSelfRenderListener adListener);

    protected abstract void requestFullScreenVideoAd(Activity activity, AdInfo info, AdRequestListener listener, AdFullScreenVideoListener adListener);

    public abstract void requestSplashAd(Activity activity, AdInfo adInfo, AdRequestListener adRequestListener, AdSplashListener adSplashListener);

    public abstract void requestRewardVideoAd(Activity activity, AdInfo adInfo, AdRequestListener adRequestListener, AdRewardVideoListener adRewardVideoListener);

    /**
     * banner广告请求
     * @param adInfo    广告信息
     * @param adRequestListener 请求回调
     * @param adBannerListener  banner事件监听
     */
    public abstract void requestBannerAd(AdInfo adInfo, AdRequestListener adRequestListener, AdBannerListener adBannerListener);

    /**
     * 缓存网络图片+
     * @param url
     */
    @Override
    public void cacheImg(String... url){
        if (url==null||url.length==0) {
            return;
        }
//        for (String s : url) {
//            Glide.with(MidasAdSdk.getContext())
//                    .load(s)
//                    .into(new SimpleTarget<Drawable>() {
//                        @Override
//                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                            LogUtils.e("cache success");
//                        }
//                    });
//        }
    }

    /**
     * 自渲染广告回调中间层
     * @param adInfo
     */
    private void bindSelfRenderAdListener(AdInfo adInfo) {
        MidasSelfRenderAd midasSelfRenderAd = (MidasSelfRenderAd) adInfo.getMidasAd();
        midasSelfRenderAd.setBindViewListener(new AdOutChargeListener<AdInfo>(){
            @Override
            public void adSuccess(AdInfo info) {
                if (midasSelfRenderAd.getAdOutChargeListener() != null) {
                    midasSelfRenderAd.getAdOutChargeListener().adSuccess(adInfo);
                }
            }

            @Override
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                if (midasSelfRenderAd.getAdOutChargeListener() != null) {
                    midasSelfRenderAd.getAdOutChargeListener().adError(adInfo, errorCode, errorMsg);
                }
            }

            @Override
            public void adExposed(AdInfo info) {
                if (midasSelfRenderAd.getAdOutChargeListener() != null) {
                    midasSelfRenderAd.getAdOutChargeListener().adExposed(adInfo);
                }
                advertisingOfferShow(adInfo);
            }

            @Override
            public void adClicked(AdInfo info) {
                if (midasSelfRenderAd.getAdOutChargeListener() != null) {
                    midasSelfRenderAd.getAdOutChargeListener().adClicked(adInfo);
                }
                StatisticUtils.advertisingClick(adInfo,intervalTime);
            }

            @Override
            public void adClose(AdInfo info) {
                if (midasSelfRenderAd.getAdOutChargeListener() != null) {
                    midasSelfRenderAd.getAdOutChargeListener().adClose(adInfo);
                }
                StatisticUtils.advertisingClose(adInfo,intervalTime);
            }
        });
    }


    /**
     *原生模板广告回调中间层（埋点可以埋到这里）
     * @return
     */
    private AdOutChargeListener getNativeTemplateAdChargeListener(){
        return new AdOutChargeListener<AdInfo>() {

            @Override
            public void adClose(AdInfo info) {
                if (((MidasNativeTemplateAd)info.getMidasAd()).getAdOutChargeListener() != null) {
                    ((MidasNativeTemplateAd)info.getMidasAd()).getAdOutChargeListener().adClose(info);
                }
                StatisticUtils.advertisingClose(info,intervalTime);
            }

            @Override
            public void adSuccess(AdInfo info) {
                if (((MidasNativeTemplateAd)info.getMidasAd()).getAdOutChargeListener() != null) {
                    ((MidasNativeTemplateAd)info.getMidasAd()).getAdOutChargeListener().adSuccess(info);
                }

            }

            @Override
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                if (((MidasNativeTemplateAd)info.getMidasAd()).getAdOutChargeListener() != null) {
                    ((MidasNativeTemplateAd)info.getMidasAd()).getAdOutChargeListener().adError(info, errorCode, errorMsg);
                }
            }

            @Override
            public void adExposed(AdInfo info) {
                if (((MidasNativeTemplateAd)info.getMidasAd()).getAdOutChargeListener() != null) {
                    ((MidasNativeTemplateAd)info.getMidasAd()).getAdOutChargeListener().adExposed(info);
                }
                advertisingOfferShow(info);
            }

            @Override
            public void adClicked(AdInfo info) {
                if (((MidasNativeTemplateAd)info.getMidasAd()).getAdOutChargeListener() != null) {
                    ((MidasNativeTemplateAd)info.getMidasAd()).getAdOutChargeListener().adClicked(info);
                }
                StatisticUtils.advertisingClick(info,intervalTime);
            }
        };
    }

    /**
     * 插屏广告回调中间层（埋点可以埋到这里）
     * @param listener
     * @return
     */
    private AdInteractionListener getInteractionListener(AdInteractionListener listener){
        return new AdInteractionListener<AdInfo>(){

            @Override
            public void adClose(AdInfo info) {
                if (listener != null) {
                    listener.adClose(info);
                }
                StatisticUtils.advertisingClose(info,intervalTime);
            }

            @Override
            public void adSuccess(AdInfo info) {
                if (listener != null) {
                    listener.adSuccess(info);
                }
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
                StatisticUtils.advertisingClick(info,intervalTime);
            }
        };
    }

    /**
     * 激励视频广告回调中间层（埋点可以埋到这里）
     * @param listener
     * @return
     */
    private AdRewardVideoListener getRewardVideoAdListener(AdRewardVideoListener listener){
        return new AdRewardVideoListener<AdInfo>() {

            @Override
            public void adClose(AdInfo info) {
                if (listener != null) {
                    listener.adClose(info);
                }
                if (TextUtils.equals(Constants.AdType.REWARD_VIDEO_TYPE,info.getAdType())){
                    StatisticUtils.advertisingRewardedClose(info,intervalTime);
                }else {
                    StatisticUtils.advertisingClose(info,intervalTime);
                }
            }

            @Override
            public void adSuccess(AdInfo info) {
                if (listener != null) {
                    listener.adSuccess(info);
                }
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
                StatisticUtils.advertisingClick(info,intervalTime);
            }

            @Override
            public void onVideoRewardVerify(AdInfo info, boolean rewardVerify, int rewardAmount, String rewardName) {
                if (listener != null) {
                    listener.onVideoRewardVerify(info, rewardVerify, rewardAmount, rewardName);
                }
                if (TextUtils.equals(Constants.AdType.REWARD_VIDEO_TYPE,info.getAdType())){
                    StatisticUtils.advertisingRewarded(info,intervalTime);
                }
            }

            @Override
            public void onVideoComplete(AdInfo info) {
                if (listener != null) {
                    listener.onVideoComplete(info);
                }
            }

        };
    }

    /**
     * 全屏视频广告回调中间层（埋点可以埋到这里）
     * @param listener
     * @return
     */
    private AdFullScreenVideoListener getFullScreenVideoAdListener(AdFullScreenVideoListener listener){
        return new AdFullScreenVideoListener<AdInfo>() {

            @Override
            public void adClose(AdInfo info) {
                if (listener != null) {
                    listener.adClose(info);
                }
                if (TextUtils.equals(Constants.AdType.REWARD_VIDEO_TYPE,info.getAdType())){
                    StatisticUtils.advertisingRewardedClose(info,intervalTime);
                }else {
                    StatisticUtils.advertisingClose(info,intervalTime);
                }
            }

            @Override
            public void adSuccess(AdInfo info) {
                if (listener != null) {
                    listener.adSuccess(info);
                }
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
                StatisticUtils.advertisingClick(info,intervalTime);
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

        };
    }

    /**
     * 开屏广告回调中间层（埋点可以埋到这里）
     * @param listener
     * @return
     */
    private AdSplashListener getAdSplashListener(AdSplashListener listener){
        return new AdSplashListener<AdInfo>() {

            @Override
            public void adClose(AdInfo info) {
                if (listener != null) {
                    listener.adClose(info);
                }
                StatisticUtils.advertisingClose(info,intervalTime);
            }

            @Override
            public void adSuccess(AdInfo info) {
                if (listener != null) {
                    listener.adSuccess(info);
                }
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
                StatisticUtils.advertisingClick(info,intervalTime);
            }

            @Override
            public void adTick(AdInfo info, long l) {
                if (listener != null) {
                    listener.adTick(info, l);
                }
            }

            @Override
            public ViewGroup getViewGroup() {
                if (listener != null) {
                    return listener.getViewGroup();
                }
                return null;
            }
        };
    }

    /**
     * banner广告回调中间层（埋点可以埋到这里）
     * @param listener
     * @return
     */
    private AdBannerListener getAdBannerListener(AdBannerListener listener){
        return new AdBannerListener() {
            @Override
            public void adSuccess(Object info) {
                if (listener != null) {
                    listener.adSuccess(info);
                }
            }

            @Override
            public void adError(Object info, int errorCode, String errorMsg) {
                if (listener != null) {
                    listener.adError(info, errorCode, errorMsg);
                }
            }

            @Override
            public void onAdShow(AdInfo info) {
                if (listener != null) {
                    listener.onAdShow(info);
                }
                advertisingOfferShow(info);
            }

            @Override
            public void onAdClicked(AdInfo info) {
                if (listener != null) {
                    listener.onAdClicked(info);
                }
                StatisticUtils.advertisingClick(info,intervalTime);
            }

            @Override
            public void adClose(AdInfo info) {
                if (listener != null) {
                    listener.adClose(info);
                }
                StatisticUtils.advertisingClose(info,intervalTime);
            }
        };
    }

    /**
     * 广告offer展示
     * @param adInfo    广告信息
     */
    private void advertisingOfferShow(AdInfo adInfo){
        StatisticUtils.advertisingOfferShow(adInfo,0);
        intervalTime = System.currentTimeMillis();
    }

}
