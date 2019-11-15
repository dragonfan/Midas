package com.comm.jksdk.ad.view.ylhview;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;

import com.comm.jksdk.R;
import com.comm.jksdk.http.utils.LogUtils;
import com.comm.jksdk.utils.CodeFactory;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;

/**
 * 开屏广告view<p>
 *
 * @author zixuefei
 * @since 2019/11/14 21:53
 */
public class YlhSplashAdView extends YLHAdView {
    private ConstraintLayout splashContainer;

    protected YlhSplashAdView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ylh_splash_ad_view;
    }

    @Override
    public void initView() {
        splashContainer = findViewById(R.id.splash_ad_container);
    }

    /**
     * 获取开屏广告并加载
     */
    public void loadSplashAd(String appId, String adId) {
        LogUtils.d(TAG, "YLH appId:" + appId + " adId:" + adId);
        SplashAD splashAD = new SplashAD((Activity) mContext, appId, adId, new SplashADListener() {
            @Override
            public void onADDismissed() {
                LogUtils.d(TAG, "YLH onADDismissed:");
            }

            @Override
            public void onNoAD(AdError adError) {
                LogUtils.d(TAG, "YLH onNoAD:");
                if (adError != null) {
                    adError(adError.getErrorCode(), adError.getErrorMsg());
                } else {
                    adError(CodeFactory.UNKNOWN, CodeFactory.getError(CodeFactory.UNKNOWN));
                }
            }

            @Override
            public void onADPresent() {
            }

            @Override
            public void onADClicked() {
                LogUtils.d(TAG, "YLH onADClicked:");
                adClicked();
            }

            @Override
            public void onADTick(long l) {

            }

            @Override
            public void onADExposure() {
                LogUtils.d(TAG, "YLH onADClicked:");
                adSuccess();
                adExposed();
            }
        });
        splashAD.fetchAndShowIn(splashContainer);
    }

}
