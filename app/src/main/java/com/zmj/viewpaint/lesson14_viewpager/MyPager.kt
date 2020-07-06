package com.zmj.viewpaint.lesson14_viewpager

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import kotlin.math.abs

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/6
 * Description :
 */
class MyPager(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private val velocityTracker = VelocityTracker.obtain()
    private var isScrolling: Boolean = false
    private var downX: Float = 0f
    private var downY: Float = 0f
    private var downScrollX = 0f
    private val viewConfiguration = ViewConfiguration.get(context)

    private var maxVelocity = viewConfiguration.scaledMaximumFlingVelocity.toFloat()
    private val minVelocity = viewConfiguration.scaledMinimumFlingVelocity.toFloat()

    private val overScroller = OverScroller(context)    //用于做滑动动画的


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec,heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        val childWidth = measuredWidth
        val childHeight = measuredHeight
        for (index in 0 until childCount){
            val childView = getChildAt(index)
            childLeft = childWidth * index
            childView.layout(childLeft,0,childLeft + childWidth,childHeight)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.actionMasked == MotionEvent.ACTION_DOWN){
            velocityTracker.clear()
        }
        velocityTracker.addMovement(ev)

        var result = false
        when(ev?.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                isScrolling = false
                downX = ev.x
                downY = ev.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = downX - ev.x
                if (abs(dx) > viewConfiguration.scaledPagingTouchSlop){//判断是否为快速滑动
                    isScrolling = true                                  //快速滑动
                    parent.requestDisallowInterceptTouchEvent(true)//通知父View不要拦截事件
                    result = true
                }
            }
        }
        return result
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.actionMasked == MotionEvent.ACTION_DOWN){
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)

        when(event?.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                var dx = (downX - event.x + downScrollX).toInt()
                Log.i("MyPager","downX: $downX    downY: $downY ")
                Log.i("MyPager","eventX: ${event.x}   downScrollX: $scrollX ")

                if (dx > width){
                    dx = width
                } else if (dx < 0){
                    dx = 0
                }

                scrollTo(dx,0)
            }
            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(1000,maxVelocity)
                val vx = velocityTracker.xVelocity
                val targetPager =
                    if(abs(vx) < minVelocity){  //没有达到快速滑动阈值，比较scrollX
                    if (scrollX < width/2){
                        2
                    }else{
                        1
                    }
                }else{                  //达到了滑动阈值，比较滑动的方向
                    if (vx < 0){1}else{0}
                }

                val scrollDistance = if (targetPager == 1){width - scrollX}else{-scrollX}

                overScroller.startScroll(scrollX,0,scrollDistance,0)
                postInvalidateOnAnimation()
            }
        }
        return true
    }

    override fun computeScroll() {
        if (overScroller.computeScrollOffset()){
            scrollTo(overScroller.currX,overScroller.currY)
            postInvalidateOnAnimation()
        }
    }
}