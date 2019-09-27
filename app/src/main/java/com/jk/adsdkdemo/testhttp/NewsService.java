package com.jk.adsdkdemo.testhttp;



import com.comm.jksdk.http.Api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
*
* @author  xiangzhenbiao
* @since 2019/4/30 14:35
*/
public interface NewsService {

    /**
     * 获取时间戳
     * @return
     */
    @Headers({DOMAIN_NAME_HEADER + Api.TIME_DF_TOUTIAO_DOMAIN_NAME})
    @GET("/newskey/ts")
    Observable<TimeResponse> getTimes();




}
