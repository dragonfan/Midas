package com.comm.jksdk.ad.admanager;

import android.app.Activity;
import android.text.TextUtils;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.comm.jksdk.GeekAdSdk;
import com.comm.jksdk.ad.entity.AdInfo;
import com.comm.jksdk.ad.factory.RequestManagerFactory;
import com.comm.jksdk.ad.listener.AdListener;
import com.comm.jksdk.ad.listener.AdManager;
import com.comm.jksdk.ad.listener.AdPreloadingListener;
import com.comm.jksdk.ad.listener.AdRequestListener;
import com.comm.jksdk.ad.listener.AdRequestManager;
import com.comm.jksdk.ad.listener.FirstAdListener;
import com.comm.jksdk.ad.listener.VideoAdListener;
import com.comm.jksdk.ad.view.AbsAdView;
import com.comm.jksdk.ad.view.CommAdView;
import com.comm.jksdk.ad.view.chjview.CHJAdView;
import com.comm.jksdk.ad.view.chjview.ChjBigImgAdPlayLampView;
import com.comm.jksdk.ad.view.chjview.ChjBigImgAdView;
import com.comm.jksdk.ad.view.chjview.ChjBigImgFakeVideoAdView;
import com.comm.jksdk.ad.view.chjview.ChjBigImgIcTvAdView;
import com.comm.jksdk.ad.view.chjview.ChjBigImgIcTvBtAdView;
import com.comm.jksdk.ad.view.chjview.ChjBigImgIcTvBtCenterAdView;
import com.comm.jksdk.ad.view.chjview.ChjBigImgNestPlayLampView;
import com.comm.jksdk.ad.view.chjview.ChjExternalDialogBigImageView;
import com.comm.jksdk.ad.view.chjview.ChjLeftImgRightTwoTextAdView;
import com.comm.jksdk.ad.view.chjview.ChjSplashAdView;
import com.comm.jksdk.ad.view.chjview.CsjCustomInsertScreenAdView;
import com.comm.jksdk.ad.view.chjview.CsjExternalInsertScreenAdView;
import com.comm.jksdk.ad.view.chjview.CsjFullScreenVideoView;
import com.comm.jksdk.ad.view.chjview.CsjRewardVideoAdView;
import com.comm.jksdk.ad.view.chjview.CsjTemplateInsertScreenAdView;
import com.comm.jksdk.ad.view.ylhview.YlhAdView;
import com.comm.jksdk.bean.ConfigBean;
import com.comm.jksdk.cache.CacheAd;
import com.comm.jksdk.config.AdsConfig;
import com.comm.jksdk.config.TTAdManagerHolder;
import com.comm.jksdk.constant.Constants;
import com.comm.jksdk.http.utils.LogUtils;
import com.comm.jksdk.utils.AdsUtils;
import com.comm.jksdk.utils.CodeFactory;
import com.comm.jksdk.utils.CollectionUtils;
import com.comm.jksdk.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 原生广告管理器
 *
 * @author: docking
 * @date: 2019/9/7 10:22
 * @description: todo ...
 **/
public class NativeAdManger implements AdManager {
    protected final String TAG = "GeekAdSdk-->";

    private List<ConfigBean.AdListBean.AdsInfosBean> adsInfoslist = new ArrayList();

    public NativeAdManger() {
    }

    /**
     * acitvity对象,优量汇开屏、视频广用到
     */
    protected Activity mActivity;
    /**
     * 广告监听器
     */
    private AdListener mAdListener;

    /**
     * 广告位置信息
     */
    private String mPosition;

    /**
     * 广告预加载监听器
     */
    private AdPreloadingListener mAdPreloadingListener;

    /**
     * 自渲染插屏广告展示时长
     */
    private int showTimeSeconds = 3;

    /**
     * 请求方式：0 - SDK 1 - API
     */
    private int requestType = 0;

    private boolean firstRequestAd = true;

    /**
     * 插屏的百分比
     */
    private String mProgress;



    private FirstAdListener mFirstAdListener = new FirstAdListener() {
        @Override
        public void firstAdError(AdInfo adInfo, int errorCode, String errorMsg) {
            LogUtils.w(TAG, "回传--->请求第一个广告失败");

            if (CollectionUtils.isEmpty(adsInfoslist)) {
                if (mAdListener != null) {
                    mAdListener.adError(adInfo, errorCode, errorMsg);
                }
                return;
            }
            ConfigBean.AdListBean.AdsInfosBean mAdsInfosBean = adsInfoslist.remove(0);
            if (mAdsInfosBean == null) {
                if (mAdListener != null) {
                    mAdListener.adError(adInfo, errorCode, errorMsg);
                }
                return;
            }
            firstRequestAd = false;
            againRequest(adInfo, mAdsInfosBean);
        }
    };


    @Override
    public void loadAd(Activity activity, String position, AdListener listener) {
        mAdListener = listener;
        AdInfo adInfo = new AdInfo();
        try {
            mPosition = position;
            mActivity = activity;
            //设置广告位置信息
            adInfo.setPosition(position);
            //获取本地配置信息
            readyInfo(adInfo);
            if (CollectionUtils.isEmpty(adsInfoslist)) {
                if (mAdListener != null) {
                    mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
                }
                return;
            }
            ConfigBean.AdListBean.AdsInfosBean mAdsInfosBean = adsInfoslist.remove(0);
            if (mAdsInfosBean == null) {
                if (mAdListener != null) {
                    mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
                }
                return;
            }
            againRequest(adInfo, mAdsInfosBean);
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdListener != null) {
                mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }
    }

    /**
     * 准备数据
     * @param adInfo
     */
    public void readyInfo(AdInfo adInfo){
        //获取本地配置信息
        ConfigBean.AdListBean mConfigInfoBean = AdsConfig.getInstance(GeekAdSdk.getContext()).getConfig(adInfo.getPosition());
        if (mConfigInfoBean == null) {
            if (mAdListener != null) {
                mAdListener.adError(adInfo, CodeFactory.LOCAL_INFO_EMPTY, CodeFactory.getError(CodeFactory.LOCAL_INFO_EMPTY));
            }
            return;
        }
        adInfo.setAdStyle(mConfigInfoBean.getAdStyle());
        adInfo.setAdRequestTimeOut(mConfigInfoBean.getAdRequestTimeOut());
        adsInfoslist.clear();
        adsInfoslist.addAll(mConfigInfoBean.getAdsInfos());
    }

    @Override
    public void preloadingAd(Activity activity, String position, AdPreloadingListener listener) {
        mAdPreloadingListener = listener;
        try {
            mActivity = activity;
            //获取本地配置信息
            ConfigBean.AdListBean mConfigInfoBean = AdsConfig.getInstance(GeekAdSdk.getContext()).getConfig(position);
            if (mConfigInfoBean == null) {
                if (mAdPreloadingListener != null) {
                    mAdPreloadingListener.adError(CodeFactory.LOCAL_INFO_EMPTY, CodeFactory.getError(CodeFactory.LOCAL_INFO_EMPTY));
                }
                return;
            }
            //当前广告位所对应的配置信息 存储到curAdlist
            adStyle = mConfigInfoBean.getAdStyle();
            adRequestTimeOut = mConfigInfoBean.getAdRequestTimeOut();
            adsInfoslist.clear();
            adsInfoslist.addAll(mConfigInfoBean.getAdsInfos());

            if (CollectionUtils.isEmpty(adsInfoslist)) {
                if (mAdPreloadingListener != null) {
                    mAdPreloadingListener.adError(CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
                }
                return;
            }
            ConfigBean.AdListBean.AdsInfosBean mAdsInfosBean = adsInfoslist.remove(0);
            if (mAdsInfosBean == null) {
                if (mAdPreloadingListener != null) {
                    mAdPreloadingListener.adError(CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
                }
                return;
            }
            adUnion = mAdsInfosBean.getAdUnion();
            mAdId = mAdsInfosBean.getAdId();
            mAppId = mAdsInfosBean.getAdsAppId();

//            loadCsjAd(position);
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdPreloadingListener != null) {
                mAdPreloadingListener.adError(CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }
    }


    /**
     * 轮询请求
     */
    public void againRequest(AdInfo adInfo, ConfigBean.AdListBean.AdsInfosBean adsInfosBean) {
        if (adInfo == null) {
            adInfo = new AdInfo();
        }
        //某些特有的数据清空，避免污染下一次请求数据
        adInfo.setAdTitle("");
        adInfo.setAdClickType(0);

        //广告源
        adInfo.setAdSource(adsInfosBean.getAdUnion());
        //广告id
        adInfo.setAdId(adsInfosBean.getAdId());
        //广告对应的appid
        adInfo.setAdAppid(adsInfosBean.getAdsAppId());
        //请求类型 0 - SDK 1 - API
        requestType = adsInfosBean.getRequestType();
        if (requestType == 0) {
            sdkRequest(adInfo);
        } else {
            apiRequest(adInfo);
        }
    }

    /**
     * api 请求
     */
    public void apiRequest(AdInfo adInfo){
        // TODO: 2019/12/3
    }

    /**
     * sdk 请求
     */
    public void sdkRequest(AdInfo adInfo){
        AdRequestManager adRequestManager = new RequestManagerFactory().produce(adInfo);
        if (adRequestManager == null) {
            if (mAdListener != null) {
                mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
            return;
        }
        adRequestManager.requestAd(adInfo, new AdRequestListener() {
            @Override
            public void adSuccess(AdInfo info) {
                createAdView(mActivity, info);
            }

            @Override
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                if (mFirstAdListener != null) {
                    mFirstAdListener.firstAdError(info, errorCode, errorMsg);
                }
            }
        });
    }

    /**
     * 创建广告View
     */
    private void createAdView(Activity activity, AdInfo adInfo) {
        String adSource = adInfo.getAdSource();
        if (Constants.AdType.ChuanShanJia.equals(adSource)) {
            createCsjAdView(activity, adInfo);
//            CommAdView mAdView = new CHJAdView(GeekAdSdk.getContext(), activity, adInfo.getAdStyle(), adInfo.getAdAppid(), adInfo.getAdId());
//            ((CHJAdView) mAdView).setOrientation(orientation);
//            if (!TextUtils.isEmpty(userId)) {
//                ((CHJAdView) mAdView).setUserId(userId);
//            }
//            ((CHJAdView) mAdView).setFullScreen(isFullScreen);
//            ((CHJAdView) mAdView).setShowTimeSeconds(showTimeSeconds);
//            ((CHJAdView) mAdView).setmProgress(mProgress);
        } else if (Constants.AdType.YouLiangHui.equals(adSource)) {
//            AbsAdView mAdView = new YlhAdView(GeekAdSdk.getContext(), activity, adStyle, appId, mAdId);
            createYlhAdView(activity, adInfo);
        } else {
            // 暂不处理
            if (mAdListener != null) {
                mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }
    }

    /**
     * 创建穿山甲view
     * @param activity
     * @param adInfo
     */
    private void createCsjAdView(Activity activity, AdInfo adInfo){
        if (activity == null) {
            if (mAdListener != null) {
                mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
            return;
        }
        if (activity.isFinishing()) {
            if (mAdListener != null) {
                mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
            return;
        }
        String style = adInfo.getAdStyle();
        //全屏视频
        if (Constants.AdStyle.FULL_SCREEN_VIDEO.equals(style)) {
            showCsjFullVideo(activity, adInfo);
            return;
        }
        CHJAdView adView = null;
        if (Constants.AdStyle.BIG_IMG.equals(style)) {
            adView = new ChjBigImgAdView(activity);
        } else if (Constants.AdStyle.DATU_ICON_TEXT_BUTTON.equals(style)) { // 大图_带icon文字按钮
            adView = new ChjBigImgIcTvBtAdView(activity);
        } else if (Constants.AdStyle.DATU_ICON_TEXT.equals(style)) { //大图_带icon文字
            adView = new ChjBigImgIcTvAdView(activity);
        } else if (Constants.AdStyle.DATU_ICON_TEXT_BUTTON_CENTER.equals(style)) { //大图_带icon文字按钮居中
            adView = new ChjBigImgIcTvBtCenterAdView(activity);
        } else if (Constants.AdStyle.BIG_IMG_BUTTON.equals(style)) { //大图带按钮（大图_下载播放按钮）
            adView = new ChjBigImgAdPlayLampView(activity);
        } else if (Constants.AdStyle.BIG_IMG_BUTTON_LAMP.equals(style)) { //大图带按钮带跑马灯
            adView = new ChjBigImgAdPlayLampView(activity, true);
        } else if (Constants.AdStyle.BIG_IMG_NEST.equals(style)) { //大图嵌套美女图片
            adView = new ChjBigImgNestPlayLampView(activity);
        } else if (Constants.AdStyle.BIG_IMG_NEST_LAMP.equals(style)) { //大图嵌套图片带跑马灯
            adView = new ChjBigImgNestPlayLampView(activity, true);
        } else if (Constants.AdStyle.FAKE_VIDEO_IARGE_IMAGE.equals(style)) { //假视频大图_01
            adView = new ChjBigImgFakeVideoAdView(activity);
        } else if (Constants.AdStyle.EXTERNAL_DIALOG_BIG_IMAGE_01.equals(style)) { //外部弹窗大图广告_01
            adView = new ChjExternalDialogBigImageView(activity);
        } else if (Constants.AdStyle.OPEN_ADS.equals(style)) { //开屏广告
            adView = new ChjSplashAdView(activity);
        } else if (Constants.AdStyle.REWARD_VIDEO.equals(style)) {
            adView = new CsjRewardVideoAdView(activity);
        } else if (Constants.AdStyle.CP.equals(style)) { //模板插屏
            adView = new CsjTemplateInsertScreenAdView(activity);
        } else if (Constants.AdStyle.CUSTOM_CP.equals(style) ) { //自定义插屏
            adView = new CsjCustomInsertScreenAdView(activity);
        } else if (Constants.AdStyle.FULLSCREEN_CP_01.equals(style)) { //自定义全屏插屏
            adView = new CsjCustomInsertScreenAdView(activity, true);
        } else if (Constants.AdStyle.EXTERNAL_CP_01.equals(style)) { //外部插屏_01
            adView = new CsjExternalInsertScreenAdView(activity);
        }
        if (adView == null) {
            if (mAdListener != null) {
                mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
            return;
        }
        adView.setAdListener(mAdListener);
        adView.setPollingAdListener(mFirstAdListener);

        adView.parseAd(adInfo);
    }

    /**
     * 穿山甲全屏视频
     * @param activity
     * @param adInfo
     */
    private void showCsjFullVideo(Activity activity, AdInfo adInfo){
        TTFullScreenVideoAd ttFullScreenVideoAd = adInfo.getTtFullScreenVideoAd();
        if (mAdListener != null) {
            mAdListener.adSuccess(adInfo);
        }
        ttFullScreenVideoAd.setFullScreenVideoAdInteractionListener(new TTFullScreenVideoAd.FullScreenVideoAdInteractionListener() {

            @Override
            public void onAdShow() {
                if (mAdListener != null) {
                    mAdListener.adExposed(adInfo);
                }
            }

            @Override
            public void onAdVideoBarClick() {
                if (mAdListener != null) {
                    mAdListener.adClicked(adInfo);
                }
            }

            @Override
            public void onAdClose() {
                if (mAdListener != null) {
                    mAdListener.adClose(adInfo);
                }
            }

            @Override
            public void onVideoComplete() {
                if (mAdListener != null && mAdListener instanceof VideoAdListener) {
                    ((VideoAdListener) mAdListener).onVideoComplete(adInfo);
                }
            }

            @Override
            public void onSkippedVideo() {

            }
        });
        //step6:在获取到广告后展示
        ttFullScreenVideoAd.showFullScreenVideoAd(activity);
    }

    /**
     * 创建优量汇view
     * @param activity
     * @param adInfo
     */
    private void createYlhAdView(Activity activity, AdInfo adInfo){
        String adStyle = adInfo.getAdStyle();
    }
}
