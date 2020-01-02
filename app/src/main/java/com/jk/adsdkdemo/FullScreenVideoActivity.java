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
import com.xnad.sdk.ad.outlistener.AdFullScreenVideoListener;
import com.xnad.sdk.config.AdParameter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

        positionEdit.setText(AdConfig.FULL_SCREEN_AD_POSITION);
//        loadFullScreenVideoAd("cp_ad_1");
    }

    /**
     * 获取视频广告并加载
     */
    private void loadFullScreenVideoAd(String position) {
        // cold_kp 、hot_kp
        splashContainer.removeAllViews();
        //传参，上下文和position（广告后台生成）
        AdParameter adParameter = new AdParameter.Builder(this, position)
                .build();
        MidasAdSdk.getAdsManger().loadMidasFullScreenVideoAd(adParameter, new AdFullScreenVideoListener<AdInfo>() {
            //广告播放完成回调
            @Override
            public void adVideoComplete(AdInfo info) {
                LogUtils.d(TAG, "-----adVideoComplete-----");
            }
            //广告跳过视频播放回调
            @Override
            public void adSkippedVideo(AdInfo info) {
                LogUtils.d(TAG, "-----adSkippedVideo-----");
            }
            //广告加载成功回调
            @Override
            public void adSuccess(AdInfo info) {
                LogUtils.d(TAG, "-----adSuccess-----");
            }
            //广告曝光回调
            @Override
            public void adExposed(AdInfo info) {
                LogUtils.d(TAG, "-----adExposed-----");
            }
            //广告点击回调
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
                loadFullScreenVideoAd(positionEdit.getText().toString().trim());
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
