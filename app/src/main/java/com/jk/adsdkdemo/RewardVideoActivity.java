package com.jk.adsdkdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.comm.jksdk.GeekAdSdk;
import com.comm.jksdk.ad.listener.AdManager;

/**
 * 激励视频页面<p>
 *
 * @author zixuefei
 * @since 2019/11/16 13:04
 */
public class RewardVideoActivity extends AppCompatActivity {
    private final String TAG = RewardVideoActivity.class.getSimpleName();
    private AdManager adManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);
        initView();
    }

    private void initView() {
        setTitle("激励视频广告");
//        splashContainer = findViewById(R.id.splash_container);
//        refreshBtn = findViewById(R.id.splash_refresh);
//        refreshBtn.setOnClickListener(this);
        adManager = GeekAdSdk.getAdsManger();
//        loadSplashAd();
    }
}
