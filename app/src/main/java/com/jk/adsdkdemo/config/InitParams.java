package com.jk.adsdkdemo.config;

/**
 * Desc:初始化参数
 * <p>
 * Author: AnYaBo
 * Date: 2020/1/3
 * Copyright: Copyright (c) 2016-2022
 * Company: @小牛科技
 * Email:anyabo@xiaoniu.com
 * Update Comments:
 *
 * @author anyabo
 */
public interface InitParams {
    /**
     * app业务线(大数据提供)
     */
    String PRODUCT_ID = "32";
    /**
     * 广告业务线（大数据提供）
     */
    String APP_ID = "320001";
    /**
     * 穿山甲app id
     */
    String CSJ_APP_ID = "5037925";
    /**
     * 应用渠道号
     */
    String CHANNEL = "vivo";
    /**
     * 埋点地址
     *  喝水生产埋点地址 String DATA_PROBE_URL = "http://aidataprobe2.openxiaoniu.com/aidataprobe2/dhs";
     */
    String DATA_PROBE_URL = "http://testaidataprobe2.51huihuahua.com/apis/v1/dataprobe2/dhs";
    /**
     * 是否是正式环境 true对应生产环境
     */
    boolean IS_FORMAL = false;
}
