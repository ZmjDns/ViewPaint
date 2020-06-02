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
import android.widget.OverScroller
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
    GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {

    private val TAG = this.javaClass.name
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatarBit(resources, dp2px(200f))

    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    private var offSetX = 0f
    private var offSetY = 0f

    private var smallScale: Float = 0f
    private var bigScale: Float = 0f
    private val OVER_SCALE = 1.5f

    private val detector = GestureDetectorCompat(context,this)

    private var fraction: Float = 0f
        get() = field
        set(value) {
            field = value
            invalidate()
        }

    private val animator by lazy {ObjectAnimator.ofFloat(this,"fraction",0f,1f)}

    /*init {
        detector.setOnDoubleTapListener(this)
    }*/

    private var big = false

    private val scroller by lazy { OverScroller(context) }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        originalOffsetX = (width.toFloat() - bitmap.width) / 2
        originalOffsetY = (height.toFloat() - bitmap.height) / 2

        //smallScale = width.toFloat()/bitmap.width
        //bigScale = height.toFloat()/bitmap.height * OVER_SCALE

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
        /*if(!big){     //缩小的时候把偏移也缩小
            offSetX = 0f
            offSetY = 0f
        }*/
        canvas.translate(offSetX * fraction,offSetY * fraction)

        val scale= smallScale + (bigScale - smallScale) * fraction
        //canvas.save()
        canvas.scale(scale,scale,width/2f,height/2f)//
        canvas.drawBitmap(bitmap,originalOffsetX,originalOffsetY,paint)
        //canvas.restore()
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }
    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(
        down: MotionEvent?,
        event: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        if(big){
            offSetX -= distanceX
            offSetX = Math.min(offSetX,(bitmap.width*bigScale-width)/2)
            offSetX = Math.max(offSetX,-(bitmap.width*bigScale-width)/2)
            offSetY -= distanceY
            offSetY = Math.min(offSetY,(bitmap.height*bigScale -height)/2)
            offSetY = Math.max(offSetY,-(bitmap.height*bigScale -height)/2)
            invalidate()
        }
        return false
    }

    override fun onFling(
        down: MotionEvent?,
        event: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if(big){
            scroller.fling(offSetX.toInt(),offSetY.toInt(),velocityX.toInt(),velocityY.toInt(),
                -((bitmap.width*bigScale-width)/2).toInt(),((bitmap.width*bigScale-width)/2).toInt(),
                -((bitmap.height*bigScale-height)/2).toInt(),((bitmap.height*bigScale-height)/2).toInt())

            postOnAnimation(this)
        }
        return false
    }

    override fun run() {
        if (scroller.computeScrollOffset()){
            offSetX = scroller.currX.toFloat()
            offSetY = scroller.currY.toFloat()
            invalidate()

            postOnAnimation(this)
        }
    }


    override fun onLongPress(e: MotionEvent?) {

    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        Log.i(TAG,"双击了.....")
        big = !big
        if(big){
            animator.start()
        }else{
            animator.reverse()
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