package com.comm.jksdk.config.listener;

import com.comm.jksdk.bean.MidasConfigBean;

/**
 * @ProjectName: MidasAdSdk
 * @Package: com.comm.jksdk.config.listener
 * @ClassName: ConfigListener
 * @Description: 请求广告配置信息回调
 * @Author: fanhailong
 * @CreateDate: 2019/11/11 17:56
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/11 17:56
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface ConfigListener {

    /**
     * 配置请求成功
     */
    void adSuccess(MidasConfigBean midasConfigBean);

    /**
     * 配置请求失败
     * @param httpRequestCode   通信协议码
     * @param errorCode
     * @param errorMsg
     */
    void adError(int httpRequestCode,int errorCode, String errorMsg);
}
