package com.jk.adsdkdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 开屏广告页面<p>
 *
 * @author zixuefei
 * @since 2019/11/14 14:25
 */
public class SplashAdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_ad);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
