package com.comm.jksdk.ad.view.ylhview;

import android.content.Context;


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
    private  String style;
    // 广告位ID
    protected String mAdPositionId = "";

    // 广告请求数量
    private final static int REQUEST_AD_COUNTS = 1;



    private CommAdView mAdView = null;

    public YLHAdView(Context context, String style, String adPositionId) {
        super(context,style,adPositionId);
        this.mAdPositionId=adPositionId;
        this.style=style;
        this.mContext=context;
        if (Constants.AdStyle.BigImg.equals(style)) {
//            mAdView = new YlhBIgImgAdView(mContext);
        } else if (Constants.AdStyle.LeftImgRightTwoText.equals(style)){
            mAdView = new YlhLeftImgRightTwoTextAdView(mContext);
        }
        this.addView(mAdView);

    }

    protected YLHAdView(Context context) {
        super(context);
    }



    @Override
    public void requestAd(int requestType,int adRequestTimeOut) {
        if(requestType==0){
           //SDK
            getAdBySdk( adRequestTimeOut);
        }else{
            //api
        }

    }

    /**
     * 通过SDK获取广告
     */
    private void getAdBySdk(final int adRequestTimeOut) {
        NativeUnifiedAD mAdManager = new NativeUnifiedAD(getContext(), Constants.YLH_APPID, mAdPositionId, new NativeADUnifiedListener() {
            @Override
            public void onADLoaded(List<NativeUnifiedADData> nativeAdList) {
                LogUtils.d(TAG, "onADLoaded->请求优量汇成功");
                Boolean requestAdOverTime=AdsUtils.requestAdOverTime(adRequestTimeOut);
                if(requestAdOverTime){
                    return;
                }
                if(adRequestTimeOut==0){

                }
                if (nativeAdList == null || nativeAdList.isEmpty()) {
                    return;
                }
                if(mAdView==null){
                    return;
                }
                adSuccess();
                mAdView.parseYlhAd(nativeAdList);
            }

            @Override
            public void onNoAD(AdError adError) {
                LogUtils.d(TAG, "onNoAD->请求优量汇失败,ErrorCode:" + adError.getErrorCode() + ",ErrorMsg:" + adError.getErrorMsg());
                adError(adError.getErrorCode(), adError.getErrorMsg());
                ylhAdError(adError.getErrorCode(), adError.getErrorMsg());


            }
        });
        mAdManager.loadData(REQUEST_AD_COUNTS);
    }


}
