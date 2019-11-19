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
  * @Description:  模板插屏广告
  * @Author:         fanhailong
  * @CreateDate:     ${DATE} ${TIME}
  * @UpdateUser:     更新者：
  * @UpdateDate:     ${DATE} ${TIME}
  * @UpdateRemark:   更新说明：
  * @Version:        1.0
 */
public class TemplateInsertScreenAcitvity extends AppCompatActivity implements View.OnClickListener {

    private Button requestBt;
    private FrameLayout container;
    private EditText positionEt;

    private final String TAG = InsertScreenActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_img_template_insert_screen);
        container = findViewById(R.id.container);
        requestBt = findViewById(R.id.button_request_ad);
        requestBt.setOnClickListener(this);
        positionEt = findViewById(R.id.et_position_id);
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
                adManager.loadCustomInsertScreenAd(this, position, false, 3, new AdListener() {
                    @Override
                    public void adSuccess() {
                        LogUtils.d(TAG, "-----adSuccess-----");
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
                    }
                });
                break;
        }
    }
}