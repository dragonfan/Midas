package com.jk.adsdkdemo.testhttp;

/**
 * 获取服务端时间戳
 * （用户启动APP时获取一次服务端时间戳后，客户端维护一套客户端与服务端同步时间，
 *  不要实时获取服务端时间，如果接口提示客户端时间戳异常后可以重新同步服务端时间）
 *  测试接口：http://123.59.142.180/newskey/ts
 * @author xiangzhenbiao
 * @since 2019/5/5 17:28
 */
public class TimeResponse {

    /**
     * 结果（精确到毫秒）：{"ts": 1516952738651}
     */
    private Long ts;

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }
}
