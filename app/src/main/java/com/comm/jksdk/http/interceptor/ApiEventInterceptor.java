package com.comm.jksdk.http.interceptor;

import android.text.TextUtils;


import com.comm.jksdk.http.Constant;
import com.comm.jksdk.http.base.BaseResponse;
import com.comm.jksdk.http.Api;
import com.comm.jksdk.http.ErrorCode;
import com.comm.jksdk.http.OkHttpWrapper;
import com.comm.jksdk.http.utils.ApiManage;
import com.comm.jksdk.http.utils.AppEnvironment;
import com.comm.jksdk.http.utils.AppInfoUtils;
import com.comm.jksdk.http.utils.ChannelUtil;
import com.comm.jksdk.http.utils.LogUtils;
import com.comm.jksdk.http.utils.NetworkUtil;
import com.comm.jksdk.http.utils.UaUtils;

import com.jk.adsdkdemo.MainApp;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 代码描述<p>
 *
 *     统计api interceptor
 *
 * @author anhuiqing
 * @since 2019/4/16 0:40
 */

public class ApiEventInterceptor implements Interceptor {
  private final Charset UTF8 = Charset.forName("UTF-8");

  @Override
  public Response intercept(Chain chain) throws IOException {
    String env = null;
    String group = null;
    String platformId = null;
    switch (AppEnvironment.getServerApiEnvironment()) {
      case Dev:
        env = Constant.HEADER_PARAM_DEV.DNV;
        group = Constant.HEADER_PARAM_DEV.GROUP;
        platformId = Constant.HEADER_PARAM_DEV.PLATFORM_ID;
        break;
      case Test:
        env = Constant.HEADER_PARAM_TEST.DNV;
        group = Constant.HEADER_PARAM_TEST.GROUP;
        platformId = Constant.HEADER_PARAM_TEST.PLATFORM_ID;
        break;
      case Uat:
        env = Constant.HEADER_PARAM_UAT.DNV;
        group = Constant.HEADER_PARAM_UAT.GROUP;
        platformId = Constant.HEADER_PARAM_UAT.PLATFORM_ID;
        break;
      case Product:
        env = Constant.HEADER_PARAM_PRODUCT.DNV;
        group = Constant.HEADER_PARAM_PRODUCT.GROUP;
        platformId = Constant.HEADER_PARAM_PRODUCT.PLATFORM_ID;
        break;
      default:
        env = Constant.HEADER_PARAM_PRODUCT.DNV;
        group = Constant.HEADER_PARAM_PRODUCT.GROUP;
        platformId = Constant.HEADER_PARAM_PRODUCT.PLATFORM_ID;
        break;
    }
    Request request = null;
    try {
      Request requestTmp = chain.request();
      Headers headersTmp = requestTmp.headers();
      if (headersTmp != null) {
        String domain = headersTmp.get("Domain-Name");
        LogUtils.e("Domain-Name:" + domain);
        if (!TextUtils.isEmpty(domain)) {
          switch (domain) {
            case Api.USER_DOMAIN_NAME:
              OkHttpWrapper.getInstance().updateBaseUrl(ApiManage.getLoginURL());
              request = chain.request().newBuilder()
                      .addHeader("Content-Type", "application/json;charset=utf-8")
                      .addHeader("env", env)
                      .addHeader("group", group)
                      .addHeader("platform-id", platformId)
                      .addHeader("source", "3")
                      .addHeader("channel", ChannelUtil.getChannel()) //上传渠道号
                      .addHeader("deviceType", "Android") //上传设备类型
                      .addHeader("app-id", "")
                      .addHeader("token", "")
                      .addHeader("UserId", "")
                      .addHeader("sign",  "")
                      .addHeader("version", AppInfoUtils.getVersionName())
                      .addHeader("versionCode", AppInfoUtils.getVersionCode()+"")
                      .build();
              break;
            case Api.WEATHER_DOMAIN_NAME:
              OkHttpWrapper.getInstance().updateBaseUrl(ApiManage.getWeatherURL());
              request = chain.request().newBuilder()
                      .addHeader("Content-Type", "application/json;charset=utf-8")
                      .addHeader("env", env)
                      .addHeader("group", group)
                      .addHeader("platform-id", platformId)
                      .addHeader("source", "3")
                      .addHeader("channel", ChannelUtil.getChannel()) //上传渠道号
                      .addHeader("deviceType", "Android") //上传设备类型
                      .addHeader("app-id","")
                      .addHeader("token","")
                      .addHeader("UserId", "")
                      .addHeader("sign",  "")
                      .addHeader("timestamp",   "")
                      .addHeader("uuid",  "")
                      .addHeader("version", AppInfoUtils.getVersionName())
                      .addHeader("versionCode", AppInfoUtils.getVersionCode()+"")
                      .addHeader("ua", UaUtils.getUa())
                      .addHeader("appSign", "").build();
              break;
            default:
              request = requestTmp;
              break;
          }
        } else {
          request = requestTmp;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      request = null;
    }

    if (request == null) {
      return null;
    }
    if (!NetworkUtil.isNetworkActive(MainApp.getContext())) {
      throw new IOException();
    }
    Response response = chain.proceed(request);
    String rBody;
    try {
      if (response != null) {
        ResponseBody responseBody = response.body();
        Headers headers = response.headers();
        LogUtils.e("ApiEventInterceptor 返回 headers->>>" + headers);
        String XReqid = headers.get("X-Reqid");
        String path = request.url().encodedPath();
        int code = -1;

        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
          try {
            charset = contentType.charset(UTF8);
          } catch (UnsupportedCharsetException e) {
            e.printStackTrace();
          }
        }
        rBody = buffer.clone().readString(charset);
        if (!TextUtils.isEmpty(rBody)) {
          Gson gson = new Gson();
          try {
            LogUtils.e("ApiEventInterceptor 请求 url->>>" + response.request().url().toString());
            BaseResponse model = gson.fromJson(rBody, BaseResponse.class);
            code = model.getCode();
            if (code == ErrorCode.SUCCESS) {
              long requestAtMillis = response.networkResponse().sentRequestAtMillis();
              long castTime = System.currentTimeMillis() - requestAtMillis;
            } else {
            }
          } catch (JsonSyntaxException e) {
            e.printStackTrace();
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }
}
