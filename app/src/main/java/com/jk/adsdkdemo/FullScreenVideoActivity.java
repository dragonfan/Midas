package com.jk.adsdkdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.comm.jksdk.GeekAdSdk;
import com.comm.jksdk.ad.listener.AdManager;

/**
 * 全屏视频页面<p>
 *
 * @author zixuefei
 * @since 2019/11/15 18:09
 */
public class FullScreenVideoActivity extends AppCompatActivity {
    private final String TAG = FullScreenVideoActivity.class.getSimpleName();
    private AdManager adManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);
        initView();
    }

    private void initView() {
        setTitle("全屏视频广告");
//        splashContainer = findViewById(R.id.splash_container);
//        refreshBtn = findViewById(R.id.splash_refresh);
//        refreshBtn.setOnClickListener(this);
        adManager = GeekAdSdk.getAdsManger();
//        loadSplashAd();
    }
}
