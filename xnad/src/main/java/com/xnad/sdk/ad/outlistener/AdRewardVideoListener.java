package com.xnad.sdk.ad.outlistener;

import com.xiaoniu.statistic.T;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.listener.AdChargeListener;

/**
 * 激励视频广告回调器(对外)<p>
 *
 * @author fanhailong
 * @since 2019/11/17 13:52
 */
public interface AdRewardVideoListener extends AdChargeListener<AdInfo> {

    /**
     * 激励视频获得激励回调(视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励数量，rewardName：奖励名称)
     * @param info 广告信息
     * @param rewardVerify 是否有效
     * @param rewardAmount 奖励金额
     * @param rewardName 奖励名称
     */
    void onVideoRewardVerify(AdInfo info, boolean rewardVerify, int rewardAmount, String rewardName);

    /**
     * 视频播放完成
     * @param info
     */
    void onVideoComplete(AdInfo info);
}
