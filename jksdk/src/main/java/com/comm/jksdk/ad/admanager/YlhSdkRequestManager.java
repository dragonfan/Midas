package com.comm.jksdk.ad.admanager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.comm.jksdk.ad.entity.AdInfo;
import com.comm.jksdk.ad.entity.MidasInteractionAd;
import com.comm.jksdk.ad.entity.MidasNativeTemplateAd;
import com.comm.jksdk.ad.entity.MidasRewardVideoAd;
import com.comm.jksdk.ad.entity.MidasSelfRenderAd;
import com.comm.jksdk.ad.entity.MidasSplashAd;
import com.comm.jksdk.ad.listener.AdChargeListener;
import com.comm.jksdk.ad.listener.AdRequestListener;
import com.comm.jksdk.ad.listener.AdSplashListener;
import com.comm.jksdk.ad.listener.InteractionListener;
import com.comm.jksdk.ad.listener.NativeTemplateListener;
import com.comm.jksdk.ad.listener.SelfRenderAdListener;
import com.comm.jksdk.ad.listener.VideoAdListener;
import com.comm.jksdk.config.TTAdManagerHolder;
import com.comm.jksdk.http.utils.LogUtils;
import com.comm.jksdk.utils.CollectionUtils;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.interstitial2.UnifiedInterstitialADListener;
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
public class YlhSdkRequestManager extends SdkRequestManager implements NativeADUnifiedListener {

    // 广告请求数量
    protected final static int REQUEST_AD_COUNTS = 1;

    private final int MAX_DURATION = 30;


    @Override
    protected void requestNativeTemplateAd(Activity activity, AdInfo info, AdRequestListener listener,
                                           NativeTemplateListener adListener, AdChargeListener adChargeListener) {
        MidasNativeTemplateAd midasNativeTemplateAd = (MidasNativeTemplateAd) info.getMidasAd();
        NativeExpressAD nativeExpressAD = new NativeExpressAD(activity, new ADSize((int) midasNativeTemplateAd.getWidth(), ADSize.AUTO_HEIGHT), midasNativeTemplateAd.getAppId(), midasNativeTemplateAd.getAdId(), new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onADLoaded(List<NativeExpressADView> list) {
                NativeExpressADView nativeExpressADView = list.get(0);
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
                if (adListener != null) {
                    adListener.adSuccess(info);
                }
            }

            //NativeExpressADView 渲染广告失败
            @Override
            public void onRenderFail(NativeExpressADView nativeExpressADView) {
                LogUtils.d(TAG, "YLH onRenderFail:");
//                if (adListener != null) {
//                    adListener.adError(midasNativeTemplateAd, 2, "on render fail");
//                }
            }

            //NativeExpressADView 渲染广告成功
            @Override
            public void onRenderSuccess(NativeExpressADView nativeExpressADView) {
                LogUtils.d(TAG, "YLH onRenderSuccess:");
                if (adChargeListener != null) {
                    adChargeListener.adSuccess(midasNativeTemplateAd);
                }
            }

            @Override
            public void onADExposure(NativeExpressADView nativeExpressADView) {
                if (adChargeListener != null) {
                    adChargeListener.adExposed(midasNativeTemplateAd);
                }
            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {
                if (adChargeListener != null) {
                    adChargeListener.adClicked(midasNativeTemplateAd);
                }
            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {
                if (adChargeListener != null) {
                    adChargeListener.adClose(midasNativeTemplateAd);
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
    protected void requestInteractionAd(Activity activity, AdInfo info, AdRequestListener listener, InteractionListener adListener) {
        MidasInteractionAd midasInteractionAd = (MidasInteractionAd) info.getMidasAd();
        if (iad != null) {
            iad.close();
            iad.destroy();
            iad = null;
        }
        iad = new UnifiedInterstitialAD(activity, midasInteractionAd.getAppId(), midasInteractionAd.getAdId(), new UnifiedInterstitialADListener() {
            @Override
            public void onADReceive() {
                //广告加载成功
                if (iad != null) {
                    iad.showAsPopupWindow();
                }
                if (adListener != null) {
                    adListener.adSuccess(info);
                }
            }

            @Override
            public void onNoAD(AdError adError) {
                if (listener != null) {
                    listener.adError(info, adError.getErrorCode(), adError.getErrorMsg());
                }
            }

            @Override
            public void onADOpened() {

            }

            @Override
            public void onADExposure() {
                if (adListener != null) {
                    adListener.adExposed(info);
                }
            }

            @Override
            public void onADClicked() {
                if (adListener != null) {
                    adListener.adClicked(info);
                }
            }

            @Override
            public void onADLeftApplication() {

            }

            @Override
            public void onADClosed() {
                if (adListener != null) {
                    adListener.adClose(info);
                }
            }
        });
        ((MidasInteractionAd) info.getMidasAd()).setUnifiedInterstitialAD(iad);
        iad.loadAD();
    }

    @Override
    protected void requestSelfRenderAd(Activity activity, AdInfo info, AdRequestListener listener, SelfRenderAdListener adListener) {
        MidasSelfRenderAd midasSelfRenderAd = (MidasSelfRenderAd) info.getMidasAd();
        NativeUnifiedAD mAdManager = new NativeUnifiedAD(activity, midasSelfRenderAd.getAppId(), midasSelfRenderAd.getAdId(), new NativeADUnifiedListener() {
            @Override
            public void onADLoaded(List<NativeUnifiedADData> list) {
                if (CollectionUtils.isEmpty(list)) {
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
                if (listener != null) {
                    listener.adSuccess(info);
                }
                if (adListener != null) {
                    adListener.adSuccess(info);
                }
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

//        mAdManager.setVideoPlayPolicy(NativeADUnifiedSampleActivity.getVideoPlayPolicy(getIntent(), this)); // 本次拉回的视频广告，在用户看来是否为自动播放的
        mAdManager.setVideoADContainerRender(VideoOption.VideoADContainerRender.SDK); // 视频播放前，用户看到的广告容器是由SDK渲染的

        mAdManager.loadData(3);
    }

    @Override
    protected void requestFullScreenVideoAd(Activity activity, AdInfo info, AdRequestListener listener, VideoAdListener adListener) {
        if (listener != null) {
            listener.adError(info, 3, "优量汇暂不支持全屏视频");
        }
    }

    @Override
    public void requestSplashAd(Activity activity, AdInfo adInfo, AdRequestListener adRequestListener, AdSplashListener adSplashListener) {
        MidasSplashAd midasSplashAd = (MidasSplashAd) adInfo.getMidasAd();
        SplashAD splashAD = new SplashAD(activity, midasSplashAd.getAppId(), midasSplashAd.getAdId(), new SplashADListener() {
            @Override
            public void onADDismissed() {
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

            }
            //广告曝光时调用，此处的曝光不等于有效曝光（如展示时长未满足）
            @Override
            public void onADExposure() {
                LogUtils.d(TAG, "YLH onADClicked:");
                if (adSplashListener != null) {
                    adSplashListener.adExposed(adInfo);
                }
            }
        });
        ((MidasSplashAd)adInfo.getMidasAd()).setSplashAD(splashAD);
        if (adSplashListener != null) {
            ViewGroup viewGroup = adSplashListener.getViewGroup();
            if (viewGroup != null) {
                splashAD.fetchAndShowIn(viewGroup);
            }
        }
    }

    @Override
    public void requestRewardVideoAd(Activity activity, AdInfo adInfo, AdRequestListener adRequestListener, VideoAdListener videoAdListener) {
        if (activity == null) {
            throw new NullPointerException("loadFullScreenVideoAd activity is null");
        }
        String REWARD_VIDEO_AD_POS_ID_UNSUPPORT_H = "5040942242835423";//不支持竖版出横版视频
        MidasRewardVideoAd midasRewardVideoAd = (MidasRewardVideoAd) adInfo.getMidasAd();
        int orientation = midasRewardVideoAd.getOrientation();
        if (orientation == 2) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        // 1. 初始化激励视频广告
        RewardVideoAD rewardVideoAD = null;
        RewardVideoAD finalRewardVideoAD = rewardVideoAD;
        rewardVideoAD = new RewardVideoAD(activity, midasRewardVideoAd.getAppId(), midasRewardVideoAd.getAdId(), new RewardVideoADListener() {
            @Override
            public void onADLoad() {
                //请求成功回调
                if (adRequestListener != null) {
                    adRequestListener.adSuccess(adInfo);
                }

                //广告加载成功标志
                finalRewardVideoAD.showAD();
                if (videoAdListener != null) {
                    videoAdListener.adSuccess(adInfo);
                }
            }

            @Override
            public void onVideoCached() {
                //视频素材缓存成功，可在此回调后进行广告展示

            }

            @Override
            public void onADShow() {

            }

            @Override
            public void onADExpose() {
                if (videoAdListener != null) {
                    videoAdListener.adExposed(adInfo);
                }
            }

            @Override
            public void onReward() {

            }

            @Override
            public void onADClick() {
                if (videoAdListener != null) {
                    videoAdListener.adClicked(adInfo);
                }
            }

            @Override
            public void onVideoComplete() {
                if (videoAdListener != null) {
                    videoAdListener.onVideoComplete(adInfo);
                }
            }

            @Override
            public void onADClose() {
                if (videoAdListener != null) {
                    videoAdListener.adClose(adInfo);
                }
            }

            @Override
            public void onError(AdError adError) {
                if (videoAdListener != null) {
                    videoAdListener.adError(adInfo, adError.getErrorCode(), adError.getErrorMsg());
                }
            }
        });
        midasRewardVideoAd.setRewardVideoAD(rewardVideoAD);
        // 2. 加载激励视频广告
        rewardVideoAD.loadAD();
    }

    /**
     * 获取信息流模板广告
     *
     * @param activity
     * @param info
     * @param listener
     */
    private void getFeedTemplate(Activity activity, AdInfo info, AdRequestListener listener) {
        if (listener != null) {
            listener.adSuccess(info);
        }
    }

    /**
     * 请求图片广告
     */
    protected void getAdByBigImg(Activity activity, AdInfo adInfo, AdRequestListener listener) {
        LogUtils.d(TAG, "onADLoaded->请求优量汇广告");
        NativeUnifiedAD mAdManager = new NativeUnifiedAD(activity, adInfo.getAdAppid(), adInfo.getAdId(), new NativeADUnifiedListener() {
            @Override
            public void onNoAD(AdError adError) {
                LogUtils.d(TAG, "onNoAD->请求优量汇失败,ErrorCode:" + adError.getErrorCode() + ",ErrorMsg:" + adError.getErrorMsg());
                if (listener != null) {
                    listener.adError(adInfo, adError.getErrorCode(), adError.getErrorMsg());
                }
            }

            @Override
            public void onADLoaded(List<NativeUnifiedADData> list) {
                if (CollectionUtils.isEmpty(list)) {
                    if (listener != null) {
                        listener.adError(adInfo, 1, "广告数据为空");
                    }
                    return;
                }
                NativeUnifiedADData nativeUnifiedADData = list.get(0);
                if (nativeUnifiedADData == null) {
                    return;
                }
                caheImage(nativeUnifiedADData);
                String title = nativeUnifiedADData.getTitle();
                adInfo.setAdTitle(title);
                if (nativeUnifiedADData.isAppAd()) {
                    adInfo.setAdClickType(1);
                } else {
                    adInfo.setAdClickType(2);
                }
                adInfo.setNativeUnifiedADData(nativeUnifiedADData);
                if (listener != null) {
                    listener.adSuccess(adInfo);
                }
            }
        });
        mAdManager.loadData(REQUEST_AD_COUNTS);
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

    /**
     * 获取自定义插屏广告
     *
     * @param info
     * @param listener
     */
    private void getCustomInsertScreenAd(Activity activity, AdInfo info, AdRequestListener listener) {
        if (listener != null) {
            listener.adSuccess(info);
        }
    }

    /**
     * 获取模板插屏广告
     *
     * @param info
     * @param listener
     */
    private void getTemplateInsertScreenAd(Activity activity, AdInfo info, AdRequestListener listener) {
        float expressViewWidth = 300;
        float expressViewHeight = 300;
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(info.getAdId()) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(expressViewWidth, expressViewHeight) //期望模板广告view的size,单位dp
                .setImageAcceptedSize(640, 320)//这个参数设置即可，不影响模板广告的size
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        TTAdManagerHolder.get().createAdNative(activity).loadInteractionExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                if (listener != null) {
                    listener.adError(info, code, message);
                }
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (CollectionUtils.isEmpty(ads)) {
                    if (listener != null) {
                        listener.adError(info, 1, "广告获取为空");
                    }
                    return;
                }
                TTNativeExpressAd ttNativeExpressAd = ads.get(0);
                if (ttNativeExpressAd == null) {
                    if (listener != null) {
                        listener.adError(info, 1, "广告获取为空");
                    }
                    return;
                }
                info.setTtNativeExpressAd(ttNativeExpressAd);
                if (listener != null) {
                    listener.adSuccess(info);
                }
            }
        });
    }

    /**
     * 获取激励视频广告
     *
     * @param info
     * @param listener
     */
    private void getRewardVideoAd(Activity activity, AdInfo info, AdRequestListener listener) {
        if (activity == null) {
            throw new NullPointerException("loadFullScreenVideoAd activity is null");
        }
        String REWARD_VIDEO_AD_POS_ID_UNSUPPORT_H = "5040942242835423";//不支持竖版出横版视频
        MidasRewardVideoAd midasRewardVideoAd = (MidasRewardVideoAd) info.getMidasAd();
        int orientation = midasRewardVideoAd.getOrientation();
        if (orientation == 2) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        // 1. 初始化激励视频广告
        RewardVideoAD rewardVideoAD = null;
        RewardVideoAD finalRewardVideoAD = rewardVideoAD;
        rewardVideoAD = new RewardVideoAD(activity, midasRewardVideoAd.getAppId(), midasRewardVideoAd.getAdId(), new RewardVideoADListener() {
            @Override
            public void onADLoad() {
                //广告加载成功标志
                finalRewardVideoAD.showAD();
//                adSuccess(mAdInfo);
            }

            @Override
            public void onVideoCached() {
                //视频素材缓存成功，可在此回调后进行广告展示

            }

            @Override
            public void onADShow() {

            }

            @Override
            public void onADExpose() {

            }

            @Override
            public void onReward() {

            }

            @Override
            public void onADClick() {

            }

            @Override
            public void onVideoComplete() {
            }

            @Override
            public void onADClose() {
            }

            @Override
            public void onError(AdError adError) {
            }
        });
        // 2. 加载激励视频广告
        rewardVideoAD.loadAD();
    }

    /**
     * 获取全屏视频广告
     *
     * @param adInfo
     * @param listener
     */
    private void getFullScreenVideoAd(Activity activity, AdInfo adInfo, AdRequestListener listener) {
        NativeUnifiedAD nativeUnifiedAD = new NativeUnifiedAD(activity, adInfo.getAdAppid(), adInfo.getAdId(), this);
        nativeUnifiedAD.setMaxVideoDuration(MAX_DURATION);

        /**
         * 如果广告位支持视频广告，强烈建议在调用loadData请求广告前，调用下面两个方法，有助于提高视频广告的eCPM值 <br/>
         * 如果广告位仅支持图文广告，则无需调用
         */

        /**
         * 设置本次拉取的视频广告，从用户角度看到的视频播放策略<p/>
         *
         * "用户角度"特指用户看到的情况，并非SDK是否自动播放，与自动播放策略AutoPlayPolicy的取值并非一一对应 <br/>
         *
         * 例如开发者设置了VideoOption.AutoPlayPolicy.NEVER，表示从不自动播放 <br/>
         * 但满足某种条件(如晚上10点)时，开发者调用了startVideo播放视频，这在用户看来仍然是自动播放的
         */
        // 本次拉回的视频广告，在用户看来是否为自动播放的
        nativeUnifiedAD.setVideoPlayPolicy(VideoOption.VideoPlayPolicy.AUTO);

        /**
         * 设置在视频广告播放前，用户看到显示广告容器的渲染者是SDK还是开发者 <p/>
         *
         * 一般来说，用户看到的广告容器都是SDK渲染的，但存在下面这种特殊情况： <br/>
         *
         * 1. 开发者将广告拉回后，未调用bindMediaView，而是用自己的ImageView显示视频的封面图 <br/>
         * 2. 用户点击封面图后，打开一个新的页面，调用bindMediaView，此时才会用到SDK的容器 <br/>
         * 3. 这种情形下，用户先看到的广告容器就是开发者自己渲染的，其值为VideoADContainerRender.DEV
         * 4. 如果觉得抽象，可以参考NativeADUnifiedDevRenderContainerActivity的实现
         */
        // 视频播放前，用户看到的广告容器是由SDK渲染的
        nativeUnifiedAD.setVideoADContainerRender(VideoOption.VideoADContainerRender.SDK);
        setAdInfo(adInfo);
        setAdRequestListener(listener);
        nativeUnifiedAD.loadData(1);
    }

    /**
     * 请求开屏广告
     */
    private void getAdBySplashAd(Activity activity, AdInfo adInfo, AdRequestListener listener) {
        //优量汇的开屏广告因为请求和回调展示同时进行，也不能预加载。所以直接createview
        if (listener != null) {
            listener.adSuccess(adInfo);
        }
    }


    private AdRequestListener adRequestListener;

    private AdInfo adInfo;

    public void setAdInfo(AdInfo adInfo) {
        this.adInfo = adInfo;
    }

    public void setAdRequestListener(AdRequestListener adRequestListener) {
        this.adRequestListener = adRequestListener;
    }

    @Override
    public void onADLoaded(List<NativeUnifiedADData> list) {
        if (CollectionUtils.isEmpty(list)) {
            if (adRequestListener != null) {
                adRequestListener.adError(adInfo, 1, "没请求到广告数据");
            }
            return;
        }
        NativeUnifiedADData nativeUnifiedADData = list.get(0);
        if (nativeUnifiedADData == null) {
            if (adRequestListener != null) {
                adRequestListener.adError(adInfo, 1, "没请求到广告数据");
            }
            return;
        }
        if (adInfo != null) {
            adInfo.setNativeUnifiedADData(nativeUnifiedADData);
        }
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
}
