package com.zmj.viewpaint.lesson6

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.zmj.viewpaint.common.dp2px
import kotlin.math.cos
import kotlin.math.sin

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/4/29
 * Description :
 */
class PieChart(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rectF:RectF = RectF()

    private val angles = arrayOf(60f,100f,120f,80f)
    private val colors: Array<Int> = arrayOf(Color.parseColor("#2979FF"),Color.parseColor("#8879FF"),
        Color.parseColor("#2966FF"),Color.parseColor("#285944"))

    init {
        paint.style = Paint.Style.FILL
        paint.color = Color.RED
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        rectF.set(width/2 - dp2px(100f),height/2 - dp2px(100f),width/2 + dp2px(100f),height/2 + dp2px(100f))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var angle = 0f

        for (i in angles.indices){
            paint.color = colors[i]
            canvas?.save()
            if (i == 2){
                canvas?.translate( (cos(Math.toRadians((angle + angles[i]/2).toDouble())) * dp2px(10f)).toFloat(),(sin(Math.toRadians((angle + angles[i]/2).toDouble())) * dp2px(10f)).toFloat())
            }
            canvas?.drawArc(rectF,angle,angles[i],true,paint)
            canvas?.restore()
            angle += angles[i]
        }
    }

}