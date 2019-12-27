package com.jk.adsdkdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xnad.sdk.MidasAdSdk;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.listener.VideoAdListener;
import com.jk.adsdkdemo.utils.LogUtils;

/**
 * 全屏视频页面<p>
 *
 * @author zixuefei
 * @since 2019/11/15 18:09
 */
public class FullScreenVideoActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = FullScreenVideoActivity.class.getSimpleName();
    private FrameLayout splashContainer;
    private EditText positionEdit;
    private Button refreshBtn, preloadingAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);
        initView();
    }

    private void initView() {
        setTitle("全屏视频广告");
        splashContainer = findViewById(R.id.video_container);
        refreshBtn = findViewById(R.id.video_refresh);
        positionEdit = findViewById(R.id.splash_position_edit);
        refreshBtn.setOnClickListener(this);

        preloadingAd = findViewById(R.id.button_preloading_ad);
        preloadingAd.setOnClickListener(this);

        positionEdit.setText("8861663631");
//        loadSplashAd("cp_ad_1");
    }

    /**
     * 获取视频广告并加载
     */
    private void loadSplashAd(String position) {
        // cold_kp 、hot_kp
        splashContainer.removeAllViews();
        MidasAdSdk.getAdsManger().loadMidasFullScreenVideoAd(this, position, new VideoAdListener<AdInfo>() {
            @Override
            public void onVideoResume(AdInfo info) {

            }

            @Override
            public void onVideoRewardVerify(AdInfo info, boolean rewardVerify, int rewardAmount, String rewardName) {

            }

            @Override
            public void onVideoComplete(AdInfo info) {
                LogUtils.d(TAG, "-----onVideoComplete-----");
            }

            @Override
            public void adSuccess(AdInfo info) {
                LogUtils.d(TAG, "-----adSuccess-----");
                View addView = info.getAdView();
                if (addView != null) {
                    splashContainer.addView(addView);
                }
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
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                //显示错误信息，调试用
                LogUtils.d(TAG, "-----adError-----" + errorMsg);
                TextView textView = new TextView(FullScreenVideoActivity.this);
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
            case R.id.button_preloading_ad:
                preloadingSplashAd(positionEdit.getText().toString().trim());
                break;
            case R.id.video_refresh:
                loadSplashAd(positionEdit.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    /**
     * 预加载
     */
    private void preloadingSplashAd(String position) {
//        MidasAdSdk.getAdsManger().preloadingVideoAd(this, position, new AdPreloadingListener() {
//            @Override
//            public void adSuccess(AdInfo info) {
//                LogUtils.d(TAG, "-----adSuccess-----");
//                Toast.makeText(getApplicationContext(), "预加载成功", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void adError(AdInfo info, int errorCode, String errorMsg) {
//                LogUtils.d(TAG, "-----adError-----" + errorMsg);
//                Toast.makeText(getApplicationContext(), "预加载失败", Toast.LENGTH_LONG).show();
//            }
//
//        });
    }

}
