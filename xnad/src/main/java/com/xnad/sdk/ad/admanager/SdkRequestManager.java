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
import com.xnad.sdk.utils.ListenerUtils;
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

    @Override
    public void requestAd(Activity activity, AdInfo adInfo, AdRequestListener listener, AdBasicListener adListener) {
        if (Constants.AdType.SPLASH_TYPE.equals(adInfo.getAdType())) {
            requestSplashAd(activity, adInfo, listener, ListenerUtils.getAdSplashListener((AdSplashListener) adListener));
        } else if (Constants.AdType.REWARD_VIDEO_TYPE.equals(adInfo.getAdType())) {
            requestRewardVideoAd(activity, adInfo, listener, ListenerUtils.getRewardVideoAdListener((AdRewardVideoListener) adListener));
        } else if (Constants.AdType.FULL_SCREEN_VIDEO_TYPE.equals(adInfo.getAdType())){
            requestFullScreenVideoAd(activity, adInfo, listener, ListenerUtils.getFullScreenVideoAdListener((AdFullScreenVideoListener) adListener));
        } else if (Constants.AdType.SELF_RENDER.equals(adInfo.getAdType())){
            requestSelfRenderAd(activity, adInfo, listener, (AdSelfRenderListener)adListener);
            ListenerUtils.bindSelfRenderAdListener(adInfo);
        } else if (Constants.AdType.INTERACTION_TYPE.equals(adInfo.getAdType())){
            requestInteractionAd(activity, adInfo, listener, ListenerUtils.getInteractionListener((AdInteractionListener) adListener));
        } else if (Constants.AdType.NATIVE_TEMPLATE.equals(adInfo.getAdType())) {
            requestNativeTemplateAd(activity, adInfo, listener, (AdNativeTemplateListener) adListener, ListenerUtils.getNativeTemplateAdChargeListener());
        } else if(Constants.AdType.BANNER_TYPE.equals(adInfo.getAdType())){
            requestBannerAd(adInfo,listener, ListenerUtils.getAdBannerListener((AdBannerListener) adListener));
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

}
