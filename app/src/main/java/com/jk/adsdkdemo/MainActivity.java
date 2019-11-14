package com.jk.adsdkdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.comm.jksdk.GeekAdSdk;
import com.comm.jksdk.ad.listener.AdListener;
import com.comm.jksdk.bean.ConfigBean;
import com.comm.jksdk.config.listener.ConfigListener;
import com.comm.jksdk.utils.JsonUtils;
import com.jk.adsdkdemo.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
//    private Button button_ylh_ad;
//    private RelativeLayout adRlyt;


    private Button button_configinfo, buttonBigImg, buttonSplashAd;

    //    private EditText et_ad_pos_id;
//    public static TextView tvResult;
    private EditText et_chan_id;
    private EditText et_product_id;
    private long firstOpenAppTime;
    private int bid;

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
//        String jsonData = "{\n" +
//                "\t\"code\": 0,\n" +
//                "\t\"data\": {\n" +
//                "\t\t\"adList\": [{\n" +
//                "\t\t\t\"adPosition\": \"test_ad_code\",\n" +
//                "\t\t\t\"adRequestTimeOut\": 0,\n" +
//                "\t\t\t\"adStyle\": \"All\",\n" +
//                "\t\t\t\"adVersion\": 5,\n" +
//                "\t\t\t\"adsInfos\": [{\n" +
//                "\t\t\t\t\"adId\": \"6000484459445749\",\n" +
//                "\t\t\t\t\"adUnion\": \"youlianghui\",\n" +
//                "\t\t\t\t\"adsAppId\": \"1108839337  \",\n" +
//                "\t\t\t\t\"adsAppName\": \"即刻天气\",\n" +
//                "\t\t\t\t\"requestOrder\": 1,\n" +
//                "\t\t\t\t\"requestType\": 0\n" +
//                "\t\t\t}, {\n" +
//                "\t\t\t\t\"adId\": \"915945995\",\n" +
//                "\t\t\t\t\"adUnion\": \"chuanshanjia\",\n" +
//                "\t\t\t\t\"adsAppId\": \"5015945\",\n" +
//                "\t\t\t\t\"adsAppName\": \"即刻天气\",\n" +
//                "\t\t\t\t\"requestOrder\": 2,\n" +
//                "\t\t\t\t\"requestType\": 0\n" +
//                "\t\t\t}],\n" +
//                "\t\t\t\"isChange\": 1,\n" +
//                "\t\t\t\"productId\": 1\n" +
//                "\t\t}]\n" +
//                "\t},\n" +
//                "\t\"msg\": \"请求成功\"\n" +
//                "}";
//        SPUtils.putString(Constants.DEFAULT_CONFIG_KEY, jsonData);
//
//
//        if (isFirstInstallApp()) {// 第一次安装
//            SPUtils.putBoolean(Constants.FIRST_INSTALL_APP, false);
//            //保存用户首次安装时间
//            SPUtils.putLong(Constants.FIRST_INSTALL_APP_TIME, System.currentTimeMillis());
//            int bid=getRandomNum(99);
//            SPUtils.putInt(Constants.BID, bid);
//
//        }
//
//         firstOpenAppTime = SPUtils.getLong(Constants.FIRST_INSTALL_APP_TIME, 0L);
//         bid = SPUtils.getInt(Constants.BID, 0);

    }

//    /**
//     * 是否第一次安装app
//     * @return
//     */
//    public static boolean isFirstInstallApp(){
//        Boolean isFirstInstallApp = SPUtils.getBoolean(Constants.FIRST_INSTALL_APP, true);
//        return isFirstInstallApp;
//    }

    //    /**
//     * 获取随机数
//     * @param max
//     * @return
//     */
//    public static int getRandomNum(int max){
//        // 产生[0,max-1]范围内的随机数为例
//        int num=0;
//        Random random = new Random();
//        num=random.nextInt(max);
//        return num;
//    }
    private void initView() {
//        button_ylh_ad = findViewById(R.id.button_ylh_ad);
//        et_ad_pos_id = findViewById(R.id.et_ad_pos_id);

//        adRlyt = findViewById(R.id.first_weather_adrlyt);
        button_configinfo = findViewById(R.id.button_configinfo);
//        tvResult=findViewById(R.id.tv_result);
        et_chan_id = findViewById(R.id.et_chan_id);
        et_product_id = findViewById(R.id.et_product_id);


//        button_ylh_ad.setOnClickListener(this);
        button_configinfo.setOnClickListener(this);

        buttonBigImg = findViewById(R.id.button_big_img);
        buttonBigImg.setOnClickListener(this);

        buttonSplashAd = findViewById(R.id.button_splash_ad);
        buttonSplashAd.setOnClickListener(this);
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
                //bid 第一次安装app时产生0-99之间的随机数
                // UserActive 用户激活时间
                //ProductName 业务线
//                String marketName=et_chan_id.getText().toString();
//                String productName=et_product_id.getText().toString();
//                NativesAdManger.getInstance().setContext(this)
//                        .setBid(bid)
//                        .setMarketName(marketName)
//                        .setProductName(productName)
//                        .setLatitude("")
//                        .setLongitude("")
//                        .setProvince("")
//                        .setCity("")
//                        .setUserActive(firstOpenAppTime)
//                        .requestConfig();
                GeekAdSdk.requestConfig(new ConfigListener() {
                    @Override
                    public void adSuccess(List<ConfigBean.AdListBean> configList) {
                        LogUtils.d(TAG, "config:" + JsonUtils.encode(configList));
                        Toast.makeText(getApplicationContext(), "accept->配置信息请求成功", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void adError(int errorCode, String errorMsg) {
                        Toast.makeText(getApplicationContext(), "accept->配置信息请求失败， msg:" + errorMsg, Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case R.id.button_big_img:
                startActivity(new Intent(this, BigImgAcitvity.class));
                break;
//            case R.id.button_ylh_ad:
////                adRlyt.removeAllViews();
//////                requestYLHAd("shipin_stream_no_1");
////                String adPosId=et_ad_pos_id.getText().toString().trim();
////                requestYLHAd(adPosId);
//                break;

            case R.id.button_splash_ad:
                startActivity(new Intent(this, SplashAdActivity.class));
                break;
            default:
                break;

        }
    }


    private void requestYLHAd(String adPosId) {
//        View adView = NativesAdManger.getInstance().setContext(this)
//                .setAdPositionId(adPosId)
//                .setAdListener(mAdListener)
//                .setDefaultConfigKey(Constants.DEFAULT_CONFIG_KEY)
//                .build()
//                .getAdView();
//        if (adRlyt != null) {
//            adRlyt.addView(adView);
//        }
    }


    private AdListener mAdListener = new AdListener() {
        @Override
        public void adSuccess() {
            LogUtils.w("lpb", "adSuccess");
        }

        @Override
        public void adExposed() {
            LogUtils.w("lpb", "adExposed");

        }

        @Override
        public void adClicked() {
            LogUtils.w("lpb", "adClicked");

        }

        @Override
        public void adError(int errorCode, String errorMsg) {
            LogUtils.w("lpb", "adError errorCode = " + errorCode + " errorMsg = " + errorMsg);

        }

    };
}
