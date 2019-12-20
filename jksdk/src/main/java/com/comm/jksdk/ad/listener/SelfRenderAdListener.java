package com.comm.jksdk.ad.listener;

import com.comm.jksdk.ad.entity.AdInfo;

/**
 * 自渲染广告回调器<p>
 *
 * @author fanhailong
 * @since 2019/11/17 13:52
 */
public interface SelfRenderAdListener extends AdListener {
    void onVideoLoaded(AdInfo adInfo);
}
