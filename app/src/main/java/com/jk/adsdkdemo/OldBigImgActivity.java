package com.jk.adsdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
  *
  * @ProjectName:    ${PROJECT_NAME}
  * @Package:        ${PACKAGE_NAME}
  * @ClassName:      ${NAME}
  * @Description:     老的大图广告三样式
  * @Author:         fanhailong
  * @CreateDate:     ${DATE} ${TIME}
  * @UpdateUser:     更新者：
  * @UpdateDate:     ${DATE} ${TIME}
  * @UpdateRemark:   更新说明：
  * @Version:        1.0
 */
public class OldBigImgActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonBigImg, buttonBigBt, buttonBigImgCenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_img_old);
        buttonBigImg = findViewById(R.id.button_big_img);
        buttonBigImg.setOnClickListener(this);

        buttonBigBt = findViewById(R.id.button_big_bt_img);
        buttonBigBt.setOnClickListener(this);

        buttonBigImgCenter = findViewById(R.id.button_big_img_center);
        buttonBigImgCenter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_big_img:
                startActivity(new Intent(this, BigImgNormalAcitvity.class));
                break;
            case R.id.button_big_bt_img:
                startActivity(new Intent(this, BigImgNotDownloadAcitvity.class));
                break;
            case R.id.button_big_img_center:
                startActivity(new Intent(this, BigImgCenterActivity.class));
                break;

        }
    }
}
