package com.xnad.sdk.ad.entity;

import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;

/**
 * @ProjectName: Midas
 * @Package: com.comm.jksdk.ad.entity
 * @ClassName: MidasInteractionAd
 * @Description: 插屏广告对象
 * @Author: fanhailong
 * @CreateDate: 2019/12/20 10:21
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/20 10:21
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class MidasInteractionAd extends MidasAd{
    /**
     * 穿山甲插屏广告对象
     */
    private TTNativeExpressAd ttNativeExpressAd;

    /**
     * 优量汇插屏广告对象
     */
    private UnifiedInterstitialAD unifiedInterstitialAD;

    public UnifiedInterstitialAD getUnifiedInterstitialAD() {
        return unifiedInterstitialAD;
    }

    public void setUnifiedInterstitialAD(UnifiedInterstitialAD unifiedInterstitialAD) {
        this.unifiedInterstitialAD = unifiedInterstitialAD;
    }

    public TTNativeExpressAd getTtNativeExpressAd() {
        return ttNativeExpressAd;
    }

    public void setTtNativeExpressAd(TTNativeExpressAd ttNativeExpressAd) {
        this.ttNativeExpressAd = ttNativeExpressAd;
    }

    @Override
    public void clear() {

    }
}
