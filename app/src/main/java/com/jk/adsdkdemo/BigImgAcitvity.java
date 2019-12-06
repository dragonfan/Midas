package com.jk.adsdkdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.comm.jksdk.GeekAdSdk;
import com.comm.jksdk.ad.entity.AdInfo;
import com.comm.jksdk.ad.listener.AdListener;
import com.comm.jksdk.ad.listener.AdManager;
import com.jk.adsdkdemo.utils.LogUtils;

/**
  *
  * @ProjectName:    ${PROJECT_NAME}
  * @Package:        ${PACKAGE_NAME}
  * @ClassName:      ${NAME}
  * @Description:  大图类广告
  * @Author:         fanhailong
  * @CreateDate:     ${DATE} ${TIME}
  * @UpdateUser:     更新者：
  * @UpdateDate:     ${DATE} ${TIME}
  * @UpdateRemark:   更新说明：
  * @Version:        1.0
 */
public class BigImgAcitvity extends AppCompatActivity implements View.OnClickListener {

    private Button requestBt;
    private FrameLayout container;
    private EditText positionEt;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_img_fake_video);
        container = findViewById(R.id.container);
        requestBt = findViewById(R.id.button_request_ad);
        requestBt.setOnClickListener(this);
        positionEt = findViewById(R.id.et_position_id);
        spinner = findViewById(R.id.spinner);

        String[] ctype = new String[]{"success_page_ad_1", "success_page_ad_2", "success_page_ad_3", "newlist_1_1", "homepage_ad_1","homepage_ad_2", "lock_screen_advertising", "success_page_ad_1"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    View adView;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_request_ad:
                String position = spinner.getSelectedItem().toString();
                if (TextUtils.isEmpty(position)) {
                    Toast.makeText(getApplicationContext(), "accept->输入的位置不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                GeekAdSdk.getAdsManger().loadAd(this,position, new AdListener() {
                    @Override
                    public void adSuccess(AdInfo info) {
                        if (info == null) {
                            LogUtils.e("DEMO>>>adSuccess， AdInfo is empty");
                        } else {
                            LogUtils.e("DEMO>>>adSuccess， "+ info.toString());
                        }
                        adView = info.getAdView();
                        if (adView != null) {
                            container.removeAllViews();
                            container.addView(adView);
                        }
                    }

                    @Override
                    public void adExposed(AdInfo info) {
                        if (info == null) {
                            LogUtils.e("DEMO>>>adExposed， AdInfo is empty");
                        } else {
                            LogUtils.e("DEMO>>>adExposed， "+ info.toString());
                        }
                        LogUtils.e("adExposed");
                    }

                    @Override
                    public void adClicked(AdInfo info) {
                        if (info == null) {
                            LogUtils.e("DEMO>>>adClicked， AdInfo is empty");
                        } else {
                            LogUtils.e("DEMO>>>adClicked， "+ info.toString());
                        }
                    }


                    @Override
                    public void adError(AdInfo info, int errorCode, String errorMsg) {
                        LogUtils.e("DEMO>>>adError： "+errorMsg);
                    }
                });
                break;
        }
    }
}
