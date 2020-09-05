package com.zmj.viewpaint.lesson18_rxjava.net

import androidx.core.util.TimeUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/8/3
 * Description :
 */
object ServiceCreator {

    private const val BASE_URL = "https://app.gsxt.gov.cn/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(initOkHttp())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T>create(service: Class<T>): T = retrofit.create(service)

    inline fun <reified T> create(): T = create(T::class.java)

    private fun initOkHttp(): OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(60,TimeUnit.SECONDS)
            .readTimeout(60,TimeUnit.SECONDS)
            .writeTimeout(60,TimeUnit.SECONDS)
            .build()
    }

}