package com.comm.jksdk.ad.listener;

import android.view.ViewGroup;

import com.comm.jksdk.ad.entity.AdInfo;

/**
 * @ProjectName: Midas
 * @Package: com.comm.jksdk.ad.listener
 * @ClassName: AdSplashListener
 * @Description: 开屏广告回调接口
 * @Author: fanhailong
 * @CreateDate: 2019/12/17 16:36
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/17 16:36
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface AdSplashListener<T extends AdInfo> extends AdListener<T>{
    /**
     * 传入view容器：优量广告用到
     * @return
     */
    ViewGroup getViewGroup();
}
