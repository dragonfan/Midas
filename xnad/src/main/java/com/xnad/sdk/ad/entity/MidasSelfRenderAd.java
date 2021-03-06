package com.xnad.sdk.ad.entity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.qq.e.ads.nativ.NativeADEventListener;
import com.qq.e.ads.nativ.NativeUnifiedADData;
import com.qq.e.ads.nativ.widget.NativeAdContainer;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.util.AdError;
import com.xnad.sdk.ad.outlistener.AdOutChargeListener;
import com.xnad.sdk.config.Constants;

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

//    private ViewGroup container;
//    /**
//     * 外部计费回调（埋点用）
//     */
//    private AdOutChargeListener adOutChargeListener;

    /**
     * 穿山甲自渲染信息流广告
     */
    private TTFeedAd ttFeedAd;
    /**
     * 优量汇自渲染广告
     */
    private NativeUnifiedADData nativeUnifiedADData;
//    /**
//     * 绑定view监听器
//     */
//    private AdOutChargeListener bindViewListener;

    public TTFeedAd getTtFeedAd() {
        return ttFeedAd;
    }

    public void setTtFeedAd(TTFeedAd ttFeedAd) {
        this.ttFeedAd = ttFeedAd;
    }

//    public AdOutChargeListener getAdOutChargeListener() {
//        return adOutChargeListener;
//    }
//
//    public void setAdOutChargeListener(AdOutChargeListener adOutChargeListener) {
//        this.adOutChargeListener = adOutChargeListener;
//    }
//
//    public void bindViewToAdListener(Context context, ViewGroup viewGroup, List<View> clickViewList, List<View> creativeViewList, AdOutChargeListener adOutChargeListener) {
//        this.adOutChargeListener = adOutChargeListener;
//        bindAd(context, viewGroup, clickViewList, creativeViewList);
//    }

//    public ViewGroup getContainer() {
//        return container;
//    }
//
//    public void setContainer(ViewGroup container) {
//        this.container = container;
//    }

    public NativeUnifiedADData getNativeUnifiedADData() {
        return nativeUnifiedADData;
    }

    public void setNativeUnifiedADData(NativeUnifiedADData nativeUnifiedADData) {
        this.nativeUnifiedADData = nativeUnifiedADData;
    }

//    public void setBindViewListener(AdOutChargeListener bindViewListener) {
//        this.bindViewListener = bindViewListener;
//    }

//    private void bindAd(Context context, ViewGroup viewGroup, List<View> clickViewList, List<View> creativeViewList){
//        if (Constants.AdSourceType.ChuanShanJia.equals(getAdSource())) {
//            if (ttFeedAd == null) {
//                return;
//            }
//            ttFeedAd.registerViewForInteraction(viewGroup, clickViewList, creativeViewList, new TTNativeAd.AdInteractionListener() {
//                @Override
//                public void onAdClicked(View view, TTNativeAd ttNativeAd) {
//                    if (bindViewListener != null) {
//                        bindViewListener.adSuccess(null);
//                        bindViewListener.adClicked(null);
//                    }
//                }
//
//                @Override
//                public void onAdCreativeClick(View view, TTNativeAd ttNativeAd) {
//                    if (bindViewListener != null) {
//                        bindViewListener.adClicked(null);
//                    }
//                }
//
//                @Override
//                public void onAdShow(TTNativeAd ttNativeAd) {
//                    if (bindViewListener != null) {
//                        bindViewListener.adExposed(null);
//                    }
//                }
//            });
////            if (viewGroup != null && !CollectionUtils.isEmpty(clickViewList) && !CollectionUtils.isEmpty(creativeViewList)) {
////            }
//        } else {
//            if (nativeUnifiedADData == null) {
//                return;
//            }
//            nativeUnifiedADData.bindAdToView(context, (NativeAdContainer) viewGroup, null,
//                    clickViewList);
//            nativeUnifiedADData.setNativeAdEventListener(new NativeADEventListener() {
//                @Override
//                public void onADExposed() {
//                    if (bindViewListener != null) {
//                        bindViewListener.adSuccess(null);
//                        bindViewListener.adExposed(null);
//                    }
//                }
//
//                @Override
//                public void onADClicked() {
//                    if (bindViewListener != null) {
//                        bindViewListener.adClicked(null);
//                    }
//                }
//
//                @Override
//                public void onADError(AdError adError) {
//                    if (bindViewListener != null) {
//                        bindViewListener.adError(null, adError.getErrorCode(), adError.getErrorMsg());
//                    }
//                }
//
//                @Override
//                public void onADStatusChanged() {
//
//                }
//            });
//        }
//    }

    @Override
    public void clear() {

    }

    @Override
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

    @Override
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
            return null;
        }
    }

    @Override
    public String getIconUrl(){
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
            return nativeUnifiedADData.getIconUrl();
        }
    }

    public List<String> getImageList(){
        if (Constants.AdSourceType.ChuanShanJia.equals(getAdSource())) {
            if (ttFeedAd == null) {
                return null;
            }
            List<TTImage> images = ttFeedAd.getImageList();
            if (images==null||images.size()==0) {
                return null;
            }
            List<String> imgList = new ArrayList<>();
            for (TTImage image : images) {
                imgList.add(image.getImageUrl());
            }
            return imgList;
        } else {
            if (nativeUnifiedADData == null) {
                return null;
            }
            return nativeUnifiedADData.getImgList();
        }
    }

    @Override
    public String getImageUrl(){
        if (Constants.AdSourceType.ChuanShanJia.equals(getAdSource())) {
            if (ttFeedAd == null) {
                return null;
            }
            List<TTImage> images = ttFeedAd.getImageList();
            if (images==null||images.size()==0) {
                return null;
            }
            List<String> imgList = new ArrayList<>();
            for (TTImage image : images) {
                imgList.add(image.getImageUrl());
            }
            return imgList.get(0);
        } else {
            if (nativeUnifiedADData == null) {
                return null;
            }
            return nativeUnifiedADData.getImgUrl();
        }
    }

    /**
     * 判断是否是视频或者图片广告 1=图片广告；2=视频广告; -1=未知
     * @return
     */
    public int getMidasAdPatternType(){
        if (Constants.AdSourceType.ChuanShanJia.equals(getAdSource())) {
            if (ttFeedAd == null) {
                return -1;
            }
            if (ttFeedAd.getImageMode() == TTAdConstant.IMAGE_MODE_VIDEO) {
                return 2;
            } else {
                return 1;
            }
        } else {
            if (nativeUnifiedADData == null) {
                return -1;
            }
            if (nativeUnifiedADData.getAdPatternType() == AdPatternType.NATIVE_VIDEO) {
                return 2;
            } else {
                return 1;
            }
        }
    }

    /**
     * 获取穿山甲图文方式。-1=未知；1=小图；2=大图；3=组图；4=竖版图
     * @return
     */
    public int getCsjImageMode(){
        if (ttFeedAd == null) {
            return -1;
        } else if (ttFeedAd.getImageMode() == TTAdConstant.IMAGE_MODE_SMALL_IMG) {
            return 1;
        } else if (ttFeedAd.getImageMode() == TTAdConstant.IMAGE_MODE_LARGE_IMG) {
            return 2;
        } else if (ttFeedAd.getImageMode() == TTAdConstant.IMAGE_MODE_GROUP_IMG) {
            return 3;
        } else if (ttFeedAd.getImageMode() == TTAdConstant.IMAGE_MODE_VERTICAL_IMG) {//竖版图片
            return 4;
        } else {
            return -1;
        }
    }

    /**
     * 优量汇广告用到
     */
    public void resume(){
        try {
            if (nativeUnifiedADData != null) {
                nativeUnifiedADData.resume();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 优量汇广告用到
     */
    public void destroy(){
        try {
            if (!Constants.AdSourceType.YouLiangHui.equals(getSource())) {
                return;
            }
            if (nativeUnifiedADData != null) {
                nativeUnifiedADData.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
