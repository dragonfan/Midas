package com.jk.adsdkdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.comm.jksdk.GeekAdSdk;
import com.comm.jksdk.ad.listener.AdListener;
import com.comm.jksdk.ad.listener.AdManager;
import com.jk.adsdkdemo.utils.LogUtils;

/**
 * 开屏广告页面<p>
 *
 * @author zixuefei
 * @since 2019/11/14 14:25
 */
public class SplashAdActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = SplashAdActivity.class.getSimpleName();
    private FrameLayout splashContainer;
    private Button refreshBtn;
    private AdManager adManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_ad);
        initView();
    }

    private void initView() {
        setTitle("开屏广告");
        splashContainer = findViewById(R.id.splash_container);
        refreshBtn = findViewById(R.id.splash_refresh);
        refreshBtn.setOnClickListener(this);
        adManager = GeekAdSdk.getAdsManger();
        loadSplashAd();
    }

    /**
     * 获取开屏广告并加载
     */
    private void loadSplashAd() {
        adManager.loadAd("open_screen_ad", new AdListener() {
            @Override
            public void adSuccess() {
                LogUtils.d(TAG, "-----adSuccess-----");
                splashContainer.addView(adManager.getAdView());
            }

            @Override
            public void adExposed() {
                LogUtils.d(TAG, "-----adExposed-----");
            }

            @Override
            public void adClicked() {
                LogUtils.d(TAG, "-----adClicked-----");
            }

            @Override
            public void adError(int errorCode, String errorMsg) {
                LogUtils.d(TAG, "-----adError-----" + errorMsg);
                TextView textView = new TextView(SplashAdActivity.this);
                textView.setText("error:" + errorCode + errorMsg);
                splashContainer.removeAllViews();
                splashContainer.addView(textView);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.splash_refresh:
                loadSplashAd();
                break;
            default:
                break;
        }
    }
}
