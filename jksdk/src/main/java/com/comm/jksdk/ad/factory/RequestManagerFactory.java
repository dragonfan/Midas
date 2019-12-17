package com.comm.jksdk.ad.factory;

import com.comm.jksdk.ad.admanager.CsjSdkRequestManager;
import com.comm.jksdk.ad.admanager.YlhSdkRequestManager;
import com.comm.jksdk.ad.entity.AdInfo;
import com.comm.jksdk.ad.listener.AdRequestManager;
import com.comm.jksdk.ad.listener.RequestProvider;
import com.comm.jksdk.constant.Constants;

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
        if (Constants.AdSourceType.ChuanShanJia.equals(adInfo.getAdSource())) {
            return new CsjSdkRequestManager();
        } else if (Constants.AdSourceType.YouLiangHui.equals(adInfo.getAdSource())) {
            return new YlhSdkRequestManager();
        } else {
            return null;
        }
    }
}
