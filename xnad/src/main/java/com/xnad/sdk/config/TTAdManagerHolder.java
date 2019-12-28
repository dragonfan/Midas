package com.xnad.sdk.config;

import android.content.Context;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.xnad.sdk.BuildConfig;
import com.xnad.sdk.utils.AppUtils;

/**
 * 可以用一个单例来保存TTAdManager实例，在需要初始化sdk的时候调用
 */
public class TTAdManagerHolder {

    private static boolean sInit;
    private static String mAppId;

    public static TTAdManager get() {
        if (!sInit) {
            throw new RuntimeException("TTAdSdk is not init, please check.");
        }
        return TTAdSdk.getAdManager();
    }

    public synchronized static void init(Context context, String appId) {
        mAppId = appId;
        doInit(context);
    }

    //step1:接入网盟广告sdk的初始化操作，详情见接入文档和穿山甲平台说明
    private static void doInit(Context context) {
        if (!sInit) {

            TTAdSdk.init(context, buildConfig(context));
            sInit = true;
        }
    }

    private static TTAdConfig buildConfig(Context context) {
        String chjAppName = AppUtils.getAppName(context);
        boolean adsDebug = false;
        if (BuildConfig.DEBUG) {
            adsDebug = true;
        } else {
            adsDebug = false;
        }

        return new TTAdConfig.Builder()
                .appId(mAppId.trim())
                //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                .useTextureView(true)
                .appName(chjAppName)
                .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                //是否允许sdk展示通知栏提示
                .allowShowNotify(true)
                //是否在锁屏场景支持展示广告落地页
                .allowShowPageWhenScreenLock(true)
                //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                .debug(adsDebug)
                //允许直接下载的网络状态集合
                .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_3G, TTAdConstant.NETWORK_STATE_4G)
                //是否支持多进程
                .supportMultiProcess(false)
                //.httpStack(new MyOkStack3())//自定义网络库，demo中给出了okhttp3版本的样例，其余请自行开发或者咨询工作人员。
                .build();
    }

    public static boolean issInit() {
        return sInit;
    }
}
