package com.comm.jksdk.ad.entity;

import android.view.ViewGroup;

import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.comm.jksdk.ad.listener.AdListener;
import com.comm.jksdk.constant.Constants;

/**
 * @ProjectName: Midas
 * @Package: com.comm.jksdk.ad.entity
 * @ClassName: MidasSelfRenderAd
 * @Description: Midas自渲染广告
 * @Author: fanhailong
 * @CreateDate: 2019/12/19 17:22
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/19 17:22
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class MidasSelfRenderAd extends MidasAd{

    private ViewGroup container;
    /**
     * 计费回调（埋点用）
     */
    private AdListener adListener;

    /**
     * 穿山甲自渲染信息流广告
     */
    private TTFeedAd ttFeedAd;

    public TTFeedAd getTtFeedAd() {
        return ttFeedAd;
    }

    public void setTtFeedAd(TTFeedAd ttFeedAd) {
        this.ttFeedAd = ttFeedAd;
    }

    public AdListener getAdListener() {
        return adListener;
    }

    public void setAdListener(AdListener adListener) {
        this.adListener = adListener;
    }

    public ViewGroup getContainer() {
        return container;
    }

    public void setContainer(ViewGroup container) {
        this.container = container;
    }

    private void bindLinstenr(){
        if (Constants.AdSourceType.ChuanShanJia.equals(getAdSource())) {
            if (ttFeedAd == null) {
                return;
            }
            if (adListener == null) {
                return;
            }
//            ttFeedAd.registerViewForInteraction();
        } else {

        }
    }

    @Override
    public void clear() {

    }
}
