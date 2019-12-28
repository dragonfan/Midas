package com.xnad.sdk.ad.admanager;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xnad.sdk.MidasAdSdk;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.entity.MidasNativeTemplateAd;
import com.xnad.sdk.ad.entity.MidasSelfRenderAd;
import com.xnad.sdk.ad.listener.AdBasicListener;
import com.xnad.sdk.ad.listener.AdChargeListener;
import com.xnad.sdk.ad.listener.AdRequestListener;
import com.xnad.sdk.ad.listener.AdRequestManager;
import com.xnad.sdk.ad.listener.AdSplashListener;
import com.xnad.sdk.ad.listener.BindViewListener;
import com.xnad.sdk.ad.listener.InteractionListener;
import com.xnad.sdk.ad.listener.NativeTemplateListener;
import com.xnad.sdk.ad.listener.SelfRenderAdListener;
import com.xnad.sdk.ad.listener.VideoAdListener;
import com.xnad.sdk.constant.Constants;
import com.xnad.sdk.utils.LogUtils;
import com.xnad.sdk.utils.StatisticUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
            requestRewardVideoAd(activity, adInfo, listener, getVideoAdListener((VideoAdListener) adListener));
        } else if (Constants.AdType.FULL_SCREEN_VIDEO_TYPE.equals(adInfo.getAdType())){
            requestFullScreenVideoAd(activity, adInfo, listener, getVideoAdListener((VideoAdListener) adListener));
        } else if (Constants.AdType.SELF_RENDER.equals(adInfo.getAdType())){
            requestSelfRenderAd(activity, adInfo, listener, (SelfRenderAdListener)adListener);
            bindSelfRenderAdListener(adInfo);
        } else if (Constants.AdType.INTERACTION_TYPE.equals(adInfo.getAdType())){
            requestInteractionAd(activity, adInfo, listener, getInteractionListener((InteractionListener) adListener));
        } else if (Constants.AdType.NATIVE_TEMPLATE.equals(adInfo.getAdType())) {
            requestNativeTemplateAd(activity, adInfo, listener, (NativeTemplateListener) adListener, getNativeTemplateAdChargeListener());
//            bindNativeTempLateListener(adInfo);
        } else {
            if (listener != null) {
                listener.adError(adInfo, 3, "没有该广告类型 ");
            }
        }
    }


    protected abstract void requestNativeTemplateAd(Activity activity, AdInfo info, AdRequestListener adRequestListener, NativeTemplateListener nativeTemplateListener, AdChargeListener adChargeListener);

    protected abstract void requestInteractionAd(Activity activity, AdInfo info, AdRequestListener listener, InteractionListener adListener);

    protected abstract void requestSelfRenderAd(Activity activity, AdInfo info, AdRequestListener listener, SelfRenderAdListener adListener);

    protected abstract void requestFullScreenVideoAd(Activity activity, AdInfo info, AdRequestListener listener, VideoAdListener adListener);

    public abstract void requestSplashAd(Activity activity, AdInfo adInfo, AdRequestListener adRequestListener, AdSplashListener adSplashListener);

    public abstract void requestRewardVideoAd(Activity activity, AdInfo adInfo, AdRequestListener adRequestListener, VideoAdListener videoAdListener);


    /**
     * 缓存网络图片+
     * @param url
     */
    @Override
    public void cacheImg(String... url){
        if (url==null||url.length==0) {
            return;
        }
        for (String s : url) {
            Glide.with(MidasAdSdk.getContext())
                    .load(s)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            LogUtils.e("cache success");
                        }
                    });
        }
    }

    /**
     * 自渲染广告回调中间层
     * @param info
     */
    private void bindSelfRenderAdListener(AdInfo info) {
        MidasSelfRenderAd midasSelfRenderAd = (MidasSelfRenderAd) info.getMidasAd();
        midasSelfRenderAd.setBindViewListener(new BindViewListener() {

            @Override
            public void adClose() {
                StatisticUtils.advertisingClose(info,intervalTime);
            }

            @Override
            public void adExposed() {
                if (midasSelfRenderAd.getSelfRenderChargeListener() != null) {
                    midasSelfRenderAd.getSelfRenderChargeListener().adExposed(info);
                }
                advertisingOfferShow(info);
            }

            @Override
            public void adClicked() {
                if (midasSelfRenderAd.getSelfRenderChargeListener() != null) {
                    midasSelfRenderAd.getSelfRenderChargeListener().adClicked(info);
                }
                StatisticUtils.advertisingClick(info,intervalTime);
            }
        });
    }


    /**
     *原生模板广告回调中间层（埋点可以埋到这里）
     * @return
     */
    private AdChargeListener getNativeTemplateAdChargeListener(){
        return new AdChargeListener<AdInfo>() {

            @Override
            public void adClose(AdInfo info) {
                StatisticUtils.advertisingClose(info,intervalTime);
            }

            @Override
            public void adSuccess(AdInfo info) {
                if (((MidasNativeTemplateAd)info.getMidasAd()).getAdChargeListener() != null) {
                    ((MidasNativeTemplateAd)info.getMidasAd()).getAdChargeListener().adSuccess(info);
                }
            }

            @Override
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                if (((MidasNativeTemplateAd)info.getMidasAd()).getAdChargeListener() != null) {
                    ((MidasNativeTemplateAd)info.getMidasAd()).getAdChargeListener().adError(info, errorCode, errorMsg);
                }
            }

            @Override
            public void adExposed(AdInfo info) {
                if (((MidasNativeTemplateAd)info.getMidasAd()).getAdChargeListener() != null) {
                    ((MidasNativeTemplateAd)info.getMidasAd()).getAdChargeListener().adExposed(info);
                }
                advertisingOfferShow(info);
            }

            @Override
            public void adClicked(AdInfo info) {
                if (((MidasNativeTemplateAd)info.getMidasAd()).getAdChargeListener() != null) {
                    ((MidasNativeTemplateAd)info.getMidasAd()).getAdChargeListener().adClicked(info);
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
    private InteractionListener getInteractionListener(InteractionListener listener){
        return new InteractionListener<AdInfo>(){

            @Override
            public void adClose(AdInfo info) {
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
     * 激励视频和全屏视频广告回调中间层（埋点可以埋到这里）
     * @param listener
     * @return
     */
    private VideoAdListener getVideoAdListener(VideoAdListener listener){
        return new VideoAdListener<AdInfo>() {

            @Override
            public void adClose(AdInfo info) {
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
            public void onVideoResume(AdInfo info) {
                if (listener != null) {
                    listener.onVideoResume(info);
                }
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
     * 开屏广告回调中间层（埋点可以埋到这里）
     * @param listener
     * @return
     */
    private AdSplashListener getAdSplashListener(AdSplashListener listener){
        return new AdSplashListener<AdInfo>() {

            @Override
            public void adClose(AdInfo info) {
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
            public ViewGroup getViewGroup() {
                if (listener != null) {
                    return listener.getViewGroup();
                }
                return null;
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
