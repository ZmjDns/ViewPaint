package com.zmj.viewpaint.lesson11

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_UP
import android.view.View

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/20
 * Description :
 */
class TouchView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //event?.actionMasked支持多点触控
        //。。。action老版的不支持多点触控
        if (event?.actionMasked == MotionEvent.ACTION_UP){
            performClick()
        }
        //只有在第一次的MotionEvent.ACTION_DOWN事件下返回true宣布主权有效
        return true//消费事件
    }
}