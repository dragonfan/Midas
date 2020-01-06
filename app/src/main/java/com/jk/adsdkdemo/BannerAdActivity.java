package com.jk.adsdkdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;


import com.jk.adsdkdemo.config.AdConfig;
import com.jk.adsdkdemo.utils.ToastUtils;
import com.xnad.sdk.MidasAdSdk;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.outlistener.AdBannerListener;
import com.xnad.sdk.config.AdParameter;


/**
 * Desc:banner广告类型activity
 * <p>
 * Author: AnYaBo
 * Date: 2020/1/3
 * Copyright: Copyright (c) 2016-2022
 * Company: @小牛科技
 * Email:anyabo@xiaoniu.com
 * Update Comments:
 *
 * @author anyabo
 */
public class BannerAdActivity extends AppCompatActivity {
    private EditText mPositionIdEt;
    private ViewGroup mBannerContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_ad);
        initViews();
    }

    private void initViews() {
        mPositionIdEt = findViewById(R.id.position_id_edit);
        mBannerContainer = findViewById(R.id.bannerContainer);
        mPositionIdEt.setText(AdConfig.BANNER_AD_POSITION);
        findViewById(R.id.load_ad_btn).setOnClickListener(v -> requestAd());
    }

    private void requestAd() {
        String adPositionId = mPositionIdEt.getText().toString().trim();
        AdParameter adParameter = new AdParameter.Builder(this, adPositionId)
                .setViewContainer(mBannerContainer)
                .build();
        MidasAdSdk.getAdsManger().loadMidasBannerAd(adParameter, new AdBannerListener() {
            @Override
            public void onAdShow(AdInfo info) {

            }

            @Override
            public void onAdClicked(AdInfo info) {

            }

            @Override
            public void adClose(AdInfo info) {

            }

            @Override
            public void adSuccess(Object info) {

            }

            @Override
            public void adError(Object info, int errorCode, String errorMsg) {
                ToastUtils.showShort(errorMsg);
            }
        });

    }


}
