package com.xnad.sdk.ad.entity;

import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;

/**
 * @ProjectName: Midas
 * @Package: com.comm.jksdk.ad.entity
 * @ClassName: MidasFullScreenVideoAd
 * @Description: 全屏视频广告类
 * @Author: fanhailong
 * @CreateDate: 2019/12/19 9:51
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/19 9:51
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class MidasFullScreenVideoAd extends MidasAd{

    /**
     * 穿山甲全屏视频广告对象
     */
    private TTFullScreenVideoAd ttFullScreenVideoAd;

    public TTFullScreenVideoAd getTtFullScreenVideoAd() {
        return ttFullScreenVideoAd;
    }

    public void setTtFullScreenVideoAd(TTFullScreenVideoAd ttFullScreenVideoAd) {
        this.ttFullScreenVideoAd = ttFullScreenVideoAd;
    }

    @Override
    public void clear() {
        if (ttFullScreenVideoAd != null) {
            ttFullScreenVideoAd = null;
        }
    }
}
