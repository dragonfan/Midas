package com.comm.jksdk.http;

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

    class URL_DEV{//开发环境
        public static final String APP_WEATHER_DOMAIN = "http://devpizarroadsenseapi.hellogeek.com/";
        /**
         * 牛数埋点地址
          */
        public static final String STATISTIC_DOMAN = "http://testaidataprobe2.51huihuahua.com/v/v/dataprobe2/ggbx";
    }

    class URL_TEST{//测试环境
//        public static final String APP_WEATHER_DOMAIN = "http://172.16.11.247:8974/adsenseapi/"; //测试环境
//        public static final String APP_WEATHER_DOMAIN = "http://testadsenseapi.hellogeek.com/adsenseapi/"; //测试环境
        public static final String APP_WEATHER_DOMAIN = "http://devpizarroadsenseapi.hellogeek.com/"; //测试环境
        /**
         * 牛数埋点地址
         */
        public static final String STATISTIC_DOMAN = "http://testaidataprobe2.51huihuahua.com/v/v/dataprobe2/ggbx";

    }

    class URL_UAT{//预发布环境
        public static final String APP_WEATHER_DOMAIN = "http://testadsenseapi.hellogeek.com/adsenseapi/";//预发布域名
        /**
         * 牛数埋点地址
         */
        public static final String STATISTIC_DOMAN = "http://testaidataprobe2.51huihuahua.com/v/v/dataprobe2/ggbx";
    }

    class URL_PRODUCT{//生产环境
        public static final String APP_WEATHER_DOMAIN = "http://adsenseapi.hellogeek.com/adsenseapi/";
        /**
         * 牛数埋点地址   TODO 埋点生产地址
         */
        public static final String STATISTIC_DOMAN = "";
    }




}