package com.comm.jksdk.bean;

import java.util.List;

/**
 * @ProjectName: Midas
 * @Package: com.comm.jksdk.bean
 * @ClassName: MidasConfigBean
 * @Description: java类作用描述
 * @Author: fanhailong
 * @CreateDate: 2019/12/20 21:04
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/20 21:04
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class MidasConfigBean {


    /**
     * adstrategyid : 232323
     * adPostid : dd
     * adType : 0
     * renderType : 0
     * adStrategy : [{"adId":"65487887820","adUnion":"youlianhui0","requestOrder":"2","adsAppid":"1213130"},{"adId":"65487887821","adUnion":"youlianhui1","requestOrder":"1","adsAppid":"1213131"}]
     */

    private String adstrategyid;
    private String adPostid;
    private String adType;
    private String renderType;
    private List<AdStrategyBean> adStrategy;

    public String getAdstrategyid() {
        return adstrategyid;
    }

    public void setAdstrategyid(String adstrategyid) {
        this.adstrategyid = adstrategyid;
    }

    public String getAdPostid() {
        return adPostid;
    }

    public void setAdPostid(String adPostid) {
        this.adPostid = adPostid;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public String getRenderType() {
        return renderType;
    }

    public void setRenderType(String renderType) {
        this.renderType = renderType;
    }

    public List<AdStrategyBean> getAdStrategy() {
        return adStrategy;
    }

    public void setAdStrategy(List<AdStrategyBean> adStrategy) {
        this.adStrategy = adStrategy;
    }

    public static class AdStrategyBean {
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
    }
}
