package com.jk.adsdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jk.adsdkdemo.config.AdConfig;
import com.jk.adsdkdemo.utils.LogUtils;
import com.xnad.sdk.MidasAdSdk;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.entity.MidasSelfRenderAd;
import com.xnad.sdk.ad.outlistener.AdSelfRenderListener;
import com.xnad.sdk.config.AdParameter;

/**
 * @ProjectName: ${PROJECT_NAME}
 * @Package: ${PACKAGE_NAME}
 * @ClassName: ${NAME}
 * @Description: 自渲染广告
 * @Author: fanhailong
 * @CreateDate: ${DATE} ${TIME}
 * @UpdateUser: 更新者：
 * @UpdateDate: ${DATE} ${TIME}
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class SelfRenderActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = SelfRenderActivity.class.getSimpleName();

    private EditText positionEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_render);
        positionEt = findViewById(R.id.et_position_id);
        positionEt.setText(AdConfig.SELF_RENDER_AD_POSITION);
        findViewById(R.id.button_request_ad).setOnClickListener(this);
    }

    String position = null;

    /**
     * 在生命周期调用相应的方法
     */
    MidasSelfRenderAd midasSelfRenderAd;

    @Override
    protected void onResume() {
        super.onResume();
        if (midasSelfRenderAd != null) {
            midasSelfRenderAd.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (midasSelfRenderAd != null) {
            midasSelfRenderAd.destroy();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_preloading_ad:  //预加载广告
            case R.id.button_request_ad: //预加载广告
//                position = spinner.getSelectedItem().toString();
                position = positionEt.getText().toString().trim();
                if (TextUtils.isEmpty(position)) {
                    Toast.makeText(getApplicationContext(), "accept->输入的位置不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                ViewGroup viewContainer = findViewById(R.id.view_container);
                AdParameter adParameter = new AdParameter.Builder(this, position)
                        .setLayoutId(R.layout.self_render_view)
                        .setViewContainer(viewContainer)
                        .build();
                MidasAdSdk.getAdsManger().loadMidasSelfRenderAd(adParameter, new AdSelfRenderListener() {
                    @Override
                    public void adExposed(AdInfo info) {

                    }

                    @Override
                    public void adClicked(AdInfo info) {

                    }

                    @Override
                    public void callbackView(View view) {
                        ImageView animView = view.findViewById(R.id.ivAdAnim);
                        Glide.with(view.getContext()).load(R.drawable.iv_ad_anim)
                                .into(animView);
                    }

                    //请求广告成功回调
                    @Override
                    public void adSuccess(AdInfo info) {
//                        midasSelfRenderAd = (MidasSelfRenderAd) info.getMidasAd();
                    }
                    //请求广告失败回到
                    @Override
                    public void adError(AdInfo info, int errorCode, String errorMsg) {
                        LogUtils.e(TAG, "DEMO>>>adError： " + errorMsg);
                    }
                });
                break;
        }
    }

    public void jumpActivity(View view) {
        startActivity(new Intent(this, InsertScreenActivity.class));
    }
}
