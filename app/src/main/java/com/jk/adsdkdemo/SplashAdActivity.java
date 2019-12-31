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
import com.xnad.sdk.ad.outlistener.AdSplashListener;
import com.xnad.sdk.config.AdParameter;
import com.xnad.sdk.config.Constants;
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
        positionEdit.setText("2861108501");
//        loadSplashAd("cold_kp");
    }

    /**
     * 获取开屏广告并加载
     */
    private void loadSplashAd(String position) {
        // cold_kp 、hot_kp
        splashContainer.removeAllViews();
        stateTxt.setText("");
        AdParameter adParameter = new AdParameter.Builder(this, position)
                //设置超时时间
                .setTimeOut(3000)
                .build();
        MidasAdSdk.getAdsManger().loadMidasSplashAd(adParameter, new AdSplashListener<AdInfo>() {
            //返回容器给到sdk（优量汇填充广告的方式）
            @Override
            public ViewGroup getViewGroup() {
                return splashContainer;
            }

            //广告加载成功回调
            @Override
            public void adSuccess(AdInfo info) {
                LogUtils.d(TAG, "-----adSuccess-----");
                stateTxt.setText("-----adSuccess-----");
                //穿山甲广告（穿山甲填充广告的方式）
                if (Constants.AdSourceType.ChuanShanJia.equals(info.getMidasAd().getAdSource())) {
                    View view = info.getMidasAd().getAddView();
                    if (view != null) {
                        splashContainer.removeAllViews();
                        splashContainer.addView(view);
                    }
                }
            }
            //曝光回调
            @Override
            public void adExposed(AdInfo info) {
                LogUtils.d(TAG, "-----adExposed-----");
                stateTxt.setText("-----adExposed-----");
            }
            //点击回调
            @Override
            public void adClicked(AdInfo info) {
                LogUtils.d(TAG, "-----adClicked-----");
            }
            //广告加载错误回调
            @Override
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                LogUtils.e(TAG, "-----adError-----" + errorMsg);
                stateTxt.setText("errorCode:" + errorCode + " errorMsg:" + errorMsg);
            }
            //广告关闭回调
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
                loadSplashAd(positionEdit.getText().toString().trim());
                break;
            default:
                break;
        }
    }
}
