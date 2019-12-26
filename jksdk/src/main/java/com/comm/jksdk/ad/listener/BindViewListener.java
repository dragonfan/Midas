package com.comm.jksdk.ad.listener;

/**
 * @ProjectName: Midas
 * @Package: com.comm.jksdk.ad.listener
 * @ClassName: BindViewListener
 * @Description: 绑定view监听器，内部用
 * @Author: fanhailong
 * @CreateDate: 2019/12/24 20:15
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/24 20:15
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface BindViewListener {
    /**
     * 广告展示
     */
    void adExposed();

    /**
     * 广告点击
     */
    void adClicked();

    /**
     * 广告关闭
     */
    default void adClose(){

    }

    /**
     * 广告请求成功
     */
    default void adSuccess() {

    }

    /**
     * 广告失败
     * @param errorCode
     * @param errorMsg
     */
    default void adError(int errorCode, String errorMsg){

    }
}
