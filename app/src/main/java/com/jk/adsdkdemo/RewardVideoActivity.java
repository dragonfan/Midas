package com.jk.adsdkdemo;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comm.jksdk.GeekAdSdk;
import com.comm.jksdk.ad.entity.AdInfo;
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
    private EditText positionEdit;
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
        positionEdit = findViewById(R.id.splash_position_edit);
        refreshBtn.setOnClickListener(this);
        adManager = GeekAdSdk.getAdsManger();
        positionEdit.setText("click_virus_killing_ad");
        loadSplashAd("click_virus_killing_ad");
    }

    /**
     * 获取视频广告并加载
     */
    private void loadSplashAd(String position) {
        // cp_ad_1
        splashContainer.removeAllViews();
        adManager.loadRewardVideoAd(this, position, "user123", 1, new VideoAdListener() {

            @Override
            public void onVideoResume(AdInfo info) {

            }

            @Override
            public void onVideoRewardVerify(AdInfo info, boolean rewardVerify, int rewardAmount, String rewardName) {
                Toast.makeText(RewardVideoActivity.this, rewardName + rewardAmount, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVideoComplete(AdInfo info) {

            }


            @Override
            public void adSuccess(AdInfo info) {
                LogUtils.d(TAG, "-----adSuccess-----");
                splashContainer.addView(adManager.getAdView());
            }

            @Override
            public void adExposed(AdInfo info) {
                LogUtils.d(TAG, "-----adExposed-----");
            }

            @Override
            public void adClicked(AdInfo info) {
                LogUtils.d(TAG, "-----adClicked-----");
            }

            @Override
            public void adClose(AdInfo info) {
                LogUtils.d(TAG, "-----adClose-----");
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
                loadSplashAd(positionEdit.getText().toString().trim());
                break;
            default:
                break;
        }
    }
}
