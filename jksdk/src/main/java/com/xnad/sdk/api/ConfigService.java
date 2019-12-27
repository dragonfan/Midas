package com.xnad.sdk.api;


import com.xnad.sdk.bean.MidasConfigBean;
import com.xnad.sdk.http.Api;
import com.xnad.sdk.http.base.BaseResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;


public interface ConfigService {

    /**
     * 获取时间戳
     * @return
     */
    @Headers({DOMAIN_NAME_HEADER + Api.WEATHER_DOMAIN_NAME})
    @POST("/pizarroadsenseapi/ads/strategyInfo")
    Observable<BaseResponse<MidasConfigBean>> getConfig(@Body RequestBody requestBody);

}
