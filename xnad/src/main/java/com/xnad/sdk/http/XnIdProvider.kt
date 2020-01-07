package com.xnad.sdk.http

import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import com.xiaoniu.statistic.NiuDataAPI
import com.xnad.sdk.MidasAdSdk
import com.xnad.sdk.config.Constants
import com.xnad.sdk.config.ErrorCode
import com.xnad.sdk.http.protocol.Protocal
import com.xnad.sdk.utils.AppUtils
import com.xnad.sdk.utils.MacUtils
import com.xnad.sdk.utils.SpUtils
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.io.Serializable
import java.util.concurrent.TimeUnit

/**
 * Desc:xnId提供者
 * <p>
 * Author: AnYaBo
 * Date: 2020/1/6
 * Copyright: Copyright (c) 2016-2022
 * Company: @小牛科技
 * Email:anyabo@xiaoniu.com
 * Update Comments:
 * @author 安亚波
 */
object XnIdProvider : Serializable {
    /**
     * 激活后获取xnId的延长时间 5s
     */
    private const val ACTIVE_OBTAIN_XN_ID_DELAY_TIME = 5000
    /**
     * 请求xnId失败轮训间隔
     */
    private const val FAILED_REQUEST_XN_ID_DELAY_TIME = 2000
    private const val MAX_REQUEST_COUNT = 3
    var mRequestCount: Int = 0
    private var mLocalXnId : String = ""
    private var mLocalUserMark : Boolean = false
    private val mHandler = Handler(Looper.getMainLooper())

    private var mOkHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

    private fun readResolve(): Any {
        return XnIdProvider
    }

    /**
     * 激活5秒后请求xnId接口
     */
    fun requestDelayFiveSecondAfterActive() {
        Log.e("requestXnId", "requestDelayFiveSecondAfterActive")
        mHandler.postDelayed({
            requestXnId(true) }, ACTIVE_OBTAIN_XN_ID_DELAY_TIME.toLong())
    }

    /**
     * 保存xnId到本地
     */
    fun saveXnIdToLocal(xnId: String) {
        if (!TextUtils.isEmpty(xnId)) {
            SpUtils.putString(Constants.SpUtils.XN_ID, xnId)
        }
    }

    /**
     * 从本地缓存或内存缓存中取xnId
     */
    fun getXnIdFromLocal(): String {
        if (TextUtils.isEmpty(mLocalXnId)){
            mLocalXnId = SpUtils.getString(Constants.SpUtils.XN_ID, "")
        }
        return mLocalXnId
    }


    /**
     * 标识用户为老用户
     */
    fun markUserWithOldUser() {
        SpUtils.putBoolean(Constants.SpUtils.BIG_DATA_MARK_OLD_USER, true)
    }

    /**
     * 从本地缓存或内存缓存获取用户标识,是否用户标识为老用户
     */
    fun isUserMarkToOld(): Boolean {
        if (!mLocalUserMark){
            mLocalUserMark = SpUtils.getBoolean(Constants.SpUtils.BIG_DATA_MARK_OLD_USER, false);
        }
        return mLocalUserMark
    }

    /**
     * 请求xnId
     *  @param isNeedLoop 是否轮训请求
     */
    fun requestXnId(isNeedLoop: Boolean) {
        try {
            Log.e("requestXnId", "requestXnId$isNeedLoop")
            if (isNeedLoop && mRequestCount >= MAX_REQUEST_COUNT){
                return
            }
            Log.e("requestXnId", "requestXnId$mRequestCount")
            val requestJson = JSONObject()
            val oaId = if (!TextUtils.isEmpty(NiuDataAPI.getOaid())) NiuDataAPI.getOaid() else ""
            requestJson.put("imei", AppUtils.getIMEI())
            requestJson.put("mac", MacUtils.getMacFromHardware(AppUtils.getContext()))
            requestJson.put("oaid", oaId)
            requestJson.put("android_id", AppUtils.getAndroidID())
            val formBody = FormBody.Builder()
                    .add("data", requestJson.toString())
                    .build()
            val request = Request.Builder()
                    .url(getBigDataBaseUrl() + "pk/id")
                    .post(formBody)
                    .build()
            val call = mOkHttpClient.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    if (isNeedLoop && mRequestCount < MAX_REQUEST_COUNT) {
                        mRequestCount++
                        mHandler.postDelayed({
                            requestXnId(true) },
                                FAILED_REQUEST_XN_ID_DELAY_TIME.toLong())
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    try {
                        if (response.code() == ErrorCode.HTTP_RESPONSE_SUCCESS_CODE.errorCode) {
                            try {
                                val jsonStr = response.body()!!.string()
                                Log.e("onResponse", "jsonStr : $jsonStr")

                                val jsonObject = JSONObject(jsonStr)
                                val code = jsonObject.getInt("code")
                                if (code == 0) {
                                    val xnId = jsonObject.getString("xnid")
                                    if (!TextUtils.isEmpty(xnId)) {
                                        saveXnIdToLocal(xnId)
                                    }
                                }else{
                                    onFailure(call, IOException())
                                }
                                //非轮训模式下，并且未获取到xnId
                                if (!isNeedLoop && TextUtils.isEmpty(getXnIdFromLocal())){
                                    //标识用户为老用户
                                    markUserWithOldUser()
                                }
                            } catch (e: Exception) {
                                onFailure(call, IOException())
                            }
                        }
                    } catch (e: Exception) {
                        onFailure(call, IOException())
                    }
                }
            })
        } catch (e: Exception) {
            Log.e("onResponse", "Exception : " + e.message)
        }
    }

    /**
     * 获取大数据域名地址
     *  业务端控制使用测试环境还是生产环境
     */
    private fun getBigDataBaseUrl(): String {
        return if (MidasAdSdk.isFormal()) {
            Protocal.XN_ID_SOURCE_HOST_PRODUCT
        } else {
            Protocal.XN_ID_SOURCE_HOST_TEST
        }
    }


}