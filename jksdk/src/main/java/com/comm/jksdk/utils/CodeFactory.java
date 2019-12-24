package com.comm.jksdk.utils;

import android.text.TextUtils;

import java.util.Map;

/**
 * @ProjectName: MidasAdSdk
 * @Package: com.comm.jksdk.utils
 * @ClassName: CodeFactory
 * @Description: 返回码定义类
 * @Author: fanhailong
 * @CreateDate: 2019/11/13 19:23
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/13 19:23
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class CodeFactory {
    /**
     * 网络未连接
     */
    public final static int HTTP_NET_WORK_DISCONNECT = 100001;
    /**
     * 通信数据解析异常
     */
    public final static int HTTP_PARSE_EXCEPTION = 100002;
    /**
     * 通信协议IO异常
     */
    public final static int HTTP_IO_EXCEPTION = 100003;
    /**
     * HTTP通信异常
     */
    public final static int HTTP_EXCEPTION = 100004;
    /**
     * 配置数据为空
     */
    public final static int CONFIG_DATA_EMPTY = 100005;

    /**
     * 配置解析异常
     */
    public final static int CONFIG_PARSE_EXCEPTION = 100006;

    public final static int SUCCESS = 0;
    public final static int UNKNOWN = 900;

    public final static int LOCAL_INFO_EMPTY = 101;

    private final static Map<Integer, String> map = CollectionUtils.createMap();

    static {
        map.put(900, "未知错误");

        //请求广告失败码
        map.put(LOCAL_INFO_EMPTY, "本地配置信息为空");
        map.put(102, "未知错误");
        map.put(103, "未知错误");
        map.put(104, "未知错误");

        map.put(HTTP_NET_WORK_DISCONNECT, "网络未连接");
        map.put(HTTP_PARSE_EXCEPTION, "通信数据解析异常");
        map.put(HTTP_IO_EXCEPTION, "通信协议IO异常");
        map.put(HTTP_EXCEPTION, "HTTP通信异常");
        map.put(CONFIG_DATA_EMPTY, "配置数据为空");
        map.put(CONFIG_PARSE_EXCEPTION, "配置解析异常");

    }

    public static String getError(int code){
        String msg = map.get(code);
        if (TextUtils.isEmpty(msg)) {
            return "未知错误，错误码："+code;
        }
        return msg;
    }
}
