package com.comm.jksdk.http.utils;


import com.comm.jksdk.http.Api;

/**
 * 代码描述<p>
 *
 * @author anhuiqing
 * @since 2019/4/24 12:40
 */
public class ApiManage {

    public static String getWeatherURL(){
        String weatherUrl = null;
        switch (AppEnvironment.getServerApiEnvironment()){
            case Dev:
                weatherUrl = Api.URL_DEV.APP_WEATHER_DOMAIN;
                break;
            case Test:
                weatherUrl = Api.URL_TEST.APP_WEATHER_DOMAIN;
                break;
            case Uat:
                weatherUrl = Api.URL_UAT.APP_WEATHER_DOMAIN;
                break;
            case Product:
                weatherUrl = Api.URL_PRODUCT.APP_WEATHER_DOMAIN;
                break;
            default:
                weatherUrl = Api.URL_PRODUCT.APP_WEATHER_DOMAIN;
                break;
        }
        return weatherUrl;
    }

    /**
     * 获取牛数埋点地址
     * @return  牛数埋点地址
     */
    public static String getNiuDataURL(){
        String niuDataUrl;
        switch (AppEnvironment.getServerApiEnvironment()){
            case Dev:
                niuDataUrl = Api.URL_DEV.STATISTIC_DOMAN;
                break;
            case Test:
                niuDataUrl = Api.URL_TEST.STATISTIC_DOMAN;
                break;
            case Uat:
                niuDataUrl = Api.URL_UAT.STATISTIC_DOMAN;
                break;
            default:
                niuDataUrl = Api.URL_PRODUCT.STATISTIC_DOMAN;
                break;
        }
        return niuDataUrl;
    }

}

