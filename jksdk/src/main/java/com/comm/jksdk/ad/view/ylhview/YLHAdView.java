package com.comm.jksdk.ad.view.ylhview;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.comm.jksdk.ad.view.CommAdView;
import com.comm.jksdk.ad.view.chjview.ChjBigImgAdPlayLampView;
import com.comm.jksdk.ad.view.chjview.ChjBigImgAdView;
import com.comm.jksdk.ad.view.chjview.ChjBigImgAdViewCenter;
import com.comm.jksdk.ad.view.chjview.ChjBigImgAdViewNormal;
import com.comm.jksdk.ad.view.chjview.ChjBigImgNestPlayLampView;
import com.comm.jksdk.ad.view.chjview.ChjBigImgNotDownloadAdView;
import com.comm.jksdk.ad.view.chjview.ChjLeftImgRightTwoTextAdView;
import com.comm.jksdk.ad.view.chjview.ChjSplashAdView;
import com.comm.jksdk.ad.view.chjview.CsjFullScreenVideoView;
import com.comm.jksdk.ad.view.chjview.CsjRewardVideoAdView;
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

    protected Activity mActivity;

    // 广告请求数量
    protected final static int REQUEST_AD_COUNTS = 1;


    protected CommAdView mAdView = null;

    public YLHAdView(Context context, String style, String appId, String mAdId) {
        this(context, null, style, appId, mAdId);
    }

    public YLHAdView(Context context, Activity activity, String style, String appId, String mAdId) {
        super(context, style, mAdId);
        this.mActivity = activity;
        this.mAdId = mAdId;
        this.style = style;
        this.mContext = context;
        this.mAppId = appId;

        if (Constants.AdStyle.BIG_IMG.equals(style)) {
            mAdView = new YlhBIgImgAdView(mContext);
        } else if (Constants.AdStyle.DATU_ICON_TEXT_BUTTON.equals(style)) { // 大图_带icon文字按钮
            mAdView = new ChjBigImgAdViewNormal(mContext);
        } else if (Constants.AdStyle.DATU_ICON_TEXT.equals(style)) { //大图_带icon文字
            mAdView = new ChjBigImgNotDownloadAdView(mContext);
        } else if (Constants.AdStyle.DATU_ICON_TEXT_BUTTON_CENTER.equals(style)) { //大图_带icon文字按钮居中
            mAdView = new ChjBigImgAdViewCenter(mContext);
        } else if (Constants.AdStyle.BIG_IMG_BUTTON.equals(style)) { //大图带按钮（大图_下载播放按钮）
            mAdView = new YLHBigImgAdPlayLampView(mContext);
        } else if (Constants.AdStyle.BIG_IMG_BUTTON_LAMP.equals(style)) { //大图带按钮带跑马灯
            mAdView = new YLHBigImgAdPlayLampView(mContext, true);
        } else if (Constants.AdStyle.BIG_IMG_NEST.equals(style)) { //大图嵌套美女图片
            mAdView = new ChjBigImgNestPlayLampView(mContext);
        } else if (Constants.AdStyle.BIG_IMG_NEST_LAMP.equals(style)) { //大图嵌套图片带跑马灯
            mAdView = new ChjBigImgNestPlayLampView(mContext, true);
        } else if (Constants.AdStyle.LEFT_IMG_RIGHT_TWO_TEXT.equals(style)) {
            mAdView = new YlhLeftImgRightTwoTextAdView(mContext);
        } else if (Constants.AdStyle.OPEN_ADS.equals(style)) {
            mAdView = new YlhSplashAdView(mContext);
        } else if (Constants.AdStyle.FULL_SCREEN_VIDEO.equals(style)) {
            mAdView = new YlhFullScreenVideoAdView(mContext);
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
            getAdBySdk(adRequestTimeOut);
        } else {
            //api
        }

    }

    /**
     * 通过SDK获取广告
     */
    protected void getAdBySdk(final int adRequestTimeOut) {
//        String ylhAppid= SpUtils.getString(Constants.SPUtils.YLH_APPID,"");
//        if(TextUtils.isEmpty(ylhAppid)){
//            ylhAppid=Constants.YLH_APPID;
//        }
        if (Constants.AdStyle.BIG_IMG.equals(style) || Constants.AdStyle.DATU_ICON_TEXT.equals(style) || Constants.AdStyle.DATU_ICON_TEXT_BUTTON_CENTER.equals(style)
                || Constants.AdStyle.DATU_ICON_TEXT_BUTTON.equals(style) || Constants.AdStyle.LEFT_IMG_RIGHT_TWO_TEXT.equals(style) || Constants.AdStyle.BIG_IMG_BUTTON_LAMP.equals(style)
                || Constants.AdStyle.BIG_IMG_BUTTON.equals(style) || Constants.AdStyle.BIG_IMG_NEST.equals(style) || Constants.AdStyle.BIG_IMG_NEST_LAMP.equals(style)) {
            //todo请求大图广告
            getAdByBigImg(adRequestTimeOut);
        } else if (Constants.AdStyle.LEFT_IMG_RIGHT_TWO_TEXT.equals(style)) {
            //dodo
            getAdByBigImg(adRequestTimeOut);
        } else if (Constants.AdStyle.OPEN_ADS.equals(style)) {
            getAdBySplashAd();
        } else if (Constants.AdStyle.FULL_SCREEN_VIDEO.equals(style)) {
            getFullScreenVideoAd();
        }
    }


    /**
     * 请求图片广告
     *
     * @param adRequestTimeOut
     */
    protected void getAdByBigImg(final int adRequestTimeOut) {
        LogUtils.d(TAG, "onADLoaded->请求优量汇广告");
        Toast.makeText(mContext, "onADLoaded->请求优量汇广告" + "广告id：" + mAdId.trim(), Toast.LENGTH_LONG).show();

        NativeUnifiedAD mAdManager = new NativeUnifiedAD(mActivity, mAppId, mAdId.trim(), new NativeADUnifiedListener() {
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

    /**
     * 请求开屏广告
     */
    protected void getAdBySplashAd() {
        if (mAdView == null) {
            return;
        }
        if (mAdView instanceof YlhSplashAdView) {
            mAdView.setAdListener(mAdListener);
            mAdView.setYlhAdListener(mFirstAdListener);
            ((YlhSplashAdView) mAdView).loadSplashAd(mActivity, mAppId, mAdId);
        }
    }

    /**
     * 请求全屏视频广告
     */
    protected void getFullScreenVideoAd() {
        if (mAdView == null) {
            return;
        }
        if (mAdView instanceof YlhFullScreenVideoAdView) {
            mAdView.setAdListener(mAdListener);
            mAdView.setYlhAdListener(mFirstAdListener);
            ((YlhFullScreenVideoAdView) mAdView).loadFullScreenVideoAd(mAppId, mAdId);
        }
    }

}
