package com.zmj.viewpaint.lesson7

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.zmj.viewpaint.common.getAvatarBit
import com.zmj.viewpaint.common.getZForCamera

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/3
 * Description :翻页效果View
 */
class FlipPageView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val camera = Camera()

    private val bitmap = getAvatarBit(resources,400f)

    init {
        camera.rotateX(45f)
        camera.setLocation(0f,0f, getZForCamera())
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //canvas?.clipRect(100f,100f,100 + bitmap.width.toFloat(),(100 + bitmap.height)/2f)
        canvas?.save()
        canvas?.translate((100 + bitmap.width)/2f,(100 + bitmap.height)/2f)
        canvas?.rotate(-20f)
        canvas?.clipRect(-(100f + bitmap.width),-(100f + bitmap.height),(100f + bitmap.width),0f)
        canvas?.rotate(20f)
        canvas?.translate(-(100 + bitmap.width)/2f,-(100 + bitmap.height)/2f)
        canvas?.drawBitmap(bitmap,100f,100f,paint)
        canvas?.restore()

        //下班部分
        canvas?.save()
        canvas?.translate((100 + bitmap.width)/2f,(100 + bitmap.height)/2f)
        canvas?.rotate(-20f)
        camera.applyToCanvas(canvas)
        canvas?.clipRect(-(100f + bitmap.width),0f,(100f + bitmap.width),(100f + bitmap.height))
        canvas?.rotate(20f)
        canvas?.translate(-(100 + bitmap.width)/2f,-(100 + bitmap.height)/2f)
        canvas?.drawBitmap(bitmap,100f,100f,paint)
        canvas?.restore()
    }

}