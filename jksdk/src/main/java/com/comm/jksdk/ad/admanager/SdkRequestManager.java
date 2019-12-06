package com.comm.jksdk.ad.admanager;

import com.comm.jksdk.ad.listener.AdListener;
import com.comm.jksdk.ad.listener.AdRequestManager;

/**
 * @ProjectName: GeekAdSdk
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
    protected final String TAG = "GeekAdSdk-->";

    protected AdListener mAdListener;

    public AdListener getAdListener() {
        return mAdListener;
    }

    public void setAdListener(AdListener mAdListener) {
        this.mAdListener = mAdListener;
    }
}
