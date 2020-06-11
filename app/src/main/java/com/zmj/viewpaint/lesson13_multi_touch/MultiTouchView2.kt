package com.zmj.viewpaint.lesson13_multi_touch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.zmj.viewpaint.common.dp2px
import com.zmj.viewpaint.common.getAvatarBit

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/6/7
 * Description :多点触控事件接管（后来的接管触摸事件）
 * 触摸反馈
 * 1.接力型
 * 2.协助型
 * 3.互不干扰
 */
class MultiTouchView2(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatarBit(resources, dp2px(200f))

    private var downX: Float = 0f
    private var downY: Float = 0f
    private var offSetX = 0f
    private var offStY = 0f
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var sumX = 0f
        var sumY = 0f
        var pointCount = event!!.pointerCount
        val isPointUp = (event.actionMasked == MotionEvent.ACTION_POINTER_UP)
        for (i in 0 until pointCount){
            if (!(isPointUp && i == event.actionIndex)){
                sumX += event.getX(i)
                sumY += event.getY(i)
            }
        }
        if (isPointUp){
            pointCount -= 1
        }
        val focusX = sumX / pointCount
        val focusY = sumY / pointCount
        when(event.actionMasked){
            //多种情况合并时，只能用 “,”隔开，否则不能达到情况合并的效果
            MotionEvent.ACTION_DOWN ,
            MotionEvent.ACTION_POINTER_DOWN ,
            MotionEvent.ACTION_POINTER_UP -> {
                downX = focusX
                downY = focusY
                originalOffsetX = offSetX
                originalOffsetY = offStY
            }
            MotionEvent.ACTION_MOVE -> {
                offSetX = originalOffsetX + focusX - downX
                offStY = originalOffsetY + focusY - downY
                invalidate()
            }
        }
        return true
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas!!)
        canvas.drawBitmap(bitmap,offSetX,offStY,paint)
    }
}