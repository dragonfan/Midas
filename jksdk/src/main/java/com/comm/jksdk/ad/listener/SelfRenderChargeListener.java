package com.comm.jksdk.ad.listener;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

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
     * view容器
     * @return
     */
    ViewGroup getViewGroup(T info);

    /**
     * 可以被点击的view
     * @return
     */
    List<View>  getClickViewList(T info);

    /**
     * 触发创意广告的view（点击下载或拨打电话）
     * @return
     */
    List<View>  getCreativeViewList(T info);

    /**
     * 被创意按钮被点击
     * @param info
     */
    void adCreativeClick(T info);
}
