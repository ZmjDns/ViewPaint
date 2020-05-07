package com.zmj.viewpaint.lesson7

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.zmj.viewpaint.common.dp2px
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

    private val PADDIND = dp2px(50f)

    private val bitmap = getAvatarBit(resources,400f)

    init {
        camera.rotateX(30f)
        camera.setLocation(0f,0f, getZForCamera())
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //canvas?.clipRect(100f,100f,100 + bitmap.width.toFloat(),(100 + bitmap.height)/2f)
        canvas?.save()
        canvas?.translate((PADDIND + bitmap.width)/2f,(PADDIND + bitmap.height)/2f)
        canvas?.rotate(-20f)
        canvas?.clipRect(-(PADDIND + bitmap.width),-(PADDIND + bitmap.height),(PADDIND + bitmap.width),0f)
        canvas?.rotate(20f)
        canvas?.translate(-(PADDIND + bitmap.width)/2f,-(PADDIND + bitmap.height)/2f)
        canvas?.drawBitmap(bitmap,PADDIND,PADDIND,paint)
        canvas?.restore()

        //下班部分
        canvas?.save()
        canvas?.translate((PADDIND + bitmap.width)/2f,(PADDIND + bitmap.height)/2f)
        canvas?.rotate(-20f)
        camera.applyToCanvas(canvas)
        canvas?.clipRect(-(PADDIND + bitmap.width),0f,(PADDIND + bitmap.width),(PADDIND + bitmap.height))
        canvas?.rotate(20f)
        canvas?.translate(-(PADDIND + bitmap.width)/2f,-(PADDIND + bitmap.height)/2f)
        canvas?.drawBitmap(bitmap,PADDIND,PADDIND,paint)
        canvas?.restore()
    }

}