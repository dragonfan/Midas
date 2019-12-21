package com.comm.jksdk.ad.listener;

import android.app.Activity;
import android.view.ViewGroup;

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

    void loadMidasSplashAd(Activity activity, String position, AdSplashListener listener);

    void loadMidasRewardVideoAd(Activity activity, String position, String userId, int orientation, String rewardName, int rewardAmount, VideoAdListener listener);

    void loadMidasFullScreenVideoAd(Activity activity, String position, VideoAdListener listener);

    void loadMidasSelfRenderAd(Activity activity, String position, SelfRenderAdListener listener);

    void loadMidasInteractionAd(Activity activity, String position, AdListener listener);

    void loadMidasNativeTemplateAd(Activity activity, String position, float width, AdListener listener);
}
