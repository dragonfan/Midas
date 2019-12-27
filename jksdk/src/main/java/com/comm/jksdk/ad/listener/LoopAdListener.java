package com.comm.jksdk.ad.listener;

import com.comm.jksdk.ad.entity.AdInfo;

/**
  *
  * @ProjectName:    ${PROJECT_NAME}
  * @Package:        ${PACKAGE_NAME}
  * @ClassName:      ${NAME}
  * @Description:     轮询请求接口（sdk内部用）
  * @Author:         fanhailong
  * @CreateDate:     ${DATE} ${TIME}
  * @UpdateUser:     更新者：
  * @UpdateDate:     ${DATE} ${TIME}
  * @UpdateRemark:   更新说明：
  * @Version:        1.0
 */
public interface LoopAdListener {


    /**
     * 优量汇广告失败  SDK中优量汇请求失败后请求穿山甲广告回掉接口 不向外提供
     * @param errorCode
     * @param errorMsg
     */
    void loopAdError(AdInfo adInfo, int errorCode, String errorMsg);

}
