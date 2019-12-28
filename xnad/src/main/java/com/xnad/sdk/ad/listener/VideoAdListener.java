package com.xnad.sdk.ad.listener;

import com.xnad.sdk.ad.entity.AdInfo;

/**
 * 视频广告回调器<p>
 *
 * @author zixuefei
 * @since 2019/11/17 13:52
 */
public interface VideoAdListener<T extends AdInfo> extends AdChargeListener<T> {
    /**
     * 视频恢复
     */
    void onVideoResume(T info);

    /**
     * 激励视频获得激励回调(视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称)
     */
    void onVideoRewardVerify(T info, boolean rewardVerify, int rewardAmount, String rewardName);

    void onVideoComplete(T info);
}
