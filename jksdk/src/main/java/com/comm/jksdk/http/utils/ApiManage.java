package com.comm.jksdk.http.utils;


import com.comm.jksdk.http.Api;
import com.comm.jksdk.http.Constant;

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

    public static String getLoginURL(){
        String loginUrl = null;
        switch (AppEnvironment.getServerApiEnvironment()){
            case Dev:
                loginUrl = Api.URL_DEV.APP_USER_DOMAIN;
                break;
            case Test:
                loginUrl = Api.URL_TEST.APP_USER_DOMAIN;
                break;
            case Uat:
                loginUrl = Api.URL_UAT.APP_USER_DOMAIN;
                break;
            case Product:
                loginUrl = Api.URL_PRODUCT.APP_USER_DOMAIN;
                break;
            default:
                loginUrl = Api.URL_PRODUCT.APP_USER_DOMAIN;
                break;
        }
        return loginUrl;
    }

    /**
     * 加密接口地址,测试和生产环境都是一样的
     * @return
     */
    public static String getDfWeimiURL(){
        String weiMiUrl = null;
        //http://weimiinfo.dftoutiao.com/appidfa_encryption/appidfa
        switch (AppEnvironment.getServerApiEnvironment()){
            case Dev:
                weiMiUrl = Constant.Test_weiMiUrl;
                break;
            case Test:
                weiMiUrl = Constant.Test_weiMiUrl;
                break;
            case Uat:
                weiMiUrl = Constant.Product_weiMiUrl;
                break;
            case Product:
                weiMiUrl = Constant.Product_weiMiUrl;
                break;
            default:
                weiMiUrl = Constant.Product_weiMiUrl;
                break;
        }
        return weiMiUrl;
    }

    /**
     * 时间戳接口地址
     * @return
     */
    public static String getDfTimeURL(){
        String dfTimeURL = null;
        switch (AppEnvironment.getServerApiEnvironment()){
            case Dev:
                dfTimeURL = Constant.Test_dfTimeURL;
                break;
            case Test:
                dfTimeURL = Constant.Test_dfTimeURL;
                break;
            case Uat:
                dfTimeURL = Constant.Product_dfTimeURL;
                break;
            case Product:
                dfTimeURL = Constant.Product_dfTimeURL;
                break;
            default:
                dfTimeURL = Constant.Product_dfTimeURL;
                break;
        }
        return dfTimeURL;
    }
    /**
     * 时间戳接口地址
     * @return
     */
    public static String getDfConfiginfoURL(){
        String dfConfiginfoURL = null;
        switch (AppEnvironment.getServerApiEnvironment()){
            case Dev:
                dfConfiginfoURL = Constant.TEST_CONFIG_INFO_URL;
                break;
            case Test:
                dfConfiginfoURL = Constant.TEST_CONFIG_INFO_URL;
                break;
            case Uat:
                dfConfiginfoURL = Constant.TEST_CONFIG_INFO_URL;
                break;
            case Product:
                dfConfiginfoURL = Constant.TEST_CONFIG_INFO_URL;
                break;
            default:
                dfConfiginfoURL = Constant.TEST_CONFIG_INFO_URL;
                break;
        }
        return dfConfiginfoURL;
    }
    /**
     * 东方头条信息流接口
     * @return
     */
    public static String getDfNewsURL(){
        String dfNewsURL = null;
        switch (AppEnvironment.getServerApiEnvironment()){
            case Dev:
                dfNewsURL = Constant.Test_DfNewsURL;
                break;
            case Test:
                dfNewsURL = Constant.Test_DfNewsURL;
                break;
            case Uat:
                dfNewsURL = Constant.Product_DfNewsURL;
                break;
            case Product:
                dfNewsURL = Constant.Product_DfNewsURL;
                break;
            default:
                dfNewsURL = Constant.Product_DfNewsURL;
                break;
        }
        return dfNewsURL;
    }

    /**
     * 今日头条次流回传统计
     * @return
     */
    public static String getJinRiTouTiaoURL(){
        String JINRITOUTIAO_URL = null;
        switch (AppEnvironment.getServerApiEnvironment()){
            case Dev:
                JINRITOUTIAO_URL = Constant.JINRITOUTIAO_DEV_URL;
                break;
            case Test:
                JINRITOUTIAO_URL = Constant.JINRITOUTIAO_Test_URL;
                break;
            case Uat:
                JINRITOUTIAO_URL = Constant.JINRITOUTIAO_UAT_URL;
                break;
            case Product:
                JINRITOUTIAO_URL = Constant.JINRITOUTIAO_Product_URL;
                break;
            default:
                JINRITOUTIAO_URL = Constant.JINRITOUTIAO_Product_URL;
                break;
        }
        return JINRITOUTIAO_URL;
    }

}

