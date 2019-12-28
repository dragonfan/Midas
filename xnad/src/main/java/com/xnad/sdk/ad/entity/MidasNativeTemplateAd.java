package com.xnad.sdk.ad.entity;

import android.view.View;

import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.xnad.sdk.ad.listener.AdChargeListener;
import com.xnad.sdk.ad.listener.BindViewListener;
import com.xnad.sdk.config.Constants;
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
     * 绑定view监听
     */
    private BindViewListener bindViewListener;
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

    public BindViewListener getBindViewListener() {
        return bindViewListener;
    }

    public void setBindViewListener(BindViewListener bindViewListener) {
        this.bindViewListener = bindViewListener;
    }

    /**
     * 开始渲染
     */
    public void render(){
        if (Constants.AdSourceType.ChuanShanJia.equals(getAdSource())) {
            if (ttNativeExpressAd == null) {
                return;
            }
//            csjBindListener(ttNativeExpressAd);
            ttNativeExpressAd.render();
        } else {
            if (nativeExpressAD == null) {
                return;
            }
            ylhBindListener(nativeExpressAD);
            if (getAddView() != null) {
                ((NativeExpressADView)getAddView()).render();
            }
        }
    }

    private void ylhBindListener(NativeExpressAD nativeExpressAD) {

    }

    @Override
    public void clear() {

    }


    private void csjBindListener(TTNativeExpressAd ttNativeExpressAd){
        ttNativeExpressAd.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
            @Override
            public void onAdClicked(View view, int type) {
                if (bindViewListener != null) {
                    bindViewListener.adClicked();
                }
            }

            @Override
            public void onAdShow(View view, int type) {
                if (bindViewListener != null) {
                    bindViewListener.adExposed();
                }
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                if (bindViewListener != null) {
                    bindViewListener.adError(code, msg);
                }
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                setAddView(view);
                if (bindViewListener != null) {
                    bindViewListener.adSuccess();
                }
            }
        });
        //dislike设置
//        bindDislike(ttNativeExpressAd, false);
        if (ttNativeExpressAd.getInteractionType() == TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
            ttNativeExpressAd.setDownloadListener(new TTAppDownloadListener() {
                @Override
                public void onIdle() {
//                TToast.show(NativeExpressActivity.this, "点击开始下载", Toast.LENGTH_LONG);
                }

                @Override
                public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
//                if (!mHasShowDownloadActive) {
//                    mHasShowDownloadActive = true;
//                    TToast.show(NativeExpressActivity.this, "下载中，点击暂停", Toast.LENGTH_LONG);
//                }
                }

                @Override
                public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
//                TToast.show(NativeExpressActivity.this, "下载暂停，点击继续", Toast.LENGTH_LONG);
                }

                @Override
                public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
//                TToast.show(NativeExpressActivity.this, "下载失败，点击重新下载", Toast.LENGTH_LONG);
                }

                @Override
                public void onInstalled(String fileName, String appName) {
//                TToast.show(NativeExpressActivity.this, "安装完成，点击图片打开", Toast.LENGTH_LONG);
                }

                @Override
                public void onDownloadFinished(long totalBytes, String fileName, String appName) {
//                TToast.show(NativeExpressActivity.this, "点击安装", Toast.LENGTH_LONG);
                }
            });
        }
    }
}
