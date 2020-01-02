package com.jk.adsdkdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jk.adsdkdemo.bean.AdConfig;
import com.jk.adsdkdemo.utils.LogUtils;
import com.xnad.sdk.MidasAdSdk;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.outlistener.AdRewardVideoListener;
import com.xnad.sdk.config.AdParameter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 激励视频页面<p>
 *
 * @author zixuefei
 * @since 2019/11/16 13:04
 */
public class RewardVideoActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = RewardVideoActivity.class.getSimpleName();
    private EditText positionEdit;
    private FrameLayout splashContainer;
    private Button refreshBtn, preloadingAd;

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
        positionEdit.setText(AdConfig.REWARD_AD_POSITION);

        preloadingAd = findViewById(R.id.button_preloading_ad);
        preloadingAd.setOnClickListener(this);
//        loadRewardVideoAd("click_virus_killing_ad");
    }

    /**
     * 获取视频广告并加载
     */
    private void loadRewardVideoAd(String position) {
        // cp_ad_1
        splashContainer.removeAllViews();
        AdParameter adParameter = new AdParameter.Builder(this, position)
                //激励广告用到的用户id
                .setUserId("user123")
                //激励视频横竖屏 1=竖屏；2=横屏
                .setOrientation(1)
                //金币名称
                .setRewardName("金币")
                //金币数量
                .setRewardAmount(3)
                .build();
        MidasAdSdk.getAdsManger().loadMidasRewardVideoAd(adParameter, new AdRewardVideoListener<AdInfo>() {
            //激励奖励回调
            @Override
            public void onVideoRewardVerify(AdInfo info, boolean rewardVerify, int rewardAmount, String rewardName) {

            }
            //视频播放完成回调
            @Override
            public void onVideoComplete(AdInfo info) {
                LogUtils.d(TAG, "-----onVideoComplete-----");
            }

            //加载广告成功回调
            @Override
            public void adSuccess(AdInfo info) {
                LogUtils.d(TAG, "-----adSuccess-----");
            }
            //曝光回调
            @Override
            public void adExposed(AdInfo info) {
                LogUtils.d(TAG, "-----adExposed-----");
            }
            //点击回调
            @Override
            public void adClicked(AdInfo info) {
                LogUtils.d(TAG, "-----adClicked-----");
            }
            //广告关闭回调
            @Override
            public void adClose(AdInfo info) {
                LogUtils.d(TAG, "-----adClose-----");
            }
            //广告加载失败回调
            @Override
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                LogUtils.d(TAG, "-----adError-----" + errorMsg);
                TextView textView = new TextView(RewardVideoActivity.this);
                textView.setText("error:" + errorCode + errorMsg);
                splashContainer.removeAllViews();
                splashContainer.addView(textView);
            }

        });
    }

    /**
     * 预加载
     */
    private void preloadingSplashAd(String position) {
//        splashContainer.removeAllViews();
//        MidasAdSdk.getAdsManger().preloadingRewardVideoAd(this, position, "user123", 1, new AdPreloadingListener() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_preloading_ad:
                preloadingSplashAd(positionEdit.getText().toString().trim());
                break;
            case R.id.video_refresh:
                loadRewardVideoAd(positionEdit.getText().toString().trim());
                break;
            default:
                break;
        }
    }
}
