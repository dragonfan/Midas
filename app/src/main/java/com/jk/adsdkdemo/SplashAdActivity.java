package com.jk.adsdkdemo;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xnad.sdk.MidasAdSdk;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.listener.AdSplashListener;
import com.xnad.sdk.constant.Constants;
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
    private EditText positionEdit;
    private TextView stateTxt;
    private Button refreshBtn;

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
        positionEdit = findViewById(R.id.splash_position_edit);
        stateTxt = findViewById(R.id.state_txt);
        refreshBtn.setOnClickListener(this);
//        positionEdit.setText("68870548_0_1");
        positionEdit.setText("8938370901");
//        loadSplashAd("cold_kp");
    }

    /**
     * 获取开屏广告并加载
     */
    private void loadSplashAd(String position) {
        // cold_kp 、hot_kp
        splashContainer.removeAllViews();
        stateTxt.setText("");
        MidasAdSdk.getAdsManger().loadMidasSplashAd(this, position, new AdSplashListener<AdInfo>() {
            @Override
            public ViewGroup getViewGroup() {
                return splashContainer;
            }

            @Override
            public void adSuccess(AdInfo info) {
                LogUtils.d(TAG, "-----adSuccess-----");
                stateTxt.setText("-----adSuccess-----");
                //穿山甲广告
                if (Constants.AdSourceType.ChuanShanJia.equals(info.getMidasAd().getAdSource())) {
                    View view = info.getMidasAd().getAddView();
                    if (view != null) {
                        splashContainer.removeAllViews();
                        splashContainer.addView(view);
                    }
                }
            }

            @Override
            public void adExposed(AdInfo info) {
                LogUtils.d(TAG, "-----adExposed-----");
                stateTxt.setText("-----adExposed-----");
            }

            @Override
            public void adClicked(AdInfo info) {
                LogUtils.d(TAG, "-----adClicked-----");
            }

            @Override
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                LogUtils.e(TAG, "-----adError-----" + errorMsg);
                stateTxt.setText("errorCode:" + errorCode + " errorMsg:" + errorMsg);
            }

            @Override
            public void adClose(AdInfo info) {
                if (splashContainer != null) {
                    splashContainer.removeAllViews();
                }
            }

            //优量广告倒计时回调，时间单位ms
            @Override
            public void adTick(AdInfo info, long l) {

            }
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.splash_refresh:
                loadSplashAd(positionEdit.getText().toString().trim());
                break;
            default:
                break;
        }
    }
}
