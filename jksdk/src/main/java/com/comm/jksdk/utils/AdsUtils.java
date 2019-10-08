package com.comm.jksdk.utils;

import com.comm.jksdk.constant.Constants;
import com.comm.jksdk.http.utils.LogUtils;

import java.util.Random;

/**
 * @author liupengbing
 * @date 2019/9/28
 */
public class AdsUtils {
    protected final static String TAG = "GeekAdSdk-->";
    public static boolean requestAdOverTime(int adRequestTimeOut){
         boolean requestAdOverTime=false;
         if(adRequestTimeOut>0) {
             Long curTime = System.currentTimeMillis();
             Long firstRequestAdTime = SpUtils.getLong(Constants.SPUtils.FIRST_REQUEST_AD_TIME, 0L);
             if (firstRequestAdTime.longValue() != 0) {
                 if (curTime - firstRequestAdTime > adRequestTimeOut * 1000) {
                     // 超时
                     LogUtils.d(TAG, "onADLoaded->请求广告超时");
                     requestAdOverTime = true;
                 } else {
                     requestAdOverTime = false;
                 }
             } else {
                 requestAdOverTime = false;
             }
         }else{
             requestAdOverTime = false;
         }
        return requestAdOverTime;
    }

    public static int getRandomNum(int max){
        // 产生[0,max-1]范围内的随机数为例
        int num=0;
        Random random = new Random();
        num=random.nextInt(max);
        return num;
    }
}
