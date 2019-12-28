package com.xnad.sdk.ad.admanager;

import android.app.Activity;

import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.listener.AdBasicListener;
import com.xnad.sdk.ad.listener.AdRequestListener;

/**
 * @ProjectName: MidasAdSdk
 * @Package: com.comm.jksdk.ad.listener
 * @ClassName: AdRequestManager
 * @Description: 广告请求逻辑接口
 * @Author: fanhailong
 * @CreateDate: 2019/12/2 18:17
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/2 18:17
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface AdRequestManager {
    void requestAd(Activity activity, AdInfo adInfo, AdRequestListener listener, AdBasicListener adListener);

    void cacheImg(String ...url);
}
