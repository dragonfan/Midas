package com.comm.jksdk.ad.admanager;

import android.app.Activity;

import com.comm.jksdk.MidasAdSdk;
import com.comm.jksdk.ad.entity.AdInfo;
import com.comm.jksdk.ad.entity.MidasFullScreenVideoAd;
import com.comm.jksdk.ad.entity.MidasInteractionAd;
import com.comm.jksdk.ad.entity.MidasNativeTemplateAd;
import com.comm.jksdk.ad.entity.MidasRewardVideoAd;
import com.comm.jksdk.ad.entity.MidasSelfRenderAd;
import com.comm.jksdk.ad.entity.MidasSplashAd;
import com.comm.jksdk.ad.factory.RequestManagerFactory;
import com.comm.jksdk.ad.listener.AdBasicListener;
import com.comm.jksdk.ad.listener.AdManager;
import com.comm.jksdk.ad.listener.AdPreloadingListener;
import com.comm.jksdk.ad.listener.AdRequestListener;
import com.comm.jksdk.ad.listener.AdRequestManager;
import com.comm.jksdk.ad.listener.AdSplashListener;
import com.comm.jksdk.ad.listener.FirstAdListener;
import com.comm.jksdk.ad.listener.InteractionListener;
import com.comm.jksdk.ad.listener.NativeTemplateListener;
import com.comm.jksdk.ad.listener.SelfRenderAdListener;
import com.comm.jksdk.ad.listener.VideoAdListener;
import com.comm.jksdk.bean.MidasConfigBean;
import com.comm.jksdk.config.AdsConfig;
import com.comm.jksdk.config.listener.ConfigListener;
import com.comm.jksdk.constant.Constants;
import com.comm.jksdk.http.ErrorCode;
import com.comm.jksdk.http.utils.LogUtils;
import com.comm.jksdk.utils.CodeFactory;
import com.comm.jksdk.utils.CollectionUtils;
import com.comm.jksdk.utils.StatisticUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 原生广告管理器
 *
 * @author: docking
 * @date: 2019/9/7 10:22
 * @description: todo ...
 **/
public class MidasAdManger implements AdManager {
    protected final String TAG = "MidasAdSdk-->";

    private List<MidasConfigBean.AdStrategyBean> adsInfoslist = new ArrayList();

    public MidasAdManger() {
    }

    /**
     * acitvity对象,优量汇开屏、视频广用到
     */
    protected Activity mActivity;
    /**
     * 广告监听器
     */
    private AdBasicListener mAdListener;

    /**
     * 广告预加载监听器
     */
    private AdPreloadingListener mAdPreloadingListener;

    /**
     * 请求方式：0 - SDK 1 - API
     */
    private int requestType = 0;

    private boolean firstRequestAd = true;

    private FirstAdListener mFirstAdListener = new FirstAdListener() {
        @Override
        public void firstAdError(AdInfo adInfo, int errorCode, String errorMsg) {
            LogUtils.w(TAG, "回传--->请求第一个广告失败");

            if (CollectionUtils.isEmpty(adsInfoslist)) {
                if (adInfo.isPreload()) {
                    if (mAdPreloadingListener != null) {
                        mAdPreloadingListener.adError(adInfo, errorCode, errorMsg);
                    }
                } else {
                    if (mAdListener != null) {
                        mAdListener.adError(adInfo, errorCode, errorMsg);
                    }
                }
                return;
            }
            MidasConfigBean.AdStrategyBean mAdsInfosBean = adsInfoslist.remove(0);
            if (mAdsInfosBean == null) {
                if (adInfo.isPreload()) {
                    if (mAdPreloadingListener != null) {
                        mAdPreloadingListener.adError(adInfo, errorCode, errorMsg);
                    }
                } else {
                    if (mAdListener != null) {
                        mAdListener.adError(adInfo, errorCode, errorMsg);
                    }
                }
                return;
            }
            firstRequestAd = false;
            againRequest(adInfo, mAdsInfosBean);
        }
    };


    @Override
    public void loadMidasSplashAd(Activity activity, String position, AdSplashListener listener) {

        mAdListener = listener;
        AdInfo adInfo = new AdInfo();
        adInfo.setAdType(Constants.AdType.SPLASH_TYPE);
        MidasSplashAd midasSplashAd = new MidasSplashAd();
        adInfo.setMidasAd(midasSplashAd);
        try {
            mActivity = activity;
            //设置广告位置信息
            adInfo.setPosition(position);
            getMidasConfigBean(adInfo, position);
//            //获取本地配置信息
//            readyInfo(adInfo);
//            if (CollectionUtils.isEmpty(adsInfoslist)) {
//                if (mAdListener != null) {
//                    mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
//                }
//                return;
//            }
//            ConfigBean.AdListBean.AdsInfosBean mAdsInfosBean = adsInfoslist.remove(0);
//            if (mAdsInfosBean == null) {
//                if (mAdListener != null) {
//                    mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
//                }
//                return;
//            }
//            againRequest(adInfo, mAdsInfosBean);
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdListener != null) {
                mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }
    }

    @Override
    public void loadMidasRewardVideoAd(Activity activity, String position, String userId, int orientation, String rewardName, int rewardAmount, VideoAdListener listener) {
        AdInfo adInfo = new AdInfo();
        adInfo.setAdType(Constants.AdType.REWARD_VIDEO_TYPE);
        MidasRewardVideoAd midasAdEntity = new MidasRewardVideoAd();
        midasAdEntity.setUserId(userId);
        midasAdEntity.setOrientation(orientation);
        midasAdEntity.setRewardName(rewardName);
        midasAdEntity.setRewardAmount(rewardAmount);
        adInfo.setMidasAd(midasAdEntity);
        mAdListener = listener;
        try {
            mActivity = activity;
            //设置广告位置信息
            adInfo.setPosition(position);
//            //获取本地配置信息
//            readyInfo(adInfo);
//            if (CollectionUtils.isEmpty(adsInfoslist)) {
//                if (mAdListener != null) {
//                    mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
//                }
//                return;
//            }
//            ConfigBean.AdListBean.AdsInfosBean mAdsInfosBean = adsInfoslist.remove(0);
//            if (mAdsInfosBean == null) {
//                if (mAdListener != null) {
//                    mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
//                }
//                return;
//            }
//            againRequest(adInfo, mAdsInfosBean);
            getMidasConfigBean(adInfo, position);
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdListener != null) {
                mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }

    }

    @Override
    public void loadMidasFullScreenVideoAd(Activity activity, String position, VideoAdListener listener) {
        mAdListener = listener;
        AdInfo adInfo = new AdInfo();
        adInfo.setAdType(Constants.AdType.FULL_SCREEN_VIDEO_TYPE);
        MidasFullScreenVideoAd midasAdEntity = new MidasFullScreenVideoAd();
        adInfo.setMidasAd(midasAdEntity);
        try {
            mActivity = activity;
            //设置广告位置信息
            adInfo.setPosition(position);
            //获取本地配置信息
//            readyInfo(adInfo);
//            if (CollectionUtils.isEmpty(adsInfoslist)) {
//                if (mAdListener != null) {
//                    mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
//                }
//                return;
//            }
//            ConfigBean.AdListBean.AdsInfosBean mAdsInfosBean = adsInfoslist.remove(0);
//            if (mAdsInfosBean == null) {
//                if (mAdListener != null) {
//                    mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
//                }
//                return;
//            }
//            againRequest(adInfo, mAdsInfosBean);

            getMidasConfigBean(adInfo, position);
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdListener != null) {
                mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }
    }

    @Override
    public void loadMidasSelfRenderAd(Activity activity, String position, SelfRenderAdListener listener) {
        mAdListener = listener;
        AdInfo adInfo = new AdInfo();
        adInfo.setAdType(Constants.AdType.SELF_RENDER);
        MidasSelfRenderAd midasAdEntity = new MidasSelfRenderAd();
        adInfo.setMidasAd(midasAdEntity);
        try {
            mActivity = activity;
            //设置广告位置信息
            adInfo.setPosition(position);
//            getMidasConfigBean(adInfo, position);
            //测试用
            //广告源
            adInfo.getMidasAd().setAdSource(Constants.AdSourceType.ChuanShanJia);
            //广告id
//        adInfo.getMidasAd().setAdId(adsInfosBean.getAdId());
            //测试用
            adInfo.getMidasAd().setAdId("936430473");
            //广告对应的appid
            adInfo.getMidasAd().setAppId("5036430");
            sdkRequest(adInfo);

//            //获取本地配置信息
//            readyInfo(adInfo);
//            if (CollectionUtils.isEmpty(adsInfoslist)) {
//                if (mAdListener != null) {
//                    mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
//                }
//                return;
//            }
//            ConfigBean.AdListBean.AdsInfosBean mAdsInfosBean = adsInfoslist.remove(0);
//            if (mAdsInfosBean == null) {
//                if (mAdListener != null) {
//                    mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
//                }
//                return;
//            }
//            againRequest(adInfo, mAdsInfosBean);
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdListener != null) {
                mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }
    }

    @Override
    public void loadMidasInteractionAd(Activity activity, String position, InteractionListener listener) {
        mAdListener = listener;
        AdInfo adInfo = new AdInfo();
        adInfo.setAdType(Constants.AdType.INTERACTION_TYPE);
        MidasInteractionAd midasInteractionAd = new MidasInteractionAd();
        adInfo.setMidasAd(midasInteractionAd);
        try {
            mActivity = activity;
            //设置广告位置信息
            adInfo.setPosition(position);
            //获取本地配置信息
//            readyInfo(adInfo);
//            if (CollectionUtils.isEmpty(adsInfoslist)) {
//                if (mAdListener != null) {
//                    mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
//                }
//                return;
//            }
//            ConfigBean.AdListBean.AdsInfosBean mAdsInfosBean = adsInfoslist.remove(0);
//            if (mAdsInfosBean == null) {
//                if (mAdListener != null) {
//                    mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
//                }
//                return;
//            }
//            againRequest(adInfo, mAdsInfosBean);
            getMidasConfigBean(adInfo, position);
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdListener != null) {
                mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }
    }

    @Override
    public void loadMidasNativeTemplateAd(Activity activity, String position, float width, NativeTemplateListener listener) {
        mAdListener = listener;
        AdInfo adInfo = new AdInfo();
        adInfo.setAdType(Constants.AdType.NATIVE_TEMPLATE);
        MidasNativeTemplateAd midasNativeTemplateAd = new MidasNativeTemplateAd();
        midasNativeTemplateAd.setWidth(width);
        adInfo.setMidasAd(midasNativeTemplateAd);
        try {
            mActivity = activity;
            //设置广告位置信息
            adInfo.setPosition(position);
            //获取本地配置信息
//            readyInfo(adInfo);
//            if (CollectionUtils.isEmpty(adsInfoslist)) {
//                if (mAdListener != null) {
//                    mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
//                }
//                return;
//            }
//            ConfigBean.AdListBean.AdsInfosBean mAdsInfosBean = adsInfoslist.remove(0);
//            if (mAdsInfosBean == null) {
//                if (mAdListener != null) {
//                    mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
//                }
//                return;
//            }
//            againRequest(adInfo, mAdsInfosBean);
            getMidasConfigBean(adInfo, position);
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdListener != null) {
                mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }
    }


    public void getMidasConfigBean(AdInfo adInfo, String adPosId){
        //埋点流程开始
        long beginTime = System.currentTimeMillis();
        StatisticUtils.singleStatisticBegin(adInfo,beginTime);

        adsInfoslist.clear();
        AdsConfig.getInstance(MidasAdSdk.getContext()).requestConfig(adPosId, new ConfigListener() {
            @Override
            public void adSuccess(MidasConfigBean midasConfigBean) {
                List<MidasConfigBean.AdStrategyBean> adStrategyBeans = midasConfigBean.getAdStrategy();
                adsInfoslist.addAll(adStrategyBeans);
                if (CollectionUtils.isEmpty(adsInfoslist)) {
                    int code = CodeFactory.UNKNOWN;
                    adError(200,code,CodeFactory.getError(code));
                    return;
                }
                MidasConfigBean.AdStrategyBean mAdsInfoBean = adsInfoslist.remove(0);
                if (mAdsInfoBean == null) {
                    int code = CodeFactory.UNKNOWN;
                    adError(200,code,CodeFactory.getError(code));
                    return;
                }

                againRequest2(adInfo, mAdsInfoBean);

                //广告策略请求事件埋点
                StatisticUtils.strategyConfigurationRequest(adInfo, adPosId,
                        midasConfigBean.getAdstrategyid(), 200+"",
                        ErrorCode.SUCCESS + "", beginTime);
            }

            @Override
            public void adError(int httpCode,int errorCode, String errorMsg) {
                if (mAdListener != null) {
                    mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
                }
                //广告策略请求事件埋点
                StatisticUtils.strategyConfigurationRequest(adInfo, adPosId,"",
                        httpCode+"",errorCode + "", beginTime);

            }
        });
    }

    /**
     * 轮询请求
     *
     * @param adInfo
     * @param adsInfosBean
     */
    public void againRequest(AdInfo adInfo, MidasConfigBean.AdStrategyBean adsInfosBean) {
        if (adInfo == null) {
            adInfo = new AdInfo();
        }
        //某些特有的数据清空，避免污染下一次请求数据
        adInfo.clear();
        adInfo.getMidasAd().clear();

        //广告源
        adInfo.getMidasAd().setAdSource(adsInfosBean.getAdUnion());
        //广告id
//        adInfo.getMidasAd().setAdId(adsInfosBean.getAdId());
        adInfo.getMidasAd().setAdId(adsInfosBean.getAdId()); //测试用
        //广告对应的appid
        adInfo.getMidasAd().setAppId(adsInfosBean.getAdsAppid());
        sdkRequest(adInfo);
//        //请求类型 0 - SDK 1 - API
//        requestType = adsInfosBean.getRequestType();
//        if (requestType == 0) {
//        } else {
//            apiRequest(adInfo);
//        }
    }

    /**
     * 轮询请求
     *
     * @param adInfo
     * @param adsInfosBean
     */
    public void againRequest2(AdInfo adInfo, MidasConfigBean.AdStrategyBean adsInfosBean) {
        if (adInfo == null) {
            adInfo = new AdInfo();
        }
        //某些特有的数据清空，避免污染下一次请求数据
        adInfo.clear();
        adInfo.getMidasAd().clear();

        //广告源
        adInfo.getMidasAd().setAdSource(adsInfosBean.getAdUnion());
        //广告id
//        adInfo.getMidasAd().setAdId(adsInfosBean.getAdId());
        //测试用
        adInfo.getMidasAd().setAdId("936430473");
        //广告对应的appid
        adInfo.getMidasAd().setAppId(adsInfosBean.getAdsAppid());
        //请求类型 0 - SDK 1 - API
        sdkRequest(adInfo);
//        requestType = adsInfosBean.getRequestType();
//        if (requestType == 0) {
//            sdkRequest(adInfo);
//        } else {
//            apiRequest(adInfo);
//        }
    }

    /**
     * api 请求
     */
    public void apiRequest(AdInfo adInfo) {
        // TODO: 2019/12/3
        if (mAdListener != null) {
            mAdListener.adError(adInfo, 2, "暂时不支持api广告");
        }
    }

    /**
     * sdk 请求
     */
    public void sdkRequest(AdInfo adInfo) {
//  unit_request_num    ?

//placement_id          adInfo.getAdId()
//      source_id         adInfo.getMidasAd().getAdSource()

//        source_request_num ?
//      source_timeout ?
//        style     ?

//        advertiser    ?
//          pkg ?

//        priority      ?

//        fill_count    1/0

//        result_code   ?

//        result_info   ?

        AdRequestManager adRequestManager = new RequestManagerFactory().produce(adInfo);

        long beginTime = System.currentTimeMillis();
        adRequestManager.requestAd(mActivity, adInfo, new AdRequestListener() {
            @Override
            public void adSuccess(AdInfo info) {
                //广告位请求事件埋点
                StatisticUtils.advertisingPositionRequest(adInfo,1,beginTime);

            }

            @Override
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                if (mFirstAdListener != null) {
                    mFirstAdListener.firstAdError(info, errorCode, errorMsg);
                }
                //广告位请求事件埋点
                StatisticUtils.advertisingPositionRequest(adInfo,0,beginTime);
            }
        }, mAdListener);
    }

}
