package com.xnad.sdk.http.interceptor;

import android.text.TextUtils;

import com.xnad.sdk.http.Api;
import com.xnad.sdk.http.ErrorCode;
import com.xnad.sdk.http.OkHttpWrapper;
import com.xnad.sdk.http.base.BaseResponse;
import com.xnad.sdk.http.exception.DataParseException;
import com.xnad.sdk.http.exception.HttpRequestException;
import com.xnad.sdk.http.utils.ApiManage;
import com.xnad.sdk.http.utils.LogUtils;
import com.xnad.sdk.utils.CodeFactory;
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
  public Response intercept(Chain chain) throws HttpRequestException {
    Request request = null;
    try {
      Request requestTmp = chain.request();
      Headers headersTmp = requestTmp.headers();
      if (headersTmp != null) {
        String domain = headersTmp.get("Domain-Name");
        LogUtils.e("Domain-Name:" + domain);
        if (!TextUtils.isEmpty(domain)) {
          switch (domain) {
            case Api.WEATHER_DOMAIN_NAME:
              OkHttpWrapper.getInstance().updateBaseUrl(ApiManage.getWeatherURL());
              request = chain.request().newBuilder()
                      .addHeader("Content-Type", "application/json;charset=utf-8")
                      .build();
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
      request = null;
    }

    if (request == null) {
      return null;
    }

    Response response = null;
    try {
      response = chain.proceed(request);
    } catch (IOException e) {
    }
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
        if (response.code() == 200) {
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
                throw new DataParseException(code, model.getMsg());
              }
            } catch (JsonSyntaxException e) {
              throw new DataParseException(CodeFactory.HTTP_PARSE_EXCEPTION,
                      CodeFactory.getError(CodeFactory.HTTP_PARSE_EXCEPTION));
            }
          }
        }else {
          throw new HttpRequestException(response.code(),
                  CodeFactory.getError(CodeFactory.HTTP_EXCEPTION));
        }
      }else {
        throw new DataParseException(CodeFactory.HTTP_NET_WORK_DISCONNECT,
                CodeFactory.getError(CodeFactory.HTTP_NET_WORK_DISCONNECT));
      }
    } catch (Exception e) {
      throw new HttpRequestException(CodeFactory.HTTP_EXCEPTION,
              CodeFactory.getError(CodeFactory.HTTP_EXCEPTION));
    }
    return response;
  }

}
