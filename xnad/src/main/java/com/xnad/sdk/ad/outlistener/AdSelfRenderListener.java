package com.xnad.sdk.ad.outlistener;

import android.view.View;

import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.listener.AdBasicListener;
import com.xnad.sdk.ad.listener.AdChargeListener;

/**
 * 自渲染广告回调器(对外)<p>
 *
 * @author fanhailong
 * @since 2019/11/17 13:52
 */
public interface AdSelfRenderListener extends AdChargeListener<AdInfo> {
    /**
     * 广告显示后的view
     * @param view
     */
    void callbackView(View view);
}
