package com.xnad.sdk.ad.entity;

import com.bytedance.sdk.openadsdk.TTBannerAd;
import com.qq.e.ads.banner2.UnifiedBannerView;

/**
 * Desc:banner广告实体类
 * <p>
 * Author: AnYaBo
 * Date: 2020/1/2
 * Copyright: Copyright (c) 2016-2022
 * Company: @小牛科技
 * Email:anyabo@xiaoniu.com
 * Update Comments:
 *
 * @author anyabo
 */
public class MidasBannerAd extends MidasAd{
    /**
     * 穿山甲banner广告对象
     */
    private TTBannerAd mTTBannerAd;
    /**
     *优量汇banner广告对象
     */
    UnifiedBannerView   mUnifiedBannerView;

    public TTBannerAd getTTBannerAd() {
        return mTTBannerAd;
    }

    public void setTTBannerAd(TTBannerAd TTBannerAd) {
        mTTBannerAd = TTBannerAd;
    }

    public UnifiedBannerView getUnifiedBannerView() {
        return mUnifiedBannerView;
    }

    public void setUnifiedBannerView(UnifiedBannerView unifiedBannerView) {
        mUnifiedBannerView = unifiedBannerView;
    }

    @Override
    public void clear() {

    }
}
