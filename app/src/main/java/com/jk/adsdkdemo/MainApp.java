package com.jk.adsdkdemo;

import android.app.Application;
import android.content.Context;

import com.comm.jksdk.GeekAdSdk;

/**
 * @author liupengbing
 * @date 2019/9/21
 */
public class MainApp extends Application {
    private static final String TAG = "MainApp";
    private static Context mContext;
    @Override
    public void onCreate() {
      mContext = getApplicationContext();

        super.onCreate();
        // 需要放在接口请求之前
//        NativesAdManger.getInstance().init(mContext,"5015945","即刻天气");

        //初始化聚合广告sdk
//        GeekAdSdk.init(this, "13", "jinritoutiao", "5015945",  false);
        GeekAdSdk.init(this, "18", "jinritoutiao", "5034152",  false);
        GeekAdSdk.setBid(10);

    }


    public static Context getContext(){
        return mContext;
    }
}
