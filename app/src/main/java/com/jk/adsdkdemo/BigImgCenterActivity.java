package com.jk.adsdkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.comm.jksdk.GeekAdSdk;
import com.comm.jksdk.ad.listener.AdListener;
import com.comm.jksdk.ad.listener.AdManager;
import com.jk.adsdkdemo.utils.LogUtils;

public class BigImgCenterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button requestBt, loadBt;
    private FrameLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_img_center);
        container = findViewById(R.id.container);
        requestBt = findViewById(R.id.button_request_ad);
        loadBt = findViewById(R.id.button_load_ad);
        requestBt.setOnClickListener(this);
    }

    View adView;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_request_ad:
                AdManager adManager = GeekAdSdk.getAdsManger();
                adManager.loadAd("12", new AdListener() {
                    @Override
                    public void adSuccess() {

                    }

                    @Override
                    public void adExposed() {
                        LogUtils.e("adExposed");
                    }

                    @Override
                    public void adClicked() {

                    }

                    @Override
                    public void adError(int errorCode, String errorMsg) {

                    }
                });
                adView = adManager.getAdView();
                if (adView != null) {
                    container.removeAllViews();
                    container.addView(adView);
                }
                break;
        }
    }
}
