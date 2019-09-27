package com.jk.adsdkdemo;

import android.app.Application;
import android.content.Context;


import com.comm.jksdk.ad.AdsManger;

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
        AdsManger.getInstance().init(mContext,"5015945","即刻天气");

    }


    public static Context getContext(){
        return mContext;
    }
}
