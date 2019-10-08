package com.comm.jksdk.ad.view.chjview;

import android.content.Context;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.comm.jksdk.ad.view.CommAdView;
import com.comm.jksdk.ad.view.ylhview.YlhBIgImgAdView;
import com.comm.jksdk.ad.view.ylhview.YlhLeftImgRightTwoTextAdView;
import com.comm.jksdk.config.TTAdManagerHolder;
import com.comm.jksdk.constant.Constants;
import com.comm.jksdk.http.utils.LogUtils;
import com.comm.jksdk.utils.AdsUtils;

import java.util.List;

/**
 * @author liupengbing
 * @date 2019/9/24
 */
public class CHJAdView extends CommAdView {
    private  String style;
    // 广告位ID
    protected String mAdPositionId = "";
    // 广告请求数量
    private final static int REQUEST_AD_COUNTS = 1;

    private CommAdView mAdView = null;
    public CHJAdView(Context context, String style, String adPositionId) {
        super(context,style,adPositionId);
        this.mAdPositionId=adPositionId;
        this.mContext=context;
        this.style=style;

        if (Constants.AdStyle.BigImg.equals(style)) {
            mAdView = new ChjBigImgAdView(mContext);
        } else if (Constants.AdStyle.LeftImgRightTwoText.equals(style)){
            mAdView = new ChjLeftImgRightTwoTextAdView(mContext);
        }else if(Constants.AdStyle.All.equals(style)){
            //所有样式都支持 随机展示
            //所有样式都支持 随机展示
            int num=AdsUtils.getRandomNum(2);
            LogUtils.w("------->num:",num+"");
            switch (num){
                case 0:
                    mAdView = new ChjLeftImgRightTwoTextAdView(mContext);
                    break;
                case 1:
                    mAdView = new ChjBigImgAdView(mContext);
                    break;
                default:
                    mAdView = new ChjLeftImgRightTwoTextAdView(mContext);
                    break;
            }

        }
        this.addView(mAdView);

    }


    @Override
    public void requestAd(int requestType,int adRequestTimeOut) {
        if(mContext==null){
            return;
        }
        if(requestType==0){
            //SDK
            getAdBySdk(adRequestTimeOut);
        }else{
            //api
        }

    }

    /**
     * 通过SDK获取广告
     */
    private void getAdBySdk(final int adRequestTimeOut) {
        //step1:初始化sdk
        TTAdManager ttAdManager = TTAdManagerHolder.get();
        //step2:创建TTAdNative对象,用于调用广告请求接口
        TTAdNative mTTAdNative = ttAdManager.createAdNative(mContext);
        //step3:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
//        TTAdManagerHolder.get().requestPermissionIfNecessary(mContext);

        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(mAdPositionId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(640, 320)
                .setAdCount(1)
                .build();
        mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
            @Override
            public void onError(int i, String s) {
                LogUtils.d(TAG, "onNoAD->请求穿山甲失败,ErrorCode:" + i+ ",ErrorMsg:" +s);
                if (s != null) {
                    adError(i, s);
                }
            }

            @Override
            public void onFeedAdLoad(List<TTFeedAd> list) {
                LogUtils.d(TAG, "onADLoaded->请求穿山甲成功");
                Boolean requestAdOverTime= AdsUtils.requestAdOverTime(adRequestTimeOut);
                if(requestAdOverTime){
                    return;
                }
                if (list == null || list.isEmpty()) {
                    return;
                }
                if(mAdView==null){
                    return;
                }
                adSuccess();
                mAdView.parseChjAd(list);
            }
        });
    }
}
