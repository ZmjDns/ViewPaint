package com.zmj.viewpaint.lesson14_viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/6/13
 * Description : 简单的ViewPager
 */
class TwoPager(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private var minVelocity: Float
    private var maxVelocity: Float
    private val overScroller = OverScroller(context)
    private var viewConfiguration: ViewConfiguration = ViewConfiguration.get(context)
    private val velocityTracker = VelocityTracker.obtain()//速度追踪器

    init {
        minVelocity = viewConfiguration.scaledMinimumFlingVelocity.toFloat()
        maxVelocity = viewConfiguration.scaledMaximumFlingVelocity.toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec,heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.actionMasked == MotionEvent.ACTION_DOWN){
            velocityTracker.clear()
        }
        velocityTracker.addMovement(ev)

        return super.onInterceptTouchEvent(ev)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }
}