package com.comm.jksdk.http.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.comm.jksdk.MidasAdSdk;
import com.google.gson.Gson;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 代码描述<p>
 *
 * @author anhuiqing
 * @since 2019/4/24 12:51
 */
public class AppInfoUtils {

    /**
     * 获取versionName
     * @return
     */
    public static String getVersionName() {
        if(MidasAdSdk.getContext()==null){
            return "";
        }
        String packageName = MidasAdSdk.getContext().getPackageName();
        String versionName = null;
        try {
            versionName = MidasAdSdk.getContext().getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取versionCode
     * @return
     */
    public static int getVersionCode() {
        int versionCode = 0;
        if(MidasAdSdk.getContext()==null){
            return 0;
        }
        try {
            versionCode = MidasAdSdk.getContext().getPackageManager()
                    .getPackageInfo(MidasAdSdk.getContext().getPackageName(),
                            0).versionCode;
        } catch (Exception e) {
            versionCode = 0;
        }
        return versionCode;
    }


    /**
     * 获取当前进程的名字，一般就是当前app的包名
     *
     * @param context 当前上下文
     * @return 返回进程的名字
     */
    public static String getAppName(Context context)
    {
        int pid = android.os.Process.myPid(); // Returns the identifier of this process
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        Iterator i = list.iterator();
        while (i.hasNext())
        {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try
            {
                if (info.pid == pid)
                {
                    // 根据进程的信息获取当前进程的名字
                    return info.processName;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        // 没有匹配的项，返回为null
        return null;
    }


    /**
     * 把一个json的字符串转换成为一个包含POJO对象的List
     *
     * @param string
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonStringConvertToList(String string, Class<T[]> cls) {
        Gson gson = new Gson();
        T[] array = gson.fromJson(string, cls);
        return Arrays.asList(array);
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVerCode(Context context) {
        int vercoe = 0;

        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            vercoe = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        return vercoe;
    }
}
