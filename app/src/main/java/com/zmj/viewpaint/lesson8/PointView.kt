package com.zmj.viewpaint.lesson8

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.View
import com.zmj.viewpaint.common.dp2px

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/11
 * Description :
 */
class PointView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var point = Point(0,0)

    fun getPoint():Point{
        return point
    }

    fun setPoint(point: Point){
        this.point = point
        invalidate()
    }
    init {
        paint.style = Paint.Style.FILL
        paint.strokeWidth = dp2px(15f)
        paint.strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawPoint(point.x.toFloat(),point.y.toFloat(),paint)
    }
}