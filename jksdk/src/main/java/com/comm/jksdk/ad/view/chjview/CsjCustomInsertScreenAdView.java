package com.comm.jksdk.ad.view.chjview;

import android.app.Activity;
import android.content.Context;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.comm.jksdk.R;
import com.comm.jksdk.config.TTAdManagerHolder;
import com.comm.jksdk.http.utils.LogUtils;
import com.comm.jksdk.utils.CodeFactory;
import com.comm.jksdk.utils.CollectionUtils;

import java.util.List;

/**
 * 穿山甲自渲染插屏广告<p>
 *
 * @author zixuefei
 * @since 2019/11/18 11:24
 */
public class CsjCustomInsertScreenAdView extends CHJAdView {
    private Activity activity;
    private TTAdNative mTTAdNative;

    public CsjCustomInsertScreenAdView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.csj_full_screen_video_ad_view;
    }

    @Override
    public void initView() {
    }

    /**
     * 获取插屏广告并展示
     */
    public void loadCustomInsertScreenAd(Activity activity, final boolean isFullScreen, String adId) {
        if (activity == null) {
            throw new NullPointerException("loadCustomInsertScreenAd activity is null");
        }
        LogUtils.d(TAG, "isFullScreen:" + isFullScreen + " adId:" + adId);
        //step3:创建TTAdNative对象,用于调用广告请求接口
        this.activity = activity;
        mTTAdNative = TTAdManagerHolder.get(mAppId).createAdNative(activity.getApplicationContext());
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(adId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(720, 1280)
                //请求原生广告时候，请务必调用该方法，设置参数为TYPE_BANNER或TYPE_INTERACTION_AD
                .setNativeAdType(AdSlot.TYPE_INTERACTION_AD)
                .build();

        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNative.loadNativeAd(adSlot, new TTAdNative.NativeAdListener() {
            @Override
            public void onError(int code, String message) {
                LogUtils.d(TAG, "code:" + code + " message:" + message);
                adError(code, message);
                firstAdError(code, message);
            }

            @Override
            public void onNativeAdLoad(List<TTNativeAd> ads) {
                if (!CollectionUtils.isEmpty(ads)) {
                    adSuccess();
                    showAdDialog(ads.get(0), isFullScreen);
                } else {
                    adError(CodeFactory.UNKNOWN, "请求广告数据为空");
                }
            }
        });
    }

    /**
     * 展示插屏广告
     */
    private void showAdDialog(TTNativeAd ttNativeAd, boolean isFullScreen) {

    }
}
