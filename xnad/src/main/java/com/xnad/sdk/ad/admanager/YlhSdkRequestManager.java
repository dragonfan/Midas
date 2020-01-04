package com.xnad.sdk.ad.admanager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.ViewGroup;

import com.qq.e.ads.banner2.UnifiedBannerView;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeUnifiedAD;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.util.AdError;
import com.xnad.sdk.ad.cache.ADTool;
import com.xnad.sdk.ad.cache.AdContainerWrapper;
import com.xnad.sdk.ad.cache.wrapper.WrapperBannerADListener;
import com.xnad.sdk.ad.cache.wrapper.WrapperInterstitialADListener;
import com.xnad.sdk.ad.cache.wrapper.WrapperNativeTemplateAdListener;
import com.xnad.sdk.ad.cache.wrapper.WrapperRewardVideoAdListener;
import com.xnad.sdk.ad.cache.wrapper.WrapperSelfRenderAdListener;
import com.xnad.sdk.ad.cache.wrapper.WrapperSplashADListener;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.entity.MidasBannerAd;
import com.xnad.sdk.ad.entity.MidasInteractionAd;
import com.xnad.sdk.ad.entity.MidasNativeTemplateAd;
import com.xnad.sdk.ad.entity.MidasRewardVideoAd;
import com.xnad.sdk.ad.entity.MidasSelfRenderAd;
import com.xnad.sdk.ad.entity.MidasSplashAd;
import com.xnad.sdk.ad.listener.AdRequestListener;
import com.xnad.sdk.ad.outlistener.AdBannerListener;
import com.xnad.sdk.ad.outlistener.AdFullScreenVideoListener;
import com.xnad.sdk.ad.outlistener.AdInteractionListener;
import com.xnad.sdk.ad.outlistener.AdNativeTemplateListener;
import com.xnad.sdk.ad.outlistener.AdOutChargeListener;
import com.xnad.sdk.ad.outlistener.AdRewardVideoListener;
import com.xnad.sdk.ad.outlistener.AdSelfRenderListener;
import com.xnad.sdk.ad.outlistener.AdSplashListener;
import com.xnad.sdk.config.AdParameter;
import com.xnad.sdk.utils.AppUtils;
import com.xnad.sdk.utils.LogUtils;

import java.util.List;

/**
 * @ProjectName: MidasAdSdk
 * @Package: com.comm.jksdk.ad.admanager
 * @ClassName: CsjSdkRequestManager
 * @Description: 优量汇广告请求类
 * @Author: fanhailong
 * @CreateDate: 2019/12/2 18:21
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/2 18:21
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class YlhSdkRequestManager extends SdkRequestManager {

    // 广告请求数量
    protected final static int REQUEST_AD_COUNTS = 1;

    private final int MAX_DURATION = 30;


    @Override
    protected void requestNativeTemplateAd(Activity activity, AdInfo info, AdRequestListener listener,
                                           AdNativeTemplateListener adListener, AdOutChargeListener adOutChargeListener) {
        MidasNativeTemplateAd midasNativeTemplateAd = (MidasNativeTemplateAd) info.getMidasAd();


        //设置自定义监听
        WrapperNativeTemplateAdListener wrapperInterstitialADListener = new WrapperNativeTemplateAdListener();
        wrapperInterstitialADListener.setAdInfo(info);
        wrapperInterstitialADListener.setLoadListener(listener);
        wrapperInterstitialADListener.setOutListener(adListener);


        // 这里的Context必须为Activity
        NativeExpressAD nativeExpressAD = new NativeExpressAD(activity, new ADSize((int) midasNativeTemplateAd.getWidth(), ADSize.AUTO_HEIGHT), midasNativeTemplateAd.getAppId(),
                midasNativeTemplateAd.getAdId(), wrapperInterstitialADListener);
        nativeExpressAD.setVideoOption(new VideoOption.Builder()
                // 设置什么网络环境下可以自动播放视频
                .setAutoPlayPolicy(VideoOption.AutoPlayPolicy.ALWAYS)
                // 设置自动播放视频时，是否静音
                .setAutoPlayMuted(true)
                .build());
//        nativeExpressAD.setMaxVideoDuration(getMaxVideoDuration());
        nativeExpressAD.setMaxVideoDuration(8);
        /**
         * 如果广告位支持视频广告，强烈建议在调用loadData请求广告前调用setVideoPlayPolicy，有助于提高视频广告的eCPM值 <br/>
         * 如果广告位仅支持图文广告，则无需调用
         * 设置本次拉取的视频广告，从用户角度看到的视频播放策略<p/>
         * "用户角度"特指用户看到的情况，并非SDK是否自动播放，与自动播放策略AutoPlayPolicy的取值并非一一对应 <br/>
         * 如自动播放策略为AutoPlayPolicy.WIFI，但此时用户网络为4G环境，在用户看来就是手工播放的
         */
        // 本次拉回的视频广告，在用户看来是否为自动播放的
        nativeExpressAD.setVideoPlayPolicy(getVideoPlayPolicy(VideoOption.AutoPlayPolicy.ALWAYS, activity));
        nativeExpressAD.loadAD(1);

        midasNativeTemplateAd.setNativeExpressAD(nativeExpressAD);
    }

    public static int getVideoPlayPolicy(int autoPlayPolicy, Context context) {
        if (autoPlayPolicy == VideoOption.AutoPlayPolicy.ALWAYS) {
            return VideoOption.VideoPlayPolicy.AUTO;
        } else if (autoPlayPolicy == VideoOption.AutoPlayPolicy.WIFI) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiNetworkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return wifiNetworkInfo != null && wifiNetworkInfo.isConnected() ? VideoOption.VideoPlayPolicy.AUTO
                    : VideoOption.VideoPlayPolicy.MANUAL;
        } else if (autoPlayPolicy == VideoOption.AutoPlayPolicy.NEVER) {
            return VideoOption.VideoPlayPolicy.MANUAL;
        }
        return VideoOption.VideoPlayPolicy.UNKNOWN;
    }

    private UnifiedInterstitialAD iad;

    @Override
    protected void requestInteractionAd(Activity activity, AdInfo info, AdRequestListener listener, AdInteractionListener adListener) {
        MidasInteractionAd midasInteractionAd = (MidasInteractionAd) info.getMidasAd();
        if (iad != null) {
            iad.close();
            iad.destroy();
            iad = null;
        }

        //设置自定义监听
        WrapperInterstitialADListener wrapperInterstitialADListener = new WrapperInterstitialADListener();
        wrapperInterstitialADListener.setAdInfo(info);
        wrapperInterstitialADListener.setLoadListener(listener);
        wrapperInterstitialADListener.setOutListener(adListener);


        iad = new UnifiedInterstitialAD(activity, midasInteractionAd.getAppId(), midasInteractionAd.getAdId(), wrapperInterstitialADListener);
        ((MidasInteractionAd) info.getMidasAd()).setUnifiedInterstitialAD(iad);
        iad.loadAD();
    }

    @Override
    protected void requestSelfRenderAd(Activity activity, AdInfo info, AdRequestListener listener, AdSelfRenderListener adListener) {
        MidasSelfRenderAd midasSelfRenderAd = (MidasSelfRenderAd) info.getMidasAd();

        WrapperSelfRenderAdListener wrapperSelfRenderAdListener = new WrapperSelfRenderAdListener();
        wrapperSelfRenderAdListener.setAdInfo(info);
        wrapperSelfRenderAdListener.setLoadListener(listener);
        wrapperSelfRenderAdListener.setOutListener(adListener);

        NativeUnifiedAD mAdManager = new NativeUnifiedAD(activity, midasSelfRenderAd.getAppId(), midasSelfRenderAd.getAdId(), wrapperSelfRenderAdListener);
        //设置视频时长
        mAdManager.setMaxVideoDuration(12);

        // 本次拉回的视频广告，在用户看来是否为自动播放的
        mAdManager.setVideoPlayPolicy(VideoOption.VideoPlayPolicy.AUTO);
        // 视频播放前，用户看到的广告容器是由SDK渲染的
        mAdManager.setVideoADContainerRender(VideoOption.VideoADContainerRender.SDK);

        mAdManager.loadData(3);
    }

    @Override
    protected void requestFullScreenVideoAd(Activity activity, AdInfo info, AdRequestListener listener, AdFullScreenVideoListener adListener) {
        if (listener != null) {
            listener.adError(info, 3, "优量汇暂不支持全屏视频");
        }
    }

    @Override
    public void requestSplashAd(Activity activity, AdInfo adInfo, AdRequestListener adRequestListener, AdSplashListener adSplashListener) {
        MidasSplashAd midasSplashAd = (MidasSplashAd) adInfo.getMidasAd();
        int timeOut = midasSplashAd.getTimeOut();
        if (timeOut == 0) {
            timeOut = 3000;
        }

        //设置自定义监听
        WrapperSplashADListener wrapperSplashADListener = new WrapperSplashADListener();
        wrapperSplashADListener.setAdInfo(adInfo);
        wrapperSplashADListener.setLoadListener(adRequestListener);
        wrapperSplashADListener.setOutListener(adSplashListener);

        SplashAD splashAD = new SplashAD(activity, midasSplashAd.getAppId(), midasSplashAd.getAdId(), wrapperSplashADListener, timeOut);
        ((MidasSplashAd) adInfo.getMidasAd()).setSplashAD(splashAD);
        if (adSplashListener != null) {
            //判断是否显示
            if (adRequestListener.adShow(adInfo)) {
                ViewGroup viewGroup = adSplashListener.getViewGroup();
                if (viewGroup != null) {
                    splashAD.fetchAndShowIn(viewGroup);
                }
            } else {
                splashAD.preLoad();
                //添加到缓存
                ADTool.getInstance().cacheAd(wrapperSplashADListener, adInfo);
            }

        }
    }

    @Override
    public void requestRewardVideoAd(Activity activity, AdInfo adInfo, AdRequestListener adRequestListener, AdRewardVideoListener adRewardVideoListener) {
        if (activity == null) {
            throw new NullPointerException("loadFullScreenVideoAd activity is null");
        }
        MidasRewardVideoAd midasRewardVideoAd = (MidasRewardVideoAd) adInfo.getMidasAd();
        int orientation = midasRewardVideoAd.getOrientation();
        if (orientation == 2) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        //设置自定义监听
        WrapperRewardVideoAdListener wrapperRewardVideoAdListener = new WrapperRewardVideoAdListener();
        wrapperRewardVideoAdListener.setAdInfo(adInfo);
        wrapperRewardVideoAdListener.setLoadListener(adRequestListener);
        wrapperRewardVideoAdListener.setOutListener(adRewardVideoListener);

        RewardVideoAD rewardVideoAD = new RewardVideoAD(activity, midasRewardVideoAd.getAppId(), midasRewardVideoAd.getAdId(), wrapperRewardVideoAdListener);
        midasRewardVideoAd.setRewardVideoAD(rewardVideoAD);
        // 2. 加载激励视频广告
        rewardVideoAD.loadAD();
    }

    @Override
    public void requestBannerAd(AdInfo adInfo, AdRequestListener adRequestListener,
                                AdBannerListener adBannerListener) {
        AdParameter adParameter = adInfo.getAdParameter();
        MidasBannerAd midasBannerAd = (MidasBannerAd) adInfo.getMidasAd();

        if (adParameter != null && adParameter.getActivity() != null
                && adParameter.getViewContainer() != null) {
            WrapperBannerADListener mWrapperBannerADListener = new WrapperBannerADListener();
            mWrapperBannerADListener.setAdInfo(adInfo);
            mWrapperBannerADListener.setLoadListener(adRequestListener);
            mWrapperBannerADListener.setOutListener(adBannerListener);

            UnifiedBannerView bannerView = new UnifiedBannerView(adParameter.getActivity(),
                    midasBannerAd.getAppId(), midasBannerAd.getAdId(),mWrapperBannerADListener);

            adParameter.getViewContainer().removeAllViews();
            Point screenSize = new Point();
            adParameter.getActivity().getWindowManager().
                    getDefaultDisplay().getSize(screenSize);
            adParameter.getViewContainer().addView(bannerView, new ViewGroup.
                    LayoutParams(screenSize.x, Math.round(screenSize.x / 6.4F)));
            midasBannerAd.setUnifiedBannerView(bannerView);
            bannerView.setRefresh(30);
            bannerView.loadAD();

        }
    }

}

