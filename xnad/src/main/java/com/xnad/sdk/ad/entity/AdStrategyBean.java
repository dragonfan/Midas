package com.xnad.sdk.ad.entity;

/**
 * Desc:广告策略对象
 * <p>
 * Author: ZhouTao
 * Date: 2019/12/27
 * Copyright: Copyright (c) 2016-2020
 * Company: @小牛科技
 * Email:zhoutao@xiaoniu.com
 * Update Comments:
 *
 * @author zhoutao
 */
public class AdStrategyBean {
    /**
     * adId : 65487887820
     * adUnion : youlianhui0
     * requestOrder : 2
     * adsAppid : 1213130
     */

    private String adId;
    private String adUnion;
    private String requestOrder;
    private String adsAppid;
    /**
     * 展示次数：0 - 为默认不限制
     */
    private int showNum;

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getAdUnion() {
        return adUnion;
    }

    public void setAdUnion(String adUnion) {
        this.adUnion = adUnion;
    }

    public String getRequestOrder() {
        return requestOrder;
    }

    public void setRequestOrder(String requestOrder) {
        this.requestOrder = requestOrder;
    }

    public String getAdsAppid() {
        return adsAppid;
    }

    public void setAdsAppid(String adsAppid) {
        this.adsAppid = adsAppid;
    }

    public int getShowNum() {
        return showNum;
    }

    public void setShowNum(int showNum) {
        this.showNum = showNum;
    }
}