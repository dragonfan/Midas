package com.comm.jksdk.ad.entity;

import android.view.View;

/**
 * @ProjectName: Midas
 * @Package: com.comm.jksdk.ad.entity
 * @ClassName: MidasAd
 * @Description: 封装广告对象的接口
 * @Author: fanhailong
 * @CreateDate: 2019/12/17 14:41
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/17 14:41
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public abstract class MidasAd implements Ad{

    protected String appId;
    protected String adId;
    protected String adSource;

    protected View addView;

    public View getAddView() {
        return addView;
    }

    public void setAddView(View addView) {
        this.addView = addView;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getAdSource() {
        return adSource;
    }

    public void setAdSource(String adSource) {
        this.adSource = adSource;
    }
}