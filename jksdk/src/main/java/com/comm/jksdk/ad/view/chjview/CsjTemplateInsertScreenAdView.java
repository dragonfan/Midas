package com.comm.jksdk.ad.view.chjview;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTInteractionAd;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.comm.jksdk.R;
import com.comm.jksdk.config.TTAdManagerHolder;
import com.comm.jksdk.http.utils.LogUtils;
import com.comm.jksdk.utils.CodeFactory;
import com.comm.jksdk.utils.CollectionUtils;

import java.util.List;

/**
 * 穿山甲模板渲染插屏广告<p>
 *
 * @author zixuefei
 * @since 2019/11/18 11:24
 */
public class CsjTemplateInsertScreenAdView extends CHJAdView {
    private Activity activity;
    private TTAdNative mTTAdNative;

    public CsjTemplateInsertScreenAdView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.csj_full_screen_video_ad_view;
    }

    @Override
    public void initView() {
    }

    /**
     * 获取插屏广告并展示
     */
    public void loadTemplateInsertScreenAd(final Activity activity, final boolean isFullScreen, final int showTimeSeconds, String adId) {
        if (activity == null) {
            throw new NullPointerException("loadCustomInsertScreenAd activity is null");
        }
        LogUtils.d(TAG, "isFullScreen:" + isFullScreen + " adId:" + adId + " showTimeSeconds:" + showTimeSeconds);
        //step3:创建TTAdNative对象,用于调用广告请求接口
        this.activity = activity;
        mTTAdNative = TTAdManagerHolder.get(mAppId).createAdNative(activity.getApplicationContext());
        //step4:创建插屏广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(adId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(600, 600) //根据广告平台选择的尺寸，传入同比例尺寸
                .build();
        //step5:请求广告，调用插屏广告异步请求接口
        mTTAdNative.loadInteractionAd(adSlot, new TTAdNative.InteractionAdListener() {
            @Override
            public void onError(int code, String message) {
                adError(code, message);
                firstAdError(code, message);
            }

            @Override
            public void onInteractionAdLoad(TTInteractionAd ttInteractionAd) {
                ttInteractionAd.setAdInteractionListener(new TTInteractionAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked() {
                        Log.d(TAG, "被点击");
                        adClicked();
                    }

                    @Override
                    public void onAdShow() {
                        Log.d(TAG, "被展示");
                        adExposed();
                    }

                    @Override
                    public void onAdDismiss() {
                        Log.d(TAG, "插屏广告消失");
                    }
                });
                //如果是下载类型的广告，可以注册下载状态回调监听
                if (ttInteractionAd.getInteractionType() == TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
                    ttInteractionAd.setDownloadListener(new TTAppDownloadListener() {
                        @Override
                        public void onIdle() {
                            Log.d(TAG, "点击开始下载");
                        }

                        @Override
                        public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                            Log.d(TAG, "下载中");
                        }

                        @Override
                        public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                            Log.d(TAG, "下载暂停");
                        }

                        @Override
                        public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                            Log.d(TAG, "下载失败");
                        }

                        @Override
                        public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                            Log.d(TAG, "下载完成");
                        }

                        @Override
                        public void onInstalled(String fileName, String appName) {
                            Log.d(TAG, "安装完成");
                        }
                    });
                }
                //弹出插屏广告
                ttInteractionAd.showInteractionAd(activity);
            }
        });
    }
}