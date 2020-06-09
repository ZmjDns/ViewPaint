package com.zmj.viewpaint.lesson13_multi_touch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
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
class MultiTouchView1(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatarBit(resources, dp2px(200f))

    private var downX: Float = 0f
    private var downY: Float = 0f
    private var offSetX = 0f
    private var offStY = 0f
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var trackingPointerId: Int = 0

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                trackingPointerId = event.getPointerId(0)
                downX = event.x
                downY = event.y
                originalOffsetX = offSetX
                originalOffsetY = offStY
            }
            MotionEvent.ACTION_MOVE -> {
                val index = event.findPointerIndex(trackingPointerId)
                offSetX = originalOffsetX + event.getX(index) - downX
                offStY = originalOffsetY + event.getY(index) - downY
                invalidate()
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                val actionIndex = event.actionIndex
                trackingPointerId = event.getPointerId(actionIndex)
                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)
                originalOffsetX = offSetX
                originalOffsetY = offStY
            }
            MotionEvent.ACTION_POINTER_UP -> {
                val actioIndex = event.actionIndex
                val pointId = event.getPointerId(actioIndex)
                if (pointId == trackingPointerId){
                    val newIndex: Int
                    if (actioIndex == event.pointerCount - 1){
                        newIndex = event.pointerCount - 2
                    }else{
                        newIndex = event.pointerCount - 1
                    }
                    trackingPointerId = event.getPointerId(newIndex)
                    downX = event.getX(actioIndex)
                    downY = event.getY(actioIndex)
                    originalOffsetX = downX
                    originalOffsetY = downY
                }
            }
        }
        return true
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas!!)
        canvas.drawBitmap(bitmap,offSetX,offStY,paint)
    }
}