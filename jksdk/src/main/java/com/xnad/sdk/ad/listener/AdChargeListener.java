package com.xnad.sdk.ad.listener;

/**
 * @ProjectName: Midas
 * @Package: com.comm.jksdk.ad.listener
 * @ClassName: AdChargeListener
 * @Description: 计费用到的回调
 * @Author: fanhailong
 * @CreateDate: 2019/12/20 15:12
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/20 15:12
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface AdChargeListener<T> extends AdBasicListener<T> {
    /**
     * 广告展示
     */
    void adExposed(T info);

    /**
     * 广告点击
     */
    void adClicked(T info);

    /**
     * 广告关闭
     * @param info
     */
    default void adClose(T info){

    }
}
