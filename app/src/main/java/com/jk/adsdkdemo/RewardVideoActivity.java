package com.jk.adsdkdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comm.jksdk.GeekAdSdk;
import com.comm.jksdk.ad.listener.AdManager;
import com.comm.jksdk.ad.listener.VideoAdListener;
import com.jk.adsdkdemo.utils.LogUtils;

/**
 * 激励视频页面<p>
 *
 * @author zixuefei
 * @since 2019/11/16 13:04
 */
public class RewardVideoActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = RewardVideoActivity.class.getSimpleName();
    private AdManager adManager;
    private FrameLayout splashContainer;
    private Button refreshBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);
        initView();
    }

    private void initView() {
        setTitle("激励视频广告");
        splashContainer = findViewById(R.id.video_container);
        refreshBtn = findViewById(R.id.video_refresh);
        refreshBtn.setOnClickListener(this);
        adManager = GeekAdSdk.getAdsManger();
        loadSplashAd();
    }

    /**
     * 获取视频广告并加载
     */
    private void loadSplashAd() {
        // cp_ad_1
        adManager.loadRewardVideoAd(this, "click_virus_killing_ad", "user123", 1, new VideoAdListener() {

            @Override
            public void onVideoResume() {

            }

            @Override
            public void onVideoRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                Toast.makeText(RewardVideoActivity.this, rewardName + rewardAmount, Toast.LENGTH_SHORT).show();
            }

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
                TextView textView = new TextView(RewardVideoActivity.this);
                textView.setText("error:" + errorCode + errorMsg);
                splashContainer.removeAllViews();
                splashContainer.addView(textView);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_refresh:
                loadSplashAd();
                break;
            default:
                break;
        }
    }
}
