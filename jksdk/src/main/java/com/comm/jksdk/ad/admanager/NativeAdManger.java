package com.comm.jksdk.ad.admanager;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.RelativeLayout;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.comm.jksdk.GeekAdSdk;
import com.comm.jksdk.ad.entity.AdInfo;
import com.comm.jksdk.ad.listener.AdListener;
import com.comm.jksdk.ad.listener.AdManager;
import com.comm.jksdk.ad.listener.AdPreloadingListener;
import com.comm.jksdk.ad.listener.FirstAdListener;
import com.comm.jksdk.ad.view.CommAdView;
import com.comm.jksdk.ad.view.chjview.CHJAdView;
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
    private CommAdView mAdView = null;


    private RelativeLayout adParentView;

    private List<ConfigBean.AdListBean.AdsInfosBean> adsInfoslist = new ArrayList();

    public NativeAdManger() {
    }

    /**
     * acitvity对象,优量汇开屏、视频广用到
     */
    protected Activity mActivity;
    /**
     * 广告ID
     */
    private String mAdId;
    /**
     * 广告sdk对应的appid
     */
    private String mAppId;
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
     * 广告样式
     */
    private String adStyle = "";
    private int adRequestTimeOut;
    /**
     * 广告类型
     */
    private String adUnion;

    /**
     * 视频广告方向: 1 竖屏, 2 横屏
     */
    private int orientation = 1;

    /**
     * 激励视频userid
     */
    private String userId = "";

    /**
     * 自渲染插屏广告是否是全屏
     */
    private boolean isFullScreen = false;

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


    /**
     * 创建广告View
     *
     * @param adType 广告样式
     */
    private void createAdView(Activity activity, String adType, String appId, String mAdId) {

        if (Constants.AdType.ChuanShanJia.equals(adType)) {
            mAdView = new CHJAdView(GeekAdSdk.getContext(), activity, adStyle, appId, mAdId);
            ((CHJAdView) mAdView).setOrientation(orientation);
            if (!TextUtils.isEmpty(userId)) {
                ((CHJAdView) mAdView).setUserId(userId);
            }
            ((CHJAdView) mAdView).setFullScreen(isFullScreen);
            ((CHJAdView) mAdView).setShowTimeSeconds(showTimeSeconds);
            ((CHJAdView) mAdView).setmProgress(mProgress);
        } else if (Constants.AdType.YouLiangHui.equals(adType)) {
            mAdView = new YlhAdView(GeekAdSdk.getContext(), activity, adStyle, appId, mAdId);
        } else {
            // 暂不处理
            if (mAdListener != null) {
                mAdListener.adError(CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
            return;
        }

        if (mAdView != null && mAdListener != null) {
            //向客户端提供接口
            mAdView.setAdListener(mAdListener);
            //ylh请求失败请求chj广告接口回掉
            mAdView.setYlhAdListener(mFirstAdListener);
        }

        adParentView.removeAllViews();
        adParentView.addView(mAdView);
        mAdView.setAdId(mAdId);
        mAdView.setAppId(appId);
        requestAd();
    }

    /**
     * 创建广告View
     *
     * @param adType 广告样式
     */
    private void createAdView(Activity activity, TTFeedAd ttFeedAd, String adType, String appId, String mAdId) {

        if (Constants.AdType.ChuanShanJia.equals(adType)) {
            mAdView = new CHJAdView(GeekAdSdk.getContext(), activity, adStyle, appId, mAdId);
            ((CHJAdView) mAdView).setOrientation(orientation);
            if (!TextUtils.isEmpty(userId)) {
                ((CHJAdView) mAdView).setUserId(userId);
            }
            ((CHJAdView) mAdView).setFullScreen(isFullScreen);
            ((CHJAdView) mAdView).setShowTimeSeconds(showTimeSeconds);
            ((CHJAdView) mAdView).setmProgress(mProgress);
        } else if (Constants.AdType.YouLiangHui.equals(adType)) {
            mAdView = new YlhAdView(GeekAdSdk.getContext(), activity, adStyle, appId, mAdId);
        } else {
            // 暂不处理
            if (mAdListener != null) {
                mAdListener.adError(CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
            return;
        }

        if (mAdView != null && mAdListener != null) {
            //向客户端提供接口
            mAdView.setAdListener(mAdListener);
            //ylh请求失败请求chj广告接口回掉
            mAdView.setYlhAdListener(mFirstAdListener);
        }

        adParentView.removeAllViews();
        adParentView.addView(mAdView);
        mAdView.setAdId(mAdId);
        mAdView.setAppId(appId);
        if (mAdView != null) {
            //第一次请求广告保存请求时间
            if (firstRequestAd) {
                Long curTime = System.currentTimeMillis();
                SpUtils.putLong(Constants.SPUtils.FIRST_REQUEST_AD_TIME, curTime);
            }
            mAdView.requestAd(requestType, ttFeedAd, adRequestTimeOut);
        }
    }


    /**
     * 请求广告
     */
    private void requestAd() {
        if (mAdView != null) {
            //第一次请求广告保存请求时间
            if (firstRequestAd) {
                Long curTime = System.currentTimeMillis();
                SpUtils.putLong(Constants.SPUtils.FIRST_REQUEST_AD_TIME, curTime);
            }
            mAdView.requestAd(requestType, adRequestTimeOut);
        }
    }

    @Override
    public RelativeLayout getAdView() {
        return adParentView;
    }

    private FirstAdListener mFirstAdListener = new FirstAdListener() {
        @Override
        public void firstAdError(int errorCode, String errorMsg) {
            LogUtils.w(TAG, "回传--->请求第一个广告失败");
            firstRequestAd = false;
            againRequest();

        }
    };

    @Override
    public void loadAd(Activity activity, String position, AdListener listener) {
        mAdListener = listener;
        try {
            mPosition = position;
            mActivity = activity;
            //创建view
            adParentView = new RelativeLayout(GeekAdSdk.getContext());
            //获取本地配置信息
            ConfigBean.AdListBean mConfigInfoBean = AdsConfig.getInstance(GeekAdSdk.getContext()).getConfig(position);
            if (mConfigInfoBean == null) {
                if (mAdListener != null) {
                    mAdListener.adError(CodeFactory.LOCAL_INFO_EMPTY, CodeFactory.getError(CodeFactory.LOCAL_INFO_EMPTY));
                }
                return;
            }
            //当前广告位所对应的配置信息 存储到curAdlist
            adStyle = mConfigInfoBean.getAdStyle();
            adRequestTimeOut = mConfigInfoBean.getAdRequestTimeOut();
            adsInfoslist.clear();
            adsInfoslist.addAll(mConfigInfoBean.getAdsInfos());

            againRequest();
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdListener != null) {
                mAdListener.adError(CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }
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

            loadCsjAd(position);
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdPreloadingListener != null) {
                mAdPreloadingListener.adError(CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }
    }

    public void loadCsjAd(String position){
        //step1:初始化sdk
        TTAdManager ttAdManager = TTAdManagerHolder.get(mAppId);
        //step2:创建TTAdNative对象,用于调用广告请求接口
        TTAdNative mTTAdNative = ttAdManager.createAdNative(GeekAdSdk.getContext());
        //step3:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
//        TTAdManagerHolder.get().requestPermissionIfNecessary(mContext);

        LogUtils.d(TAG, "onADLoaded->请求穿山甲广告");

        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(mAdId.trim())
                .setSupportDeepLink(true)
                .setImageAcceptedSize(640, 320)
                .setAdCount(1)
                .build();
        mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
            @Override
            public void onError(int i, String s) {
                LogUtils.d(TAG, "onNoAD->请求穿山甲失败,ErrorCode:" + i + ",ErrorMsg:" + s);
                if (mAdPreloadingListener != null) {
                    mAdPreloadingListener.adError(i, s);
                }
            }

            @Override
            public void onFeedAdLoad(List<TTFeedAd> list) {
                LogUtils.d(TAG, "onADLoaded->请求穿山甲成功");
                if (CollectionUtils.isEmpty(list)) {
                    if (mAdPreloadingListener != null) {
                        mAdPreloadingListener.adError(CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
                    }
                    return;
                }
                TTFeedAd ttFeedAd = list.get(0);
                if (ttFeedAd == null) {
                    if (mAdPreloadingListener != null) {
                        mAdPreloadingListener.adError(CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
                    }
                    return;
                }
                CacheAd. setAd(position, ttFeedAd);
                if (mAdPreloadingListener != null) {
                    mAdPreloadingListener.adSuccess(null);
                }
            }
        });
    }

    /**
     * 开屏广告加载方法
     *
     * @param activity
     * @param position
     * @param listener
     */
    @Override
    public void loadSplashAd(Activity activity, String position, AdListener listener) {
        mAdListener = listener;
        try {
            mActivity = activity;
            //创建view
            adParentView = new RelativeLayout(GeekAdSdk.getContext());
            //获取本地配置信息
            ConfigBean.AdListBean mConfigInfoBean = AdsConfig.getInstance(GeekAdSdk.getContext()).getConfig(position);
            if (mConfigInfoBean == null) {
                if (mAdListener != null) {
                    mAdListener.adError(CodeFactory.LOCAL_INFO_EMPTY, CodeFactory.getError(CodeFactory.LOCAL_INFO_EMPTY));
                }
                return;
            }
            //当前广告位所对应的配置信息 存储到curAdlist
            adStyle = mConfigInfoBean.getAdStyle();
            adRequestTimeOut = mConfigInfoBean.getAdRequestTimeOut();
            adsInfoslist.clear();
            adsInfoslist.addAll(mConfigInfoBean.getAdsInfos());

            againRequest();
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdListener != null) {
                mAdListener.adError(CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }
    }

    /**
     * 全屏视频广告加载方法
     *
     * @param activity
     * @param position
     * @param listener
     */
    @Override
    public void loadVideoAd(Activity activity, String position, AdListener listener) {
        mAdListener = listener;
        try {
            mActivity = activity;
            orientation = 1;
            //创建view
            adParentView = new RelativeLayout(GeekAdSdk.getContext());
            //获取本地配置信息
            ConfigBean.AdListBean mConfigInfoBean = AdsConfig.getInstance(GeekAdSdk.getContext()).getConfig(position);
            if (mConfigInfoBean == null) {
                if (mAdListener != null) {
                    mAdListener.adError(CodeFactory.LOCAL_INFO_EMPTY, CodeFactory.getError(CodeFactory.LOCAL_INFO_EMPTY));
                }
                return;
            }
            //当前广告位所对应的配置信息 存储到curAdlist
            adStyle = mConfigInfoBean.getAdStyle();
            adRequestTimeOut = mConfigInfoBean.getAdRequestTimeOut();
            adsInfoslist.clear();
            adsInfoslist.addAll(mConfigInfoBean.getAdsInfos());

            againRequest();
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdListener != null) {
                mAdListener.adError(CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }
    }

    /**
     * 激励视频广告加载方法
     *
     * @param activity
     * @param position
     * @param listener
     */
    @Override
    public void loadRewardVideoAd(Activity activity, String position, String userId, int orientation, AdListener listener) {
        mAdListener = listener;
        try {
            mActivity = activity;
            this.orientation = orientation;
            this.userId = userId;
            //创建view
            adParentView = new RelativeLayout(GeekAdSdk.getContext());
            //获取本地配置信息
            ConfigBean.AdListBean mConfigInfoBean = AdsConfig.getInstance(GeekAdSdk.getContext()).getConfig(position);
            if (mConfigInfoBean == null) {
                if (mAdListener != null) {
                    mAdListener.adError(CodeFactory.LOCAL_INFO_EMPTY, CodeFactory.getError(CodeFactory.LOCAL_INFO_EMPTY));
                }
                return;
            }
            //当前广告位所对应的配置信息 存储到curAdlist
            adStyle = mConfigInfoBean.getAdStyle();
            adRequestTimeOut = mConfigInfoBean.getAdRequestTimeOut();
            adsInfoslist.clear();
            adsInfoslist.addAll(mConfigInfoBean.getAdsInfos());

            againRequest();
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdListener != null) {
                mAdListener.adError(CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }
    }

    /**
     * 自定义插屏广告加载方法
     *
     * @param activity
     * @param position
     * @param listener
     */
    @Override
    public void loadCustomInsertScreenAd(Activity activity, String position, int showTimeSeconds, AdListener listener) {
        mAdListener = listener;
        try {
            mActivity = activity;
            this.isFullScreen = isFullScreen;
            this.showTimeSeconds = showTimeSeconds;
            //创建view
            adParentView = new RelativeLayout(GeekAdSdk.getContext());
            //获取本地配置信息
            ConfigBean.AdListBean mConfigInfoBean = AdsConfig.getInstance(GeekAdSdk.getContext()).getConfig(position);
            if (mConfigInfoBean == null) {
                if (mAdListener != null) {
                    mAdListener.adError(CodeFactory.LOCAL_INFO_EMPTY, CodeFactory.getError(CodeFactory.LOCAL_INFO_EMPTY));
                }
                return;
            }
            //当前广告位所对应的配置信息 存储到curAdlist
            adStyle = mConfigInfoBean.getAdStyle();
            adRequestTimeOut = mConfigInfoBean.getAdRequestTimeOut();
            adsInfoslist.clear();
            adsInfoslist.addAll(mConfigInfoBean.getAdsInfos());
            LogUtils.d(TAG, "-----loadCustomInsertScreenAd--------");
            againRequest();
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdListener != null) {
                mAdListener.adError(CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }
    }

    @Override
    public void loadCustomInsertScreenAd(Activity activity, String position, int showTimeSeconds, AdListener listener, String... pos) {
        mAdListener = listener;
        try {
            String progress = ""; //进度
            if (!CollectionUtils.isEmpty(pos)) {
                progress = pos[0];
            }
            mProgress = progress;
            mActivity = activity;
            this.isFullScreen = isFullScreen;
            this.showTimeSeconds = showTimeSeconds;
            //创建view
            adParentView = new RelativeLayout(GeekAdSdk.getContext());
            //获取本地配置信息
            ConfigBean.AdListBean mConfigInfoBean = AdsConfig.getInstance(GeekAdSdk.getContext()).getConfig(position);
            if (mConfigInfoBean == null) {
                if (mAdListener != null) {
                    mAdListener.adError(CodeFactory.LOCAL_INFO_EMPTY, CodeFactory.getError(CodeFactory.LOCAL_INFO_EMPTY));
                }
                return;
            }
            //当前广告位所对应的配置信息 存储到curAdlist
            adStyle = mConfigInfoBean.getAdStyle();
            adRequestTimeOut = mConfigInfoBean.getAdRequestTimeOut();
            adsInfoslist.clear();
            adsInfoslist.addAll(mConfigInfoBean.getAdsInfos());
            LogUtils.d(TAG, "-----loadCustomInsertScreenAd--------");
            againRequest();
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdListener != null) {
                mAdListener.adError(CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }
    }


    /**
     * 轮询请求
     */
    public void againRequest() {
        if (CollectionUtils.isEmpty(adsInfoslist)) {
            if (mAdListener != null) {
                mAdListener.adError(CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
            return;
        }
        ConfigBean.AdListBean.AdsInfosBean mAdsInfosBean = adsInfoslist.remove(0);
        if (mAdsInfosBean == null) {
            if (mAdListener != null) {
                mAdListener.adError(CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
            return;
        }
        adUnion = mAdsInfosBean.getAdUnion();
        mAdId = mAdsInfosBean.getAdId();
        mAppId = mAdsInfosBean.getAdsAppId();
        TTFeedAd ttFeedAd = CacheAd.getAd(mPosition);
        if (ttFeedAd != null) {
            createAdView(mActivity, ttFeedAd, adUnion, mAppId, mAdId);
        } else {
            createAdView(mActivity, adUnion, mAppId, mAdId);
        }
    }

    /**
     * 请求插屏广告
     * @param activity
     * @param adType
     * @param appId
     * @param mAdId
     */
    protected void requsetInteractionScreenn(Activity activity, String adType, String appId, String mAdId){
        if (Constants.AdType.ChuanShanJia.equals(adType)) {
            mAdView = new CHJAdView(GeekAdSdk.getContext(), activity, adStyle, appId, mAdId);
            ((CHJAdView) mAdView).setOrientation(orientation);
            if (!TextUtils.isEmpty(userId)) {
                ((CHJAdView) mAdView).setUserId(userId);
            }
            ((CHJAdView) mAdView).setFullScreen(isFullScreen);
            ((CHJAdView) mAdView).setShowTimeSeconds(showTimeSeconds);
        } else if (Constants.AdType.YouLiangHui.equals(adType)) {
            mAdView = new YlhAdView(GeekAdSdk.getContext(), activity, adStyle, appId, mAdId);
        } else {
            // 暂不处理
            if (mAdListener != null) {
                mAdListener.adError(CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
            return;
        }

        if (mAdView != null && mAdListener != null) {
            //向客户端提供接口
            mAdView.setAdListener(mAdListener);
            //ylh请求失败请求chj广告接口回掉
            mAdView.setYlhAdListener(mFirstAdListener);
        }

        adParentView.removeAllViews();
        adParentView.addView(mAdView);
        requestAd();
    }
}
