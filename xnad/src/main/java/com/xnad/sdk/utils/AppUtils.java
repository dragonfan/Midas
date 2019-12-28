package com.xnad.sdk.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * Desc:app工具类
 * <p>
 * Author: ZhouTao
 * Date: 2019/12/27
 * Copyright: Copyright (c) 2016-2020
 * Company: @小牛科技
 * Email:zhoutao@xiaoniu.com
 * Update Comments:
 *
 * @author zhoutao
 */
public class AppUtils {

    /**
     * 获取应用程序名称
     * @param context   上下文
     * @return  String app名称
     */
    public static synchronized String getAppName(Context context) {
        String appName;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            appName = context.getResources().getString(labelRes);
            if (TextUtils.isEmpty(appName)) {
                return "未知";
            }
            return appName;
        } catch (Exception e) {
            return "未知";
        }
    }
    /**
     * 获取IMEI
     * @param mContext  上下文
     * @return  String  imei
     */
    public static String getIMEI(Context mContext) {
        String imei = "";
        try {
            if (!checkHasPermission(mContext, "android.permission.READ_PHONE_STATE")) {
                return imei;
            }
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    imei = tm.getImei();
                } else {
                    imei = tm.getDeviceId();
                }
            }
        } catch (Exception e) {
        }
        return imei;
    }

    /**
     * 检测权限
     *
     * @param context    Context
     * @param permission 权限名称
     * @return true:已允许该权限; false:没有允许该权限
     */
    public static boolean checkHasPermission(Context context, String permission) {
        try {
            Class<?> contextCompat = null;
            try {
                contextCompat = Class.forName("android.support.v4.content.ContextCompat");
            } catch (Exception e) {
                //ignored
            }
            if (contextCompat == null) {
                try {
                    contextCompat = Class.forName("androidx.core.content.ContextCompat");
                } catch (Exception e) {
                    //ignored
                }
            }
            if (contextCompat == null) {
                return true;
            }
            Method checkSelfPermissionMethod = contextCompat.getMethod("checkSelfPermission", new Class[]{Context.class, String.class});
            int result = (int) checkSelfPermissionMethod.invoke(null, new Object[]{context, permission});
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return true;
        }

    }
}
