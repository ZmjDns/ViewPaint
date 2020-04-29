package com.zmj.viewpaint.lesson6

import android.content.Context
import android.graphics.*
import android.hardware.camera2.params.MeteringRectangle
import android.util.AttributeSet
import android.view.View
import com.zmj.viewpaint.common.dp2px
import java.lang.Math.*
import java.nio.file.PathMatcher

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/4/28
 * Description :
 */
class DashBoard: View {
    private val angle = 120f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val radius = dp2px(150f)
    private val needle = dp2px(140f)

    private val dash = Path()
    private var effect: PathDashPathEffect

    constructor(context: Context):super(context)
    constructor(context: Context,attrs:AttributeSet): super(context, attrs)
    constructor(context: Context,attrs: AttributeSet,selfDef:Int):super(context, attrs,selfDef)

    //在view形状改变的时候调用，避免在onDraw中增加运算
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = dp2px(2f)
        dash.addRect(0f,0f, dp2px(2f), dp2px(10f),Path.Direction.CW)
        val arc = Path()
        arc.addArc(width/2 - radius,height/2 -radius,width/2 + radius,height/2 +radius,90 + angle/2,360f-angle)
        val pathMeasure = PathMeasure(arc,false)

        effect = PathDashPathEffect(dash,(pathMeasure.length - dp2px(2f)) / 20,0f,PathDashPathEffect.Style.ROTATE)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)



        canvas?.drawArc(width/2 - radius,height/2 -radius,width/2 + radius,height/2 +radius,90 + angle/2,360f-angle,false,paint)


        //画刻度  把要画的线转换成刻度
        paint.pathEffect = effect
        canvas?.drawArc(width/2 - radius,height/2 -radius,width/2 + radius,height/2 +radius,90 + angle/2,360f-angle,false,paint)

        paint.pathEffect = null

        //画指针
        val marker = getAngle(5)
        canvas?.drawLine(width/2f,height/2f,
            (kotlin.math.cos(toRadians(marker.toDouble())) * needle  + width/2).toFloat(),
            (kotlin.math.sin(toRadians(marker.toDouble())) * needle + height/2).toFloat(),paint)
    }

    fun getAngle(marker: Int): Float{
        return 90 + angle/2 + (360-angle)/20 * marker
    }


}