package com.jk.adsdkdemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.comm.jksdk.MidasAdSdk;
import com.comm.jksdk.ad.entity.AdInfo;
import com.comm.jksdk.ad.listener.AdListener;
import com.jk.adsdkdemo.utils.LogUtils;

/**
  *
  * @ProjectName:    ${PROJECT_NAME}
  * @Package:        ${PACKAGE_NAME}
  * @ClassName:      ${NAME}
  * @Description:     大图_带icon文字
  * @Author:         fanhailong
  * @CreateDate:     ${DATE} ${TIME}
  * @UpdateUser:     更新者：
  * @UpdateDate:     ${DATE} ${TIME}
  * @UpdateRemark:   更新说明：
  * @Version:        1.0
 */
public class BigImgNotDownloadAcitvity extends AppCompatActivity implements View.OnClickListener {

    private Button requestBt;
    private FrameLayout container;
    private EditText positionEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_img_not_download_acitvity);
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
//                MidasAdSdk.getAdsManger().loadAd(this,position, new AdListener() {
//                    @Override
//                    public void adSuccess(AdInfo info) {
//                        adView = info.getAdView();
//                        if (adView != null) {
//                            container.removeAllViews();
//                            container.addView(adView);
//                        }
//                    }
//
//                    @Override
//                    public void adExposed(AdInfo info) {
//                        LogUtils.e("adExposed");
//                    }
//
//                    @Override
//                    public void adClicked(AdInfo info) {
//                        LogUtils.e("adClicked");
//                    }
//
//                    @Override
//                    public void adError(AdInfo info, int errorCode, String errorMsg) {
//                        LogUtils.e("adError："+errorMsg);
//                    }
//                });
//                break;
        }
    }
}
