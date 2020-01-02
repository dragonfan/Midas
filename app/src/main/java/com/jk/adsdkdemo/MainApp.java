package com.jk.adsdkdemo;

import android.app.Application;
import android.content.Context;

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
        //test
        // 需要放在接口请求之前
//        MidasAdSdk.init(this, "88", "5036430", "jinritoutiao",   false);

    }

    public static Context getContext(){
        return mContext;
    }

}
