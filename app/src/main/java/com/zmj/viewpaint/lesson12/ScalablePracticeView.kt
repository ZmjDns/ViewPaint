package com.zmj.viewpaint.lesson12

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import com.zmj.viewpaint.common.dp2px
import com.zmj.viewpaint.common.getAvatarBit
import kotlin.properties.Delegates

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/25
 * Description :
 */
class ScalablePracticeView(context: Context?, attrs: AttributeSet?) : View(context, attrs),
    GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private val TAG = this.javaClass.name
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatarBit(resources, dp2px(200f))

    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    private var smallScale: Float = 0f
    private var bigScale: Float = 0f
    private val OVER_SCALE = 1.5f

    private val detector = GestureDetectorCompat(context,this)

    private var fraction: Float = 0f
        get() = field
        set(value) {
            field = value
        }

    private val animator = ObjectAnimator.ofFloat(this,"fraction",0f,1f)


    /*init {
        detector.setOnDoubleTapListener(this)
    }*/

    private var big = false

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        originalOffsetX = (width.toFloat() - bitmap.width) / 2
        originalOffsetY = (height.toFloat() - bitmap.height) / 2

        if (bitmap.width.toFloat() / bitmap.height > width.toFloat()/height){//小的状态
            smallScale = width.toFloat()/bitmap.width
            bigScale = height.toFloat()/bitmap.height * OVER_SCALE
        }else{                                                              //大的状态
            smallScale = height.toFloat()/bitmap.height
            bigScale = width.toFloat()/bitmap.width * OVER_SCALE
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return detector.onTouchEvent(event)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas!!)


        val scale= smallScale + (bigScale - smallScale) * fraction
        canvas.save()
        canvas.scale(scale,scale,width/2f,height/2f)//
        canvas.drawBitmap(bitmap,originalOffsetX,originalOffsetY,paint)
        canvas.restore()
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }
    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {

    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        Log.i(TAG,"双击了.....")
        big = !big
        if(big){
            animator.reverse()
        }else{
            animator.start()
        }
        //invalidate()
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return false
    }
}