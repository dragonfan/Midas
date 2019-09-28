package com.comm.jksdk.http;

/**
 * @author liupengbing
 * @date 2019/9/21
 */
public class Constant {

    public static final String JINRITOUTIAO_DEV_URL = "http://172.16.11.251:9102";
    //    public static final String JINRITOUTIAO_Test_URL = "http://172.16.11.247:9102";
    public static final String JINRITOUTIAO_Test_URL = "http://172.16.11.48:9102";
    public static final String JINRITOUTIAO_UAT_URL = "http://preadstoutiao.hellogeek.com";
    public static final String JINRITOUTIAO_Product_URL = "http://adstoutiaoproducer.hellogeek.com";

    public static final String  MAIN_PROCESS_NAME = "com.geek.jk.weather";
    /**
     * 测试环境通用key
     */
    public static final String Test_DF_NEWS_KEY = "4d038755758f3a3a";

    /**
     * 正式环境通用key
     */
    public static final String Product_DF_NEWS_KEY = "8f7a62e74b17c3a8";

    /**
     * 测试环境时间戳地址
     */
    public static final String Test_dfTimeURL = "http://123.59.142.180";
    /**
     * 测试环境获取configInfo
     */
    public static final String TEST_CONFIG_INFO_URL = "http://172.16.11.251:8974/adsenseapi";
    /**
     * 正式环境时间戳地址,与信息流正式环境一致
     */
    public static final String Product_dfTimeURL = "http://newsapicom.dftoutiao.com";

    public static final String Test_DfNewsURL = "http://106.75.3.64";
    public static final String Product_DfNewsURL = "http://newsapicom.dftoutiao.com";

    /**
     * 测试环境加密接口地址
     */
    public static final String Test_weiMiUrl = "http://weimiinfo.dftoutiao.com";
    /**
     * 正式环境加密接口地址，与测试一致
     */
    public static final String Product_weiMiUrl = "http://weimiinfo.dftoutiao.com";

    public static class HEADER_PARAM_DEV{//开发环境
        public static final String DNV = "gMUoh3hIftoweQel/13zpABRZD2KWPaPsAwZA1BK5hcf9GoM7Q2uOd0ljXaueRGJmnvOh+rMjqxrUkXmt3xYsg==";
        public static final String GROUP = "Z3zqJsDTXVeldqo6C2fDcqLyy0JvHCOAujLpX8A/0T38mUlGYFQL8FNPtrjU5OZr1gZVYz3/kQ/IGqz/aWKDVQ==";
        public static final String PLATFORM_ID = "dqhngAPOWQX6DzId6uNpOJjmMF1NtbBDZd6jrXZfZPXI7kT3JasuALzur2t+f6KWDD/F3/J1mBhF7D34s+mJtQ==";
    }

    public static class HEADER_PARAM_TEST{//测试环境
        public static final String DNV = "HO9gbqo35qYT91j0fiy/x37Icbkd2UoTnCN2eQ+Y1aFTxFB6Bi3BGI0WurW823fqjOEgvE7ZV1h7Ocf4EZoXBg==";
        public static final String GROUP = "mDA+2UicASikWBs82nbK14Vt9Jc764HXfQ9rzsKfBb4U6933qdjhlc5l8U9hewntrA43WwTSxfLU1eJYTjMEGg==";
        public static final String PLATFORM_ID = "Dy5WK0YIUUE2CArFoDtrNZicTGv+HIu5729Ji+Y/j/bhqXUqrSgeROXXuR3m6Ajtsaa8u1m/Li8HptvzWMGuTA==";
    }

    public static class HEADER_PARAM_UAT{//预发布环境
        public static final String DNV = "KsFcXHNiSq0eSPdmkx1p6hyZlaV6O/qpv82N9kf8EHyVXCGcwFwONB3H7JQYVEPMrNFD4dS7rsiKxwMiLhJxtw==";
        public static final String GROUP = "tK9+xnpcyEhjAxX1MflNdMSN4wvnN+fA2Sasj4NrqDrrpyaC5Axpb5RQsTTW9vtScNB/lxyud/7GiawKoMMLtA==";
        public static final String PLATFORM_ID = "nCR3AJKRIb4qC2fQgYN6YHEEQpzwT3VzIlo+hf+kdtQtCcrJQxj60BOzw7tWLRHMOWU17LkFwLKm57fxyMFphQ==";
    }

    public static class HEADER_PARAM_PRODUCT{//生产环境
        public static final String DNV = "ErVbrAcgeqx4rnvigsxU9KcF5yt4RJfsXOoBl9Mxqgp9O/vwd24KZzk2BpSCVpXVILvZSsWRaIjlrhB8DRT5Hw==";
        public static final String GROUP = "MX+SWMgU4DqH+euS75OOpxN3ROzeU7ap6VuiLx9ssWKmqOka9/DbmrXmtaJUW5T6VPgZRprkXDBgiBLsKZmcEA==";
        public static final String PLATFORM_ID = "mtJrYxVbj/i0pSdxPY5TUO3hqNtcGhan8P/4wWLCRrnw7XcmXeoww6ir/tK4DSrA/CXNzoIXtAUJrPCNl7RGbA==";
    }

    public static final String CHANNEL_TEST = "wt_test";
    public static final String CHANNEL_RELEASE = "wt_guanwang";
}
