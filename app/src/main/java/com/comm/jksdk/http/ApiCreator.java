package com.comm.jksdk.http;

/**
 * 代码描述<p>
 *
 *     api creator
 *
 * @author anhuiqing
 * @since 2019/4/16 0:32
 */

public class ApiCreator {
  public static <T> T createApi(Class<T> clazz) {
    if (clazz == null) {
      return null;
    }
    return OkHttpWrapper.getInstance().getRetrofit().create(clazz);
  }
}
