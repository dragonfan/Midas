package com.comm.jksdk.ad.view.ylhview;

import android.content.Context;
import android.widget.Toast;

import com.comm.jksdk.ad.view.CommAdView;
import com.comm.jksdk.constant.Constants;
import com.comm.jksdk.http.utils.LogUtils;
import com.comm.jksdk.utils.AdsUtils;
import com.qq.e.ads.nativ.NativeADUnifiedListener;
import com.qq.e.ads.nativ.NativeUnifiedAD;
import com.qq.e.ads.nativ.NativeUnifiedADData;
import com.qq.e.comm.util.AdError;

import java.util.List;


/**
 * 目前只有首页广告位使用该模板，后续优化增加其他广告位
 * 优量汇广告模块
 */
public class YLHAdView extends CommAdView {
    private String style;
    // 广告位ID
    protected String mAdId = "";

    /**
     * 广告appid
     */
    protected String mAppId = "";

    // 广告请求数量
    private final static int REQUEST_AD_COUNTS = 1;


    private CommAdView mAdView = null;

    public YLHAdView(Context context, String style, String appId, String mAdId) {
        super(context, style, mAdId);
        this.mAdId = mAdId;
        this.style = style;
        this.mContext = context;
        this.mAppId = appId;

        if (Constants.AdStyle.BigImg.equals(style)) {
            mAdView = new YlhBIgImgAdView(mContext);
        } else if (Constants.AdStyle.LeftImgRightTwoText.equals(style)) {
            mAdView = new YlhLeftImgRightTwoTextAdView(mContext);
        } else if (Constants.AdStyle.SplashAd.equals(style)) {
            mAdView = new YlhSplashAdView(mContext);
        } else {
            //all
            //所有样式都支持 随机展示
            int num = AdsUtils.getRandomNum(2);
            LogUtils.w("------->num:", num + "");
            switch (num) {
                case 0:
                    mAdView = new YlhLeftImgRightTwoTextAdView(mContext);
                    break;
                case 1:
                    mAdView = new YlhBIgImgAdView(mContext);
                    break;
                default:
                    mAdView = new YlhLeftImgRightTwoTextAdView(mContext);
                    break;
            }

        }
        this.addView(mAdView);

    }

    protected YLHAdView(Context context) {
        super(context);
    }


    @Override
    public void requestAd(int requestType, int adRequestTimeOut) {
        if (requestType == 0) {
            LogUtils.d(TAG, "request ad:" + mAppId + " mAdId:" + mAdId);
            //SDK
            if (mAdView instanceof YlhSplashAdView) {
                mAdView.setAdListener(mAdListener);
                ((YlhSplashAdView) mAdView).loadSplashAd(mAppId, mAdId);
            } else {
                getAdBySdk(adRequestTimeOut);
            }
        } else {
            //api
        }

    }

    /**
     * 通过SDK获取广告
     */
    private void getAdBySdk(final int adRequestTimeOut) {
//        String ylhAppid= SpUtils.getString(Constants.SPUtils.YLH_APPID,"");
//        if(TextUtils.isEmpty(ylhAppid)){
//            ylhAppid=Constants.YLH_APPID;
//        }
        LogUtils.d(TAG, "onADLoaded->请求优量汇广告");
        Toast.makeText(mContext, "onADLoaded->请求优量汇广告" + "广告id：" + mAdId.trim(), Toast.LENGTH_LONG).show();

        NativeUnifiedAD mAdManager = new NativeUnifiedAD(mContext, mAppId, mAdId.trim(), new NativeADUnifiedListener() {
            @Override
            public void onADLoaded(List<NativeUnifiedADData> nativeAdList) {
                LogUtils.d(TAG, "onADLoaded->请求优量汇成功");
                Toast.makeText(mContext, "onADLoaded->请求优量汇成功", Toast.LENGTH_LONG).show();

                Boolean requestAdOverTime = AdsUtils.requestAdOverTime(adRequestTimeOut);
                if (requestAdOverTime) {
                    return;
                }

                if (nativeAdList == null || nativeAdList.isEmpty()) {
                    return;
                }
                if (mAdView == null) {
                    return;
                }
                adSuccess();
                mAdView.parseYlhAd(nativeAdList);
            }

            @Override
            public void onNoAD(AdError adError) {
                LogUtils.d(TAG, "onNoAD->请求优量汇失败,ErrorCode:" + adError.getErrorCode() + ",ErrorMsg:" + adError.getErrorMsg());
                Toast.makeText(mContext, "onNoAD->请求优量汇失败,ErrorCode:" + adError.getErrorCode() + ",ErrorMsg:" + adError.getErrorMsg(), Toast.LENGTH_LONG).show();

                adError(adError.getErrorCode(), adError.getErrorMsg());
                firstAdError(adError.getErrorCode(), adError.getErrorMsg());


            }
        });
        mAdManager.loadData(REQUEST_AD_COUNTS);
    }


}
