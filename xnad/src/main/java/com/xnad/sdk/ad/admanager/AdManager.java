package com.xnad.sdk.ad.admanager;

import com.xnad.sdk.ad.outlistener.AdFullScreenVideoListener;
import com.xnad.sdk.ad.outlistener.AdInteractionListener;
import com.xnad.sdk.ad.outlistener.AdNativeTemplateListener;
import com.xnad.sdk.ad.outlistener.AdRewardVideoListener;
import com.xnad.sdk.ad.outlistener.AdSelfRenderListener;
import com.xnad.sdk.ad.outlistener.AdSplashListener;
import com.xnad.sdk.config.AdParameter;

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
     * @param adParameter
     * @param listener
     */
    void loadMidasSplashAd(AdParameter adParameter, AdSplashListener listener);

    /**
     * 请求激励广告
     * @param adParameter
     * @param listener
     */
    void loadMidasRewardVideoAd(AdParameter adParameter, AdRewardVideoListener listener);

    /**
     * 请求全屏视广告
     * @param adParameter
     * @param listener
     */
    void loadMidasFullScreenVideoAd(AdParameter adParameter, AdFullScreenVideoListener listener);

    /**
     * 请求自渲染广告
     * @param adParameter
     * @param listener
     */
    void loadMidasSelfRenderAd(AdParameter adParameter, AdSelfRenderListener listener);

    /**
     * 请求插屏广告
     * @param adParameter
     * @param listener
     */
    void loadMidasInteractionAd(AdParameter adParameter, AdInteractionListener listener);

    /**
     * 请求原生广告
     * @param adParameter
     * @param listener
     */
    void loadMidasNativeTemplateAd(AdParameter adParameter, AdNativeTemplateListener listener);
}
