package com.xnad.sdk.ad.listener;

import com.xnad.sdk.ad.entity.AdInfo;

/**
 * @ProjectName: MidasAdSdk
 * @Package: com.comm.jksdk.ad.listener
 * @ClassName: AdRequestListener
 * @Description: 广告请求回调
 * @Author: fanhailong
 * @CreateDate: 2019/12/2 19:40
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/2 19:40
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public abstract class AdRequestListener {

    /**
     * 广告请求成功
     */
    public   abstract void adSuccess(AdInfo info);
    /**
     * 请求广告失败
     * @param errorCode
     * @param errorMsg
     */
    public abstract void adError(AdInfo info, int errorCode, String errorMsg);

    /**
     * 广告显示回调
     * @param info
     *      desc:1.展示后业务缓存一个广告源使用
     */
    public boolean adShow(AdInfo info){
        return false;
    }



}
