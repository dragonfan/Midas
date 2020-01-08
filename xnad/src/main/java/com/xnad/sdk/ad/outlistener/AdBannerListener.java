package com.xnad.sdk.ad.outlistener;

import com.xiaoniu.statistic.T;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.listener.AdBasicListener;

/**
 * Desc:
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
public interface AdBannerListener extends AdBasicListener<AdInfo> {

    /**
     * 广告曝光展示时的回调
     * @param info  广告信息
     */
    void onAdShow(AdInfo info);

    /**
     *广告点击时发起的回调
     * @param info  广告信息
     */
    void onAdClicked(AdInfo info);

    /**
     * 广告关闭时调用
     * @param info  广告信息
     */
    void adClose(AdInfo info);

}
