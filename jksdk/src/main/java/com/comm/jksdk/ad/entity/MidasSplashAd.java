package com.comm.jksdk.ad.entity;

import android.view.View;

import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.qq.e.ads.splash.SplashAD;

/**
 * @ProjectName: Midas
 * @Package: com.comm.jksdk.ad.entity
 * @ClassName: MidasSplashAd
 * @Description: 开屏广告类
 * @Author: fanhailong
 * @CreateDate: 2019/12/17 14:31
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/17 14:31
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class MidasSplashAd implements MidasAdEntity{
    /**
     * 用来填充的view
     */
    private View addView;
    /**
     * 优量开屏广告对象
     */
    private SplashAD splashAD;
    /**
     * 穿山甲开屏广告对象
     */
    private TTSplashAd ttSplashAd;

    public View getAddView() {
        return addView;
    }

    public void setAddView(View addView) {
        this.addView = addView;
    }

    public SplashAD getSplashAD() {
        return splashAD;
    }

    public void setSplashAD(SplashAD splashAD) {
        this.splashAD = splashAD;
    }

    public TTSplashAd getTtSplashAd() {
        return ttSplashAd;
    }

    public void setTtSplashAd(TTSplashAd ttSplashAd) {
        this.ttSplashAd = ttSplashAd;
    }

    @Override
    public void clear() {
        if (splashAD != null) {
            splashAD = null;
        }
        if (ttSplashAd != null) {
            ttSplashAd = null;
        }
        if (addView != null) {
            addView = null;
        }
    }
}
