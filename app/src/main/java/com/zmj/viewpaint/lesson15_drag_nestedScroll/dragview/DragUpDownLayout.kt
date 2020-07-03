package com.zmj.viewpaint.lesson15_drag_nestedScroll.dragview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.zmj.viewpaint.R
import kotlin.math.abs

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/3
 * Description :
 */
class DragUpDownLayout(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private lateinit var draggedView: View

    private var myDragListener  = MyDragListener()
    private val viewDragHelper by lazy { ViewDragHelper.create(this,myDragListener) }

    private val viewConfiguration = ViewConfiguration.get(context)

    override fun onFinishInflate() {
        super.onFinishInflate()

        draggedView = findViewById(R.id.draggedView)

    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return viewDragHelper.shouldInterceptTouchEvent(ev!!)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        viewDragHelper.processTouchEvent(event!!)
        return true
    }

    override fun computeScroll() {
        if (viewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    inner class MyDragListener: ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child == draggedView
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return 0
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            if (abs(yvel) > viewConfiguration.scaledMinimumFlingVelocity){      //filing
                if (yvel > 0){
                    viewDragHelper.settleCapturedViewAt(0,height - releasedChild.height)
                }else{
                    viewDragHelper.settleCapturedViewAt(0,0)
                }
            }else{
                if (releasedChild.top < height - releasedChild.bottom){//向下滑动的距离小于childView底部距离界面底部的距离
                    viewDragHelper.settleCapturedViewAt(0,0)
                }else{
                    viewDragHelper.settleCapturedViewAt(0,releasedChild.top)
                }
            }

            postInvalidateOnAnimation()
        }
    }
}