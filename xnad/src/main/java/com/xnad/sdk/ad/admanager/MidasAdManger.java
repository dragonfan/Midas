package com.xnad.sdk.ad.admanager;

import android.app.Activity;
import android.text.TextUtils;

import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.entity.MidasFullScreenVideoAd;
import com.xnad.sdk.ad.entity.MidasInteractionAd;
import com.xnad.sdk.ad.entity.MidasNativeTemplateAd;
import com.xnad.sdk.ad.entity.MidasRewardVideoAd;
import com.xnad.sdk.ad.entity.MidasSelfRenderAd;
import com.xnad.sdk.ad.entity.MidasSplashAd;
import com.xnad.sdk.ad.factory.RequestManagerFactory;
import com.xnad.sdk.ad.listener.AdBasicListener;
import com.xnad.sdk.ad.listener.AdRequestListener;
import com.xnad.sdk.ad.listener.LoopAdListener;
import com.xnad.sdk.ad.outlistener.AdFullScreenVideoListener;
import com.xnad.sdk.ad.outlistener.AdInteractionListener;
import com.xnad.sdk.ad.outlistener.AdNativeTemplateListener;
import com.xnad.sdk.ad.outlistener.AdRewardVideoListener;
import com.xnad.sdk.ad.outlistener.AdSelfRenderListener;
import com.xnad.sdk.ad.outlistener.AdSplashListener;
import com.xnad.sdk.bean.MidasConfigBean;
import com.xnad.sdk.config.Constants;
import com.xnad.sdk.config.ErrorCode;
import com.xnad.sdk.http.ApiProvider;
import com.xnad.sdk.http.callback.HttpCallback;
import com.xnad.sdk.utils.CodeFactory;
import com.xnad.sdk.utils.LogUtils;
import com.xnad.sdk.utils.StatisticUtils;

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
     * 请求方式：0 - SDK 1 - API
     */
    private int requestType = 0;

    /**
     * 第一次请求广告时间
     */
    private long firstRequestAdTime;

    private LoopAdListener mLoopAdListener = new LoopAdListener() {
        @Override
        public void loopAdError(AdInfo adInfo, int errorCode, String errorMsg) {
            LogUtils.w(TAG, "回传--->请求第一个广告失败");

            if (adsInfoslist==null||adsInfoslist.size()==0) {
                if (mAdListener != null) {
                    mAdListener.adError(adInfo, errorCode, errorMsg);
                }

                return;
            }
            MidasConfigBean.AdStrategyBean mAdsInfosBean = adsInfoslist.remove(0);
            if (mAdsInfosBean == null) {
                if (mAdListener != null) {
                    mAdListener.adError(adInfo, errorCode, errorMsg);
                }
                return;
            }
            againRequest(adInfo, mAdsInfosBean);
        }
    };


    @Override
    public void loadMidasSplashAd(Activity activity, String position, AdSplashListener listener, int timeOut) {

        mAdListener = listener;
        AdInfo adInfo = new AdInfo();
        adInfo.setAdType(Constants.AdType.SPLASH_TYPE);
        MidasSplashAd midasSplashAd = new MidasSplashAd();
        midasSplashAd.setTimeOut(timeOut);
        adInfo.setMidasAd(midasSplashAd);
        try {
            mActivity = activity;
            //设置广告位置信息
            adInfo.setPosition(position);
            getMidasConfigBean(adInfo, position);
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdListener != null) {
                mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }
    }

    @Override
    public void loadMidasRewardVideoAd(Activity activity, String position, String userId, int orientation, String rewardName, int rewardAmount, AdRewardVideoListener listener) {
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
            getMidasConfigBean(adInfo, position);
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdListener != null) {
                mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }

    }

    @Override
    public void loadMidasFullScreenVideoAd(Activity activity, String position, AdFullScreenVideoListener listener) {
        mAdListener = listener;
        AdInfo adInfo = new AdInfo();
        adInfo.setAdType(Constants.AdType.FULL_SCREEN_VIDEO_TYPE);
        MidasFullScreenVideoAd midasAdEntity = new MidasFullScreenVideoAd();
        adInfo.setMidasAd(midasAdEntity);
        try {
            mActivity = activity;
            //设置广告位置信息
            adInfo.setPosition(position);
            getMidasConfigBean(adInfo, position);
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdListener != null) {
                mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }
    }

    @Override
    public void loadMidasSelfRenderAd(Activity activity, String position, AdSelfRenderListener listener) {
        mAdListener = listener;
        AdInfo adInfo = new AdInfo();
        adInfo.setAdType(Constants.AdType.SELF_RENDER);
        MidasSelfRenderAd midasAdEntity = new MidasSelfRenderAd();
        adInfo.setMidasAd(midasAdEntity);
        try {
            mActivity = activity;
            //设置广告位置信息
            adInfo.setPosition(position);
            getMidasConfigBean(adInfo, position);
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdListener != null) {
                mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }
    }

    @Override
    public void loadMidasInteractionAd(Activity activity, String position, AdInteractionListener listener) {
        mAdListener = listener;
        AdInfo adInfo = new AdInfo();
        adInfo.setAdType(Constants.AdType.INTERACTION_TYPE);
        MidasInteractionAd midasInteractionAd = new MidasInteractionAd();
        adInfo.setMidasAd(midasInteractionAd);
        try {
            mActivity = activity;
            //设置广告位置信息
            adInfo.setPosition(position);
            getMidasConfigBean(adInfo, position);
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdListener != null) {
                mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
            }
        }
    }

    @Override
    public void loadMidasNativeTemplateAd(Activity activity, String position, float width, AdNativeTemplateListener listener) {
        mAdListener = listener;
        mActivity = activity;
        AdInfo adInfo = new AdInfo();
        adInfo.setAdType(Constants.AdType.NATIVE_TEMPLATE);
        MidasNativeTemplateAd midasNativeTemplateAd = new MidasNativeTemplateAd();
        midasNativeTemplateAd.setWidth(width);
        adInfo.setMidasAd(midasNativeTemplateAd);
        try {
            //设置广告位置信息
            adInfo.setPosition(position);
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


        ApiProvider.getStrategyInfo(adPosId, new HttpCallback<MidasConfigBean>() {
            @Override
            public void onFailure(int httpResponseCode, int errorCode, String message) {
                if (mAdListener != null) {
                    mAdListener.adError(adInfo, CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
                }
                //广告策略请求事件埋点
                StatisticUtils.strategyConfigurationRequest(adInfo, adPosId,"",
                        httpResponseCode+"",errorCode + "", beginTime);
            }

            @Override
            public void onSuccess(int httpResponseCode, MidasConfigBean midasConfigBean) {
                List<MidasConfigBean.AdStrategyBean> adStrategyBeans = midasConfigBean.getAdStrategy();
                if (adStrategyBeans==null || adStrategyBeans.size()==0) {
                    onFailure(httpResponseCode, ErrorCode.STRATEGY_DATA_EMPTY.errorCode
                            ,ErrorCode.STRATEGY_DATA_EMPTY.errorMsg);
                    return;
                }
                adsInfoslist.addAll(adStrategyBeans);
                MidasConfigBean.AdStrategyBean mAdsInfoBean = adsInfoslist.remove(0);
                if (mAdsInfoBean == null) {
                    onFailure(httpResponseCode, ErrorCode.STRATEGY_DATA_EMPTY.errorCode
                            ,ErrorCode.STRATEGY_DATA_EMPTY.errorMsg);
                    return;
                }
                //广告策略请求事件埋点
                adInfo.getStatisticBaseProperties().setPriorityS(mAdsInfoBean.getRequestOrder());
                StatisticUtils.strategyConfigurationRequest(adInfo, adPosId,
                        midasConfigBean.getAdstrategyid(), 200+"",
                        0 + "", beginTime);


                firstRequestAdTime = System.currentTimeMillis();
                againRequest(adInfo, mAdsInfoBean);
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
        if (adInfo.getStatisticBaseProperties() != null
                && !TextUtils.isEmpty(adsInfosBean.getRequestOrder())){
            adInfo.getStatisticBaseProperties().setPriorityS(adsInfosBean.getRequestOrder());
        }

        //某些特有的数据清空，避免污染下一次请求数据
        adInfo.clear();
        adInfo.getMidasAd().clear();

        //广告源
        adInfo.getMidasAd().setAdSource(adsInfosBean.getAdUnion());
        //广告id
        adInfo.getMidasAd().setAdId(adsInfosBean.getAdId());
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
        long beginTime = System.currentTimeMillis();
        AdRequestManager adRequestManager = new RequestManagerFactory().produce(adInfo);
        adRequestManager.requestAd(mActivity, adInfo, new AdRequestListener() {
            @Override
            public void adSuccess(AdInfo info) {
                //广告源请求事件埋点
                StatisticUtils.advertisingSourceRequest(adInfo, 1,
                        200 + "", beginTime);
                //广告位请求事件埋点[放在广告源后面，可以清晰知道请求次数]
                StatisticUtils.advertisingPositionRequest(adInfo,firstRequestAdTime);
            }

            @Override
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                //广告源请求事件埋点
                StatisticUtils.advertisingSourceRequest(adInfo, 0,
                        errorCode + "", beginTime);
                if (adsInfoslist==null||adsInfoslist.size()==0) {
                    //广告位请求事件埋点[与监听有时序关系，下面的监听先移除的
                    // ,放在广告源后面，可以清晰知道请求次数]
                    StatisticUtils.advertisingPositionRequest(adInfo,firstRequestAdTime);
                }
                if (mLoopAdListener != null) {
                    mLoopAdListener.loopAdError(info, errorCode, errorMsg);
                }
            }
        }, mAdListener);
    }

}
