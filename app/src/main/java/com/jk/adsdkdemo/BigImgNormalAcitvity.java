package com.jk.adsdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.comm.jksdk.GeekAdSdk;
import com.comm.jksdk.ad.listener.AdListener;
import com.comm.jksdk.ad.listener.AdManager;
import com.jk.adsdkdemo.utils.LogUtils;

/**
  *
  * @ProjectName:    ${PROJECT_NAME}
  * @Package:        ${PACKAGE_NAME}
  * @ClassName:      ${NAME}
  * @Description:     大图_带icon文字按钮
  * @Author:         fanhailong
  * @CreateDate:     ${DATE} ${TIME}
  * @UpdateUser:     更新者：
  * @UpdateDate:     ${DATE} ${TIME}
  * @UpdateRemark:   更新说明：
  * @Version:        1.0
 */
public class BigImgNormalAcitvity extends AppCompatActivity implements View.OnClickListener {

    private Button requestBt, loadBt;
    private FrameLayout container;
    private EditText positionEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_img_acitvity);
        container = findViewById(R.id.container);
        requestBt = findViewById(R.id.button_request_ad);
        loadBt = findViewById(R.id.button_load_ad);
        positionEt = findViewById(R.id.et_position_id);
        requestBt.setOnClickListener(this);
        loadBt.setOnClickListener(this);
    }

    View adView;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_request_ad:
                String position = positionEt.getText().toString().trim();
                if (TextUtils.isEmpty(position)) {
                    Toast.makeText(getApplicationContext(), "accept->输入的位置不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                AdManager adManager = GeekAdSdk.getAdsManger();
                adManager.loadAd(this,position, new AdListener() {
                    @Override
                    public void adSuccess() {
                        adView = adManager.getAdView();
                        if (adView != null) {
                            container.removeAllViews();
                            container.addView(adView);
                        }
                    }

                    @Override
                    public void adExposed() {
                        LogUtils.e("adExposed");
                    }

                    @Override
                    public void adClicked() {

                    }

                    @Override
                    public void adError(int errorCode, String errorMsg) {

                    }
                });
                break;
        }
    }
}
