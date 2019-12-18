package com.comm.jksdk.ad.entity;

import android.view.View;

import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.qq.e.ads.rewardvideo.RewardVideoAD;

/**
 * @ProjectName: Midas
 * @Package: com.comm.jksdk.ad.entity
 * @ClassName: MidasRewardVideoAd
 * @Description: java类作用描述
 * @Author: fanhailong
 * @CreateDate: 2019/12/18 14:07
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/18 14:07
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class MidasRewardVideoAd extends MidasAd {

    /**
     * 穿山甲广告对象
     */
    private TTRewardVideoAd ttRewardVideoAd;
    /**
     * 优量汇广告对象
     */
    private RewardVideoAD rewardVideoAD;
    /**
     * userid
     */
    private String userId;
    /**
     * 竖屏还是横屏
     */
    private int orientation;
    /**
     * 奖励名称
     */
    private String rewardName;
    /**
     * 奖励数量
     */
    private int rewardAmount;

    public RewardVideoAD getRewardVideoAD() {
        return rewardVideoAD;
    }

    public void setRewardVideoAD(RewardVideoAD rewardVideoAD) {
        this.rewardVideoAD = rewardVideoAD;
    }

    public TTRewardVideoAd getTtRewardVideoAd() {
        return ttRewardVideoAd;
    }

    public void setTtRewardVideoAd(TTRewardVideoAd ttRewardVideoAd) {
        this.ttRewardVideoAd = ttRewardVideoAd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public int getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(int rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    @Override
    public void clear() {

    }

    @Override
    public View getAddView() {
        return null;
    }
}
