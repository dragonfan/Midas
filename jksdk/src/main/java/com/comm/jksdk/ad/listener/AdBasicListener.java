package com.comm.jksdk.ad.listener;

import com.comm.jksdk.ad.entity.AdInfo;

/**
 * @ProjectName: Midas
 * @Package: com.comm.jksdk.ad.listener
 * @ClassName: AdBasicListener
 * @Description: java类作用描述
 * @Author: fanhailong
 * @CreateDate: 2019/12/19 20:36
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/19 20:36
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface AdBasicListener {
    /**
     * 广告请求成功
     */
    void adSuccess(AdInfo info);

    /**
     * 广告失败
     * @param errorCode
     * @param errorMsg
     */
    void adError(AdInfo info, int errorCode, String errorMsg);
}
