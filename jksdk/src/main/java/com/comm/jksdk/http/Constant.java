package com.comm.jksdk.http;

/**
 * @author liupengbing
 * @date 2019/9/21
 */
public class Constant {


    public static final String CHANNEL_TEST = "wt_test";
    public static final String CHANNEL_RELEASE = "wt_guanwang";


    /**
     * 一个项目只能上报两次IMEI，第一次上报为空，第二次上报为真是IMEI
     * 首次上报IMEI
     */
    public static final String FIRST_REPORT_IMEI = "first_report_imei";
    /**
     * 第二次上报IMEI
     */
    public static final String AGAIN_REPORT_IMEI = "again_report_imei";

}
