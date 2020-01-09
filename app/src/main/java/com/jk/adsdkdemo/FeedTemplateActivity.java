package com.jk.adsdkdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jk.adsdkdemo.config.AdConfig;
import com.jk.adsdkdemo.utils.LogUtils;
import com.xnad.sdk.MidasAdSdk;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.entity.MidasNativeTemplateAd;
import com.xnad.sdk.ad.outlistener.AdNativeTemplateListener;
import com.xnad.sdk.ad.outlistener.AdOutChargeListener;
import com.xnad.sdk.config.AdParameter;

/**
  *
  * @ProjectName:    ${PROJECT_NAME}
  * @Package:        ${PACKAGE_NAME}
  * @ClassName:      ${NAME}
  * @Description:     原生广告(模板广告)
  * @Author:         fanhailong
  * @CreateDate:     ${DATE} ${TIME}
  * @UpdateUser:     更新者：
  * @UpdateDate:     ${DATE} ${TIME}
  * @UpdateRemark:   更新说明：
  * @Version:        1.0
 */
public class FeedTemplateActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout mExpressContainer;
    private Context mContext;
    private Button mButtonLoadAd;
    private Button mButtonLoadAdVideo;
    private EditText mEtWidth;
    private EditText mEtHeight, mEtPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.activity_feed_template);
        mContext = this.getApplicationContext();
        mExpressContainer = (FrameLayout) findViewById(R.id.express_container);
        mButtonLoadAd = (Button) findViewById(R.id.btn_express_load);
        mButtonLoadAdVideo = (Button) findViewById(R.id.btn_express_load_video);
        mEtHeight = (EditText) findViewById(R.id.express_height);
        mEtWidth = (EditText) findViewById(R.id.express_width);
        mEtPosition = findViewById(R.id.et_position_id);
        mEtPosition.setText(AdConfig.FEED_TEMPLATE_AD_POSITION);
        mButtonLoadAd.setOnClickListener(this);
        mButtonLoadAdVideo.setOnClickListener(this);
//        //step2:创建TTAdNative对象，createAdNative(Context context) banner广告context需要传入Activity对象
//        mTTAdNative = TTAdManagerHolder.get().createAdNative(this);
//        //step3:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
//        TTAdManagerHolder.get().requestPermissionIfNecessary(this);
    }

    MidasNativeTemplateAd midasNativeTemplateAd;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_express_load:
                midasNativeTemplateAd = null;
                String position = mEtPosition.getText().toString().trim();
                if (TextUtils.isEmpty(position)) {
                    Toast.makeText(getApplicationContext(), "accept->输入的位置不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                String width = mEtWidth.getText().toString().trim();
                if (TextUtils.isEmpty(width)) {
                    width = "350";
                }
                float fWidth = Float.valueOf(width);
                AdParameter adParameter = new AdParameter.Builder(this, position)
                        //模板广告宽度
                        .setWidth(fWidth)
                        .setViewContainer(mExpressContainer)
                        .build();
                MidasAdSdk.getAdsManger().loadMidasNativeTemplateAd(adParameter, new AdNativeTemplateListener(){
                    @Override
                    public void adRenderSuccess(AdInfo adInfo) {

                    }

                    @Override
                    public void adExposed(AdInfo info) {

                    }

                    @Override
                    public void adClicked(AdInfo info) {

                    }

                    //请求广告成功回调
                    @Override
                    public void adSuccess(AdInfo info) {

                    }
                    //请求广告失败回调
                    @Override
                    public void adError(AdInfo info, int errorCode, String errorMsg) {
                        LogUtils.e("adError：" + errorMsg);
                    }
                });
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (midasNativeTemplateAd != null) {
            midasNativeTemplateAd.destroy();
        }
    }
}
