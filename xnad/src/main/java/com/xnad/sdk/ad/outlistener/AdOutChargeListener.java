package com.xnad.sdk.ad.outlistener;

import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.listener.AdChargeListener;

/**
 * @ProjectName: Midas
 * @Package: com.xnad.sdk.ad.outlistener
 * @ClassName: AdOutChargeListener
 * @Description: 对外计费回调（用在信息流先请求后监听计费回调）
 * @Author: fanhailong
 * @CreateDate: 2019/12/28 15:21
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/28 15:21
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface AdOutChargeListener<T extends AdInfo> extends AdChargeListener<T> {
}
