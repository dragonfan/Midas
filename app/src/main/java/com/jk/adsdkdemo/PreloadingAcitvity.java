package com.jk.adsdkdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.comm.jksdk.GeekAdSdk;
import com.comm.jksdk.ad.entity.AdInfo;
import com.comm.jksdk.ad.listener.AdListener;
import com.comm.jksdk.ad.listener.AdManager;
import com.comm.jksdk.ad.listener.AdPreloadingListener;
import com.jk.adsdkdemo.utils.LogUtils;

/**
  *
  * @ProjectName:    ${PROJECT_NAME}
  * @Package:        ${PACKAGE_NAME}
  * @ClassName:      ${NAME}
  * @Description:  预加载
  * @Author:         fanhailong
  * @CreateDate:     ${DATE} ${TIME}
  * @UpdateUser:     更新者：
  * @UpdateDate:     ${DATE} ${TIME}
  * @UpdateRemark:   更新说明：
  * @Version:        1.0
 */
public class PreloadingAcitvity extends AppCompatActivity implements View.OnClickListener {

    private Button requestBt, preloadingAd;
    private FrameLayout container;
    private EditText positionEt;

    AdManager adManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloading);
        container = findViewById(R.id.container);
        requestBt = findViewById(R.id.button_request_ad);
        requestBt.setOnClickListener(this);
        positionEt = findViewById(R.id.et_position_id);

        preloadingAd = findViewById(R.id.button_preloading_ad);
        preloadingAd.setOnClickListener(this);

        adManager = GeekAdSdk.getAdsManger();
    }

    View adView;

    String position;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_request_ad:
                position = positionEt.getText().toString().trim();
                if (TextUtils.isEmpty(position)) {
                    Toast.makeText(getApplicationContext(), "accept->输入的位置不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                adManager.loadAd(this,position, new AdListener() {
                    @Override
                    public void adSuccess(AdInfo info) {
                        adView = adManager.getAdView();
                        if (adView != null) {
                            container.removeAllViews();
                            container.addView(adView);
                        }
                    }

                    @Override
                    public void adExposed(AdInfo info) {
                        LogUtils.e("adExposed");
                    }

                    @Override
                    public void adClicked(AdInfo info) {

                    }

                    @Override
                    public void adError(int errorCode, String errorMsg) {

                    }
                });
                break;
            case R.id.button_preloading_ad:
                position = positionEt.getText().toString().trim();
                if (TextUtils.isEmpty(position)) {
                    Toast.makeText(getApplicationContext(), "accept->输入的位置不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                adManager.preloadingAd(this, position, new AdPreloadingListener() {
                    @Override
                    public void adSuccess(AdInfo info) {
                        LogUtils.e("adSuccess");
                    }

                    @Override
                    public void adError(int errorCode, String errorMsg) {
                        LogUtils.e("adError");
                    }
                });
                break;
        }
    }
}
