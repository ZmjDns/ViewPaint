package com.zmj.viewpaint.lesson18_rxjava.net

import com.zmj.viewpaint.lesson18_rxjava.model.BaseResp
import com.zmj.viewpaint.lesson18_rxjava.model.Data
import com.zmj.viewpaint.lesson18_rxjava.model.DetailResp
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/8/3
 * Description :
 */
interface GongShangApi {

    //@Headers("X-Requested-With: XMLHttpRequest","Accept: application/json","User-Agent: Mozilla/5.0 (Linux; Android 9; MI 6 Build/PKQ1.190118.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/74.0.3729.136 Mobile")
    @Headers("X-Requested-With: XMLHttpRequest","Accept: application/json")
    @POST("gsxt/cn/gov/saic/web/controller/PrimaryInfoIndexAppController/search")
    fun getBaseInfo(@Body data:Data): Call<BaseResp>

    //@Headers("X-Requested-With: XMLHttpRequest","Accept: application/json","User-Agent: Mozilla/5.0 (Linux; Android 9; MI 6 Build/PKQ1.190118.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/74.0.3729.136 Mobile")
    @Headers("X-Requested-With: XMLHttpRequest","Accept: application/json")
    @GET("gsxt/{page}")//?nodeNum=120000&entType=1100&sourceType=A
    //corp-query-entprise-info-primaryinfoapp-entbaseInfo-165D30B11840F943D56CED6DFB42DF0BA684AA84AA845AFFDDD3C09932ECB58B268B558B200E208D9EC703A6B5EC64BAB4CB1579BDAE-1596441316131.html
    fun getDetailInfo(@Path("page") page: String,
                      @Query("nodeNum") nodeNum: Int,
                      @Query("entType") entType: Int,
                      @Query("sourceType")sourceType: String): Call<DetailResp>
}