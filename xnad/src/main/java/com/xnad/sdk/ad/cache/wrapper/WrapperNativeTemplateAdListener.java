package com.xnad.sdk.ad.cache.wrapper;

import android.view.ViewGroup;

import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.util.AdError;
import com.xnad.sdk.ad.cache.ADTool;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.entity.MidasNativeTemplateAd;
import com.xnad.sdk.ad.entity.MidasRewardVideoAd;
import com.xnad.sdk.ad.listener.AdRequestListener;
import com.xnad.sdk.ad.outlistener.AdNativeTemplateListener;
import com.xnad.sdk.ad.outlistener.AdOutChargeListener;
import com.xnad.sdk.ad.outlistener.AdRewardVideoListener;
import com.xnad.sdk.utils.AppUtils;
import com.xnad.sdk.utils.LogUtils;
import com.xnad.sdk.utils.StatisticUtils;

import java.util.List;

/**
 * Desc: 优量汇 自渲染 广告 监听
 * <p>
 * Author: ZhouTao
 * Date: 2020/1/2
 * Copyright: Copyright (c) 2016-2020
 * Company: @小牛科技
 * Email:zhoutao@xiaoniu.com
 * Update Comments:
 *
 * @author zhoutao
 */
public class WrapperNativeTemplateAdListener implements NativeExpressAD.NativeExpressADListener {
    /**
     * 加载监听
     */
    AdRequestListener adRequestListener;
    /**
     * 对外的监听
     */
    AdNativeTemplateListener outListener;
    /**
     * 广告信息
     */
    AdInfo adInfo;
    /**
     * 是否曝光
     */
    boolean isExposed = false;

    @Override
    public void onADLoaded(List<NativeExpressADView> list) {

        NativeExpressADView nativeExpressADView = list.get(0);

        if (nativeExpressADView.getBoundData().getAdPatternType() == AdPatternType.NATIVE_VIDEO) {



            nativeExpressADView.setMediaListener(new NativeExpressMediaListener() {
                @Override
                public void onVideoInit(NativeExpressADView nativeExpressADView) {
                }
                @Override
                public void onVideoLoading(NativeExpressADView nativeExpressADView) {
                }
                @Override
                public void onVideoReady(NativeExpressADView nativeExpressADView, long l) {
                }
                @Override
                public void onVideoCached(NativeExpressADView nativeExpressADView) {
                }
                @Override
                public void onVideoStart(NativeExpressADView nativeExpressADView) {
                }
                @Override
                public void onVideoPause(NativeExpressADView nativeExpressADView) {
                }
                @Override
                public void onVideoComplete(NativeExpressADView nativeExpressADView) {
                }
                @Override
                public void onVideoError(NativeExpressADView nativeExpressADView, AdError adError) {
                }
                @Override
                public void onVideoPageOpen(NativeExpressADView nativeExpressADView) {
                }
                @Override
                public void onVideoPageClose(NativeExpressADView nativeExpressADView) {

                }
            });

        }



        MidasNativeTemplateAd midasNativeTemplateAd = (MidasNativeTemplateAd) adInfo.getMidasAd();
        midasNativeTemplateAd.setAddView(nativeExpressADView);
//                nativeExpressADView.render();


        if (adRequestListener==null || adRequestListener.adShow(adInfo)) {
            ViewGroup viewContainer = adInfo.getAdParameter().getViewContainer();
            if (viewContainer!=null) {
                viewContainer.removeAllViews();
                viewContainer.addView(nativeExpressADView);
            }
            nativeExpressADView.render();
            if (outListener != null) {
                outListener.adSuccess(adInfo);
            }
        }else{
            //添加到缓存
            ADTool.getInstance().cacheAd(WrapperNativeTemplateAdListener.this, adInfo);
        }

        // 广告可见才会产生曝光，否则将无法产生收益。
        //请求成功回调
        if (adRequestListener != null) {
            adRequestListener.adSuccess(adInfo);
        }


    }

    //NativeExpressADView 渲染广告失败
    @Override
    public void onRenderFail(NativeExpressADView nativeExpressADView) {
        LogUtils.d( "YLH onRenderFail:");
        if (outListener != null) {
            outListener.adError(adInfo, 2, "on render fail");
        }
    }

    //NativeExpressADView 渲染广告成功
    @Override
    public void onRenderSuccess(NativeExpressADView nativeExpressADView) {
        LogUtils.d("YLH onRenderSuccess:");
        if (outListener != null) {
            outListener.adRenderSuccess(adInfo);
        }
    }

    @Override
    public void onADExposure(NativeExpressADView nativeExpressADView) {
        if (outListener != null) {
            outListener.adExposed(adInfo);
        }
        if (!isExposed) {
            isExposed = true;
            AppUtils.getAdCount(adInfo.getMidasAd().getAdId());
        }
    }

    @Override
    public void onADClicked(NativeExpressADView nativeExpressADView) {
        if (outListener != null) {
            outListener.adClicked(adInfo);
        }
    }

    @Override
    public void onADClosed(NativeExpressADView nativeExpressADView) {
        if (outListener != null) {
            outListener.adClose(adInfo);
        }
    }

    //	因为广告点击等原因离开当前 app 时调用
    @Override
    public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

    }

    //广告展开遮盖时调用
    @Override
    public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

    }

    //广告关闭遮盖时调用
    @Override
    public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onNoAD(AdError adError) {
        if (adRequestListener != null) {
            adRequestListener.adError(adInfo, adError.getErrorCode(), adError.getErrorMsg());
        }
    }


    public void setLoadListener(AdRequestListener listener) {
        adRequestListener = listener;
    }

    public void setOutListener(AdNativeTemplateListener listener) {
        //防止内存泄漏
        if (adRequestListener != null && outListener != null) {
            adRequestListener = null;
            outListener = null;
        }

        outListener = listener;

    }

    public void setAdInfo(AdInfo info) {
        this.adInfo = info;
    }


}
