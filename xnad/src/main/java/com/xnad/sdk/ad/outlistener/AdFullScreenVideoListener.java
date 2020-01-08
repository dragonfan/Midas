package com.xnad.sdk.ad.outlistener;

import com.xiaoniu.statistic.T;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.listener.AdChargeListener;

/**
 * 全屏视频广告回调器(对外)<p>
 *
 * @author fanhailong
 * @since 2019/11/17 13:52
 */
public interface AdFullScreenVideoListener extends AdChargeListener<AdInfo> {

    /**
     * 视频播放完回调
     * @param info
     */
    void adVideoComplete(AdInfo info);

    void adSkippedVideo(AdInfo info);
}
