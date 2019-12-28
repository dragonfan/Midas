package com.jk.adsdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.jk.adsdkdemo.utils.LogUtils;
import com.xnad.sdk.MidasAdSdk;
import com.xnad.sdk.config.ADConfigBuild;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @ProjectName: ${PROJECT_NAME}
 * @Package: ${PACKAGE_NAME}
 * @ClassName: ${NAME}
 * @Description: 配置信息界面
 * @Author: fanhailong
 * @CreateDate: ${DATE} ${TIME}
 * @UpdateUser: 更新者：
 * @UpdateDate: ${DATE} ${TIME}
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ConfigActivity extends AppCompatActivity implements View.OnClickListener {

    private Button requestBt, setBidTb, initTb, nextBt;
    private EditText bidEt, editProduct, editChan, editUuid, editAdAppId;
    private TextView stateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        setBidTb = findViewById(R.id.button_set_bid);
        requestBt = findViewById(R.id.button_request_config);
        stateText = findViewById(R.id.config_state);
        bidEt = findViewById(R.id.et_bid_id);
        editChan = findViewById(R.id.et_chan_id);
        editProduct = findViewById(R.id.et_product_id);
        initTb = findViewById(R.id.button_init);
        nextBt = findViewById(R.id.next);

        editUuid = findViewById(R.id.et_uuid_id);
        editAdAppId = findViewById(R.id.et_ad_app_id);

        nextBt.setOnClickListener(this);
        initTb.setOnClickListener(this);
        setBidTb.setOnClickListener(this);
        requestBt.setOnClickListener(this);
        stateText.setTextIsSelectable(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_set_bid:
                break;
            case R.id.button_request_config:
                if (!MidasAdSdk.isInit()) {
                    Toast.makeText(getApplicationContext(), "请先初始化", Toast.LENGTH_LONG).show();
                    return;
                }
                stateText.setText("");
                String visionName = TTAdSdk.getAdManager().getSDKVersion();
                LogUtils.e("visionName>>>" + visionName);
                break;
            case R.id.button_init:
                String product = editProduct.getText().toString().trim();
                String channel = editChan.getText().toString().trim();
                String appId = editAdAppId.getText().toString().trim();
                LogUtils.e(">>>product=" + product);
                LogUtils.e(">>>渠道号=" + channel);
                //牛数上报地址
                String serverUrl = "http://testaidataprobe2.51huihuahua.com/v/v/dataprobe2/ggbx";

                //appid, product, chan,"5036430", serverUrl, false
                ADConfigBuild configBuild = new ADConfigBuild()
                        .setAppId(appId)
                        .setProductId(product)
                        .setCsjAppId("5036430")
                        .setChannel(channel)
                        .setServerUrl(serverUrl)
                        .setIsFormal(false);
                MidasAdSdk.init(this, configBuild);
                break;
            case R.id.next:
                if (!MidasAdSdk.isInit()) {
                    Toast.makeText(getApplicationContext(), "请先初始化", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
