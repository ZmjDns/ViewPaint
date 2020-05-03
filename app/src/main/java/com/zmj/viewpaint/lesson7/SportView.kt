package com.zmj.viewpaint.lesson7

import android.content.Context
import android.graphics.*
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.zmj.viewpaint.common.dp2px

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/1
 * Description :
 */
class SportView(context: Context?, attrs: AttributeSet?) : View(context, attrs){

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val CIRCEL_COLOR = Color.GRAY
    private val RING_WIDTH = dp2px(2f)
    private val RADIUS = dp2px(100f)

    private val textBounds = Rect()
    private val fontMtrics = Paint.FontMetrics()

    init {
        paint.textSize = dp2px(40f)
        //paint.typeface = Typeface.createFromAsset(getContext().assets,"Quicksand-Regular.ttf")
        paint.textAlign = Paint.Align.CENTER
        paint.getFontMetrics(fontMtrics)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //绘制环
        paint.style = Paint.Style.STROKE
        paint.color = CIRCEL_COLOR
        paint.strokeWidth = RING_WIDTH
        canvas?.drawCircle(width/2f,height/2f,RADIUS,paint)

        //绘制进度条
        paint.color = Color.RED
        paint.strokeCap = Paint.Cap.ROUND
        canvas?.drawArc(width/2 - RADIUS,height/2 - RADIUS,width/2 + RADIUS,height/2 + RADIUS,-90f,220f,false,paint)

        //绘制文字
        paint.style = Paint.Style.FILL
        //paint.getTextBounds("nbja",0,"nbja".length,textBounds)
        //val offsetY = (textBounds.top + textBounds.bottom) /2

        val offsetY = (fontMtrics.ascent + fontMtrics.descent) / 2

        canvas?.drawText("nbja",width/2f,height/2f - offsetY,paint)

        paint.textAlign = Paint.Align.LEFT
        paint.textSize = dp2px(150f)
        paint.getTextBounds("aaa",0,"aaa".length,textBounds)
        canvas?.drawText("aaa",-textBounds.left.toFloat(),200f,paint)

        paint.textSize = dp2px(15f)
        canvas?.drawText("aaa",0f,200 + paint.fontSpacing,paint)

    }

}