package com.comm.jksdk.bean;

import com.comm.jksdk.BuildConfig;

import java.io.Serializable;

/**
 * Desc:埋点基础属性
 * <p>
 * Author: AnYaBo
 * Date: 2019/12/23
 * Copyright: Copyright (c) 2016-2022
 * Company: @小牛科技
 * Email:anyabo@xiaoniu.com
 * Update Comments:
 *
 * @author anyabo
 */
public class StatisticBaseProperties implements Serializable {
    /**
     * 发起一次广告请求时生成的会话ID，用于串联所有的广告事件。便于数据跟踪以及公共属性关联。
     *      uuid+时间戳
     */
    private String sessionId;
    /**
     * 当前用户的设备ID信息
     *投票法通过大数据接口生成的
     */
    private String primaryId;
    /**
     * 广告位的位置信息。（一般为英文缩写说明，比如SPB_locker_boost_result）
     */
    private String unitId;
    /**
     * 广告位的位置编号，用于向广告系统请求广告位策略。
     */
    private String adPosId;
    /**
     * 广告位对应策略编号，每次策略调整均会改变，全库表唯一。
     */
    private String strategyId;
    /**
     * 广告策略的类型。0：本地策略。1：内存缓存策略。2：文件缓存策略。3：实时请求策略。
     *      预留，暂时用3
     */
    private String strategyType;
    /**
     * 广告策略的实时请求结果。详见表格<Result_Code>
     *     midas后台和midas sdk业务code
     */
    private String configResultCode;
    /**
     * 广告策略的最终请求结果（包括加载缓存和本地策略）。详见表格<Result_Code>
     *     通讯协议的code
     */
    private String resultCode;
    /**
     * SDK版本
     */
    private String sdkVersion;
    /**
     * 广告位请求广告个数
     *      实际请求广告联盟的次数
     */
    private int unitRequestNum;
    /**
     * 广告位请求广告方式。0：串行，1：并行。2：串并行结合。当前只做串行，默认值：0
     *      预留，暂时用0
     */
    private int unitRequestType;
    /**
     * 并行请求的最佳等待时间。
     *      预留，暂时用5s
     */
    private int unitBestWaiting;
    /**
     * 广告位请求广告，是否需要提前下载icon图片。
     *      预留，暂时没有
     */
    private boolean unitPrepareIcon;
    /**
     * 广告位请求广告，是否需要提前下载banner大图片。
     *      预留，暂时没有
     */
    private boolean unitPrepareBanner;
    /**
     * 广告的单元ID。简称PID或plID
     *     广告联盟的adId
     */
    private String placementId;
    /**
     * 广告源的ID。 由海龙定义后产出。
     *      穿山甲：chuanshanjia；优量汇：youlianghui
     */
    private String sourceId;
    /**
     * 广告源请求广告个数。
     *      特定广告类型才有多个如信息流广告，其他默认为1
     */
    private int sourceRequestNum;
    /**
     * 广告源请求广告超时时间。
     *      只有开屏能设置超时时间，其他没有
     */
    private int sourceTimeOut;
    /**
     * 广告类型。0：原生，1：插屏，2：Banner，3：激励视频。
     *      类型用王通定义的类型
     */
    private String style;
    /**
     * 聚合广告源的广告单元ID
     *      自己的聚合：midas,后期用到其他的在定义
     */
    private String mediationId;
    /**
     * 广告主信息
     *      不确定 ：广告主？目前穿山甲部分广告有getSource
     */
    private String advertiser;
    /**
     * offer包名（下载类型对应下载app的包名）
     *      不确定 是否能拿到
     */
    private String pkg;
    /**
     * 竞价价格，单位RMB
     *      预留，现在没有
     */
    private String bidPrice;
    /**
     * 支付价格，单位RMB
     *      预留，现在没有
     */
    private String chargePrice;
    /**
     * 填充广告个数（实际拿到广告的个数。串行0-1；并行0-n）
     */
    private int fillCount;
    /**
     * 当广告源请求超时后，广告源的真实返回结果。
     *      示例：当广告源请求超时后，result_code 赋值为超时， result_info赋值为真实结果
     *      联盟异常的code码
     */
    private String resultInfo;
    /**
     * 广告offer ID
     *      预留，现在没有
     */
    private String adId;
    /**
     *广告源（offer）在策略中的优先级。（行数）
     *      接口文档中requestOrder字段
     */
    private String priorityS;
    /**
     * 广告源（offer）在策略的同一优先级中的权重。（同一行中的列数） 预留字段
     *      预留，并行请求用到
     */
    private long weightL;
    /**
     * 原生广告标题。
     */
    private String headLine;
    /**
     * 原生广告描述。
     */
    private String summary;
    /**
     * 原生广告的icon链接。
     */
    private String iconUrl;
    /**
     * 原生广告的大图链接。
     */
    private String bannerUrl;

    public StatisticBaseProperties(String primaryId,String sessionId) {
        this.sessionId = sessionId;
        this.primaryId = primaryId;

        //广告位的位置编号，用于向广告系统请求广告位策略。
        this.adPosId = "";
        //广告位对应策略编号，每次策略调整均会改变，全库表唯一。
        this.strategyId = "";
        //midas后台和midas sdk业务code
        this.configResultCode = "";
        //通讯协议的code
        this.resultCode = "";
        //实际请求广告联盟的次数
        this.unitRequestNum = 0;
        //广告的单元ID。简称PID或plID
        this.placementId = "";
        //穿山甲：chuanshanjia；优量汇：youlianghui
        this.sourceId = "";
        //特定广告类型才有多个如信息流广告，其他默认为1
        this.sourceRequestNum = 1;
        //只有开屏能设置超时时间，其他没有
        this.sourceTimeOut = 0;
        //类型用王通定义的类型
        this.style = "";
        //不确定 ：广告主？目前穿山甲部分广告有getSource
        this.advertiser = "";
        //实际拿到广告的个数。串行0-1；并行0-n
        this.fillCount = 0;
        //联盟异常的code码
        this.resultInfo = "";
        //接口文档中requestOrder字段
        this.priorityS = "";
        //原生广告标题。
        this.headLine = "";
        //原生广告描述。
        this.summary = "";
        //原生广告的icon链接。
        this.iconUrl = "";
        //原生广告的大图链接。
        this.bannerUrl = "";

        /**
         * 以下为默认值，外面也可以通过set方法设置更改
         */
        //直接获取的BuildConfig里面的版本号
        this.sdkVersion = BuildConfig.VERSION_NAME;
        //自己的聚合：midas,后期用到其他的在定义
        this.mediationId = "midas";
        //广告位的位置信息。 预留，服务端暂时没有返回，产品那边确认可以通过adPosId后台查询
        this.unitId = "";
        //预留，暂时用3
        this.strategyType = "3";
        //预留，暂时用0
        this.unitRequestType = 0;
        //预留，暂时用5s
        this.unitBestWaiting = 5;
        //预留，暂时没有
        this.unitPrepareIcon = false;
        //预留，暂时没有
        this.unitPrepareBanner = false;
        //预留，现在没有
        this.adId = "";
        //预留，现在没有
        this.bidPrice = "";
        //预留，现在没有
        this.chargePrice = "";
        //预留，并行请求用到
        this.weightL = 0;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getAdPosId() {
        return adPosId;
    }

    public void setAdPosId(String adPosId) {
        this.adPosId = adPosId;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public String getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(String strategyType) {
        this.strategyType = strategyType;
    }

    public String getConfigResultCode() {
        return configResultCode;
    }

    public void setConfigResultCode(String configResultCode) {
        this.configResultCode = configResultCode;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public int getUnitRequestNum() {
        return unitRequestNum;
    }

    public void setUnitRequestNum(int unitRequestNum) {
        this.unitRequestNum = unitRequestNum;
    }

    public int getUnitRequestType() {
        return unitRequestType;
    }

    public void setUnitRequestType(int unitRequestType) {
        this.unitRequestType = unitRequestType;
    }

    public int getUnitBestWaiting() {
        return unitBestWaiting;
    }

    public void setUnitBestWaiting(int unitBestWaiting) {
        this.unitBestWaiting = unitBestWaiting;
    }

    public boolean isUnitPrepareIcon() {
        return unitPrepareIcon;
    }

    public void setUnitPrepareIcon(boolean unitPrepareIcon) {
        this.unitPrepareIcon = unitPrepareIcon;
    }

    public boolean isUnitPrepareBanner() {
        return unitPrepareBanner;
    }

    public void setUnitPrepareBanner(boolean unitPrepareBanner) {
        this.unitPrepareBanner = unitPrepareBanner;
    }

    public String getPlacementId() {
        return placementId;
    }

    public void setPlacementId(String placementId) {
        this.placementId = placementId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public int getSourceRequestNum() {
        return sourceRequestNum;
    }

    public void setSourceRequestNum(int sourceRequestNum) {
        this.sourceRequestNum = sourceRequestNum;
    }

    public int getSourceTimeOut() {
        return sourceTimeOut;
    }

    public void setSourceTimeOut(int sourceTimeOut) {
        this.sourceTimeOut = sourceTimeOut;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getMediationId() {
        return mediationId;
    }

    public void setMediationId(String mediationId) {
        this.mediationId = mediationId;
    }

    public String getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getChargePrice() {
        return chargePrice;
    }

    public void setChargePrice(String chargePrice) {
        this.chargePrice = chargePrice;
    }

    public int getFillCount() {
        return fillCount;
    }

    public void setFillCount(int fillCount) {
        this.fillCount = fillCount;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getPriorityS() {
        return priorityS;
    }

    public void setPriorityS(String priorityS) {
        this.priorityS = priorityS;
    }

    public long getWeightL() {
        return weightL;
    }

    public void setWeightL(long weightL) {
        this.weightL = weightL;
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

}
