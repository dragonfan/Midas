package com.jk.adsdkdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * 自选染插屏广告<p>
 *
 * @author zixuefei
 * @since 2019/11/17 17:25
 */
public class InsertScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnNormal, btnNormalDownload, btnFullScreen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_screen_ad);
        initView();
    }

    private void initView() {
        setTitle("自选染插屏广告");
        btnNormal = findViewById(R.id.btn_normal_browse);
        btnNormal.setOnClickListener(this);

        btnNormalDownload = findViewById(R.id.btn_normal_download);
        btnNormalDownload.setOnClickListener(this);

        btnFullScreen = findViewById(R.id.btn_fullscreen_download);
        btnFullScreen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fullscreen_download:

                break;
            case R.id.btn_normal_browse:

                break;
            case R.id.btn_normal_download:
                break;
            default:
                break;
        }
    }
}
