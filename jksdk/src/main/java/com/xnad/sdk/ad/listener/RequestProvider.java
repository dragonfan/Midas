package com.xnad.sdk.ad.listener;

import com.xnad.sdk.ad.entity.AdInfo;

/**
 * @ProjectName: MidasAdSdk
 * @Package: com.comm.jksdk.ad.listener
 * @ClassName: Provider
 * @Description: 请求广告工厂接口
 * @Author: fanhailong
 * @CreateDate: 2019/11/11 19:16
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/11 19:16
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface RequestProvider {
    AdRequestManager produce(AdInfo adInfo);
}
