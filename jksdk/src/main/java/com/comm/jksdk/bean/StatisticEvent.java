package com.comm.jksdk.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Desc:埋点事件
 * <p>
 * Author: AnYaBo
 * Date: 2019/12/24
 * Copyright: Copyright (c) 2016-2022
 * Company: @小牛科技
 * Email:anyabo@xiaoniu.com
 * Update Comments:
 *
 * @author anyabo
 */
public enum  StatisticEvent {

    MIDAS_CONFIG_REQUEST("MIDAS_CONFIG_REQUEST","广告策略请求事件"),
    MIDAS_UNIT_REQUEST("MIDAS_UNIT_REQUEST","广告位请求事件"),
    MIDAS_SOURCE_REQUEST("MIDAS_SOURCE_REQUEST","广告源请求事件"),
    MIDAS_IMAGE_LOAD("MIDAS_IMAGE_LOAD","广告offer下图事件"),
    MIDAS_IMPRESSION("MIDAS_IMPRESSION","广告offer展示事件"),
    MIDAS_CLICK("MIDAS_CLICK","广告offer点击事件"),
    MIDAS_REWARDED("MIDAS_REWARDED","激励视频广告激励事件"),
    MIDAS_REWARDED_CLOSE("MIDAS_REWARDED_CLOSE","激励视频广告关闭事件"),
    MIDAS_CLOSE("MIDAS_CLOSE","除激励视频外广告窗口关闭事件");

    private String eventCode;
    private String eventName;
    private JSONObject extension;

    StatisticEvent(String eventCode, String eventName) {
        this.eventCode = eventCode;
        this.eventName = eventName;
    }

    public String getEventCode() {
        return eventCode;
    }

    public JSONObject getExtension() {
        return extension;
    }

    public String getEventName() {
        return eventName;
    }

    public StatisticEvent setExtension(JSONObject extension) {
        if (extension != null) {
            this.extension = extension;
        }
        return this;
    }

    public StatisticEvent setExtension(String... extension) {
        if (extension != null && extension.length > 0) {
            JSONObject jObj = new JSONObject();
            for (int i = 0; i < extension.length; i++) {
                try {
                    String key = extension[i++];
                    String value = "";
                    if (i < extension.length) {
                        value = extension[i];
                    }
                    jObj.put(key, value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            this.extension = jObj;
        }
        return this;
    }

    public StatisticEvent put(String key, Object value) {
        if (extension == null) {
            extension = new JSONObject();
        }
        try {
            extension.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

}
