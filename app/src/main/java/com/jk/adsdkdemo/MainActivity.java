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
        //保存默认配置文件
        String jsonData="{\"code\":0,\"data\":{\"adList\":[{\"adPosition\":\"home_page_list1\",\"adRequestTimeOut\":10,\"adStyle\":\"BigImg\",\"adVersion\":1,\"adsInfos\":[{\"adId\":\"6548444565561\",\"adUnion\":\"youlianghui\",\"adsAppId\":\"jktq1103\",\"adsAppName\":\"即刻天气\",\"requestOrder\":1,\"requestType\":0},{\"adId\":\"4654564654644\",\"adUnion\":\"chuanshanjia\",\"adsAppId\":\"tq1103\",\"adsAppName\":\"天气\",\"requestOrder\":5,\"requestType\":0}],\"isChange\":0,\"productId\":1},{\"adPosition\":\"home_page_list2\",\"adRequestTimeOut\":3,\"adStyle\":\"LeftImgRightTwoText\",\"adVersion\":3,\"adsInfos\":[{\"adId\":\"6548444565561\",\"adUnion\":\"youlianghui\",\"adsAppId\":\"jrl1103\",\"adsAppName\":\"吉日历\",\"requestOrder\":10,\"requestType\":0},{\"adId\":\"4654564654644\",\"adUnion\":\"chuanshanjia\",\"adsAppId\":\"jrl1103\",\"adsAppName\":\"吉日历\",\"requestOrder\":5,\"requestType\":0}],\"isChange\":0,\"productId\":3}]},\"msg\":\"请求成功\"}";
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
            case R.id.button_test_http:
                //测试网络框架
//                requestNetwok();

                break;
            case R.id.button_configinfo:
                //请求配置信息
              AdsManger.getInstance().setContext(this)
                        .setBid(10)
                        .setProductName("13")
                        .setMarketName("jrl_jinritoutiao_35")
                        .setLatitude("")
                        .setLongitude("")
                        .setProvince("")
                        .setCity("")
                        .setUserActive(System.currentTimeMillis())
                        .requestConfig();

                break;
            case R.id.button_cms_ad:

                break;
            case R.id.button_ylh_ad:
                adRlyt.removeAllViews();
                requestYLHAd();
                break;
            case R.id.button_chj_ad:
                requestCHJAd();
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
                .setAdPositionId("60004844594457490")
                .setAdListener(mAdListener)
                .setDefaultConfigKey(Constants.DEFAULT_CONFIG_KEY)
                .build()
                .getAdView();
        if (adRlyt != null) {
            adRlyt.addView(adView);
        }
    }

//    private void requestNetwok() {
//        getTime().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<TimeResponse>() {
//                    @Override
//                    public void accept(TimeResponse timeResponse) throws Exception {
//                        LogUtils.d(TAG, "accept->请求成功 ");
//                        Toast.makeText(MainActivity.this, "accept->请求成功 ", Toast.LENGTH_SHORT).show();
//
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        LogUtils.d(TAG, "accept->请求失败");
//                        Toast.makeText(MainActivity.this, "accept->请求失败 ", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//    }
//
//    public Observable<TimeResponse> getTime() {
//        return OkHttpWrapper.getInstance().getRetrofit().create(NewsService.class).getTimes()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }

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
