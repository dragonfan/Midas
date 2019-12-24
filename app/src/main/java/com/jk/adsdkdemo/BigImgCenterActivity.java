package com.jk.adsdkdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

/**
  *
  * @ProjectName:    ${PROJECT_NAME}
  * @Package:        ${PACKAGE_NAME}
  * @ClassName:      ${NAME}
  * @Description:     大图_带icon文字按钮居中
  * @Author:         fanhailong
  * @CreateDate:     ${DATE} ${TIME}
  * @UpdateUser:     更新者：
  * @UpdateDate:     ${DATE} ${TIME}
  * @UpdateRemark:   更新说明：
  * @Version:        1.0
 */
public class BigImgCenterActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = RewardVideoActivity.class.getSimpleName();
    private Button requestBt;
    private FrameLayout container;
    private EditText positionEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_img_center);
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
//                String position = positionEt.getText().toString().trim();
//                if (TextUtils.isEmpty(position)) {
//                    Toast.makeText(getApplicationContext(), "accept->输入的位置不能为空", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                List<String> list = new ArrayList<>();
//                MidasAdSdk.getAdsManger().loadAd(this,position, new AdListener<AdInfo>() {
//                    @Override
//                    public void adSuccess(AdInfo info) {
//                        if (info == null) {
//                            LogUtils.e("DEMO>>>adSuccess， AdInfo is empty");
//                        } else {
//                            LogUtils.e("DEMO>>>adSuccess， "+ info.toString());
//                        }
//                        adView = info.getAdView();
//                        if (adView != null) {
//                            container.removeAllViews();
//                            container.addView(adView);
//                        }
//                    }
//
//                    @Override
//                    public void adExposed(AdInfo info) {
//                        if (info == null) {
//                            LogUtils.e("DEMO>>>adExposed， AdInfo is empty");
//                        } else {
//                            LogUtils.e("DEMO>>>adExposed， "+ info.toString());
//                        }
//                        LogUtils.e("adExposed");
//                    }
//
//                    @Override
//                    public void adClicked(AdInfo info) {
//                        if (info == null) {
//                            LogUtils.e("DEMO>>>adClicked， AdInfo is empty");
//                        } else {
//                            LogUtils.e("DEMO>>>adClicked， "+ info.toString());
//                        }
//                    }
//
//
//                    @Override
//                    public void adError(AdInfo info, int errorCode, String errorMsg) {
//                        LogUtils.e("DEMO>>>adError： "+errorMsg);
//                    }
//                });
                break;
        }
    }
}
