package com.zmj.viewpaint.lesson9

import android.graphics.*
import android.graphics.drawable.Drawable
import com.zmj.viewpaint.common.dp2px

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/13
 * Description :
 */
class MeshDrawable: Drawable() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val INTERVAL = dp2px(10f).toInt()

    init {
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        paint.strokeWidth = dp2px(1f)
    }

    override fun draw(canvas: Canvas) {
        //绘制算法
        for (i in bounds.left..bounds.right step INTERVAL){
            for (j in bounds.top..bounds.bottom step INTERVAL){
                canvas.drawLine(bounds.left.toFloat(),j.toFloat(),bounds.right.toFloat(),j.toFloat(),paint)
                canvas.drawLine(i.toFloat(),bounds.top.toFloat(),i.toFloat(),bounds.bottom.toFloat(),paint)
            }
        }

    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getAlpha(): Int {
        return paint.alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        if (paint.alpha == 0){
            return PixelFormat.TRANSPARENT
        }else{
            if (paint.alpha == 0xff){
                return PixelFormat.OPAQUE
            }else{
                return PixelFormat.TRANSLUCENT
            }
        }
    }
}