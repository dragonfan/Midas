package com.xnad.sdk.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    private static Context sContext;

    private AppUtils() {
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        sContext = context;
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        return sContext;
    }

    /**
     * 获取应用程序名称
     *
     * @param context 上下文
     * @return String app名称
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
     *
     * @return String  imei
     */
    public static String getIMEI() {
        String imei = "";
        try {
            if (!checkHasPermission(sContext, "android.permission.READ_PHONE_STATE")) {
                return imei;
            }
            TelephonyManager tm = (TelephonyManager) sContext.getSystemService(Context.TELEPHONY_SERVICE);
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
     * 获取 Android ID
     *
     * @return androidID
     */
    public static String getAndroidID() {
        String androidID = "";
        try {
            androidID = Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
        }
        return androidID;
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


    public static int px2dp(float pxValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)
                getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }


    /**
     * 广告曝光后计数加一
     *
     * @param key
     */
    public static void putAdCount(String key) {
        int adCount = SpUtils.getInt(getKey(key), 0);
        SpUtils.putInt(getKey(key), adCount++);
    }

    public static int getAdCount(String key) {
        return SpUtils.getInt(getKey(key), 0);

    }

    private static String getKey(String key) {
        return getDay() + "_" + key;
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getDay() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return simpleDateFormat.format(new Date());
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 缓存图片集合
     * @param imgList
     */
    public static void cacheImages(List<String> imgList) {
        if (imgList==null) {
            return;
        }
        for (String s : imgList) {
            Glide.with(AppUtils.getContext())
                    .load(s)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            LogUtils.e("cache success");
                        }
                    });
        }
    }


}
