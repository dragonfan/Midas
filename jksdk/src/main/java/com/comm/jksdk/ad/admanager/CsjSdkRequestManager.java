package com.comm.jksdk.ad.admanager;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.comm.jksdk.MidasAdSdk;
import com.comm.jksdk.ad.entity.AdInfo;
import com.comm.jksdk.ad.entity.MidasFullScreenVideoAd;
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
import com.comm.jksdk.ad.listener.SelfRenderChargeListener;
import com.comm.jksdk.ad.listener.VideoAdListener;
import com.comm.jksdk.config.TTAdManagerHolder;
import com.comm.jksdk.http.utils.LogUtils;
import com.comm.jksdk.utils.CodeFactory;
import com.comm.jksdk.utils.CollectionUtils;

import java.util.List;

/**
 * @ProjectName: MidasAdSdk
 * @Package: com.comm.jksdk.ad.admanager
 * @ClassName: CsjSdkRequestManager
 * @Description: java类作用描述
 * @Author: fanhailong
 * @CreateDate: 2019/12/2 18:21
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/2 18:21
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class CsjSdkRequestManager extends SdkRequestManager {

//    @Override
//    public void requestAd(Activity activity, AdInfo adInfo, AdRequestListener listener) {
//        //广告样式
//        String style = adInfo.getAdStyle();
//        if (Constants.AdStyle.DATU_ICON_TEXT.equals(style) || Constants.AdStyle.DATU_ICON_TEXT_BUTTON_CENTER.equals(style) || Constants.AdStyle.EXTERNAL_DIALOG_BIG_IMAGE_01.equals(style)
//                || Constants.AdStyle.DATU_ICON_TEXT_BUTTON.equals(style) || Constants.AdStyle.BIG_IMG_BUTTON_LAMP.equals(style) || Constants.AdStyle.EXTERNAL_DIALOG_BIG_IMAGE_02.equals(style)
//                || Constants.AdStyle.BIG_IMG_BUTTON.equals(style) || Constants.AdStyle.FAKE_VIDEO_IARGE_IMAGE.equals(style)) {
//            //todo请求大图广告
//            getImageAd(adInfo, listener);
//        } else if (Constants.AdStyle.OPEN_ADS.equals(style)) {
//            getAdBySplashAd(adInfo, listener);
//        } else if (Constants.AdStyle.FULL_SCREEN_VIDEO.equals(style)) {
//            getFullScreenVideoAd(adInfo, listener);
//        } else if (Constants.AdStyle.REWARD_VIDEO.equals(style)) {
//            getRewardVideoAd(adInfo, listener);
//        } else if(Constants.AdStyle.CP.equals(style)) {
//            getTemplateInsertScreenAd(activity, adInfo, listener);
//        } else if (Constants.AdStyle.CUSTOM_CP.equals(style) || Constants.AdStyle.FULLSCREEN_CP_01.equals(style)) {
//            getCustomInsertScreenAd(adInfo, listener);
//        } else if(Constants.AdStyle.FEED_TEMPLATE.equals(style)) {
//            getFeedTemplate(activity, adInfo, listener);
//        } else {
//            if (listener != null) {
//                listener.adError(adInfo, 2, "暂不支持该样式");
//            }
//        }
//    }

    @Override
    protected void requestNativeTemplateAd(Activity activity, AdInfo info, AdRequestListener listener, NativeTemplateListener adListener, AdChargeListener adChargeListener) {
        MidasNativeTemplateAd midasNativeTemplateAd = (MidasNativeTemplateAd) info.getMidasAd();
        AdSlot adSlot = new AdSlot.Builder()
                //广告位id
                .setCodeId(midasNativeTemplateAd.getAdId())
                .setSupportDeepLink(true)
                //请求广告数量为1到3条
                .setAdCount(1)
                //期望模板广告view的size,单位dp
                .setExpressViewAcceptedSize(midasNativeTemplateAd.getWidth(), 0)
                //这个参数设置即可，不影响模板广告的size
                .setImageAcceptedSize(640, 320)
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        TTAdManagerHolder.get().createAdNative(MidasAdSdk.getContext()).loadNativeExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
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
                        listener.adError(info, 1, "没请求到广告数据");
                    }
                    return;
                }
                TTNativeExpressAd ttNativeAd = ads.get(0);
                if (ttNativeAd == null) {
                    if (listener != null) {
                        listener.adError(info, 1, "没请求到广告数据");
                    }
                    return;
                }
                midasNativeTemplateAd.setTtNativeExpressAd(ttNativeAd);

                ttNativeAd.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        if (adChargeListener != null) {
                            adChargeListener.adClicked(midasNativeTemplateAd);
                        }
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        if (adChargeListener != null) {
                            adChargeListener.adExposed(midasNativeTemplateAd);
                        }
                    }

                    @Override
                    public void onRenderFail(View view, String msg, int code) {
//                Log.e("ExpressView","render fail:"+(System.currentTimeMillis() - startTime));
                        if (adChargeListener != null) {
                            adChargeListener.adError(midasNativeTemplateAd, code, msg);
                        }
                    }

                    @Override
                    public void onRenderSuccess(View view, float width, float height) {
                        midasNativeTemplateAd.setAddView(view);
                        if (adChargeListener != null) {
                            adChargeListener.adSuccess(midasNativeTemplateAd);
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
        });
    }

    @Override
    protected void requestInteractionAd(Activity activity, AdInfo info, AdRequestListener listener, InteractionListener adListener) {
        MidasInteractionAd midasInteractionAd = (MidasInteractionAd) info.getMidasAd();
        float expressViewWidth = 300;
        float expressViewHeight = 300;
        AdSlot adSlot = new AdSlot.Builder()
                //广告位id
                .setCodeId(midasInteractionAd.getAdId())
                .setSupportDeepLink(true)
                //请求广告数量为1到3条
                .setAdCount(1)
                //期望模板广告view的size,单位dp
                .setExpressViewAcceptedSize(expressViewWidth, expressViewHeight)
                //这个参数设置即可，不影响模板广告的size
                .setImageAcceptedSize(640, 320)
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        TTAdManagerHolder.get().createAdNative(activity.getApplicationContext()).loadInteractionExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
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

                ((MidasInteractionAd) info.getMidasAd()).setTtNativeExpressAd(ttNativeExpressAd);
                ttNativeExpressAd.setExpressInteractionListener(new TTNativeExpressAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int i) {
                        if (adListener != null) {
                            adListener.adClicked(info);
                        }
                    }

                    @Override
                    public void onAdShow(View view, int i) {
                        if (adListener != null) {
                            adListener.adExposed(info);
                        }
                    }

                    @Override
                    public void onRenderFail(View view, String s, int i) {
                        if (adListener != null) {
                            adListener.adError(info, i, s);
                        }
                    }

                    @Override
                    public void onRenderSuccess(View view, float v, float v1) {
                        if (adListener != null) {
                            adListener.adSuccess(info);
                        }
                        ttNativeExpressAd.showInteractionExpressAd(activity);
                    }

                    @Override
                    public void onAdDismiss() {
                        if (adListener != null) {
                            adListener.adClose(info);
                        }
                    }
                });
                ttNativeExpressAd.render();
            }
        });
    }

    @Override
    protected void requestSelfRenderAd(Activity activity, AdInfo info, AdRequestListener listener, SelfRenderAdListener adListener) {
        MidasSelfRenderAd midasSelfRenderAd = (MidasSelfRenderAd) info.getMidasAd();
        //step1:初始化sdk
        TTAdManager ttAdManager = TTAdManagerHolder.get();
        //step2:创建TTAdNative对象,用于调用广告请求接口
        TTAdNative mTTAdNative = ttAdManager.createAdNative(MidasAdSdk.getContext());
        //step3:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
//        TTAdManagerHolder.get().requestPermissionIfNecessary(mContext);

        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(midasSelfRenderAd.getAdId())
                .setSupportDeepLink(true)
                .setImageAcceptedSize(640, 320)
                .setAdCount(3)
                .build();
        mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
            @Override
            public void onError(int i, String s) {
                LogUtils.d(TAG, "onNoAD->请求穿山甲失败,ErrorCode:" + i + ",ErrorMsg:" + s);
                if (listener != null) {
                    listener.adError(info, i, s);
                }
            }

            @Override
            public void onFeedAdLoad(List<TTFeedAd> list) {
                LogUtils.d(TAG, "onADLoaded->请求穿山甲成功");
                if (CollectionUtils.isEmpty(list)) {
                    if (listener != null) {
                        listener.adError(info, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
                    }
                    return;
                }
                TTFeedAd ttFeedAd = list.get(0);
                if (ttFeedAd == null) {
                    if (listener != null) {
                        listener.adError(info, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
                    }
                    return;
                }
                midasSelfRenderAd.setTtFeedAd(ttFeedAd);
                if (adListener != null) {
                    adListener.adSuccess(info);
                }
            }
        });
    }

    @Override
    protected void requestFullScreenVideoAd(Activity activity, AdInfo info, AdRequestListener listener, VideoAdListener adListener) {
        MidasFullScreenVideoAd midasFullScreenVideoAd = (MidasFullScreenVideoAd) info.getMidasAd();
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(midasFullScreenVideoAd.getAdId())
                .setSupportDeepLink(true)
                .setImageAcceptedSize(720, 1280)
                .setOrientation(TTAdConstant.VERTICAL)//必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .build();
        //step5:请求广告
        TTAdManagerHolder.get().createAdNative(MidasAdSdk.getContext()).loadFullScreenVideoAd(adSlot, new TTAdNative.FullScreenVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                LogUtils.e(TAG, "loadFullScreenVideoAd error:" + code + " message:" + message);
                if (listener != null) {
                    listener.adError(info, code, message);
                }
            }

            @Override
            public void onFullScreenVideoAdLoad(TTFullScreenVideoAd ad) {
                if (ad != null) {
                    midasFullScreenVideoAd.setTtFullScreenVideoAd(ad);
                    ad.setFullScreenVideoAdInteractionListener(new TTFullScreenVideoAd.FullScreenVideoAdInteractionListener() {

                        @Override
                        public void onAdShow() {
                            if (adListener != null) {
                                adListener.adExposed(info);
                            }
                        }

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

                        @Override
                        public void onVideoComplete() {
                            if (adListener != null) {
                                adListener.onVideoComplete(info);
                            }
                        }

                        @Override
                        public void onSkippedVideo() {

                        }
                    });
                    //step6:在获取到广告后展示
                    ad.showFullScreenVideoAd(activity);
                    if (adListener != null) {
                        adListener.adSuccess(info);
                    }
                } else {
                    if (listener != null) {
                        listener.adError(info, 1, "请求广告为空");
                    }
                }
            }

            @Override
            public void onFullScreenVideoCached() {
//                if (listener != null && adInfo.isPreload()) {
//                    listener.adSuccess(adInfo);
//                }
            }
        });
    }

    @Override
    public void requestSplashAd(Activity activity, AdInfo adInfo, AdRequestListener adRequestListener, AdSplashListener adSplashListener) {
        MidasSplashAd midasSplashAd = (MidasSplashAd) adInfo.getMidasAd();
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(midasSplashAd.getAdId())
                .setSupportDeepLink(true)
                .setImageAcceptedSize(720, 1280)
                .build();
        TTAdManagerHolder.get().createAdNative(MidasAdSdk.getContext()).loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            public void onError(int errorCode, String errorMsg) {
                LogUtils.e(TAG, "csj errorCode:" + errorCode + " errorMsg:" + errorMsg);
                if (adRequestListener != null) {
                    adRequestListener.adError(adInfo, 1, "广告对象为空");
                }

            }

            @Override
            public void onTimeout() {
                LogUtils.e(TAG, "csj splash request time out.");
                if (adRequestListener != null) {
                    adRequestListener.adError(adInfo, 2, "请求开屏广告超时");
                }
            }

            @Override
            public void onSplashAdLoad(TTSplashAd ttSplashAd) {
                if (ttSplashAd != null) {
                    LogUtils.d(TAG, "csj onSplashAdLoad:" + ttSplashAd.getInteractionType());
                    MidasSplashAd midasSplashAd = (MidasSplashAd) adInfo.getMidasAd();
                    midasSplashAd.setTtSplashAd(ttSplashAd);
                    midasSplashAd.setAddView(ttSplashAd.getSplashView());
                    if (adSplashListener != null) {
                        adSplashListener.adSuccess(adInfo);
                    }
                    ttSplashAd.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                        @Override
                        public void onAdClicked(View view, int i) {
                            if (adSplashListener != null) {
                                adSplashListener.adClicked(adInfo);
                            }
                        }

                        @Override
                        public void onAdShow(View view, int i) {
                            if (adSplashListener != null) {
                                adSplashListener.adExposed(adInfo);
                            }
                        }

                        //开屏广告跳过
                        @Override
                        public void onAdSkip() {
                            if (adSplashListener != null) {
                                adSplashListener.adClose(adInfo);
                            }
                        }

                        //倒计时结束
                        @Override
                        public void onAdTimeOver() {
                            if (adSplashListener != null) {
                                adSplashListener.adClose(adInfo);
                            }
                        }
                    });
                } else {
                    if (adRequestListener != null) {
                        adRequestListener.adError(adInfo, 1, "广告对象为空");
                    }
                }
            }
        });
    }

    @Override
    public void requestRewardVideoAd(Activity activity, AdInfo adInfo, AdRequestListener adRequestListener, VideoAdListener videoAdListener) {
        MidasRewardVideoAd midasRewardVideoAd = (MidasRewardVideoAd) adInfo.getMidasAd();
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(midasRewardVideoAd.getAdId())
                .setSupportDeepLink(true)
                .setImageAcceptedSize(720, 1280)
                //奖励的名称
                .setRewardName(TextUtils.isEmpty(midasRewardVideoAd.getRewardName()) ? "金币" : midasRewardVideoAd.getRewardName())
                //奖励的数量
                .setRewardAmount(midasRewardVideoAd.getRewardAmount() == 0 ? 3 : midasRewardVideoAd.getRewardAmount())
                //用户id,必传参数
                .setUserID(TextUtils.isEmpty(midasRewardVideoAd.getUserId()) ? "" : midasRewardVideoAd.getUserId())
                //附加参数，可选
                .setMediaExtra("media_extra")
                //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .setOrientation(midasRewardVideoAd.getOrientation() == 2 ? TTAdConstant.HORIZONTAL : TTAdConstant.VERTICAL)
                .build();
        //step5:请求广告
        TTAdManagerHolder.get().createAdNative(MidasAdSdk.getContext()).loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                LogUtils.e(TAG, "rewardVideoAd error:" + code + " message:" + message);
                if (adRequestListener != null) {
                    adRequestListener.adError(adInfo, code, message);
                }
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRewardVideoCached() {
                LogUtils.e(TAG, "onRewardVideoCached");
//                if (listener != null && info.isPreload()) {
//                    listener.adSuccess(info);
//                }
//                if (videoAdListener != null) {
//                    videoAdListener.adSuccess(adInfo);
//                }
            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd mttRewardVideoAd) {
                LogUtils.d(TAG, "rewardVideoAd loaded");
//                if (mttRewardVideoAd != null) {
//                    info.setTtRewardVideoAd(mttRewardVideoAd);
//                    if (listener != null && !info.isPreload()) {
//                        listener.adSuccess(info);
//                    }
//                } else {
//                    if (listener != null) {
//                        listener.adError(info, 1, "请求结果为空");
//                    }
//                }
                if (mttRewardVideoAd != null) {
                    ((MidasRewardVideoAd) adInfo.getMidasAd()).setTtRewardVideoAd(mttRewardVideoAd);
                    mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {
                        @Override
                        public void onAdShow() {
                            if (videoAdListener != null) {
                                videoAdListener.adExposed(adInfo);
                            }
                        }

                        @Override
                        public void onAdVideoBarClick() {
                            LogUtils.d(TAG, "rewardVideoAd bar click");
                            if (videoAdListener != null) {
                                videoAdListener.adClicked(adInfo);
                            }
                        }

                        @Override
                        public void onAdClose() {
                            LogUtils.d(TAG, "rewardVideoAd close");
                            if (videoAdListener != null) {
                                videoAdListener.adClose(adInfo);
                            }
                        }

                        //视频播放完成回调
                        @Override
                        public void onVideoComplete() {
                            LogUtils.d(TAG, "rewardVideoAd complete");
                            if (videoAdListener != null) {
                                videoAdListener.onVideoComplete(adInfo);
                            }
                        }

                        @Override
                        public void onVideoError() {
                            LogUtils.d(TAG, "rewardVideoAd error");
                            if (videoAdListener != null) {
                                videoAdListener.adError(adInfo, 1, "rewardVideoAd error");
                            }
                        }

                        //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                        @Override
                        public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                            LogUtils.d(TAG, "verify:" + rewardVerify + " amount:" + rewardAmount + " name:" + rewardName);
                            if (videoAdListener != null) {
                                videoAdListener.onVideoRewardVerify(adInfo, rewardVerify, rewardAmount, rewardName);
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
                    if (videoAdListener != null) {
                        videoAdListener.adSuccess(adInfo);
                    }
                } else {
                    if (adRequestListener != null) {
                        adRequestListener.adError(adInfo, 1, "请求结果为空");
                    }
                }
            }
        });
    }

    /**
     * 获取信息流模板广告
     *
     * @param activity
     * @param info
     * @param listener
     */
    private void getFeedTemplate(Activity activity, AdInfo info, AdRequestListener listener) {
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(info.getAdId()) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(info.getWidth(), 0) //期望模板广告view的size,单位dp
                .setImageAcceptedSize(640, 320)//这个参数设置即可，不影响模板广告的size
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        TTAdManagerHolder.get().createAdNative(MidasAdSdk.getContext()).loadNativeExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
//                TToast.show(NativeExpressActivity.this, "load error : " + code + ", " + message);
//                mExpressContainer.removeAllViews();
                LogUtils.e(TAG, "loadNativeAd code:" + code + " message:" + message);
                if (listener != null) {
                    listener.adError(info, code, message);
                }
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
//                if (ads == null || ads.size() == 0){
//                    return;
//                }
//                mTTAd = ads.get(0);
//                bindAdListener(mTTAd);
//                startTime = System.currentTimeMillis();
//                mTTAd.render();
                if (CollectionUtils.isEmpty(ads)) {
                    if (listener != null) {
                        listener.adError(info, 1, "没请求到广告数据");
                    }
                    return;
                }
                TTNativeExpressAd ttNativeAd = ads.get(0);
                if (ttNativeAd == null) {
                    if (listener != null) {
                        listener.adError(info, 1, "没请求到广告数据");
                    }
                    return;
                }
                info.setTtNativeExpressAd(ttNativeAd);
                if (listener != null) {
                    listener.adSuccess(info);
                }
            }
        });
    }

    /**
     * 获取自定义插屏广告
     *
     * @param info
     * @param listener
     */
    private void getCustomInsertScreenAd(AdInfo info, AdRequestListener listener) {
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(info.getAdId())
                .setSupportDeepLink(true)
                .setAdCount(1)
                .setImageAcceptedSize(720, 1280)
                //请求原生广告时候，请务必调用该方法，设置参数为TYPE_BANNER或TYPE_INTERACTION_AD
                .setNativeAdType(AdSlot.TYPE_INTERACTION_AD)
                .build();

        //step5:请求广告，对请求回调的广告作渲染处理
        TTAdManagerHolder.get().createAdNative(MidasAdSdk.getContext()).loadNativeAd(adSlot, new TTAdNative.NativeAdListener() {
            @Override
            public void onError(int code, String message) {
                LogUtils.e(TAG, "loadNativeAd code:" + code + " message:" + message);
                if (listener != null) {
                    listener.adError(info, code, message);
                }
            }

            @Override
            public void onNativeAdLoad(List<TTNativeAd> ads) {
                if (CollectionUtils.isEmpty(ads)) {
                    if (listener != null) {
                        listener.adError(info, 1, "没请求到广告数据");
                    }
                    return;
                }
                TTNativeAd ttNativeAd = ads.get(0);
                if (ttNativeAd == null) {
                    if (listener != null) {
                        listener.adError(info, 1, "没请求到广告数据");
                    }
                    return;
                }
                info.setTtNativeAd(ttNativeAd);
                if (listener != null) {
                    listener.adSuccess(info);
                }
            }
        });
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
        TTAdManagerHolder.get().createAdNative(activity.getApplicationContext()).loadInteractionExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
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
    private void getRewardVideoAd(AdInfo info, AdRequestListener listener) {
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(info.getAdId())
                .setSupportDeepLink(true)
                .setImageAcceptedSize(720, 1280)
                //奖励的名称
                .setRewardName(TextUtils.isEmpty(info.getRewardName()) ? "金币" : info.getRewardName())
                //奖励的数量
                .setRewardAmount(info.getRewardAmount() == 0 ? 3 : info.getRewardAmount())
                //用户id,必传参数
                .setUserID(TextUtils.isEmpty(info.getUserId()) ? "" : info.getUserId())
                //附加参数，可选
                .setMediaExtra("media_extra")
                //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .setOrientation(TTAdConstant.VERTICAL)
                .build();
        //step5:请求广告
        TTAdManagerHolder.get().createAdNative(MidasAdSdk.getContext()).loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                LogUtils.e(TAG, "rewardVideoAd error:" + code + " message:" + message);
                if (listener != null) {
                    listener.adError(info, code, message);
                }
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRewardVideoCached() {
                if (listener != null && info.isPreload()) {
                    listener.adSuccess(info);
                }
                LogUtils.e(TAG, "onRewardVideoCached");
            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd mttRewardVideoAd) {
                LogUtils.d(TAG, "rewardVideoAd loaded");
                if (mttRewardVideoAd != null) {
                    info.setTtRewardVideoAd(mttRewardVideoAd);
                    if (listener != null && !info.isPreload()) {
                        listener.adSuccess(info);
                    }
                } else {
                    if (listener != null) {
                        listener.adError(info, 1, "请求结果为空");
                    }
                }
            }
        });
    }

    /**
     * 获取全屏视频广告
     *
     * @param adInfo
     * @param listener
     */
    private void getFullScreenVideoAd(AdInfo adInfo, AdRequestListener listener) {
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(adInfo.getAdId())
                .setSupportDeepLink(true)
                .setImageAcceptedSize(720, 1280)
                .setOrientation(TTAdConstant.VERTICAL)//必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .build();
        //step5:请求广告
        TTAdManagerHolder.get().createAdNative(MidasAdSdk.getContext()).loadFullScreenVideoAd(adSlot, new TTAdNative.FullScreenVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                LogUtils.e(TAG, "loadFullScreenVideoAd error:" + code + " message:" + message);
                if (listener != null) {
                    listener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
                }
//                adError(code, message);
//                firstAdError(code, message);
            }

            @Override
            public void onFullScreenVideoAdLoad(TTFullScreenVideoAd ad) {
                if (ad != null) {
                    adInfo.setTtFullScreenVideoAd(ad);
                    if (listener != null && !adInfo.isPreload()) {
                        listener.adSuccess(adInfo);
                    }
                } else {
                    if (listener != null) {
                        listener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
                    }
                }
            }

            @Override
            public void onFullScreenVideoCached() {
                if (listener != null && adInfo.isPreload()) {
                    listener.adSuccess(adInfo);
                }
            }
        });
    }

    /**
     * 请求开屏广告
     */
    private void getAdBySplashAd(AdInfo adInfo, AdRequestListener listener) {
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(adInfo.getAdId())
                .setSupportDeepLink(true)
                .setImageAcceptedSize(720, 1280)
                .build();
        TTAdManagerHolder.get().createAdNative(MidasAdSdk.getContext()).loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            public void onError(int errorCode, String errorMsg) {
                LogUtils.e(TAG, "csj errorCode:" + errorCode + " errorMsg:" + errorMsg);
                if (listener != null) {
                    listener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
                }
            }

            @Override
            public void onTimeout() {
                LogUtils.e(TAG, "csj splash request time out.");
                if (listener != null) {
                    listener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
                }
            }

            @Override
            public void onSplashAdLoad(TTSplashAd ttSplashAd) {
                if (ttSplashAd != null) {
                    LogUtils.d(TAG, "csj onSplashAdLoad:" + ttSplashAd.getInteractionType());
                    adInfo.setTtSplashAd(ttSplashAd);
                    if (listener != null) {
                        listener.adSuccess(adInfo);
                    }
                } else {
                    if (listener != null) {
                        listener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
                    }
                }
            }
        });
    }

    /**
     * 获取图片类广告
     *
     * @param adInfo
     */
    public void getImageAd(AdInfo adInfo, AdRequestListener listener) {
        //step1:初始化sdk
        TTAdManager ttAdManager = TTAdManagerHolder.get();
        //step2:创建TTAdNative对象,用于调用广告请求接口
        TTAdNative mTTAdNative = ttAdManager.createAdNative(MidasAdSdk.getContext());
        //step3:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
//        TTAdManagerHolder.get().requestPermissionIfNecessary(mContext);

        LogUtils.d(TAG, "getImageAd->请求穿山甲图文类广告");

        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(adInfo.getAdId().trim())
                .setSupportDeepLink(true)
                .setImageAcceptedSize(640, 320)
                .setAdCount(1)
                .build();
        mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
            @Override
            public void onError(int i, String s) {
                LogUtils.d(TAG, "onNoAD->请求穿山甲失败,ErrorCode:" + i + ",ErrorMsg:" + s);
                if (listener != null) {
                    listener.adError(adInfo, i, s);
                }
            }

            @Override
            public void onFeedAdLoad(List<TTFeedAd> list) {
                LogUtils.d(TAG, "onADLoaded->请求穿山甲成功");
                if (CollectionUtils.isEmpty(list)) {
                    if (listener != null) {
                        listener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
                    }
                    return;
                }
                TTFeedAd ttFeedAd = list.get(0);
                if (ttFeedAd == null) {
                    if (listener != null) {
                        listener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
                    }
                    return;
                }
                caheImage(ttFeedAd);
                String title = ttFeedAd.getTitle();
                adInfo.setAdTitle(title);
                if (TTAdConstant.INTERACTION_TYPE_DOWNLOAD == ttFeedAd.getInteractionType()) {
                    adInfo.setAdClickType(1);
                } else {
                    adInfo.setAdClickType(2);
                }
                adInfo.setTtFeedAd(ttFeedAd);
                if (listener != null) {
                    listener.adSuccess(adInfo);
                }
            }
        });
    }

    private void caheImage(TTFeedAd ttFeedAd) {
        if (ttFeedAd == null) {
            return;
        }
        TTImage image = ttFeedAd.getImageList().get(0);
        TTImage icon = ttFeedAd.getIcon();
        if (image != null && image.isValid() && icon != null && icon.isValid()) {
            try {
                cacheImg(image.getImageUrl(), icon.getImageUrl());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
