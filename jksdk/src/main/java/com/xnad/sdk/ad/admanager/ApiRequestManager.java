package com.xnad.sdk.ad.admanager;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xnad.sdk.MidasAdSdk;
import com.xnad.sdk.ad.listener.AdRequestManager;
import com.xnad.sdk.utils.LogUtils;

/**
 * @ProjectName: MidasAdSdk
 * @Package: com.comm.jksdk.ad.admanager
 * @ClassName: ApiRequestManager
 * @Description: Api广告请求
 * @Author: fanhailong
 * @CreateDate: 2019/12/2 18:20
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/2 18:20
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public abstract class ApiRequestManager implements AdRequestManager {
    protected final String TAG = "MidasAdSdk-->";

    /**
     * 缓存网络图片+
     * @param url
     */
    @Override
    public void cacheImg(String... url){
        if (url==null||url.length==0) {
            return;
        }
        for (String s : url) {
            Glide.with(MidasAdSdk.getContext())
                    .load(s)
                    .into(mSimpleTarget);
        }
    }

    private SimpleTarget<Drawable> mSimpleTarget = new SimpleTarget<Drawable>() {
        @Override
        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
            LogUtils.e("cache success");
        }
    };
}
