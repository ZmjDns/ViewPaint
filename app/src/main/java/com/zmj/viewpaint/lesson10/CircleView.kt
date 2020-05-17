package com.zmj.viewpaint.lesson10

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.zmj.viewpaint.common.dp2px

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/17
 * Description :
 */
class CircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val PADDING = dp2px(10f)
    private val RADIUS = dp2px(80f)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var width = ((PADDING + RADIUS) *2).toInt()
        var height = ((PADDING + RADIUS) *2).toInt()

        //修正
        width = resolveSize(width,widthMeasureSpec)
        height = resolveSize(height,heightMeasureSpec)

        setMeasuredDimension(width,height)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawColor(Color.RED)
        canvas.drawCircle(PADDING +RADIUS,PADDING + RADIUS,RADIUS,paint)
    }
}