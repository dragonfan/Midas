package com.jk.adsdkdemo;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.comm.jksdk.GeekAdSdk;
import com.comm.jksdk.ad.admanager.NativesAdManger;
import com.comm.jksdk.ad.listener.AdListener;
import com.comm.jksdk.ad.listener.AdManager;
import com.jk.adsdkdemo.utils.Constants;
import com.jk.adsdkdemo.utils.LogUtils;

public class BigImgAcitvity extends AppCompatActivity implements View.OnClickListener {

    private Button requestBt, loadBt;
    private FrameLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_img_acitvity);
        container = findViewById(R.id.container);
        requestBt = findViewById(R.id.button_request_ad);
        loadBt = findViewById(R.id.button_load_ad);
        requestBt.setOnClickListener(this);
        loadBt.setOnClickListener(this);
    }

    View adView;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_request_ad:
//                adView = NativesAdManger.getInstance().setContext(this)
//                        .setAdPositionId("1")
//                        .setAdListener(mAdListener)
//                        .setDefaultConfigKey(Constants.DEFAULT_CONFIG_KEY)
//                        .build()
//                        .getAdView();
                AdManager adManager = GeekAdSdk.getAdsManger();
                adManager.loadAd("1", new AdListener() {
                    @Override
                    public void adSuccess() {

                    }

                    @Override
                    public void adExposed() {

                    }

                    @Override
                    public void adClicked() {

                    }

                    @Override
                    public void adError(int errorCode, String errorMsg) {

                    }
                });
                adView = adManager.getAdView();
                break;
            case R.id.button_load_ad:
                container.addView(adView);
                break;
        }
    }

    private AdListener mAdListener = new AdListener() {
        @Override
        public void adSuccess() {
            LogUtils.w("lpb", "adSuccess");
        }

        @Override
        public void adExposed() {
            LogUtils.w("lpb", "adExposed");

        }

        @Override
        public void adClicked() {
            LogUtils.w("lpb", "adClicked");

        }

        @Override
        public void adError(int errorCode, String errorMsg) {
            LogUtils.w("lpb", "adError errorCode = " + errorCode + " errorMsg = " + errorMsg);

        }

    };
}
