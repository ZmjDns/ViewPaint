package com.zmj.viewpaint

import android.app.Application
import android.content.Context

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/6/16
 * Description :
 */
class App: Application() {

    companion object{
        private lateinit var context: Context

        fun getApplicationContext(): Context{
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}