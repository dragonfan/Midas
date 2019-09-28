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
    String USER_DOMAIN_NAME = "user";


    String WEIMI_DF_TOUTIAO_DOMAIN_NAME = "weimiinfo";
    String TIME_DF_TOUTIAO_DOMAIN_NAME = "time";
    String NEWS_DF_TOUTIAO_DOMAIN_NAME = "news";
    String JINRITOUTIAO_RETENTION_DOMAIN_NAME = "jinritoutiao_retention";
    String TIME_DF_CONFIGINFO_DOMAIN_NAME = "configinfo";

    class URL_DEV{//开发环境
        public static final String APP_WEATHER_DOMAIN = "http://devweatapi.hellogeek.com/weatapi/";
        public static final String APP_USER_DOMAIN = "http://172.16.11.248:9098";
    }

    class URL_TEST{//测试环境
        public static final String APP_WEATHER_DOMAIN = "http://testweatapi.hellogeek.com/weatapi/";
//        public static final String APP_WEATHER_DOMAIN = "http://172.16.11.16:8923/weatapi/";
//        public static final String APP_WEATHER_DOMAIN = "http://192.168.211.37:8923/weatapi/"; //聂佳懿本地地址
        public static final String APP_USER_DOMAIN = "http://172.16.11.241:9098";
    }

    class URL_UAT{//预发布环境
//        public static final String APP_WEATHER_DOMAIN = "http://weatapi.hellogeek.com/weatapi/";//目前保持和开发环境一样
        public static final String APP_WEATHER_DOMAIN = "http://preweatapi.hellogeek.com/weatapi/";//预发布域名
//        public static final String APP_WEATHER_DOMAIN = "http://172.16.11.10:8923/weatapi/";//预发布ip
        public static final String APP_USER_DOMAIN = "http://preusercenter.hellogeek.com"; //预发布域名
    }

    class URL_PRODUCT{//生产环境
        public static final String APP_WEATHER_DOMAIN = "http://weatapi.hellogeek.com/weatapi/";
        // 灰度环境
//        public static final String APP_WEATHER_DOMAIN = "http:/weatapi.hellogeek.com/weatapi/";
        public static final String APP_USER_DOMAIN = "http://usercenter.ywan3.com";
    }




}