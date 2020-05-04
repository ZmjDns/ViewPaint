package com.zmj.viewpaint.lesson7

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.zmj.viewpaint.common.getAvatarBit

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/3
 * Description : Canvas坐标系，
 * 在View的绘制过程中除了view的坐标系，还有有一个canvas的坐标系，
 * view绘制过程中会先将图形绘制在canvas上，然后再在view中展示，
 * 这个过程在一般的绘制中显示不出来，
 * 但在绘制过程中做图形变换的时候，尤其是两次以上的图形变换就显现出来了
 * 网上都说顺序要倒过来，但不知道为什么，这时引入canvas坐标系就可以说明了
 */
class CanvasCoordinate(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val bitmap = getAvatarBit(resources,200f)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.translate(100f,100f)
        canvas?.rotate(-45f,bitmap.width/2f,bitmap.height/2f)
        canvas?.drawBitmap(bitmap,0f,0f,paint)


    }
}