package com.zmj.viewpaint.lesson15_drag_nestedScroll.dragview

import android.content.Context
import android.icu.util.MeasureUnit
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.DragStartHelper
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/6/18
 * Description :ViewDragHelper
 */
class DragHelperGridView(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private val COLUMS = 2
    private val ROWS = 3

    private val dragHelper = ViewDragHelper.create(this,DragCallBack())

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWith = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        val childWidth = specWith / COLUMS
        val childHeight = specHeight / ROWS

        measureChildren(MeasureSpec.makeMeasureSpec(childWidth,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(childHeight,MeasureSpec.EXACTLY))

        setMeasuredDimension(specWith,specHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        var childTop = 0
        val childWidth = measuredWidth/COLUMS
        val childHeight = measuredHeight/ROWS
        for (index in 0 until childCount){
            val child = getChildAt(index)
            childLeft = (index % 2) * childWidth
            childTop = (index / 2) * childHeight

            child.layout(childLeft,childTop,childLeft + childWidth,childTop + childHeight)
        }
    }

    override fun onInterceptHoverEvent(event: MotionEvent?): Boolean {
        return dragHelper.shouldInterceptTouchEvent(event!!)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        dragHelper.processTouchEvent(event!!)
        return true
    }

    //会在View的OnDraw方法中不断被调用，替代了Runnable接口功能
    override fun computeScroll() {
        if (dragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }


    //拖拽回调
    inner class DragCallBack: ViewDragHelper.Callback() {

        var capturedLeft = 0
        var capturedTop = 0

        //必须实现的三个方法
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true     //拖拽成功时(即View跟着手指在动)返回true
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return left     //返回被推拽view的left（距离左边距的距离）
        }
        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top      //返回被推拽view的top（距离边距边距的距离）
        }

        override fun onViewDragStateChanged(state: Int) {
            if (state == ViewDragHelper.STATE_IDLE){
                val capturedView = dragHelper.capturedView
                if (capturedView != null){
                    capturedView.elevation = capturedView.elevation - 1
                }
            }
        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            capturedChild.elevation = elevation + 1
            //记录拖拽之前的位置，在松手以后，将view移动至原来的位置
            capturedLeft = capturedChild.left
            capturedTop = capturedChild.top
            Log.i("DragHelperGridView","capturedLeft: $capturedLeft    capturedTop: $capturedTop")
        }

        //移动过程中的变化
        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {


        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            //Log.i("DragHelperGridView","capturedLeft: $capturedLeft    capturedTop: $capturedTop")
            dragHelper.settleCapturedViewAt(capturedLeft,capturedTop)
            postInvalidateOnAnimation()
        }
    }
}