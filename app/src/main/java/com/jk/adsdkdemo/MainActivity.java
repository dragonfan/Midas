package com.jk.adsdkdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.xnad.sdk.MidasAdSdk;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
        findViewById(R.id.button_splash_ad).setOnClickListener(this);
        findViewById(R.id.button_full_screen_video_ad).setOnClickListener(this);
        findViewById(R.id.button_reward_video_ad).setOnClickListener(this);
        findViewById(R.id.button_insert_screen_ad).setOnClickListener(this);
        findViewById(R.id.big_img_bt).setOnClickListener(this);
        findViewById(R.id.button_csj_feed_template_ad).setOnClickListener(this);
        findViewById(R.id.banner_template_ad).setOnClickListener(this);
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

            MidasAdSdk.setImei();
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
                startActivity(new Intent(this, SelfRenderAcitvity.class));
                break;
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
            case R.id.button_csj_feed_template_ad:
                startActivity(new Intent(this, FeedTemplateAcitvity.class));
                break;
            case R.id.banner_template_ad:
                startActivity(new Intent(this, BannerAdActivity.class));
                break;
            default:
                break;

        }
    }

}
