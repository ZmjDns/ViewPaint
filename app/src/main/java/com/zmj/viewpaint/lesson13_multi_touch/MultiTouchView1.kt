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
 * Description :
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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                originalOffsetX = offSetX
                originalOffsetY = offStY
            }
            MotionEvent.ACTION_MOVE -> {
                offSetX = originalOffsetX + event.x - downX
                offStY = originalOffsetY + event.y - downY
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