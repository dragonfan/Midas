package com.comm.jksdk.ad.admanager;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.comm.jksdk.MidasAdSdk;
import com.comm.jksdk.ad.entity.AdInfo;
import com.comm.jksdk.ad.listener.AdListener;
import com.comm.jksdk.ad.listener.AdRequestListener;
import com.comm.jksdk.ad.listener.AdRequestManager;
import com.comm.jksdk.ad.listener.AdSplashListener;
import com.comm.jksdk.ad.listener.VideoAdListener;
import com.comm.jksdk.constant.Constants;
import com.comm.jksdk.http.utils.LogUtils;
import com.comm.jksdk.utils.CollectionUtils;

/**
 * @ProjectName: MidasAdSdk
 * @Package: com.comm.jksdk.ad.admanager
 * @ClassName: SdkRequestManager
 * @Description: sdk广告请求
 * @Author: fanhailong
 * @CreateDate: 2019/12/2 18:18
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/2 18:18
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public abstract class SdkRequestManager implements AdRequestManager {
    protected final String TAG = "MidasAdSdk-->";


    @Override
    public void requestAd(Activity activity, AdInfo adInfo, AdRequestListener listener, AdListener adListener) {
        if (Constants.AdType.SPLASH_TYPE.equals(adInfo.getAdType())) {
            requestSplashAd(activity, adInfo, listener, (AdSplashListener) adListener);
        } else if (Constants.AdType.REWARD_VIDEO_TYPE.equals(adInfo.getAdType())) {
            requestRewardVideoAd(activity, adInfo, listener, (VideoAdListener) adListener);
        } else if (Constants.AdType.FULL_SCREEN_VIDEO_TYPE.equals(adInfo.getAdType())){
            requestFullScreenVideoAd(activity, adInfo, listener, (VideoAdListener) adListener);
        } else {

        }
    }

    protected abstract void requestFullScreenVideoAd(Activity activity, AdInfo info, AdRequestListener listener, VideoAdListener adListener);

    public abstract void requestSplashAd(Activity activity, AdInfo adInfo, AdRequestListener adRequestListener, AdSplashListener adSplashListener);

    public abstract void requestRewardVideoAd(Activity activity, AdInfo adInfo, AdRequestListener adRequestListener, VideoAdListener videoAdListener);


    /**
     * 缓存网络图片+
     * @param url
     */
    @Override
    public void cacheImg(String... url){
        if (CollectionUtils.isEmpty(url)) {
            return;
        }
        for (String s : url) {
            Glide.with(MidasAdSdk.getContext())
                    .load(s)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            LogUtils.e("cache success");
                        }
                    });
        }
    }
}
