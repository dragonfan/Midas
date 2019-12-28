package com.xnad.sdk.ad.outlistener;

import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.listener.AdChargeListener;

/**
 * 全屏视频广告回调器(对外)<p>
 *
 * @author fanhailong
 * @since 2019/11/17 13:52
 */
public interface AdFullScreenVideoListener<T extends AdInfo> extends AdChargeListener<T> {

    /**
     * 视频播放完回调
     * @param info
     */
    void adVideoComplete(T info);

    void adSkippedVideo(T info);
}
