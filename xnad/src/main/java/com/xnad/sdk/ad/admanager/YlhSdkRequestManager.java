package com.xnad.sdk.ad.admanager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeADUnifiedListener;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.ads.nativ.NativeUnifiedAD;
import com.qq.e.ads.nativ.NativeUnifiedADData;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.util.AdError;
import com.xnad.sdk.ad.cache.ADTool;
import com.xnad.sdk.ad.cache.wrapper.WrapperInterstitialADListener;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.entity.MidasInteractionAd;
import com.xnad.sdk.ad.entity.MidasNativeTemplateAd;
import com.xnad.sdk.ad.entity.MidasRewardVideoAd;
import com.xnad.sdk.ad.entity.MidasSelfRenderAd;
import com.xnad.sdk.ad.entity.MidasSplashAd;
import com.xnad.sdk.ad.listener.AdRequestListener;
import com.xnad.sdk.ad.outlistener.AdFullScreenVideoListener;
import com.xnad.sdk.ad.outlistener.AdInteractionListener;
import com.xnad.sdk.ad.outlistener.AdNativeTemplateListener;
import com.xnad.sdk.ad.outlistener.AdOutChargeListener;
import com.xnad.sdk.ad.outlistener.AdRewardVideoListener;
import com.xnad.sdk.ad.outlistener.AdSelfRenderListener;
import com.xnad.sdk.ad.outlistener.AdSplashListener;
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
        NativeExpressAD nativeExpressAD = new NativeExpressAD(activity, new ADSize((int) midasNativeTemplateAd.getWidth(), ADSize.AUTO_HEIGHT), midasNativeTemplateAd.getAppId(), midasNativeTemplateAd.getAdId(), new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onADLoaded(List<NativeExpressADView> list) {
                NativeExpressADView nativeExpressADView = list.get(0);

                //添加到缓存
                ADTool.getInstance().cacheAd(nativeExpressADView, info);

                if (nativeExpressADView.getBoundData().getAdPatternType() == AdPatternType.NATIVE_VIDEO) {
                    nativeExpressADView.setMediaListener(new NativeExpressMediaListener() {
                        @Override
                        public void onVideoInit(NativeExpressADView nativeExpressADView) {

                        }

                        @Override
                        public void onVideoLoading(NativeExpressADView nativeExpressADView) {

                        }

                        @Override
                        public void onVideoReady(NativeExpressADView nativeExpressADView, long l) {

                        }

                        @Override
                        public void onVideoStart(NativeExpressADView nativeExpressADView) {

                        }

                        @Override
                        public void onVideoPause(NativeExpressADView nativeExpressADView) {

                        }

                        @Override
                        public void onVideoComplete(NativeExpressADView nativeExpressADView) {

                        }

                        @Override
                        public void onVideoError(NativeExpressADView nativeExpressADView, AdError adError) {

                        }

                        @Override
                        public void onVideoPageOpen(NativeExpressADView nativeExpressADView) {

                        }

                        @Override
                        public void onVideoPageClose(NativeExpressADView nativeExpressADView) {

                        }
                    });
                }
                // 广告可见才会产生曝光，否则将无法产生收益。
                midasNativeTemplateAd.setAddView(nativeExpressADView);
//                nativeExpressADView.render();
                //请求成功回调
                if (listener != null) {
                    listener.adSuccess(info);
                }

                if (adListener != null) {
                    adListener.adSuccess(info);
                }
            }

            //NativeExpressADView 渲染广告失败
            @Override
            public void onRenderFail(NativeExpressADView nativeExpressADView) {
                LogUtils.d(TAG, "YLH onRenderFail:");
                if (adOutChargeListener != null) {
                    adOutChargeListener.adError(info, 2, "on render fail");
                }
            }

            //NativeExpressADView 渲染广告成功
            @Override
            public void onRenderSuccess(NativeExpressADView nativeExpressADView) {
                LogUtils.d(TAG, "YLH onRenderSuccess:");
                if (adOutChargeListener != null) {
                    adOutChargeListener.adSuccess(info);
                }
            }

            @Override
            public void onADExposure(NativeExpressADView nativeExpressADView) {
                if (adOutChargeListener != null) {
                    adOutChargeListener.adExposed(info);
                }
            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {
                if (adOutChargeListener != null) {
                    adOutChargeListener.adClicked(info);
                }
            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {
                if (adOutChargeListener != null) {
                    adOutChargeListener.adClose(info);
                }
            }

            //	因为广告点击等原因离开当前 app 时调用
            @Override
            public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

            }

            //广告展开遮盖时调用
            @Override
            public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

            }

            //广告关闭遮盖时调用
            @Override
            public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onNoAD(AdError adError) {
                if (listener != null) {
                    listener.adError(info, adError.getErrorCode(), adError.getErrorMsg());
                }
            }
        }); // 这里的Context必须为Activity
        nativeExpressAD.setVideoOption(new VideoOption.Builder()
                .setAutoPlayPolicy(VideoOption.AutoPlayPolicy.ALWAYS) // 设置什么网络环境下可以自动播放视频
                .setAutoPlayMuted(true) // 设置自动播放视频时，是否静音
                .build()); // setVideoOption是可选的，开发者可根据需要选择是否配置
//        nativeExpressAD.setMaxVideoDuration(getMaxVideoDuration());
        nativeExpressAD.setMaxVideoDuration(8);
        /**
         * 如果广告位支持视频广告，强烈建议在调用loadData请求广告前调用setVideoPlayPolicy，有助于提高视频广告的eCPM值 <br/>
         * 如果广告位仅支持图文广告，则无需调用
         */

        /**
         * 设置本次拉取的视频广告，从用户角度看到的视频播放策略<p/>
         *
         * "用户角度"特指用户看到的情况，并非SDK是否自动播放，与自动播放策略AutoPlayPolicy的取值并非一一对应 <br/>
         *
         * 如自动播放策略为AutoPlayPolicy.WIFI，但此时用户网络为4G环境，在用户看来就是手工播放的
         */
        nativeExpressAD.setVideoPlayPolicy(getVideoPlayPolicy(VideoOption.AutoPlayPolicy.ALWAYS, activity));  // 本次拉回的视频广告，在用户看来是否为自动播放的
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

        //绑定监听
        WrapperInterstitialADListener wrapperInterstitialADListener = new WrapperInterstitialADListener();
        wrapperInterstitialADListener.setAdInfo(info);
        wrapperInterstitialADListener.setLoadListener(listener);
        wrapperInterstitialADListener.setOutListener(adListener);


        iad = new UnifiedInterstitialAD(activity, midasInteractionAd.getAppId(), midasInteractionAd.getAdId(), new WrapperInterstitialADListener());
        ((MidasInteractionAd) info.getMidasAd()).setUnifiedInterstitialAD(iad);
        iad.loadAD();
    }

    @Override
    protected void requestSelfRenderAd(Activity activity, AdInfo info, AdRequestListener listener, AdSelfRenderListener adListener) {
        MidasSelfRenderAd midasSelfRenderAd = (MidasSelfRenderAd) info.getMidasAd();
        NativeUnifiedAD mAdManager = new NativeUnifiedAD(activity, midasSelfRenderAd.getAppId(), midasSelfRenderAd.getAdId(), new NativeADUnifiedListener() {
            @Override
            public void onADLoaded(List<NativeUnifiedADData> list) {
                if (list == null || list.size() == 0) {
                    if (listener != null) {
                        listener.adError(info, 3, "没广告");
                    }
                    return;
                }
                NativeUnifiedADData nativeUnifiedADData = list.get(0);
                if (nativeUnifiedADData == null) {
                    if (listener != null) {
                        listener.adError(info, 3, "没广告");
                    }
                    return;
                }
                midasSelfRenderAd.setNativeUnifiedADData(nativeUnifiedADData);
                //请求成功回调
                if (listener != null) {
                    listener.adSuccess(info);
                }
                if (adListener != null) {
                    adListener.adSuccess(info);
                }

                //添加到缓存
                ADTool.getInstance().cacheAd(nativeUnifiedADData, info);
            }

            @Override
            public void onNoAD(AdError adError) {
                if (listener != null) {
                    listener.adError(info, adError.getErrorCode(), adError.getErrorMsg());
                }
            }
        });
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
        SplashAD splashAD = new SplashAD(activity, midasSplashAd.getAppId(), midasSplashAd.getAdId(), new SplashADListener() {
            @Override
            public void onADDismissed() {
                //添加到缓存
                ADTool.getInstance().cacheAd(((MidasSplashAd) adInfo.getMidasAd()).getSplashAD(), adInfo);
                LogUtils.d(TAG, "YLH onADDismissed:");
                if (adSplashListener != null) {
                    adSplashListener.adClose(adInfo);
                }
            }

            @Override
            public void onNoAD(AdError adError) {
                LogUtils.d(TAG, "YLH onNoAD:");
                //优量汇广告加载失败
                if (adRequestListener != null) {
                    adRequestListener.adError(adInfo, adError.getErrorCode(), adError.getErrorMsg());
                }
            }

            //广告成功展示时调用，成功展示不等于有效展示（比如广告容器高度不够）
            @Override
            public void onADPresent() {
                //优量汇成功展示的时候代表请求成功
                //请求成功回调
                if (adRequestListener != null) {
                    adRequestListener.adSuccess(adInfo);
                }

                if (adSplashListener != null) {
                    adSplashListener.adSuccess(adInfo);
                }
            }

            @Override
            public void onADClicked() {
                LogUtils.d(TAG, "YLH onADClicked:");
                if (adSplashListener != null) {
                    adSplashListener.adClicked(adInfo);
                }
            }

            //倒计时回调，返回广告还将被展示的剩余时间，单位是 ms
            @Override
            public void onADTick(long l) {
                if (adSplashListener != null) {
                    adSplashListener.adTick(adInfo, l);
                }
            }

            //广告曝光时调用，此处的曝光不等于有效曝光（如展示时长未满足）
            @Override
            public void onADExposure() {
                LogUtils.d(TAG, "YLH onADClicked:");
                if (adSplashListener != null) {
                    adSplashListener.adExposed(adInfo);
                }
            }
        }, timeOut);
        ((MidasSplashAd) adInfo.getMidasAd()).setSplashAD(splashAD);
        if (adSplashListener != null) {
            ViewGroup viewGroup = adSplashListener.getViewGroup();
            if (viewGroup != null) {
                splashAD.fetchAndShowIn(viewGroup);
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
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        //奖励数量
        int rewardAmount = midasRewardVideoAd.getRewardAmount();
        //奖励名称
        String rewardName = midasRewardVideoAd.getRewardName();
        // 1. 初始化激励视频广告
        RewardVideoAD rewardVideoAD = null;
        RewardVideoAD finalRewardVideoAD = rewardVideoAD;
        rewardVideoAD = new RewardVideoAD(activity, midasRewardVideoAd.getAppId(), midasRewardVideoAd.getAdId(), new RewardVideoADListener() {
            //广告加载成功，可在此回调后进行广告展示，此时广告过期时间确定，可通过RewardVideoAD.getExpireTimestamp()获取
            @Override
            public void onADLoad() {
                //请求成功回调
                if (adRequestListener != null) {
                    adRequestListener.adSuccess(adInfo);
                }
                //添加到缓存
                ADTool.getInstance().cacheAd(finalRewardVideoAD, adInfo);
                //广告加载成功标志
                finalRewardVideoAD.showAD();
                if (adRewardVideoListener != null) {
                    adRewardVideoListener.adSuccess(adInfo);
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
                if (adRewardVideoListener != null) {
                    adRewardVideoListener.adExposed(adInfo);
                }
            }

            //激励视频广告激励发放
            @Override
            public void onReward() {
                if (adRewardVideoListener != null) {
                    adRewardVideoListener.onVideoRewardVerify(adInfo, true, rewardAmount, rewardName);
                }
            }

            @Override
            public void onADClick() {
                if (adRewardVideoListener != null) {
                    adRewardVideoListener.adClicked(adInfo);
                }
            }

            //广告视频素材播放完毕
            @Override
            public void onVideoComplete() {
                if (adRewardVideoListener != null) {
                    adRewardVideoListener.onVideoComplete(adInfo);
                }
            }

            @Override
            public void onADClose() {
                if (adRewardVideoListener != null) {
                    adRewardVideoListener.adClose(adInfo);
                }
            }

            @Override
            public void onError(AdError adError) {
                if (adRewardVideoListener != null) {
                    adRewardVideoListener.adError(adInfo, adError.getErrorCode(), adError.getErrorMsg());
                }
            }
        });
        midasRewardVideoAd.setRewardVideoAD(rewardVideoAD);
        // 2. 加载激励视频广告
        rewardVideoAD.loadAD();
    }

    private void caheImage(NativeUnifiedADData ad) {
        String imgUrl = ad.getImgUrl();
        String icon = ad.getIconUrl();
        try {
            if (!TextUtils.isEmpty(imgUrl)) {
                cacheImg(imgUrl);
            }
            if (!TextUtils.isEmpty(icon)) {
                cacheImg(icon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
