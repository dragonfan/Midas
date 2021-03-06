package com.xnad.sdk.config;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

/**
 * @ProjectName: Midas
 * @Package: com.xnad.sdk.config
 * @ClassName: AdParameter
 * @Description: 请求广告参数
 * @Author: fanhailong
 * @CreateDate: 2019/12/30 9:57
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/30 9:57
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public final class AdParameter {
    //上下文
    Activity activity;
    //位置code
    String position;
    //超时时间
    int timeOut;
    //激励视频的userid
    String userId;
    //激励视频横竖版
    int orientation;
    //激励视频奖励名称
    String rewardName;
    //激励视频的奖励金额
    int rewardAmount;
    //模板广告宽度
    float width;
    //模板要添加进去的容器
    ViewGroup mViewContainer;
    //自渲染布局ID
    int mLayoutId;

    AdParameter(Builder builder) {
        this.activity = builder.activity;
        this.position = builder.position;
        this.timeOut = builder.timeOut;
        this.userId = builder.userId;
        this.orientation = builder.orientation;
        this.rewardAmount = builder.rewardAmount;
        this.rewardName = builder.rewardName;
        this.width = builder.width;
        this.mViewContainer = builder.mViewContainer;
        this.mLayoutId = builder.mLayoutId;
    }

    public Activity getActivity() {
        return activity;
    }

    public String getPosition() {
        return position;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public String getUserId() {
        return userId;
    }

    public int getOrientation() {
        return orientation;
    }

    public String getRewardName() {
        return rewardName;
    }

    public int getRewardAmount() {
        return rewardAmount;
    }

    public float getWidth() {
        return width;
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    public ViewGroup getViewContainer() {
        return mViewContainer;
    }
    public void setViewContainer(ViewGroup mViewContainer) {
        this.mViewContainer = mViewContainer;
    }
    public static class Builder {
        //上下文
        Activity activity;
        //位置code
        String position;
        //超时时间
        int timeOut;
        //激励视频的userid
        String userId;
        //激励视频横竖版
        int orientation;
        //激励视频奖励名称
        String rewardName;
        //激励视频的奖励金额
        int rewardAmount;
        //模板广告宽度
        float width;
        //模板要添加进去的容器
        ViewGroup mViewContainer;
        //自渲染布局ID
        int mLayoutId;

        public Builder(Activity activity, String position) {
            this.activity = activity;
            this.position = position;
        }

        public Builder setTimeOut(int timeOut) {
            this.timeOut = timeOut;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setOrientation(int orientation) {
            this.orientation = orientation;
            return this;
        }

        public Builder setRewardName(String rewardName) {
            this.rewardName = rewardName;
            return this;
        }

        public Builder setRewardAmount(int rewardAmount) {
            this.rewardAmount = rewardAmount;
            return this;
        }

        public Builder setWidth(float width) {
            this.width = width;
            return this;
        }

        public Builder setViewContainer(ViewGroup viewContainer) {
            this.mViewContainer = viewContainer;
            return this;
        }

        public Builder setLayoutId(@LayoutRes int layoutId) {
            this.mLayoutId = layoutId;
            return this;
        }

        public AdParameter build(){
            return new AdParameter(this);
        }
    }
}
