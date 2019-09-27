package com.comm.jksdk.ad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.comm.jksdk.ad.listener.AdListener;
import com.comm.jksdk.ad.listener.YlhAdListener;
import com.comm.jksdk.ad.view.CommAdView;
import com.comm.jksdk.ad.view.chjview.CHJAdView;
import com.comm.jksdk.ad.view.ylhview.YLHAdView;
import com.comm.jksdk.bean.ConfigBean;
import com.comm.jksdk.config.AdsConfig;
import com.comm.jksdk.config.InitBaseConfig;
import com.comm.jksdk.constant.Constants;
import com.comm.jksdk.http.base.BaseResponse;
import com.comm.jksdk.http.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: docking
 * @date: 2019/9/7 10:22
 * @description: todo ...
 **/
public class AdsManger {
    protected final String TAG = "GeekAdSdk-->";
    private Context mContext;
    private CommAdView mAdView = null;
    private List<ConfigBean.AdListBean.AdsInfosBean> adsInfoslist = new ArrayList();

    private static AdsManger instance;
    private AdsManger(){ }
    public static AdsManger getInstance(){
        if(instance==null){
            synchronized (AdsConfig.class){
                if(instance==null){
                    instance=new AdsManger();
                }
            }
        }
        return instance;
    }


    /**
     * 广告位置ID
     */
    private String mAdPositionId;

    /**
     * 广告监听器
     */
    private AdListener mAdListener;

    /**
     * 广告样式
     */
    private String adStyle="";
    private int adRequestTimeOut;
    /**
     * 广告类型
     */
    private String adType;
    /**
     * 默认配置Key
     */
    private String defaultConfigKey="";

    //请求方式：0 - SDK 1 - API
    private int requestType=0;


    public AdsManger setDefaultConfigKey(String defaultConfigKey) {
        this.defaultConfigKey = defaultConfigKey;
        return this;
    }
    public AdsManger setCity(String city) {
        Constants.city = city;
        return this;
    }
    public AdsManger setProvince(String province) {
        Constants.province = province;
        return this;
    }
    public AdsManger setLongitude(String longitude) {
        Constants.longitude = longitude;
        return this;
    }
    public AdsManger setLatitude(String latitude) {
        Constants.latitude = latitude;
        return this;
    }
    public AdsManger setUserActive(Long userActive) {
        Constants.userActive = userActive;
        return this;
    }
    public AdsManger setMarketName(String marketName) {
        Constants.marketName = marketName;
        return this;
    }
    public AdsManger setProductName(String productName) {
        Constants.productName = productName;
        return this;
    }
    public AdsManger setBid(int bid) {
        Constants.bid = bid;
        return this;
    }
    public AdsManger setContext(Context context) {
        this.mContext = context;
        return this;
    }


    public AdsManger setAdPositionId(String adPositionId) {
        this.mAdPositionId = adPositionId;
        return this;
    }

    public AdsManger setAdListener(AdListener adListener) {
        this.mAdListener = adListener;
        return this;
    }

    /**
     * 获取本地配置信息
     * @return
     */
    public AdsManger getConfig() {
        //获取本地配置信息
        BaseResponse<ConfigBean> mConfigInfoBean =AdsConfig.getInstance(mContext).getConfig(defaultConfigKey);

        if(mConfigInfoBean!=null){
            ConfigBean mConfigBean=mConfigInfoBean.getData();
            if(mConfigBean!=null){
                List<ConfigBean.AdListBean>AdList =mConfigBean.getAdList();
                if(AdList!=null){
                    for (int i = 0; i <AdList.size() ; i++) {
//                        mAdPositionId
//                        测试 暂时写死
                        if("home_page_list2".equals(AdList.get(i).getAdPosition())){
                            //当前广告位所对应的配置信息 存储到curAdlist
                            adStyle= AdList.get(i).getAdStyle();
                            adRequestTimeOut= AdList.get(i).getAdRequestTimeOut();
                            ConfigBean.AdListBean mAdListBean=AdList.get(i);
                            if(mAdListBean!=null){
                                adsInfoslist.addAll(mAdListBean.getAdsInfos());
                            }

                        }
                    }
                }

            }
        }
        // TODO: 2019/9/25 从缓存中取出数据
        getCacheConfig();

        return this;
    }

    /**
     * 创建广告View
     * @param adType 广告样式
     */
    private void createAdView(String adType) {

        if (Constants.AdType.ChuanShanJia.equals(adType)) {
            mAdView = new CHJAdView(mContext,adStyle, mAdPositionId);
        } else if (Constants.AdType.YouLiangHui.equals(adType)) {
            mAdView = new YLHAdView(mContext, adStyle, mAdPositionId);
        } else {
            // 暂不处理
        }

        if (mAdView != null && mAdListener != null) {
            //向客户端提供接口
            mAdView.setAdListener(mAdListener);
            //ylh请求失败请求chj广告接口回掉
            mAdView.setYlhAdListener(mYlhAdListener);
        }

        requestAd();
    }


    /**
     * 请求广告
     */
    private void requestAd() {
        if (mAdView != null) {
            mAdView.requestAd(requestType);
        }
    }

    public CommAdView getAdView() {
        return mAdView;
    }

    private YlhAdListener mYlhAdListener = new YlhAdListener() {
        @Override
        public void adYlhError(int errorCode, String errorMsg) {
            LogUtils.w(TAG, "回传--->请求优量汇失败");
            getCacheConfig();

        }
    };

    /**
     * 获取默认广告配置
     */
    private void getCacheConfig() {
        // TODO: 2019/9/25 从缓存中取出下一个广告配置
        // TODO: 2019/9/25 如果不存在数据，则不轮循
        if(adsInfoslist!=null&&adsInfoslist.size()>0) {
            ConfigBean.AdListBean.AdsInfosBean mAdsInfosBean = adsInfoslist.remove(0);
            if (mAdsInfosBean!=null) {
                adType= mAdsInfosBean.getAdUnion();
                mAdPositionId=mAdsInfosBean.getAdId();

                requestType=mAdsInfosBean.getRequestType();
                if(!TextUtils.isEmpty(adType)) {
                    if (adType.equals(Constants.AdType.YouLiangHui)) {
                        Constants.YLH_APPID = mAdsInfosBean.getAdsAppId();
                        Constants.YLH_APPNAME = mAdsInfosBean.getAdsAppName();
                        //测试数据 生产环境删除
                        setAdPositionId("60004844594457490");
                        Constants.YLH_APPID="1108839337";
                        Constants.YLH_APPNAME="即刻天气";
                    } else {
                        Constants.CHJ_APPID = mAdsInfosBean.getAdsAppId();
                        Constants.CHJ_APPNAME = mAdsInfosBean.getAdsAppName();
                        //测试数据  生产环境删除
                        setAdPositionId("915945995");
                        Constants.CHJ_APPID="5015945";
                        Constants.CHJ_APPNAME="即刻天气";
                    }
                    //创建广告样式
                    createAdView(adType);
                }
            }
        }
    }

    /**
     * 请求优量汇广告
     */
    public void build() {
        getConfig();
    }

    /**
     * 从cms请求广告配置
     */
    @SuppressLint("CheckResult")
    public  void requestConfig() {
        if(mContext==null){
            return;
        }
        AdsConfig.getInstance(mContext).requestConfig();
    }


    /**
     * 初始化SDK
     */
    public void init(Context mContext,String chjAppId,String chjAppName){
        if(mContext==null){
            LogUtils.w(TAG,"初始化SDK时Context为null，请检查");
            return;
        }
        if(TextUtils.isEmpty(chjAppId)){
            LogUtils.w(TAG,"初始化SDK时chjAppId为空，请检查");
            return;
        }
        if(TextUtils.isEmpty(chjAppName)){
            LogUtils.w(TAG,"初始化SDK时chjAppName为空，请检查");
            return;
        }
        Constants.CHJ_APPID=chjAppId;
        Constants.CHJ_APPNAME=chjAppName;
        //测试数据  生产环境删除
        Constants.CHJ_APPID="5015945";
        Constants.CHJ_APPNAME="即刻天气";
        //初始化基本配置信息
         InitBaseConfig.getInstance().init(mContext);
    }

}
