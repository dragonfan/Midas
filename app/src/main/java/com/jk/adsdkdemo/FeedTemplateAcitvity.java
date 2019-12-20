package com.jk.adsdkdemo;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.comm.jksdk.MidasAdSdk;
import com.comm.jksdk.ad.entity.AdInfo;
import com.comm.jksdk.ad.entity.MidasNativeTemplateAd;
import com.comm.jksdk.ad.listener.AdChargeListener;
import com.comm.jksdk.ad.listener.AdListener;
import com.jk.adsdkdemo.utils.LogUtils;

/**
  *
  * @ProjectName:    ${PROJECT_NAME}
  * @Package:        ${PACKAGE_NAME}
  * @ClassName:      ${NAME}
  * @Description:     大图美女
  * @Author:         fanhailong
  * @CreateDate:     ${DATE} ${TIME}
  * @UpdateUser:     更新者：
  * @UpdateDate:     ${DATE} ${TIME}
  * @UpdateRemark:   更新说明：
  * @Version:        1.0
 */
public class FeedTemplateAcitvity extends AppCompatActivity implements View.OnClickListener {

    private TTAdNative mTTAdNative;
    private FrameLayout mExpressContainer;
    private Context mContext;
    private TTAdDislike mTTAdDislike;
    private Button mButtonLoadAd;
    private Button mButtonLoadAdVideo;
    private EditText mEtWidth;
    private EditText mEtHeight;
    private TTNativeExpressAd mTTAd;

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
        mButtonLoadAd.setOnClickListener(this);
        mButtonLoadAdVideo.setOnClickListener(this);
//        //step2:创建TTAdNative对象，createAdNative(Context context) banner广告context需要传入Activity对象
//        mTTAdNative = TTAdManagerHolder.get().createAdNative(this);
//        //step3:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
//        TTAdManagerHolder.get().requestPermissionIfNecessary(this);
    }

    View adView;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_express_load:
//                String position = positionEt.getText().toString().trim();
//                String position = "FEED_01";
//                if (TextUtils.isEmpty(position)) {
//                    Toast.makeText(getApplicationContext(), "accept->输入的位置不能为空", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                String width = mEtWidth.getText().toString().trim();
//                if (TextUtils.isEmpty(width)) {
//                    width = "350";
//                }
//                MidasAdSdk.getAdsManger().loadNativeTemplateAd(this,position, Float.valueOf(width), new AdListener() {
//                    @Override
//                    public void adSuccess(AdInfo info) {
//                        adView = info.getAdView();
//                        if (adView != null) {
//                            mExpressContainer.removeAllViews();
//                            mExpressContainer.addView(adView);
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
//
//                    }
//
//                    @Override
//                    public void adError(AdInfo info, int errorCode, String errorMsg) {
//                        LogUtils.e("adError："+errorMsg);
//                    }
//                });
//                break;


                String position = "FEED_01";
                if (TextUtils.isEmpty(position)) {
                    Toast.makeText(getApplicationContext(), "accept->输入的位置不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                String width = mEtWidth.getText().toString().trim();
                if (TextUtils.isEmpty(width)) {
                    width = "350";
                }
                MidasAdSdk.getAdsManger().loadMidasNativeTemplateAd(this, position, Float.valueOf(width), new AdListener<AdInfo>() {
                    @Override
                    public void adSuccess(AdInfo info) {
                        MidasNativeTemplateAd midasNativeTemplateAd = (MidasNativeTemplateAd) info.getMidasAd();
                        renderAd(midasNativeTemplateAd);
                    }

                    @Override
                    public void adExposed(AdInfo info) {
                        LogUtils.e("adExposed");
                    }

                    @Override
                    public void adClicked(AdInfo info) {

                    }

                    @Override
                    public void adError(AdInfo info, int errorCode, String errorMsg) {
                        LogUtils.e("adError：" + errorMsg);
                    }
                });
        }
    }

    /**
     * 请求到广告后渲染广告
     */
    private void renderAd(MidasNativeTemplateAd midasNativeTemplateAd){
        midasNativeTemplateAd.setChargeListener(new AdChargeListener<MidasNativeTemplateAd>() {
            @Override
            public void adSuccess(MidasNativeTemplateAd info) {
                LogUtils.e("adSuccess");
                View addView = info.getAddView();
                if (addView != null) {
                    mExpressContainer.removeAllViews();
                    mExpressContainer.addView(addView);
                }
            }

            @Override
            public void adError(MidasNativeTemplateAd info, int errorCode, String errorMsg) {
                LogUtils.e("adError, errorCode= %i, errorMsg = %s"+errorMsg + errorMsg);
            }

            @Override
            public void adExposed(MidasNativeTemplateAd info) {
                LogUtils.e("adExposed");
            }

            @Override
            public void adClicked(MidasNativeTemplateAd info) {
                LogUtils.e("adClicked");
            }
        });
        midasNativeTemplateAd.render();
    }
}
