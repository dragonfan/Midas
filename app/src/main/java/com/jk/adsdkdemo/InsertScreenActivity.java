package com.jk.adsdkdemo;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.comm.jksdk.GeekAdSdk;
import com.comm.jksdk.ad.entity.AdInfo;
import com.comm.jksdk.ad.listener.AdListener;
import com.comm.jksdk.ad.listener.AdManager;
import com.jk.adsdkdemo.utils.LogUtils;

/**
 * 自渲染插屏广告<p>
 *
 * @author zixuefei
 * @since 2019/11/17 17:25
 */
public class InsertScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = InsertScreenActivity.class.getSimpleName();
    private Button btnNormal, btnFullScreen;
    private EditText positionEdit, positionEdit2;
    private TextView statePoint, statePoint2;
    private AdManager adManager;
    private FrameLayout container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_screen_ad);
        initView();
    }

    private void initView() {
        setTitle("自渲染插屏广告");
        adManager = GeekAdSdk.getAdsManger();
        statePoint = findViewById(R.id.txt_point);
        statePoint2 = findViewById(R.id.txt_point2);
        positionEdit = findViewById(R.id.splash_position_edit);
        positionEdit2 = findViewById(R.id.splash_position_edit2);
        btnNormal = findViewById(R.id.btn_normal_browse);
        btnNormal.setOnClickListener(this);
        container = findViewById(R.id.container);

//        btnNormalDownload = findViewById(R.id.btn_normal_download);
//        btnNormalDownload.setOnClickListener(this);
        positionEdit.setText("external_advertising_ad_1");
        positionEdit2.setText("cp_ad_2");
        btnFullScreen = findViewById(R.id.btn_fullscreen_download);
        btnFullScreen.setOnClickListener(this);
    }

    View adView;
    /**
     * 获取插屏广告并加载
     */
    private void loadCustomInsertScreenAd(String position) {
        LogUtils.d(TAG, "position:" + position );
        adManager.loadCustomInsertScreenAd(this, position, 3, new AdListener() {
            @Override
            public void adSuccess(AdInfo info) {
                LogUtils.d(TAG, "-----adSuccess-----");

                statePoint.setText("state:adSuccess");
                String adStyle = info.getAdStyle();
                if ("EXTERNAL_CP_01".equals(adStyle)) { //外部插屏广告用addView的形式
                    adView = adManager.getAdView();
                    if (adView != null) {
                        container.removeAllViews();
                        container.addView(adView);
                    }
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
            public void adError(int errorCode, String errorMsg) {
                LogUtils.d(TAG, "-----adError-----" + errorMsg);
                statePoint.setText("error:" + errorCode + " errorMsg:" + errorMsg);
            }
        }, "80");
    }

    /**
     * 获取全屏插屏广告并加载
     */
    private void loadCustomInsertScreenAd2(String position) {
        LogUtils.d(TAG, "position:" + position + " isFullScreen:");
        adManager.loadCustomInsertScreenAd(this, position, 3, new AdListener() {
            @Override
            public void adSuccess(AdInfo info) {
                LogUtils.d(TAG, "-----adSuccess-----");
                statePoint.setText("state:adSuccess");
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
            public void adError(int errorCode, String errorMsg) {
                LogUtils.d(TAG, "-----adError-----" + errorMsg);
                statePoint.setText("error:" + errorCode + " errorMsg:" + errorMsg);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fullscreen_download:
                loadCustomInsertScreenAd2(positionEdit2.getText().toString().trim());
                break;
            case R.id.btn_normal_browse:
//            case R.id.btn_normal_download:
                loadCustomInsertScreenAd(positionEdit.getText().toString().trim());
                break;
            default:
                break;
        }
    }
}
