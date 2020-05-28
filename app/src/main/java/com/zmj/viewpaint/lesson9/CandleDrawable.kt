package com.zmj.viewpaint.lesson9

import android.graphics.*
import android.graphics.drawable.Drawable
import com.zmj.viewpaint.common.dp2px

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/28
 * Description :
 */
class CandleDrawable: Drawable() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val lineDistance = 0f

    init {
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
    }


    override fun draw(canvas: Canvas) {
        canvas.save()
        paint.strokeWidth = dp2px(1f)
        canvas.drawLine(20f,0f,20f,20f,paint)
        canvas.restore()
        canvas.save()
        paint.strokeWidth = dp2px(10f)
        canvas.drawRect(10f,20f,30f,60f,paint)
        canvas.restore()
        canvas.save()
        paint.strokeWidth = dp2px(1f)
        canvas.drawLine(20f,60f,20f,80f,paint)
        canvas.restore()
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getOpacity(): Int {
        if (paint.alpha == 0){
            return PixelFormat.TRANSLUCENT
        }else{
            if (paint.alpha == 0xff){
                return PixelFormat.OPAQUE
            }else{
                return PixelFormat.TRANSLUCENT
            }
        }
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }
}