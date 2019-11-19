package com.jk.adsdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.comm.jksdk.GeekAdSdk;
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

    private Button requestBt, setBidTb;
    private EditText bidEt;
    private TextView stateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        setBidTb = findViewById(R.id.button_set_bid);
        requestBt = findViewById(R.id.button_request_config);
        stateText = findViewById(R.id.config_state);
        bidEt = findViewById(R.id.et_bid_id);
        setBidTb.setOnClickListener(this);
        requestBt.setOnClickListener(this);
        stateText.setTextIsSelectable(true);
        showConfigList(AdsConfig.getAdsInfoslist());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_set_bid:
                try {
                    GeekAdSdk.setBid(Integer.valueOf(bidEt.getText().toString()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_request_config:
                stateText.setText("");
                GeekAdSdk.requestConfig(new ConfigListener() {
                    @Override
                    public void adSuccess(List<ConfigBean.AdListBean> configList) {
                        String config = JsonUtils.encode(configList);
                        LogUtils.d("ConfigActivity", "config:" + config.substring(0, config.length() / 2));
                        LogUtils.d("ConfigActivity", "config-------:" + config.substring(config.length() / 2));
                        Toast.makeText(getApplicationContext(), "accept->配置信息请求成功", Toast.LENGTH_LONG).show();
                        showConfigList(configList);
                    }

                    @Override
                    public void adError(int errorCode, String errorMsg) {
                        Toast.makeText(getApplicationContext(), "accept->配置信息请求失败， msg:" + errorMsg, Toast.LENGTH_LONG).show();
                        stateText.setText("配置信息请求失败:" + errorCode + " errorMsg:" + errorMsg);
                    }
                });
                break;
        }
    }


    /**
     * 展示配置信息
     */
    private void showConfigList(List<ConfigBean.AdListBean> configList) {
        if (!CollectionUtils.isEmpty(configList)) {
            StringBuffer config = new StringBuffer();

            for (ConfigBean.AdListBean adListBean : configList) {
                config.append(JsonUtils.encode(adListBean) + "\n");
            }
            stateText.setText("配置信息:\n" + config.toString().trim());
        }
    }
}
