package com.comm.jksdk.api;



import com.comm.jksdk.bean.ConfigBean;
import com.comm.jksdk.http.Api;
import com.comm.jksdk.http.base.BaseResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
*
* @author  xiangzhenbiao
* @since 2019/4/30 14:35
*/
public interface ConfigService {

    /**
     * 获取时间戳
     * @return
     */
    @Headers({DOMAIN_NAME_HEADER + Api.TIME_DF_CONFIGINFO_DOMAIN_NAME})
    @POST("/v1/getAdsConfig/getConfig")
    Observable<BaseResponse<ConfigBean>> getConfig(@Body RequestBody requestBody);

}
