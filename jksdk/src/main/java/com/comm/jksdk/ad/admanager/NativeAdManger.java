package com.comm.jksdk.ad.admanager;

import android.widget.RelativeLayout;

import com.comm.jksdk.GeekAdSdk;
import com.comm.jksdk.ad.listener.AdListener;
import com.comm.jksdk.ad.listener.AdManager;
import com.comm.jksdk.ad.listener.FirstAdListener;
import com.comm.jksdk.ad.view.CommAdView;
import com.comm.jksdk.ad.view.chjview.CHJAdView;
import com.comm.jksdk.ad.view.ylhview.YLHAdView;
import com.comm.jksdk.bean.ConfigBean;
import com.comm.jksdk.config.AdsConfig;
import com.comm.jksdk.constant.Constants;
import com.comm.jksdk.http.utils.LogUtils;
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


    private static NativeAdManger instance;
    private RelativeLayout adParentView;

    private List<ConfigBean.AdListBean.AdsInfosBean> adsInfoslist = new ArrayList();

    public NativeAdManger() {
    }

    public static NativeAdManger getInstance() {
        if (instance == null) {
            synchronized (NativeAdManger.class) {
                if (instance == null) {
                    instance = new NativeAdManger();
                }
            }
        }
        return instance;
    }


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
     * 广告样式
     */
    private String adStyle = "";
    private int adRequestTimeOut;
    /**
     * 广告类型
     */
    private String adUnion;

    /**
     * 请求方式：0 - SDK 1 - API
     */
    private int requestType = 0;

    private boolean firstRequestAd = true;


//    public NativeAdManger setAdListener(AdListener adListener) {
//        this.mAdListener = adListener;
//        return this;
//    }

//    /**
//     * 获取本地配置信息
//     *
//     * @return
//     */
//    public NativeAdManger getConfig() {
//        //获取本地配置信息
//        ConfigBean.AdListBean mConfigInfoBean = AdsConfig.getInstance(GeekAdSdk.getContext()).getConfig(Constants.adPositionId);
//
//        if (mConfigInfoBean != null) {
//            if (Constants.adPositionId.equals(mConfigInfoBean.getAdPosition())) {
//                //当前广告位所对应的配置信息 存储到curAdlist
//                adStyle = mConfigInfoBean.getAdStyle();
//                adRequestTimeOut = mConfigInfoBean.getAdRequestTimeOut();
//
//                adsInfoslist.clear();
//                adsInfoslist.addAll(mConfigInfoBean.getAdsInfos());
//
//            }
//
//
//        }
//        // TODO: 2019/9/25 从缓存中取出数据
//        getCacheConfig();
//
//        return this;
//    }

    /**
     * 创建广告View
     *
     * @param adType 广告样式
     */
    private void createAdView(String adType,String appId, String mAdId) {

        if (Constants.AdType.ChuanShanJia.equals(adType)) {
            mAdView = new CHJAdView(GeekAdSdk.getContext(), adStyle, appId, mAdId);
        } else if (Constants.AdType.YouLiangHui.equals(adType)) {
            mAdView = new YLHAdView(GeekAdSdk.getContext(), adStyle, appId, mAdId);
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

//    /**
//     * 获取默认广告配置
//     */
//    private void getCacheConfig() {
//        // TODO: 2019/9/25 从缓存中取出下一个广告配置
//        // TODO: 2019/9/25 如果不存在数据，则不轮循
//        if (adsInfoslist != null && adsInfoslist.size() > 0) {
//            ConfigBean.AdListBean.AdsInfosBean mAdsInfosBean = adsInfoslist.remove(0);
//            if (mAdsInfosBean != null) {
//                adUnion = mAdsInfosBean.getAdUnion();
//                mAdId = mAdsInfosBean.getAdId();
//
//                requestType = mAdsInfosBean.getRequestType();
//                if (!TextUtils.isEmpty(adUnion)) {
//                    if (adUnion.equals(Constants.AdType.YouLiangHui)) {
////                        Constants.YLH_APPID = mAdsInfosBean.getAdsAppId();
////                        Constants.YLH_APPNAME = mAdsInfosBean.getAdsAppName();
//                        //保存优量汇广告APPID  APPNAME
//                        SpUtils.putString(Constants.SPUtils.YLH_APPID, mAdsInfosBean.getAdsAppId());
//                        SpUtils.putString(Constants.SPUtils.YLH_APPNAME, mAdsInfosBean.getAdsAppName());
//                    } else {
////                        Constants.CHJ_APPID = mAdsInfosBean.getAdsAppId();
////                        Constants.CHJ_APPNAME = mAdsInfosBean.getAdsAppName();
//                        // 保存穿山甲广告APPID  APPNAME
//                        SpUtils.putString(Constants.SPUtils.CHJ_APPID, mAdsInfosBean.getAdsAppId());
//                        SpUtils.putString(Constants.SPUtils.CHJ_APPNAME, mAdsInfosBean.getAdsAppName());
//                    }
//                    //创建广告样式
//                    if (TextUtils.isEmpty(mAdId)) {
//                        LogUtils.w(TAG, "广告id为空，请检查");
//                        Toast.makeText(GeekAdSdk.getContext(), "广告id为空，请检查", Toast.LENGTH_SHORT).show();
//
//                        return;
//                    }
//                    if (TextUtils.isEmpty(adUnion)) {
//                        LogUtils.w(TAG, "广告adType为空，请检查");
//                        Toast.makeText(GeekAdSdk.getContext(), "广告类型为空，请检查", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    if (TextUtils.isEmpty(adStyle)) {
//                        Toast.makeText(GeekAdSdk.getContext(), "广告样式为空，请检查", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//                    createAdView(adUnion, mAdId);
//
//
//                }
//            }
//        } else {
//            Toast.makeText(GeekAdSdk.getContext(), "第一个广告请求失败，后台配置了一个广告源，至少两个广告源，请检查？", Toast.LENGTH_LONG).show();
//        }
//    }

//    /**
//     * 请求优量汇广告
//     */
//    public NativeAdManger build() {
//        adParentView = new RelativeLayout(GeekAdSdk.getContext());
//        firstRequestAd = true;
//        getConfig();
//        return this;
//    }

//    /**
//     * 从cms请求广告配置
//     */
//    public void requestConfig() {
//        if (GeekAdSdk.getContext() == null) {
//            return;
//        }
//        AdsConfig.getInstance(GeekAdSdk.getContext()).requestConfig();
//    }


//    /**
//     * 初始化SDK
//     */
//    public void init(Context mContext, String chjAppId, String chjAppName) {
//        if (mContext == null) {
//            LogUtils.w(TAG, "初始化SDK时Context为null，请检查");
//            return;
//        }
//        if (TextUtils.isEmpty(chjAppId)) {
//            LogUtils.w(TAG, "初始化SDK时chjAppId为空，请检查");
//            return;
//        }
//        if (TextUtils.isEmpty(chjAppName)) {
//            LogUtils.w(TAG, "初始化SDK时chjAppName为空，请检查");
//            return;
//        }
//        Constants.mContext = mContext;
//        Constants.CHJ_APPID = chjAppId;
//        Constants.CHJ_APPNAME = chjAppName;
//        //初始化基本配置信息
//        InitBaseConfig.getInstance().init(mContext);
//    }

    @Override
    public void loadAd(String position, AdListener listener) {
        mAdListener = listener;
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
    }

    /**
     * 轮询请求
     */
    public void againRequest(){
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
        createAdView(adUnion, mAppId, mAdId);
    }
}
