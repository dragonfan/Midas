package com.jk.adsdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.comm.jksdk.GeekAdSdk;
import com.comm.jksdk.ad.listener.AdListener;
import com.comm.jksdk.ad.listener.AdManager;
import com.jk.adsdkdemo.utils.LogUtils;

/**
  *
  * @ProjectName:    ${PROJECT_NAME}
  * @Package:        ${PACKAGE_NAME}
  * @ClassName:      ${NAME}
  * @Description:     新的大图广告四样式
  * @Author:         fanhailong
  * @CreateDate:     ${DATE} ${TIME}
  * @UpdateUser:     更新者：
  * @UpdateDate:     ${DATE} ${TIME}
  * @UpdateRemark:   更新说明：
  * @Version:        1.0
 */
public class NewBigImgActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1, button2, button3, button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_img_new);
        button1 = findViewById(R.id.new_big_img1);
        button2 = findViewById(R.id.new_big_img2);
        button3 = findViewById(R.id.new_big_img3);
        button4 = findViewById(R.id.new_big_img4);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_big_img1:
                startActivity(new Intent(this, NewBigImg1Acitvity.class));
                break;
            case R.id.new_big_img2:
                startActivity(new Intent(this, NewBigImg2Acitvity.class));
                break;
            case R.id.new_big_img3:
                startActivity(new Intent(this, NewBigImg3Acitvity.class));
                break;
            case R.id.new_big_img4:
                startActivity(new Intent(this, NewBigImg4Acitvity.class));
                break;
        }
    }
}
