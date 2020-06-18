package com.zmj.viewpaint.lesson15_drag_nestedScroll.dragview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.customview.widget.ViewDragHelper

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/6/18
 * Description :ViewDragHelper
 */
class DragHelperGridView(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private val dragHelper = ViewDragHelper.create(this,DragCallBack())

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

    override fun onInterceptHoverEvent(event: MotionEvent?): Boolean {
        return dragHelper.shouldInterceptTouchEvent(event!!)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        dragHelper.processTouchEvent(event!!)
        return true
    }






    //拖拽回调
    inner class DragCallBack: ViewDragHelper.Callback() {

        //必须实现的三个方法
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true     //拖拽成功时返回true
        }
        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return left     //返回被推拽view的left（距离左边距的距离）
        }
        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top      //返回被推拽view的top（距离边距边距的距离）
        }



    }




}