package com.jk.adsdkdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.comm.jksdk.MidasAdSdk;
import com.comm.jksdk.http.utils.AppInfoUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
//    private Button button_ylh_ad;
//    private RelativeLayout adRlyt;


    private Button button_configinfo, buttonPreloading, buttonOldStyle, buttonSplashAd, buttonFakeVideo,
            btnFullScreenVideo, btnRewardVideo, btnNewBigImgFour, btnInsertScreenAd, btnTemplateInsertScreenAd, bigImgBt, csjFeedTemplateBt;

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

    }

    private void initView() {
//        button_configinfo = findViewById(R.id.button_configinfo);
//        et_chan_id = findViewById(R.id.et_chan_id);
//        et_product_id = findViewById(R.id.et_product_id);


//        button_ylh_ad.setOnClickListener(this);
//        button_configinfo.setOnClickListener(this);

//        buttonPreloading = findViewById(R.id.button_preloading);
//        buttonPreloading.setOnClickListener(this);
//
//        buttonOldStyle = findViewById(R.id.button_old_style);
//        buttonOldStyle.setOnClickListener(this);
//
//
//        btnNewBigImgFour = findViewById(R.id.new_big_img);
//        btnNewBigImgFour.setOnClickListener(this);
//
//        buttonFakeVideo = findViewById(R.id.fake_video_img);
//        buttonFakeVideo.setOnClickListener(this);

        buttonSplashAd = findViewById(R.id.button_splash_ad);
        buttonSplashAd.setOnClickListener(this);

        btnFullScreenVideo = findViewById(R.id.button_full_screen_video_ad);
        btnFullScreenVideo.setOnClickListener(this);

        btnRewardVideo = findViewById(R.id.button_reward_video_ad);
        btnRewardVideo.setOnClickListener(this);

        btnInsertScreenAd = findViewById(R.id.button_insert_screen_ad);
        btnInsertScreenAd.setOnClickListener(this);

//        btnTemplateInsertScreenAd = findViewById(R.id.button_template_insert_screen_ad);
//        btnTemplateInsertScreenAd.setOnClickListener(this);

        bigImgBt = findViewById(R.id.big_img_bt);
        bigImgBt.setOnClickListener(this);

        csjFeedTemplateBt = findViewById(R.id.button_csj_feed_template_ad);
        csjFeedTemplateBt.setOnClickListener(this);
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

            MidasAdSdk.setImei(AppInfoUtils.getIMEI(this));

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
            case R.id.big_img_bt:
                startActivity(new Intent(this, BigImgAcitvity.class));
                break;
//            case R.id.button_configinfo:
//                startActivity(new Intent(this, ConfigActivity.class));
//                break;
//            case R.id.button_preloading:
//                startActivity(new Intent(this, PreloadingAcitvity.class));
//                break;
//            case R.id.button_old_style:
//                startActivity(new Intent(this, CsjFeedTemplateActivity.class));
//                break;
//            case R.id.new_big_img:
//                startActivity(new Intent(this, NewBigImgActivity.class));
//                break;
//            case R.id.fake_video_img:
//                startActivity(new Intent(this, FakeVideoBigImg1Acitvity.class));
//                break;
            case R.id.button_splash_ad:
                startActivity(new Intent(this, SplashAdActivity.class));
                break;
            case R.id.button_full_screen_video_ad:
                startActivity(new Intent(this, FullScreenVideoActivity.class));
                break;
            case R.id.button_reward_video_ad:
                startActivity(new Intent(this, RewardVideoActivity.class));
                break;
            case R.id.button_insert_screen_ad:
                startActivity(new Intent(this, InsertScreenActivity.class));
                break;
//            case R.id.button_template_insert_screen_ad:
//                startActivity(new Intent(this, TemplateInsertScreenAcitvity.class));
//                break;
            case R.id.button_csj_feed_template_ad:
//                startActivity(new Intent(this, CsjFeedTemplateActivity.class));
                startActivity(new Intent(this, FeedTemplateAcitvity.class));
                break;
            default:
                break;

        }
    }

}
