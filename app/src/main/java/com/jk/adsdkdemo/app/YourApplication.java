package com.jk.adsdkdemo.app;

import android.app.Application;

import com.jk.adsdkdemo.config.InitParams;
import com.xnad.sdk.MidasAdSdk;
import com.xnad.sdk.config.AdConfig;

/**
 * Desc:应用入口
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
public class YourApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化midas sdk
        AdConfig configBuild = new AdConfig.Build()
                .setAppId(InitParams.APP_ID)
                .setProductId(InitParams.PRODUCT_ID)
                .setCsjAppId(InitParams.CSJ_APP_ID)
                .setChannel(InitParams.CHANNEL)
                .setServerUrl(InitParams.DATA_PROBE_URL)
                .setIsFormal(InitParams.IS_FORMAL)
                .build();
        MidasAdSdk.init(this, configBuild);
    }


}
