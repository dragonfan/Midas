package com.xnad.sdk.ad.admanager;

import android.app.Activity;
import android.text.TextUtils;

import com.xnad.sdk.ad.cache.ADTool;
import com.xnad.sdk.ad.cache.AdContainerWrapper;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.entity.AdStrategyBean;
import com.xnad.sdk.ad.entity.MidasBannerAd;
import com.xnad.sdk.ad.entity.MidasConfigBean;
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
import com.xnad.sdk.ad.outlistener.AdBannerListener;
import com.xnad.sdk.ad.outlistener.AdFullScreenVideoListener;
import com.xnad.sdk.ad.outlistener.AdInteractionListener;
import com.xnad.sdk.ad.outlistener.AdNativeTemplateListener;
import com.xnad.sdk.ad.outlistener.AdRewardVideoListener;
import com.xnad.sdk.ad.outlistener.AdSelfRenderListener;
import com.xnad.sdk.ad.outlistener.AdSplashListener;
import com.xnad.sdk.config.AdParameter;
import com.xnad.sdk.config.Constants;
import com.xnad.sdk.config.ErrorCode;
import com.xnad.sdk.http.ApiProvider;
import com.xnad.sdk.http.callback.HttpCallback;
import com.xnad.sdk.utils.AppUtils;
import com.xnad.sdk.utils.StatisticUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:广告管理类
 * <p>
 * Author: FanHaiLong
 * Date: 2019/12/24
 * Copyright: Copyright (c) 2016-2022
 * Company: @小牛科技
 * Email:anyabo@xiaoniu.com
 * Update Comments:
 *
 * @author fanhailong
 */
public class MidasAdManger implements AdManager {

    private List<AdStrategyBean> mStrategyBeanList = new ArrayList();

    public MidasAdManger() {
    }

    /**
     * activity 对象,优量汇开屏、视频广用到
     */
    protected Activity mActivity;
    /**
     * 广告监听器
     */
    private AdBasicListener mAdListener;

    /**
     * 第一次请求广告时间
     */
    private long firstRequestAdTime;

    private LoopAdListener mLoopAdListener = new LoopAdListener() {
        @Override
        public void loopAdError(boolean isShow, AdInfo adInfo, int errorCode, String errorMsg) {
            if (mStrategyBeanList == null || mStrategyBeanList.size() == 0) {
                if (mAdListener != null) {
                    mAdListener.adError(adInfo, errorCode, errorMsg);
                }
                return;
            }
            AdStrategyBean strategyBean = mStrategyBeanList.remove(0);
            if (strategyBean == null) {
                if (mAdListener != null) {
                    mAdListener.adError(adInfo, errorCode, errorMsg);
                }
                return;
            }
            againRequest(isShow, adInfo, strategyBean);
        }
    };

    /**
     * 获取策略配置信息
     *
     * @param isShowAd 是否展示广告
     * @param adInfo   广告信息
     * @param adPosId  广告位id
     */
    private void getMidasConfigBean(boolean isShowAd, AdInfo adInfo, String adPosId) {

//        查询本地缓存
        AdContainerWrapper adContainer = ADTool.getInstance().getAd(adInfo.getPosition());
        //判断是否有缓存并且有效
        if (adContainer != null && adContainer.isValid()&& adContainer.isValidActivity(mActivity)) {
            ADTool.getInstance().bindListener(mActivity, adContainer,null, mAdListener);
        }

        //埋点流程开始
        long beginTime = System.currentTimeMillis();
        StatisticUtils.singleStatisticBegin(adInfo, beginTime);
        mStrategyBeanList.clear();

        ApiProvider.getStrategyInfo(adPosId, new HttpCallback<MidasConfigBean>() {
            @Override
            public void onFailure(int httpResponseCode, int errorCode, String message) {
                if (mAdListener != null) {
                    mAdListener.adError(adInfo, errorCode, message);
                }
                //广告策略请求事件埋点
                StatisticUtils.strategyConfigurationRequest(adInfo, adPosId, "",
                        httpResponseCode + "", errorCode + "", beginTime);
            }

            @Override
            public void onSuccess(int httpResponseCode, MidasConfigBean midasConfigBean) {
                List<AdStrategyBean> adStrategyBeans = midasConfigBean.getAdStrategy();
                if (adStrategyBeans == null || adStrategyBeans.size() == 0) {
                    onFailure(httpResponseCode, ErrorCode.STRATEGY_DATA_EMPTY.errorCode
                            , ErrorCode.STRATEGY_DATA_EMPTY.errorMsg);
                    return;
                }
                mStrategyBeanList.addAll(adStrategyBeans);


                AdStrategyBean mAdsInfoBean = pickStrategy();


//                AdStrategyBean mAdsInfoBean = mStrategyBeanList.remove(0);
                if (mAdsInfoBean == null) {
                    onFailure(httpResponseCode, ErrorCode.STRATEGY_DATA_EMPTY.errorCode
                            , ErrorCode.STRATEGY_DATA_EMPTY.errorMsg);
                    return;
                }
                //广告策略请求事件埋点
                adInfo.getStatisticBaseProperties().setPriorityS(mAdsInfoBean.getRequestOrder());
                StatisticUtils.strategyConfigurationRequest(adInfo, adPosId,
                        midasConfigBean.getAdstrategyid(), 200 + "",
                        0 + "", beginTime);
                firstRequestAdTime = System.currentTimeMillis();
                againRequest(isShowAd, adInfo, mAdsInfoBean);

            }

        });
    }

    private AdStrategyBean pickStrategy() {
        AdStrategyBean mAdsInfoBean = null;
        int size = mStrategyBeanList.size();
        int index = 0;
        while (index < size) {

            AdStrategyBean adStrategyBean = mStrategyBeanList.remove(0);
            if (adStrategyBean != null) {
                if (adStrategyBean.getShowNum() == 0) {
                    return adStrategyBean;
                }
                //如果当前的策略超过了频控,则跳过, 取下一个策略
                if (AppUtils.getAdCount(adStrategyBean.getAdId()) <= adStrategyBean.getShowNum()) {
                    return adStrategyBean;
                }
                if (mStrategyBeanList.size() == 0) {
                    return adStrategyBean;
                }
            }

            index++;
        }


        return mAdsInfoBean;
    }

    /**
     * 轮询请求
     *
     * @param adInfo
     * @param strategyBean
     */
    public void againRequest(boolean isShowAd, AdInfo adInfo, AdStrategyBean strategyBean) {
        if (adInfo == null) {
            adInfo = new AdInfo();
        }
        if (adInfo.getStatisticBaseProperties() != null
                && !TextUtils.isEmpty(strategyBean.getRequestOrder())) {
            adInfo.getStatisticBaseProperties().setPriorityS(strategyBean.getRequestOrder());
        }
        //某些特有的数据清空，避免污染下一次请求数据
        adInfo.clear();
        adInfo.getMidasAd().clear();
        //广告源
        adInfo.getMidasAd().setAdSource(strategyBean.getAdUnion());
        //广告id
        adInfo.getMidasAd().setAdId(strategyBean.getAdId());
        //广告对应的appid
        adInfo.getMidasAd().setAppId(strategyBean.getAdsAppid());
        //频控次数
        adInfo.getMidasAd().setShowNum(strategyBean.getShowNum());

        //后续如果做api请求，在此处判断拦截处理[请求类型 0 - SDK 1 - API]
        sdkRequest(isShowAd, adInfo);


    }

    /**
     * sdk 请求
     */
    private void sdkRequest(boolean isShowAd, AdInfo adInfo) {
        long beginTime = System.currentTimeMillis();
        AdRequestManager adRequestManager = new RequestManagerFactory().produce(adInfo);
        adRequestManager.requestAd(mActivity, adInfo, new AdRequestListener() {
            @Override
            public void adSuccess(AdInfo info) {
                //广告源请求事件埋点
                StatisticUtils.advertisingSourceRequest(adInfo, 1,
                        200 + "", beginTime);
                //广告位请求事件埋点[放在广告源后面，可以清晰知道请求次数]
                StatisticUtils.advertisingPositionRequest(adInfo, firstRequestAdTime);



                //如果本次广告已经被消费,那么再添加一个广告到缓存,这个广告不要显示,
                if (adShow(info)) {
                    //那么加载一个广告到缓存中
                    getMidasConfigBean(false, adInfo, adInfo.getPosition());
                }
            }


            @Override
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                //广告源请求事件埋点
                StatisticUtils.advertisingSourceRequest(adInfo, 0,
                        errorCode + "", beginTime);
                if (mStrategyBeanList == null || mStrategyBeanList.size() == 0) {
                    //广告位请求事件埋点[与监听有时序关系，下面的监听先移除的
                    // ,放在广告源后面，可以清晰知道请求次数]
                    StatisticUtils.advertisingPositionRequest(adInfo, firstRequestAdTime);
                }
                if (mLoopAdListener != null) {
                    //加载或者预加载失败判断是否需要显示这个广告
                    boolean isShowAd = adShow(info);
                    mLoopAdListener.loopAdError(isShowAd, info, errorCode, errorMsg);
                }
            }

            @Override
            public boolean adShow(AdInfo info) {
                // 查询内存是否有缓存并且有效 ,如果有缓存那么广告不显示,做预加载用的
                AdContainerWrapper adContainer = ADTool.getInstance().getAd(adInfo.getPosition());
                if (adContainer != null && adContainer.isValid()&& adContainer.isValidActivity(mActivity)) {
                    return false;
                }
                //如果没有缓存,判断这个广告是不是真的需要显示
                //一般在APP调用时赋值 或 开始缓存时赋值
                return isShowAd;
            }

        }, mAdListener);
    }

    @Override
    public void loadMidasSplashAd(AdParameter adParameter, AdSplashListener listener) {
        if (adParameter == null) {
            throw new NullPointerException("AdParameter is null");
        }
        mAdListener = listener;
        AdInfo adInfo = new AdInfo();
        adInfo.setAdType(Constants.AdType.SPLASH_TYPE);
        MidasSplashAd midasSplashAd = new MidasSplashAd();
        midasSplashAd.setTimeOut(adParameter.getTimeOut());
        adInfo.setMidasAd(midasSplashAd);
        try {
            mActivity = adParameter.getActivity();
            //设置广告位置信息
            adInfo.setPosition(adParameter.getPosition());
            getMidasConfigBean(true, adInfo, adParameter.getPosition());
        } catch (Exception e) {
            if (mAdListener != null) {
                mAdListener.adError(adInfo, ErrorCode.STRATEGY_CONFIG_EXCEPTION.errorCode,
                        ErrorCode.STRATEGY_CONFIG_EXCEPTION.errorMsg);
            }
        }
    }

    @Override
    public void loadMidasRewardVideoAd(AdParameter adParameter, AdRewardVideoListener listener) {
        if (adParameter == null) {
            throw new NullPointerException("AdParameter is null");
        }
        AdInfo adInfo = new AdInfo();
        adInfo.setAdType(Constants.AdType.REWARD_VIDEO_TYPE);
        MidasRewardVideoAd midasAdEntity = new MidasRewardVideoAd();
        midasAdEntity.setUserId(adParameter.getUserId());
        midasAdEntity.setOrientation(adParameter.getOrientation());
        midasAdEntity.setRewardName(adParameter.getRewardName());
        midasAdEntity.setRewardAmount(adParameter.getRewardAmount());
        adInfo.setMidasAd(midasAdEntity);
        mAdListener = listener;
        try {
            mActivity = adParameter.getActivity();
            //设置广告位置信息
            adInfo.setPosition(adParameter.getPosition());
            getMidasConfigBean(true, adInfo, adParameter.getPosition());
        } catch (Exception e) {
            if (mAdListener != null) {
                mAdListener.adError(adInfo, ErrorCode.STRATEGY_CONFIG_EXCEPTION.errorCode,
                        ErrorCode.STRATEGY_CONFIG_EXCEPTION.errorMsg);
            }
        }

    }

    @Override
    public void loadMidasFullScreenVideoAd(AdParameter adParameter, AdFullScreenVideoListener listener) {
        if (adParameter == null) {
            throw new NullPointerException("AdParameter is null");
        }
        mAdListener = listener;
        AdInfo adInfo = new AdInfo();
        adInfo.setAdType(Constants.AdType.FULL_SCREEN_VIDEO_TYPE);
        MidasFullScreenVideoAd midasAdEntity = new MidasFullScreenVideoAd();
        adInfo.setMidasAd(midasAdEntity);
        try {
            mActivity = adParameter.getActivity();
            //设置广告位置信息
            adInfo.setPosition(adParameter.getPosition());
            getMidasConfigBean(true, adInfo, adParameter.getPosition());
        } catch (Exception e) {
            if (mAdListener != null) {
                mAdListener.adError(adInfo, ErrorCode.STRATEGY_CONFIG_EXCEPTION.errorCode,
                        ErrorCode.STRATEGY_CONFIG_EXCEPTION.errorMsg);
            }
        }
    }

    @Override
    public void loadMidasSelfRenderAd(AdParameter adParameter, AdSelfRenderListener listener) {
        if (adParameter == null) {
            throw new NullPointerException("AdParameter is null");
        }
        mAdListener = listener;
        AdInfo adInfo = new AdInfo();
        adInfo.setAdType(Constants.AdType.SELF_RENDER);
        MidasSelfRenderAd midasAdEntity = new MidasSelfRenderAd();
        adInfo.setMidasAd(midasAdEntity);
        try {
            mActivity = adParameter.getActivity();
            //设置广告位置信息
            adInfo.setPosition(adParameter.getPosition());
            getMidasConfigBean(true, adInfo, adParameter.getPosition());
        } catch (Exception e) {
            if (mAdListener != null) {
                mAdListener.adError(adInfo, ErrorCode.STRATEGY_CONFIG_EXCEPTION.errorCode,
                        ErrorCode.STRATEGY_CONFIG_EXCEPTION.errorMsg);
            }
        }
    }

    @Override
    public void loadMidasInteractionAd(AdParameter adParameter, AdInteractionListener listener) {
        if (adParameter == null) {
            throw new NullPointerException("AdParameter is null");
        }
        mAdListener = listener;
        AdInfo adInfo = new AdInfo();
        adInfo.setAdType(Constants.AdType.INTERACTION_TYPE);
        MidasInteractionAd midasInteractionAd = new MidasInteractionAd();
        adInfo.setMidasAd(midasInteractionAd);
        try {
            mActivity = adParameter.getActivity();
            //设置广告位置信息
            adInfo.setPosition(adParameter.getPosition());
            getMidasConfigBean(true, adInfo, adParameter.getPosition());
        } catch (Exception e) {
            if (mAdListener != null) {
                mAdListener.adError(adInfo, ErrorCode.STRATEGY_CONFIG_EXCEPTION.errorCode,
                        ErrorCode.STRATEGY_CONFIG_EXCEPTION.errorMsg);
            }
        }
    }

    @Override
    public void loadMidasNativeTemplateAd(AdParameter adParameter, AdNativeTemplateListener listener) {
        if (adParameter == null) {
            throw new NullPointerException("AdParameter is null");
        }
        mAdListener = listener;
        mActivity = adParameter.getActivity();
        AdInfo adInfo = new AdInfo();
        adInfo.setAdType(Constants.AdType.NATIVE_TEMPLATE);
        MidasNativeTemplateAd midasNativeTemplateAd = new MidasNativeTemplateAd();
        midasNativeTemplateAd.setWidth(adParameter.getWidth());
        adInfo.setMidasAd(midasNativeTemplateAd);
        try {
            //设置广告位置信息
            adInfo.setPosition(adParameter.getPosition());
            getMidasConfigBean(true, adInfo, adParameter.getPosition());
        } catch (Exception e) {
            if (mAdListener != null) {
                mAdListener.adError(adInfo, ErrorCode.STRATEGY_CONFIG_EXCEPTION.errorCode,
                        ErrorCode.STRATEGY_CONFIG_EXCEPTION.errorMsg);
            }
        }
    }

    @Override
    public void loadMidasBannerAd(AdParameter adParameter, AdBannerListener listener) {
        if (adParameter == null) {
            throw new NullPointerException("AdParameter is null");
        }
        mAdListener = listener;
        mActivity = adParameter.getActivity();
        AdInfo adInfo = new AdInfo();
        adInfo.setAdParameter(adParameter);
        adInfo.setAdType(Constants.AdType.BANNER_TYPE);
        MidasBannerAd midasBannerAd = new MidasBannerAd();
        adInfo.setMidasAd(midasBannerAd);
        try {
            //设置广告位置信息
            adInfo.setPosition(adParameter.getPosition());
            getMidasConfigBean(true, adInfo, adParameter.getPosition());
        } catch (Exception e) {
            if (mAdListener != null) {
                mAdListener.adError(adInfo, ErrorCode.STRATEGY_CONFIG_EXCEPTION.errorCode,
                        ErrorCode.STRATEGY_CONFIG_EXCEPTION.errorMsg);
            }
        }
    }


}
