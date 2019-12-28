package com.xnad.sdk.ad.factory;

import com.xnad.sdk.ad.admanager.MidasAdManger;
import com.xnad.sdk.ad.listener.AdManager;
import com.xnad.sdk.ad.listener.Provider;

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
public class MidasAdManagerFactory implements Provider {
    @Override
    public AdManager produce() {
        return new MidasAdManger();
    }
}
