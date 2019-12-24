package com.comm.jksdk.ad.admanager;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.comm.jksdk.MidasAdSdk;
import com.comm.jksdk.ad.entity.AdInfo;
import com.comm.jksdk.ad.entity.MidasNativeTemplateAd;
import com.comm.jksdk.ad.listener.AdBasicListener;
import com.comm.jksdk.ad.listener.AdChargeListener;
import com.comm.jksdk.ad.listener.AdRequestListener;
import com.comm.jksdk.ad.listener.AdRequestManager;
import com.comm.jksdk.ad.listener.AdSplashListener;
import com.comm.jksdk.ad.listener.InteractionListener;
import com.comm.jksdk.ad.listener.NativeTemplateListener;
import com.comm.jksdk.ad.listener.SelfRenderAdListener;
import com.comm.jksdk.ad.listener.VideoAdListener;
import com.comm.jksdk.constant.Constants;
import com.comm.jksdk.http.utils.LogUtils;
import com.comm.jksdk.utils.CollectionUtils;

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
        } else if (Constants.AdType.INTERACTION_TYPE.equals(adInfo.getAdType())){
            requestInteractionAd(activity, adInfo, listener, getInteractionListener((InteractionListener) adListener));
        } else if (Constants.AdType.NATIVE_TEMPLATE.equals(adInfo.getAdType())) {
            requestNativeTemplateAd(activity, adInfo, listener, getNativeTemplateListener((NativeTemplateListener) adListener), getNativeTemplateAdChargeListener());
        } else {

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
        if (CollectionUtils.isEmpty(url)) {
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
     * 原生模板广告回调中间层（埋点可以埋到这里）
     * @param listener
     * @return
     */
    private NativeTemplateListener getNativeTemplateListener(NativeTemplateListener listener){
        return new NativeTemplateListener<AdInfo>(){

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
        };
    }

    /**
     *原生模板广告回调中间层（埋点可以埋到这里）
     * @return
     */
    private AdChargeListener getNativeTemplateAdChargeListener(){
        return new AdChargeListener<MidasNativeTemplateAd>() {
            @Override
            public void adSuccess(MidasNativeTemplateAd info) {
                if (info.getAdChargeListener() != null) {
                    info.getAdChargeListener().adSuccess(info);
                }
            }

            @Override
            public void adError(MidasNativeTemplateAd info, int errorCode, String errorMsg) {
                if (info.getAdChargeListener() != null) {
                    info.getAdChargeListener().adError(info, errorCode, errorMsg);
                }
            }

            @Override
            public void adExposed(MidasNativeTemplateAd info) {
                if (info.getAdChargeListener() != null) {
                    info.getAdChargeListener().adExposed(info);
                }
            }

            @Override
            public void adClicked(MidasNativeTemplateAd info) {
                if (info.getAdChargeListener() != null) {
                    info.getAdChargeListener().adClicked(info);
                }
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
            }

            @Override
            public void adClicked(AdInfo info) {
                if (listener != null) {
                    listener.adClicked(info);
                }
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
            }

            @Override
            public void adClicked(AdInfo info) {
                if (listener != null) {
                    listener.adClicked(info);
                }
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
            }

            @Override
            public void adClicked(AdInfo info) {
                if (listener != null) {
                    listener.adClicked(info);
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
}
