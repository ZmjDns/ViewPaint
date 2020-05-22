package com.zmj.viewpaint.lesson12

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import com.zmj.viewpaint.common.dp2px
import com.zmj.viewpaint.common.getAvatarBit

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/22
 * Description :可缩放ImageView
 */
class ScalableImageView(context: Context?, attrs: AttributeSet?) : View(context, attrs),
    GestureDetector.OnGestureListener {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val BIT_WIDTH = dp2px(200f)
    private var offSetX:Float = 0f
    private var offSetY: Float = 0f
    private var smallScale: Float = 0f
    private var bigScale: Float = 0f
    private var bitmap: Bitmap = getAvatarBit(resources,BIT_WIDTH)

    private var decetor: GestureDetectorCompat = GestureDetectorCompat(context,this)


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        offSetX = (width.toFloat() - bitmap.width) / 2
        offSetY = (height.toFloat() - bitmap.height) / 2

        if ((bitmap.width/bitmap.height).toFloat() > (width/height).toFloat()){
            smallScale = width.toFloat()/bitmap.width
            bigScale = height.toFloat()/bitmap.height
        }else{
            smallScale = height.toFloat()/bitmap.height
            bigScale = width.toFloat()/bitmap.width
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return decetor.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val scale = bigScale
        canvas!!.scale(scale,scale,width/2f,height/2f)
        canvas.drawBitmap(bitmap,offSetX,offSetY,paint)
    }

    override fun onShowPress(e: MotionEvent?) {
        TODO("Not yet implemented")
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onDown(e: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onLongPress(e: MotionEvent?) {
        TODO("Not yet implemented")
    }

}