package com.comm.jksdk.cache;

import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.comm.jksdk.utils.CollectionUtils;

import java.util.Map;

/**
 * @ProjectName: GeekAdSdk
 * @Package: com.comm.jksdk.cache
 * @ClassName: CacheAd
 * @Description: java类作用描述
 * @Author: fanhailong
 * @CreateDate: 2019/11/30 17:33
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/30 17:33
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class CacheAd {
    public static Map<String, TTFeedAd> adMap = CollectionUtils.createMap();

    public static void setAd(String position, TTFeedAd ttFeedAd){
        adMap.put(position, ttFeedAd);
    }

    public static TTFeedAd getAd(String position){
        return adMap.get(position);
    }
}
