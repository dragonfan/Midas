package com.xnad.sdk.http.protocol;

/**
 * Desc:网络协议地址
 * <p>
 * Author: AnYaBo
 * Date: 2020/1/6
 * Copyright: Copyright (c) 2016-2022
 * Company: @小牛科技
 * Email:anyabo@xiaoniu.com
 * Update Comments:
 *
 * @author anyabo
 */
public interface Protocal {
    /**
     *测试接口域名
     */
    String API_DOMAIN_HOST_TEST = "http://testpizarroadsenseapi.hellogeek.com/";
    /**
     *生产接口域名
     */
    String API_DOMAIN_HOST_PRODUCT = "http://pizarroadsenseapi.xiaoniuhy.com/";
    /**
     * xnId来源测试域名
     */
    String XN_ID_SOURCE_HOST_TEST = "http://testuseradverlabel.51huihuahua.com/useradverlabel/";
    /**
     * xnId来源生产域名
     */
    String XN_ID_SOURCE_HOST_PRODUCT = "http://testuseradverlabel.51huihuahua.com/useradverlabel/";
}
