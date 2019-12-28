package com.xnad.sdk.ad.outlistener;

import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.listener.AdChargeListener;

/**
 * 激励视频广告回调器(对外)<p>
 *
 * @author fanhailong
 * @since 2019/11/17 13:52
 */
public interface AdRewardVideoListener<T extends AdInfo> extends AdChargeListener<T> {

    /**
     * 激励视频获得激励回调(视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励数量，rewardName：奖励名称)
     */
    void onVideoRewardVerify(T info, boolean rewardVerify, int rewardAmount, String rewardName);

    void onVideoComplete(T info);
}
