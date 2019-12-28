package com.xnad.sdk.ad.factory;

import com.xnad.sdk.ad.admanager.CsjSdkRequestManager;
import com.xnad.sdk.ad.admanager.YlhSdkRequestManager;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.admanager.AdRequestManager;
import com.xnad.sdk.ad.listener.RequestProvider;
import com.xnad.sdk.constant.Constants;

/**
 * @ProjectName: MidasAdSdk
 * @Package: com.comm.jksdk.ad.factory
 * @ClassName: MidasAdManagerFactory
 * @Description: java类作用描述
 * @Author: fanhailong
 * @CreateDate: 2019/11/11 19:18
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/11 19:18
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class RequestManagerFactory implements RequestProvider {
    @Override
    public AdRequestManager produce(AdInfo adInfo) {
        if (Constants.AdSourceType.ChuanShanJia.equals(adInfo.getMidasAd().getAdSource())) {
            return new CsjSdkRequestManager();
        } else if (Constants.AdSourceType.YouLiangHui.equals(adInfo.getMidasAd().getAdSource())) {
            return new YlhSdkRequestManager();
        } else {
            return null;
        }
    }
}
