package com.xnad.sdk.ad.entity;

import com.xnad.sdk.config.AdParameter;

/**
 * @ProjectName: MidasAdSdk
 * @Package: com.comm.jksdk.ad.entity
 * @ClassName: AdInfo
 * @Description: 广告信息（用来回调给业务线）
 * @Author: fanhailong
 * @CreateDate: 2019/11/21 13:40
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/21 13:40
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class AdInfo implements Ad {
    /**
     * 埋点公共属性
     */
    private StatisticBaseProperties statisticBaseProperties;

    /**
     * 广告请求参数
     */
    private AdParameter mAdParameter;

    /**
     * 广告分类（目前有6大类）
     */
    private String adType;

    /**
     * 广告对象
     */
    private MidasAd midasAd;

    public StatisticBaseProperties getStatisticBaseProperties() {
        return statisticBaseProperties;
    }

    public void setStatisticBaseProperties(StatisticBaseProperties statisticBaseProperties) {
        this.statisticBaseProperties = statisticBaseProperties;
    }

    public MidasAd getMidasAd() {
        return midasAd;
    }

    public void setMidasAd(MidasAd midasAd) {
        this.midasAd = midasAd;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public AdParameter getAdParameter() {
        return mAdParameter;
    }

    public void setAdParameter(AdParameter adParameter) {
        mAdParameter = adParameter;
    }

    /***************************************************************优雅分割线**********************************************************************/


    /**
     * 点击后的类型：1=下载；2=详情
     */
    private int adClickType;

    /**
     * 位置信息
     */
    private String mPosition;
    /**
     * 是否是预加载
     */
    private boolean mIsPreload;

    /**
     * 是否支持磁盘缓存
     */
    private boolean isDisk;


    public String getPosition() {
        return mPosition;
    }

    public void setPosition(String mPosition) {
        this.mPosition = mPosition;
    }

    @Override
    public void clear() {

    }

}
