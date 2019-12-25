package com.comm.jksdk.utils;

import android.content.Context;
import android.text.TextUtils;

import com.comm.jksdk.ad.entity.AdInfo;
import com.comm.jksdk.bean.StatisticBaseProperties;
import com.comm.jksdk.bean.StatisticEvent;
import com.comm.jksdk.http.utils.ApiManage;
import com.comm.jksdk.http.utils.AppEnvironment;
import com.xiaoniu.statistic.Configuration;
import com.xiaoniu.statistic.EventType;
import com.xiaoniu.statistic.NiuDataAPI;

import org.json.JSONObject;

import java.util.Iterator;

/**
 * Desc:牛数埋点工具类
 * <p>
 * Author: AnYaBo
 * Date: 2019/12/23
 * Copyright: Copyright (c) 2016-2022
 * Company: @小牛科技
 * Email:anyabo@xiaoniu.com
 * Update Comments:
 *      暂时用的aar形式接入的牛数，后续如更新，需要更换
 * @author anyabo
 */
public class StatisticUtils {

    /**
     * 初始化牛数
     * @param context   上下文
     * @param channel   渠道
     * @param productId 牛数业务线ID
     */
    public static void init(Context context,String channel,String productId){
        Configuration configuration = new Configuration();
        //设置渠道
        configuration.channel(channel);
        //牛数服务器地址
        configuration.serverUrl(ApiManage.getNiuDataURL());
        //是否开启日志 非成产环境开启，生产环境关闭
        boolean isProduct = AppEnvironment.getServerApiEnvironment()
                == AppEnvironment.ServerEnvironment.Product;
        if (isProduct){
            configuration.logClose();
        }else {
            configuration.logOpen();
        }
        if (!TextUtils.isEmpty(productId)){
            configuration.setProductId(productId);
        }
        //初始化
        NiuDataAPI.init(context,configuration);
    }

    /**
     * 设置牛数imei
     * @param imei  imei设备号
     *       Comments:
     *              只能设置两次，业务层通过代码判断，
     *              第一次启动设置一次，获取到设备权限后设置一次
     */
    public static void setImei(String imei){
        NiuDataAPI.setIMEI(imei);
    }

    /**
     * 广告埋点事件
     * @param statisticEvent 埋点事件
     * @param baseProperties    公共属性
     */
    public static void trackCustomEvent(StatisticEvent statisticEvent,
                                        StatisticBaseProperties baseProperties){
        if (statisticEvent != null && baseProperties != null){
            JSONObject j = new JSONObject();
            addBasePropertiesToJson(baseProperties, j);
            if (statisticEvent.getExtension() != null){
                mergeJsonObject(statisticEvent.getExtension(), j);
            }
            NiuDataAPI.trackCustomEvent(EventType.AD_PROCESS,
                    statisticEvent.getEventCode(), statisticEvent.getEventName(), j);
        }
    }

    /**
     * 合并JSONObject
     * @param source    源json对象
     * @param dest   最终要合并的对象
     */
    private static void mergeJsonObject(JSONObject source, JSONObject dest) {
        try {
            Iterator<String> superPropertiesIterator = source.keys();
            while (superPropertiesIterator.hasNext()) {
                String key = superPropertiesIterator.next();
                Object value = source.get(key);
                dest.put(key, value);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 添加公共属性
     * @param baseProperties    公共属性
     * @param j JsonObject
     */
    private static void addBasePropertiesToJson(StatisticBaseProperties baseProperties,JSONObject j){
        try{
            j.put("session_id", baseProperties.getSessionId());
            j.put("primaryid", baseProperties.getPrimaryId());
            j.put("unit_id",baseProperties.getUnitId());
            j.put("adpos_id",baseProperties.getAdPosId());
            j.put("strategy_id",baseProperties.getStrategyId());
            j.put("strategy_type",baseProperties.getStrategyType());
            j.put("config_result_code",baseProperties.getConfigResultCode());
            j.put("result_code",baseProperties.getResultCode());
            j.put("sdk_version",baseProperties.getSdkVersion());
            j.put("unit_request_num",baseProperties.getUnitRequestNum());
            j.put("unit_request_type",baseProperties.getUnitRequestType());
            j.put("unit_best_waiting",baseProperties.getUnitBestWaiting());
            j.put("unit_prepare_icon",baseProperties.isUnitPrepareIcon());
            j.put("unit_prepare_banner",baseProperties.isUnitPrepareBanner());
            j.put("placement_id",baseProperties.getPlacementId());
            j.put("source_id",baseProperties.getSourceId());
            j.put("source_request_num",baseProperties.getSourceRequestNum());
            j.put("source_timeout",baseProperties.getSourceTimeOut());
            j.put("style",baseProperties.getStyle());
            j.put("mediation_id",baseProperties.getMediationId());
            j.put("advertiser",baseProperties.getAdvertiser());
            j.put("pkg",baseProperties.getPkg());
            j.put("bid_price",baseProperties.getBidPrice());
            j.put("charge_price",baseProperties.getChargePrice());
            j.put("fill_count",baseProperties.getFillCount());
            j.put("result_info",baseProperties.getResultInfo());
            j.put("ad_id",baseProperties.getAdId());
            j.put("priority",baseProperties.getPriorityS());
            j.put("weight",baseProperties.getWeightL());
            j.put("headline",baseProperties.getHeadLine());
            j.put("summary",baseProperties.getSummary());
            j.put("icon_url",baseProperties.getIconUrl());
            j.put("banner_url",baseProperties.getBannerUrl());
        }catch (Exception e){
        }
    }

    /**
     *单埋点开始位
     * @param adInfo    广告信息实体类
     * @param beginTime    开始时间
     */
    public static void singleStatisticBegin(AdInfo adInfo,long beginTime){
        //调试代码
        String primaryId = "test";
        String sessionId = primaryId + beginTime;
        if (adInfo != null){
            StatisticBaseProperties baseProperties
                    = new StatisticBaseProperties(primaryId, sessionId);
            adInfo.setStatisticBaseProperties(baseProperties);
        }
    }

    /**
     * 广告策略配置请求埋点
     * @param adInfo    广告信息
     * @param adPosId   广告位的位置编号，用于向广告系统请求广告位策略。
     * @param strategyId   广告位对应策略编号，每次策略调整均会改变，全库表唯一。
     * @param resultCode    midas后台和midas sdk业务code
     * @param configResultCode  通讯协议的code
     * @param beginTime 请求开始的时间
     */
    public static void strategyConfigurationRequest(AdInfo adInfo,String adPosId,
                                                    String strategyId,String resultCode,
                                                    String configResultCode,
                                                    long beginTime) {
        long take = System.currentTimeMillis() - beginTime;
        StatisticBaseProperties baseProperties = adInfo.getStatisticBaseProperties();
        if (baseProperties != null){
            if (!TextUtils.isEmpty(adPosId)){
                baseProperties.setAdPosId(adPosId);
            }
            if (!TextUtils.isEmpty(resultCode)){
                baseProperties.setResultCode(resultCode);
            }
            if (!TextUtils.isEmpty(configResultCode)){
                baseProperties.setConfigResultCode(configResultCode);
            }
            if (TextUtils.isEmpty(strategyId)){
                baseProperties.setStrategyId(strategyId);
            }
            trackCustomEvent(StatisticEvent.MIDAS_CONFIG_REQUEST.put("take", take),
                    baseProperties);
        }
    }

    /**
     * 广告位请求事件埋点
     * @param adInfo    广告信息
     * @param beginTime 请求开始的时间
     */
    public static void advertisingPositionRequest(AdInfo adInfo, long beginTime) {
        long take = System.currentTimeMillis() - beginTime;
        StatisticBaseProperties baseProperties = adInfo.getStatisticBaseProperties();
        if (baseProperties != null){
            trackCustomEvent(StatisticEvent.MIDAS_UNIT_REQUEST.put("take", take),
                    baseProperties);
        }
    }

    /**
     * 广告源请求事件埋点
     * @param adInfo    广告信息
     * @param fillCount 填充广告个数（实际拿到广告的个数。串行0-1；并行0-n）
     * @param resultInfo    联盟异常的code码
     * @param beginTime 请求开始的时间
     */
    public static void advertisingSourceRequest(AdInfo adInfo,
                                                int fillCount, String resultInfo,
                                                long beginTime) {
        long take = System.currentTimeMillis() - beginTime;
        StatisticBaseProperties baseProperties = adInfo.getStatisticBaseProperties();
        if (baseProperties != null){
            baseProperties.setUnitRequestNum(baseProperties.getUnitRequestNum() + 1);
            if (fillCount != 0){
                baseProperties.setFillCount(fillCount);
            }
            if (adInfo.getMidasAd() != null){
                adInfo.getStatisticBaseProperties().setPlacementId(adInfo.getMidasAd().getAdId());
                adInfo.getStatisticBaseProperties().setSourceId(adInfo.getMidasAd().getAdSource());
                adInfo.getStatisticBaseProperties().setSourceTimeOut(adInfo.getMidasAd().getTimeOut());
                adInfo.getStatisticBaseProperties().setSourceRequestNum(adInfo.getMidasAd().getSourceRequestNum());
            }
            adInfo.getStatisticBaseProperties().setStyle(adInfo.getAdType());
            trackCustomEvent(StatisticEvent.MIDAS_SOURCE_REQUEST.put("take", take),
                    baseProperties);
        }
    }


    public static void advertisingOfferShow(AdInfo adInfo,long beginTime){
        long take = System.currentTimeMillis() - beginTime;
        StatisticBaseProperties baseProperties = adInfo.getStatisticBaseProperties();
        if (baseProperties != null){


            trackCustomEvent(StatisticEvent.MIDAS_IMPRESSION.put("take", take),
                    baseProperties);
        }
    }




}
