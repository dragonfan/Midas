package com.comm.jksdk.http.callback;


import com.comm.jksdk.http.base.BaseResponse;
import com.comm.jksdk.http.ErrorCode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * 统一封装，网络返回
 *
 * 作者：ahq on 2018/9/15 11:14
 * 邮箱：anhuiqing@bigbaser.com
 */
public abstract class LuckCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response == null) {
            onFailure("LuckCallback response model is null");
            return;
        }
        if (response.raw() == null) {
            onFailure("LuckCallback response.raw() is null");
            return;
        }
        if (response.raw().request() == null) {
            onFailure("LuckCallback response.raw().request() is null");
            return;
        }
        if (response.raw().request().url() == null) {
            onFailure("LuckCallback response.raw().request().url() is null");
            return;
        }
        BaseResponse model;
        if (response.body() != null) {
            if (response.body() instanceof BaseResponse) {
                model = (BaseResponse) response.body();
                int code = model.getCode();
                switch (code){
                    case ErrorCode.SUCCESS:
                        onSuccess(response.body());
                        break;
                    case ErrorCode.ERROR:
                        onSuccess(response.body());
                        break;
                     default:
                         onSuccess(response.body());
                         break;
                }
            } else {
                onSuccess(response.body());
            }
        } else {
            onFailure("LuckCallback response body is null");
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t != null) {
            t.printStackTrace();
            onFailure(t.getMessage());
        } else {
            onFailure("network error");
        }
    }

    /**
     * @param t model wouldn't be null
     */
    public abstract void onSuccess(T t);

    public abstract void onFailure(String message);
}


