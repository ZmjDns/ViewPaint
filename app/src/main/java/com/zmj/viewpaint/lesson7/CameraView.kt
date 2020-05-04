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
 * Description :
 */
class CameraView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatarBit(resources,400f)

    private val camera = Camera()

    init {
        camera.rotateX(45f)

        //解决不同手机显示图片效果不同的问题
        camera.setLocation(0f,0f,/*-6 * resources.displayMetrics.density*/getZForCamera())   //-8 = -8 * 72(像素)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //顺序倒置
        //第四步
        canvas?.translate((100 + bitmap.width/2f),(100 + bitmap.height/2f))
        //第三步
        camera.applyToCanvas(canvas!!)
        //第二步
        canvas.translate(-(100 + bitmap.width/2f),-(100 + bitmap.height/2f))
        //第一步
        canvas.drawBitmap(bitmap,100f,100f,paint)

        //第七步
        //canvas.xxx()
        //第六步
        //canvas.xxx()
        //第五步
        //canvas.drawBitmap(bitmap,100f,100f,paint)


    }
}