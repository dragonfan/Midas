package com.jk.adsdkdemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.comm.jksdk.MidasAdSdk;
import com.comm.jksdk.bean.ConfigBean;
import com.comm.jksdk.config.AdsConfig;
import com.comm.jksdk.config.listener.ConfigListener;
import com.comm.jksdk.utils.CollectionUtils;
import com.comm.jksdk.utils.JsonUtils;
import com.jk.adsdkdemo.utils.LogUtils;

import java.util.List;

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
    private EditText bidEt, editProduct, editChan;
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
                if (!MidasAdSdk.isInit()) {
                    Toast.makeText(getApplicationContext(), "请先初始化", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    MidasAdSdk.setBid(Integer.valueOf(bidEt.getText().toString()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_request_config:
                if (!MidasAdSdk.isInit()) {
                    Toast.makeText(getApplicationContext(), "请先初始化", Toast.LENGTH_LONG).show();
                    return;
                }
                stateText.setText("");
//                MidasAdSdk.requestConfig(new ConfigListener() {
//                    @Override
//                    public void adSuccess(List<ConfigBean.AdListBean> configList) {
//                        String config = JsonUtils.encode(configList);
//                        LogUtils.d("ConfigActivity", "config:" + config.substring(0, config.length() / 2));
//                        LogUtils.d("ConfigActivity", "config-------:" + config.substring(config.length() / 2));
//                        Toast.makeText(getApplicationContext(), "accept->配置信息请求成功", Toast.LENGTH_LONG).show();
//                        showConfigList(configList);
//                    }
//
//                    @Override
//                    public void adError(int errorCode, String errorMsg) {
//                        Toast.makeText(getApplicationContext(), "accept->配置信息请求失败， msg:" + errorMsg, Toast.LENGTH_LONG).show();
//                        stateText.setText("配置信息请求失败:" + errorCode + " errorMsg:" + errorMsg);
//                    }
//                });
                String visionName = TTAdSdk.getAdManager().getSDKVersion();
                LogUtils.e("visionName>>>"+visionName);
                break;
            case R.id.button_init:
                String product = editProduct.getText().toString().trim();
                String chan = editChan.getText().toString().trim();
                LogUtils.e(">>>product="+product);
                LogUtils.e(">>>渠道号="+chan);
//                MidasAdSdk.init(this, product, "5036430", chan,   false);
                MidasAdSdk.init(this, product, "5036430", chan,   false);
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
        if (MidasAdSdk.isInit()) {
            showConfigList(AdsConfig.getAdsInfoslist());
        }
    }

    /**
     * 展示配置信息
     */
    private void showConfigList(List<ConfigBean.AdListBean> configList) {
        if (!CollectionUtils.isEmpty(configList)) {
            StringBuffer config = new StringBuffer();
            config.append("配置信息:" + configList.size() + "条\n");
            config.append("*********************************************\n");
            for (ConfigBean.AdListBean adListBean : configList) {
                config.append(JsonUtils.encode(adListBean) + "\n");
                config.append("*********************************************\n");
            }
            stateText.setText(config.toString().trim());
        }
    }
}
