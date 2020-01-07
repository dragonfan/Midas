package com.xnad.sdk.ad.utils;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xnad.sdk.R;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.entity.MidasSelfRenderAd;
import com.xnad.sdk.ad.outlistener.AdOutChargeListener;
import com.xnad.sdk.config.AdParameter;
import com.xnad.sdk.config.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * <p>
 * Author: AnYaBo
 * Date: 2020/1/7
 * Copyright: Copyright (c) 2016-2022
 * Company: @小牛科技
 * Email:anyabo@xiaoniu.com
 * Update Comments:
 *
 * @author anyabo
 */
public class AdUtils {

    /**
     * 显示自渲染视图
     * @param adInfo    广告实体
     */
    public void showSelfRenderView(AdInfo adInfo){
        try {
            MidasSelfRenderAd midasSelfRenderAd = (MidasSelfRenderAd) adInfo.getMidasAd();
            AdParameter adParameter = adInfo.getAdParameter();
            ViewGroup viewContainer = adParameter.getViewContainer();
            View view = LayoutInflater.from(adParameter.getActivity()).
                    inflate(adParameter.getLayoutId(),viewContainer,false);
            viewContainer.removeAllViews();
            viewContainer.addView(view);
            //小图标
            ImageView adSmallLogoIv = view.findViewById(R.id.ivAdIcon);
            String iconUrl = midasSelfRenderAd.getIconUrl();
            if (!TextUtils.isEmpty(iconUrl)) {
                Glide.with(adParameter.getActivity()).load(iconUrl).into(adSmallLogoIv);
            }
            //标题
            TextView adTitleTv = view.findViewById(R.id.tvAdTitle);
            adTitleTv.setText(midasSelfRenderAd.getTitle());
            //描述
            TextView adDescTv = view.findViewById(R.id.tvAdDesc);
            adDescTv.setText(midasSelfRenderAd.getDescription());
            //大图片
            ImageView adImgIv = view.findViewById(R.id.ivAdImage);
            List<String> imageList = midasSelfRenderAd.getImageList();
            if (imageList != null && imageList.size() > 0) {
                Glide.with(adParameter.getActivity()).load(imageList.get(0)).into(adImgIv);
            }

            List<View> clickViewList = new ArrayList<>();
            clickViewList.add(view);
            //触发创意广告的view（点击下载或拨打电话）
            List<View> creativeViewList = new ArrayList<>();
//            creativeViewList.add(mCreativeButton);
            midasSelfRenderAd.bindViewToAdListener(adParameter.getActivity(),
                    viewContainer, clickViewList,
                    creativeViewList, new AdOutChargeListener<AdInfo>(){

                        @Override
                        public void adSuccess(AdInfo info) {

                        }

                        @Override
                        public void adError(AdInfo info, int errorCode, String errorMsg) {

                        }

                        @Override
                        public void adExposed(AdInfo info) {

                        }

                        @Override
                        public void adClicked(AdInfo info) {

                        }
                    });

            if (Constants.AdSourceType.ChuanShanJia.equals(adInfo.getMidasAd().getAdSource())) {

            }else if (Constants.AdSourceType.YouLiangHui.equals(adInfo.getMidasAd().getAdSource())){

            }else {

            }
        }catch (Exception e){
            Log.e("SdkRequestManager","" + e.getMessage());
        }

    }

}
