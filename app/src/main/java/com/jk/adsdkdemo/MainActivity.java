package com.jk.adsdkdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Observable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.comm.jksdk.ad.AdsManger;
import com.comm.jksdk.ad.listener.AdListener;

import com.jk.adsdkdemo.utils.Constants;
import com.jk.adsdkdemo.utils.LogUtils;
import com.jk.adsdkdemo.utils.SPUtils;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button button_ylh_ad;
    private RelativeLayout adRlyt;

    private Button button_test_http;
    private Button button_chj_ad;
    private Button button_configinfo;
    private Button button_cms_ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        if (Build.VERSION.SDK_INT >= 23) {
            checkAndRequestPermission();
        } else {
            // 如果是Android6.0以下的机器，建议在manifest中配置相关权限，这里可以直接调用SDK
        }
        //保存默认配置文件  建议保存在asset目录下的json文件
        String jsonData=" {\n" +
                "\t\"code\": 0,\n" +
                "\t\"data\": {\n" +
                "\t\t\"adList\": [{\n" +
                "\t\t\t\"adPosition\": \"shipin_stream_no_1\",\n" +
                "\t\t\t\"adRequestTimeOut\": 0,\n" +
                "\t\t\t\"adStyle\": \"All\",\n" +
                "\t\t\t\"adVersion\": 2,\n" +
                "\t\t\t\"adsInfos\": [{\n" +
                "\t\t\t\t\"adId\": \"3010171130615111\",\n" +
                "\t\t\t\t\"adUnion\": \"youlianghui\",\n" +
                "\t\t\t\t\"adsAppId\": \"1108839337\",\n" +
                "\t\t\t\t\"adsAppName\": \"即刻天气\",\n" +
                "\t\t\t\t\"requestOrder\": 1,\n" +
                "\t\t\t\t\"requestType\": 0\n" +
                "\t\t\t}],\n" +
                "\t\t\t\"isChange\": 0,\n" +
                "\t\t\t\"productId\": 1\n" +
                "\t\t}, {\n" +
                "\t\t\t\"adPosition\": \"24_tianqi_banner\",\n" +
                "\t\t\t\"adRequestTimeOut\": 0,\n" +
                "\t\t\t\"adStyle\": \"All\",\n" +
                "\t\t\t\"adVersion\": 2,\n" +
                "\t\t\t\"adsInfos\": [{\n" +
                "\t\t\t\t\"adId\": \"7020185381052226\",\n" +
                "\t\t\t\t\"adUnion\": \"youlianghui\",\n" +
                "\t\t\t\t\"adsAppId\": \"1108839337\",\n" +
                "\t\t\t\t\"adsAppName\": \"即刻天气\",\n" +
                "\t\t\t\t\"requestOrder\": 1,\n" +
                "\t\t\t\t\"requestType\": 0\n" +
                "\t\t\t}],\n" +
                "\t\t\t\"isChange\": 0,\n" +
                "\t\t\t\"productId\": 1\n" +
                "\t\t}]\n" +
                "\t},\n" +
                "\t\"msg\": \"请求成功\"\n" +
                "}";
        SPUtils.putString(Constants.DEFAULT_CONFIG_KEY,jsonData);
    }

    private void initView() {
        button_ylh_ad = findViewById(R.id.button_ylh_ad);
        button_test_http = findViewById(R.id.button_test_http);
        button_chj_ad=findViewById(R.id.button_chj_ad);
        adRlyt = findViewById(R.id.first_weather_adrlyt);
        button_configinfo=findViewById(R.id.button_configinfo);
        button_cms_ad=findViewById(R.id.button_cms_ad);
        button_ylh_ad.setOnClickListener(this);
        button_test_http.setOnClickListener(this);
        button_chj_ad.setOnClickListener(this);
        button_configinfo.setOnClickListener(this);
        button_cms_ad.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        if (!(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (!(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        // 如果需要的权限都已经有了，那么直接调用SDK
        if (lackedPermission.size() == 0) {
        } else {
            // 否则，建议请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            requestPermissions(requestPermissions, 1024);
        }
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1024 && hasAllPermissionsGranted(grantResults)) {

        } else {
            Toast.makeText(this, "应用缺少必要的权限！请点击\"权限\"，打开所需要的权限。", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            finish();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_configinfo:
                //请求配置信息
              AdsManger.getInstance().setContext(this)
                        .setBid(10)
                        .setProductName("13")
                        .setMarketName("shipin_stream_no_1")
                        .setLatitude("")
                        .setLongitude("")
                        .setProvince("")
                        .setCity("")
                        .setUserActive(System.currentTimeMillis())
                        .requestConfig();

                break;

            case R.id.button_ylh_ad:
                adRlyt.removeAllViews();
                requestYLHAd();
                break;

            default:
                break;

        }
    }

    private void requestCHJAd() {
        adRlyt.removeAllViews();
        AdsManger.getInstance().setContext(this)
                .setAdPositionId("915945995")
                .setAdListener(mAdListener)
                .build();

    }

    private void requestYLHAd() {
        View adView = AdsManger.getInstance().setContext(this)
                .setAdPositionId("6000484459445749")
                .setMarketName("shipin_stream_no_1")
                .setAdListener(mAdListener)
                .setDefaultConfigKey(Constants.DEFAULT_CONFIG_KEY)
                .build()
                .getAdView();
        if (adRlyt != null) {
            adRlyt.addView(adView);
        }
    }


    private AdListener mAdListener = new AdListener() {
        @Override
        public void adSuccess() {
             LogUtils.w("dkk", "adSuccess");

        }

        @Override
        public void adExposed() {
            LogUtils.w("dkk", "adExposed");
        }

        @Override
        public void adClicked() {
            LogUtils.w("dkk", "adClicked");
        }

        @Override
        public void adError(int errorCode, String errorMsg) {
            LogUtils.w("dkk", "adError errorCode = " + errorCode + " errorMsg = " + errorMsg);
        }

    };
}
