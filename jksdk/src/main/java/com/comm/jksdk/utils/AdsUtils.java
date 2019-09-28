package com.comm.jksdk.utils;

import com.comm.jksdk.constant.Constants;
import com.comm.jksdk.http.utils.LogUtils;

/**
 * @author liupengbing
 * @date 2019/9/28
 */
public class AdsUtils {
    protected final static String TAG = "GeekAdSdk-->";
    public static boolean requestAdOverTime(int adRequestTimeOut){
         boolean requestAdOverTime=false;
        Long curTime=System.currentTimeMillis();
        Long firstRequestAdTime=SpUtils.getLong(Constants.SPUtils.FIRST_REQUEST_AD_TIME,0L);
        if(firstRequestAdTime.longValue()!=0){
            if(curTime-firstRequestAdTime>adRequestTimeOut*1000){
                // 超时
                LogUtils.d(TAG, "onADLoaded->请求广告超时");
                requestAdOverTime=true;
            }else{
                requestAdOverTime=false;
            }
        }else{
            requestAdOverTime=false;
        }
        return requestAdOverTime;
    }
}
