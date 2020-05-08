package com.zmj.viewpaint.lesson8

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.zmj.viewpaint.common.dp2px
import com.zmj.viewpaint.common.getAvatarBit
import com.zmj.viewpaint.common.getZForCamera
import javax.crypto.BadPaddingException

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/8
 * Description :
 */
class FlipPageAnimateView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatarBit(resources,400f)
    private val camera = Camera()
    private val PADDING = dp2px(80f)

    private var topFlip:Float = 0f
    private var bottomFlip:Float = 0f
    private var rotationCanvas: Float = 0f

    init {
        camera.setLocation(0f,0f, getZForCamera())
    }

    public fun getTopFlip(): Float{
        return topFlip
    }
    public fun setTopFlip(topFlip: Float){
        this.topFlip = topFlip
        invalidate()
    }
    public fun getBottomFlip(): Float{
        return bottomFlip
    }
    public fun setBottomFlip(bottomFlip: Float){
        this.bottomFlip = bottomFlip
        invalidate()
    }

    public fun getRotationCanvas(): Float{
        return rotationCanvas
    }

    public fun setRotationCanvas(rotationCanvas: Float){
        this.rotationCanvas = rotationCanvas
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.save()
        canvas.translate(PADDING + bitmap.width/2,PADDING + bitmap.height/2)
        canvas.rotate(-rotationCanvas)

        camera.save()
        camera.rotateX(topFlip)
        camera.applyToCanvas(canvas)
        camera.restore()

        canvas.clipRect(-bitmap.width,-bitmap.height,bitmap.width,0)
        canvas.rotate(rotationCanvas)
        canvas.translate(-(PADDING + bitmap.width/2),-(PADDING + bitmap.height/2))
        canvas.drawBitmap(bitmap,PADDING,PADDING,paint)
        canvas.restore()

        canvas.save()
        canvas.translate(PADDING + bitmap.width/2,PADDING + bitmap.height/2)
        canvas.rotate(-rotationCanvas)

        camera.save()
        camera.rotateX(bottomFlip)//在旋转x轴
        camera.applyToCanvas(canvas)//先应用到canvas
        camera.restore()

        canvas.clipRect(-bitmap.width,0,bitmap.width,bitmap.height)
        canvas.rotate(rotationCanvas)
        canvas.translate(-(PADDING + bitmap.width/2),-(PADDING + bitmap.height/2))
        canvas.drawBitmap(bitmap,PADDING,PADDING,paint)
        canvas.restore()
    }
}