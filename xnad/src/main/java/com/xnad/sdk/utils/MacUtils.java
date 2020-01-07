package com.xnad.sdk.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Desc:
 * <p>
 * Author: ZhouTao
 * Date: 2019/12/20
 * Copyright: Copyright (c) 2016-2020
 * Company: @小牛科技
 * Email:zhoutao@xiaoniu.com
 * Update Comments:
 *
 * @author zhoutao
 */
public class MacUtils {

    public static String getMacFromHardware(Context context) {

        String macAddress = null;
        //5.0以下
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                macAddress = getMacDefault(context);
                if (macAddress != null ) {
                    if(macAddress.equalsIgnoreCase("020000000000")== false){
                        return macAddress;
                    }
                }
                //android 6~7 的方式获取的mac
            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                macAddress = getMacAddress();
                if (macAddress != null ) {
                    if(macAddress.equalsIgnoreCase("020000000000")== false){
                        return macAddress;
                    }
                }
                //android 7以后 的方式获取的mac
            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                macAddress = getMacFromHardware();
                if (macAddress != null ) {
                    if(macAddress.equalsIgnoreCase("020000000000") == false){
                        return macAddress;
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * Android  6.0 之前（不包括6.0）
     * 必须的权限  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     * @param context
     * @return
     */
    private static String getMacDefault(Context context) {
        String mac = null;
        if (context == null) {
            return mac;
        }

        WifiManager wifi = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) {
            return mac;
        }
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {

        }
        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH);
        }
        return mac;
    }

    /**
     * Android 6.0（包括） - Android 7.0（不包括）
     * @return
     */
    private static String getMacAddress() {
        String WifiAddress =  null;
        try {
            WifiAddress = new BufferedReader(new FileReader(new File("/sys/class/net/wlan0/address"))).readLine();
        } catch (IOException e) {
        }
        return WifiAddress;
    }

    /**
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET" />
     * @return
     */
    private static String getMacFromHardware() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) {
                    continue;
                }

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
        }
        return null;
    }


}
