package com.comm.jksdk.ad.listener;

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
public interface AdBasicListener<T> {
    /**
     * 广告请求成功
     */
    void adSuccess(T info);

    /**
     * 广告失败
     * @param errorCode
     * @param errorMsg
     */
    void adError(T info, int errorCode, String errorMsg);
}
