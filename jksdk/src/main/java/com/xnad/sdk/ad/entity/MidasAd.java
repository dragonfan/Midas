package com.xnad.sdk.ad.entity;

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
    /**
     * 超时时间只有开屏有
     */
    protected int timeOut;
    /**
     * 广告源请求广告个数。
     *      特定广告类型才有多个如信息流广告，其他默认为1
     */
    private int sourceRequestNum;

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

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public int getSourceRequestNum() {
        return sourceRequestNum;
    }

    public void setSourceRequestNum(int sourceRequestNum) {
        this.sourceRequestNum = sourceRequestNum;
    }

    /**
     * 广告标题
     * @return
     */
    public String getTitle(){
        return null;
    }

    /**
     * 广告描述
     * @return
     */
    public String getDescription(){
        return null;
    }

    /**
     * 广告的icon链接。
     * @return
     */
    public String getIconUrl(){
        return null;
    }

    /**
     * 广告的大图链接。
     * @return
     */
    public String getImageUrl(){
        return null;
    }

}
