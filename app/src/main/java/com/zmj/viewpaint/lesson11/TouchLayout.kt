package com.zmj.viewpaint.lesson11

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import kotlin.math.abs

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/21
 * Description :
 */
class TouchLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {



    //拦截、准备工作
    //重写onInterceptTouchEvent方法一般都要重写onTouchEvent
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val offsetY = ev!!.y//Y轴偏移量
        return abs(offsetY) > 300
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }









    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        TODO("Not yet implemented")
    }
}