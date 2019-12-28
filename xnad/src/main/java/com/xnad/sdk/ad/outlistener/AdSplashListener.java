package com.xnad.sdk.ad.outlistener;

import android.view.ViewGroup;

import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.listener.AdChargeListener;

/**
 * @ProjectName: Midas
 * @Package: com.comm.jksdk.ad.listener
 * @ClassName: AdSplashListener
 * @Description: 开屏广告回调接口(对外)
 * @Author: fanhailong
 * @CreateDate: 2019/12/17 16:36
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/17 16:36
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface AdSplashListener<T extends AdInfo> extends AdChargeListener<T> {
    /**
     * 传入view容器：优量广告用到
     * @return
     */
    ViewGroup getViewGroup();

    /**
     * 倒计时回调，返回广告还将被展示的剩余时间，单位是 ms
     * @param l
     */
    default void adTick(T info, long l) {

    }
}
