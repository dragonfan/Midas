package com.xnad.sdk.http;

/**
 * 代码描述<p>
 *
 *     存放一些与 API 有关的东西,如请求地址
 *
 * @author anhuiqing
 * @since 2019/3/30 18:34
 */
public interface Api {

    String WEATHER_DOMAIN_NAME = "weather";


    String STRATEGY_INFO = "pizarroadsenseapi/ads/strategyInfo";

    class URL_DEV{//开发环境
        public static final String APP_WEATHER_DOMAIN = "http://devpizarroadsenseapi.hellogeek.com/";
    }

    class URL_TEST{//测试环境
        public static final String APP_WEATHER_DOMAIN = "http://testpizarroadsenseapi.hellogeek.com/"; //测试环境
    }

    class URL_UAT{//预发布环境
        public static final String APP_WEATHER_DOMAIN = "http://testpizarroadsenseapi.hellogeek.com/";//预发布域名
    }

    class URL_PRODUCT{//生产环境
        public static final String APP_WEATHER_DOMAIN = "http://adsenseapi.hellogeek.com/adsenseapi/";
    }




}