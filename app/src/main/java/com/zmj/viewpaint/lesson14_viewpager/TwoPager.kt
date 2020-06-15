package com.zmj.viewpaint.lesson14_viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import kotlinx.android.synthetic.main.act_11.view.*
import kotlin.math.abs

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/6/13
 * Description : 简单的ViewPager
 */
class TwoPager(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private var downX: Float = 0f
    private var downY: Float = 0f
    private var downScrollX: Float = 0f
    private var scrolling: Boolean = false
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

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        var childTop = 0
        var childRight = width
        var childBottom = height
        for (i in 0 until childCount){
            val view = getChildAt(i)
            view.layout(childLeft,childTop,childRight,childBottom)
            childLeft += view.measuredWidth
            childRight += view.measuredWidth
        }
    }


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.actionMasked == MotionEvent.ACTION_DOWN){
            velocityTracker.clear()
        }
        velocityTracker.addMovement(ev)

        var result = false
        when(ev?.actionMasked){
          MotionEvent.ACTION_DOWN ->{
              scrolling = false
              downX = ev.x
              downY = ev.y
              downScrollX = scrollX.toFloat()
          }
            MotionEvent.ACTION_MOVE -> {
                val dx = ev.x - downX
                if (!scrolling){
                    if (abs(dx) > viewConfiguration.scaledPagingTouchSlop){
                        scrolling = true
                        parent.requestDisallowInterceptTouchEvent(true)
                        result = true
                    }
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
                var dx = downX - event.x + downScrollX
                if (dx > width){
                    dx = width.toFloat()
                }else if (dx < 0){
                    dx = 0f
                }
                scrollTo(dx.toInt(),0)
            }
            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(1000,maxVelocity)
                val vx = velocityTracker.xVelocity
                val scrollX = scrollX
                var targetPage: Int
                if (abs(vx) < minVelocity){
                    targetPage = if (scrollX > width /2){1}else{0}
                }else{
                    targetPage = if (vx > 0){1}else{0}
                }
                val scrollDistance = if (targetPage == 1){width - scrollX}else(-scrollX)
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