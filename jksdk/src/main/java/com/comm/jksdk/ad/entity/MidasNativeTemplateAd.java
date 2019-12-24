package com.comm.jksdk.ad.entity;

import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.comm.jksdk.ad.listener.AdChargeListener;
import com.comm.jksdk.ad.listener.SelfRenderChargeListener;
import com.comm.jksdk.constant.Constants;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;

/**
 * @ProjectName: Midas
 * @Package: com.comm.jksdk.ad.entity
 * @ClassName: MidasNativeTemplateAd
 * @Description: 原生模板广告
 * @Author: fanhailong
 * @CreateDate: 2019/12/20 14:38
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/20 14:38
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class MidasNativeTemplateAd extends MidasAd {

    /**
     * 穿山甲信息流模板广告对象
     */
    private TTNativeExpressAd ttNativeExpressAd;
    /**
     * 优量汇原生模板广告对象
     */
    private NativeExpressAD nativeExpressAD;

    /**
     * 计费/埋点用到的监听
     */
    private AdChargeListener adChargeListener;
    /**
     * 模板广告的宽度
     */
    private float width;


    public void setChargeListener(AdChargeListener<AdInfo> selfRenderChargeListener){
        this.adChargeListener = selfRenderChargeListener;
    }

    public AdChargeListener getAdChargeListener() {
        return adChargeListener;
    }

    public TTNativeExpressAd getTtNativeExpressAd() {
        return ttNativeExpressAd;
    }

    public void setTtNativeExpressAd(TTNativeExpressAd ttNativeExpressAd) {
        this.ttNativeExpressAd = ttNativeExpressAd;
    }

    public NativeExpressAD getNativeExpressAD() {
        return nativeExpressAD;
    }

    public void setNativeExpressAD(NativeExpressAD nativeExpressAD) {
        this.nativeExpressAD = nativeExpressAD;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * 开始渲染
     */
    public void render(){
        if (Constants.AdSourceType.ChuanShanJia.equals(getAdSource())) {
            if (ttNativeExpressAd == null) {
                return;
            }
            csjBindListener(ttNativeExpressAd);
            ttNativeExpressAd.render();
        } else {
            if (nativeExpressAD == null) {
                return;
            }
            if (getAddView() != null) {
                ((NativeExpressADView)getAddView()).render();
            }
        }
    }

    @Override
    public void clear() {

    }


    private void csjBindListener(TTNativeExpressAd ttNativeExpressAd){
    }
}
