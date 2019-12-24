package com.comm.jksdk.ad.entity;

import android.view.View;
import android.view.ViewGroup;

import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.comm.jksdk.ad.listener.BindViewListener;
import com.comm.jksdk.ad.listener.SelfRenderChargeListener;
import com.comm.jksdk.constant.Constants;
import com.comm.jksdk.utils.CollectionUtils;
import com.qq.e.ads.nativ.NativeUnifiedAD;
import com.qq.e.ads.nativ.NativeUnifiedADData;

import java.util.ArrayList;
import java.util.List;

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
    private SelfRenderChargeListener adListener;

    /**
     * 穿山甲自渲染信息流广告
     */
    private TTFeedAd ttFeedAd;
    /**
     * 优量汇自渲染广告
     */
    private NativeUnifiedADData nativeUnifiedADData;
    /**
     * 绑定view监听器
     */
    private BindViewListener bindViewListener;

    public TTFeedAd getTtFeedAd() {
        return ttFeedAd;
    }

    public void setTtFeedAd(TTFeedAd ttFeedAd) {
        this.ttFeedAd = ttFeedAd;
    }

    public SelfRenderChargeListener getAdListener() {
        return adListener;
    }

    public void setAdListener(SelfRenderChargeListener adListener) {
        this.adListener = adListener;
        bindLinstenr();
    }

    public ViewGroup getContainer() {
        return container;
    }

    public void setContainer(ViewGroup container) {
        this.container = container;
    }

    public NativeUnifiedADData getNativeUnifiedADData() {
        return nativeUnifiedADData;
    }

    public void setNativeUnifiedADData(NativeUnifiedADData nativeUnifiedADData) {
        this.nativeUnifiedADData = nativeUnifiedADData;
    }

    public BindViewListener getBindViewListener() {
        return bindViewListener;
    }

    public void setBindViewListener(BindViewListener bindViewListener) {
        this.bindViewListener = bindViewListener;
    }

    private void bindLinstenr(){
        if (Constants.AdSourceType.ChuanShanJia.equals(getAdSource())) {
            if (ttFeedAd == null) {
                return;
            }
            if (adListener == null) {
                return;
            }
            ViewGroup viewGroup = adListener.getViewGroup();
            List<View> clickViewList = adListener.getClickViewList();
            List<View> creativeViewList = adListener.getCreativeViewList();
            if (viewGroup != null && !CollectionUtils.isEmpty(clickViewList) && !CollectionUtils.isEmpty(creativeViewList)) {
                ttFeedAd.registerViewForInteraction(viewGroup, clickViewList, creativeViewList, new TTNativeAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, TTNativeAd ttNativeAd) {
                        if (bindViewListener != null) {
                            bindViewListener.adClicked();
                        }
                    }

                    @Override
                    public void onAdCreativeClick(View view, TTNativeAd ttNativeAd) {
                        if (bindViewListener != null) {
                            bindViewListener.adClicked();
                        }
                    }

                    @Override
                    public void onAdShow(TTNativeAd ttNativeAd) {
                        if (bindViewListener != null) {
                            bindViewListener.adExposed();
                        }
                    }
                });
            }
        } else {

        }
    }

    @Override
    public void clear() {

    }

    public String getTitle(){
        if (Constants.AdSourceType.ChuanShanJia.equals(getAdSource())) {
            if (ttFeedAd == null) {
                return null;
            }
            return ttFeedAd.getTitle();
        } else {
            if (nativeUnifiedADData == null) {
                return null;
            }
            return nativeUnifiedADData.getTitle();
        }
    }

    public String getDescription(){
        if (Constants.AdSourceType.ChuanShanJia.equals(getAdSource())) {
            if (ttFeedAd == null) {
                return null;
            }
            return ttFeedAd.getDescription();
        } else {
            if (nativeUnifiedADData == null) {
                return null;
            }
            return nativeUnifiedADData.getDesc();
        }
    }

    public String getSource(){
        if (Constants.AdSourceType.ChuanShanJia.equals(getAdSource())) {
            if (ttFeedAd == null) {
                return null;
            }
            return ttFeedAd.getSource();
        } else {
            if (nativeUnifiedADData == null) {
                return null;
            }
            return nativeUnifiedADData.getDesc();
        }
    }

    public String getImageUrl(){
        if (Constants.AdSourceType.ChuanShanJia.equals(getAdSource())) {
            if (ttFeedAd == null) {
                return null;
            }
            TTImage icon = ttFeedAd.getIcon();
            if (icon == null || !icon.isValid()) {
                return null;
            }
            return icon.getImageUrl();
        } else {
            if (nativeUnifiedADData == null) {
                return null;
            }
            return nativeUnifiedADData.getDesc();
        }
    }

    public List<String> getImageList(){
        if (Constants.AdSourceType.ChuanShanJia.equals(getAdSource())) {
            if (ttFeedAd == null) {
                return null;
            }
            List<TTImage> images = ttFeedAd.getImageList();
            if (CollectionUtils.isEmpty(images)) {
                return null;
            }
            List<String> imgList = new ArrayList<>();
            for (TTImage image : images) {
                imgList.add(image.getImageUrl());
            }
            return imgList;
        } else {
            return null;
        }
    }
}
