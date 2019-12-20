package com.comm.jksdk.ad.listener;

import com.comm.jksdk.ad.entity.AdInfo;

/**
 * 自渲染广告回调器<p>
 *
 * @author fanhailong
 * @since 2019/11/17 13:52
 */
public interface SelfRenderAdListener<T extends AdInfo> extends AdListener<T> {
    void onVideoLoaded(T adInfo);
}
