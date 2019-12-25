package com.comm.jksdk.ad.listener;

/**
 * @ProjectName: Midas
 * @Package: com.comm.jksdk.ad.listener
 * @ClassName: SelfRenderChargeListener
 * @Description:  自渲染广告计费回调
 * @Author: fanhailong
 * @CreateDate: 2019/12/24 15:48
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/24 15:48
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface SelfRenderChargeListener<T> extends AdChargeListener<T>{
    @Override
    default void adSuccess(T info){

    }

    @Override
    default void adError(T info, int errorCode, String errorMsg){

    }

    /**
     * 被创意按钮被点击
     * @param info
     */
    void adCreativeClick(T info);
}
