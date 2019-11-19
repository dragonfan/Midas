package com.jk.adsdkdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.comm.jksdk.GeekAdSdk;
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
    private Button btnNormal, btnNormalDownload, btnFullScreen;
    private EditText positionEdit;
    private TextView statePoint;
    private AdManager adManager;

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
        positionEdit = findViewById(R.id.splash_position_edit);
        btnNormal = findViewById(R.id.btn_normal_browse);
        btnNormal.setOnClickListener(this);

//        btnNormalDownload = findViewById(R.id.btn_normal_download);
//        btnNormalDownload.setOnClickListener(this);
        positionEdit.setText("external_advertising_ad_1");
        btnFullScreen = findViewById(R.id.btn_fullscreen_download);
        btnFullScreen.setOnClickListener(this);
    }

    /**
     * 获取插屏广告并加载
     */
    private void loadCustomInsertScreenAd(String position, boolean isFullScreen) {
        LogUtils.d(TAG, "position:" + position + " isFullScreen:" + isFullScreen);
        adManager.loadCustomInsertScreenAd(this, position, isFullScreen, 3, new AdListener() {
            @Override
            public void adSuccess() {
                LogUtils.d(TAG, "-----adSuccess-----");
                statePoint.setText("state:adSuccess");
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
                statePoint.setText("error:" + errorCode + " errorMsg:" + errorMsg);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fullscreen_download:
                loadCustomInsertScreenAd(positionEdit.getText().toString().trim(), true);
                break;
            case R.id.btn_normal_browse:
//            case R.id.btn_normal_download:
                loadCustomInsertScreenAd(positionEdit.getText().toString().trim(), false);
                break;
            default:
                break;
        }
    }
}
