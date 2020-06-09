package com.zmj.viewpaint.lesson13_multi_touch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import com.zmj.viewpaint.common.dp2px

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/6/9
 * Description :互不干扰
 */
class MultiTouchView3(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = dp2px(4f)
        paint.color = Color.RED
        paint.strokeJoin = Paint.Join.ROUND
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when(event!!.actionMasked){
            ACTION_DOWN -> {
                path.reset()
                path.moveTo(event.getX(),event.y)
            }
            ACTION_MOVE -> {
                path.lineTo(event.x,event.y)
                invalidate()
            }
            ACTION_UP -> {
                path.reset()
                invalidate()
            }
        }

        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas!!)

        canvas.drawPath(path,paint)

    }

}