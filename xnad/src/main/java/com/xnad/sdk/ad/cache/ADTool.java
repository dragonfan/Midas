package com.xnad.sdk.ad.cache;

import android.app.Activity;
import android.text.TextUtils;

import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.listener.AdBasicListener;
import com.xnad.sdk.ad.outlistener.AdInteractionListener;
import com.xnad.sdk.config.Constants;
import com.xnad.sdk.utils.ListenerUtils;

import java.util.HashMap;

/**
 * Desc:
 * <p>
 * Author: ZhouTao
 * Date: 2020/1/2
 * Copyright: Copyright (c) 2016-2020
 * Company: @小牛科技
 * Email:zhoutao@xiaoniu.com
 * Update Comments:
 *
 * @author zhoutao
 */
public class ADTool {

    HashMap<String, HashMap<String, AdContainerWrapper>> mCache = new HashMap<>();
    private static final ADTool ourInstance = new ADTool();

    public static ADTool getInstance() {
        return ourInstance;
    }

    private ADTool() {
    }


    /**
     * 根据广告位ID获取对应的缓存广告集合
     *
     * @param positionId 广告位ID
     * @return
     */
    public HashMap<String, AdContainerWrapper> getAds(String positionId) {
        return mCache.get(positionId);
    }


    /**
     * 根据广告位ID 和 广告ID 获取对应的缓存广告
     *
     * @param positionId 广告位ID
     * @param adId       广告ID
     * @return
     */
    public AdContainerWrapper getAd(String positionId, String adId) {
        HashMap<String, AdContainerWrapper> ads = mCache.get(positionId);
        if (ads != null) {
            return ads.get(adId);
        } else {
            return null;
        }
    }

    /**
     * 缓存广告
     *
     * @param listener
     * @param info
     */
    public void cacheAd(Object listener, AdInfo info) {
        AdContainerWrapper adContainerWrapper = new AdContainerWrapper();
        //设置开始缓存时间
        adContainerWrapper.setReceiveTime();
        //添加缓存广告, 记录广告渠道和广告类型
        adContainerWrapper.addView(info, info.getMidasAd().getAdSource(), info.getAdType());
        adContainerWrapper.addListener(listener);
        String positionId = info.getPosition();
        assertPositionId(positionId);

        //添加到集合缓存
        HashMap<String, AdContainerWrapper> ads = mCache.get(info.getPosition());
        ads.put(info.getMidasAd().getAdId(), adContainerWrapper);
        mCache.put(positionId, ads);
    }

    /**
     * 绑定监听
     * @param activity 上下文
     * @param adContainerWrapper 广告缓存对象
     * @param adListener  对外的监听
     */
    public void bindListener( Activity activity,AdContainerWrapper adContainerWrapper, AdBasicListener adListener) {
        ListenerUtils.setListenerAndShow(activity,adContainerWrapper,adListener);
    }


    private void assertPositionId(String position) {
        if (!mCache.containsKey(position)) {
            mCache.put(position, new HashMap<>(16));
        }
    }


}
