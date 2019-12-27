package com.comm.jksdk.ad.listener;

import android.app.Activity;

/**
 * @ProjectName: MidasAdSdk
 * @Package: com.comm.jksdk.ad.listener
 * @ClassName: AdManager
 * @Description: 广告管理类接口
 * @Author: fanhailong
 * @CreateDate: 2019/11/11 18:44
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/11 18:44
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface AdManager {

    /**
     * 请求开屏广告
     * @param activity 上下文
     * @param position 位置code
     * @param listener 回调
     * @param timeOut 超时时间
     */
    void loadMidasSplashAd(Activity activity, String position, AdSplashListener listener, int timeOut);

    /**
     * 请求激励广告
     * @param activity 上下文
     * @param position 位置code
     * @param userId 激励视频用的用户id
     * @param orientation 横竖版视频 1=竖版；2=横版
     * @param rewardName 奖励金额名称
     * @param rewardAmount 奖励金额数量
     * @param listener 回调
     */
    void loadMidasRewardVideoAd(Activity activity, String position, String userId, int orientation, String rewardName, int rewardAmount, VideoAdListener listener);

    /**
     * 请求全屏视广告
     * @param activity 上下文
     * @param position 位置code
     * @param listener 回调
     */
    void loadMidasFullScreenVideoAd(Activity activity, String position, VideoAdListener listener);

    /**
     * 请求自渲染广告
     * @param activity 上下文
     * @param position 位置code
     * @param listener 回调
     */
    void loadMidasSelfRenderAd(Activity activity, String position, SelfRenderAdListener listener);

    /**
     * 请求插屏广告
     * @param activity 上下文
     * @param position 位置code
     * @param listener 回到
     */
    void loadMidasInteractionAd(Activity activity, String position, InteractionListener listener);

    /**
     * 请求原生广告
     * @param activity 上下文
     * @param position 位置code
     * @param width 广告宽度（dp）
     * @param listener 回调
     */
    void loadMidasNativeTemplateAd(Activity activity, String position, float width, NativeTemplateListener listener);
}
