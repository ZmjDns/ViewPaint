package com.zmj.viewpaint.lesson12

import android.animation.ObjectAnimator
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
class ScalableImageView(context: Context?, attrs: AttributeSet?) : View(context, attrs),GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val BIT_WIDTH = dp2px(200f)
    private val OVER_SCALE_FACTOR = 1.5f        //放大系数
    private var bitmap: Bitmap = getAvatarBit(resources,BIT_WIDTH)
    private var offSetX:Float = 0f
    private var offSetY: Float = 0f
    private var smallScale: Float = 0f
    private var bigScale: Float = 0f
    private var big: Boolean = false

    private var scaleFraction: Float = 0f    //0~1
        get() = field
        set(value) {
            field = value
            invalidate()
        }
    private val scaleAnimator by lazy { ObjectAnimator.ofFloat(this,"scaleFraction",0f,1f) }

    private var decetor: GestureDetectorCompat = GestureDetectorCompat(context,this)
    /*private val decteor2 = GestureDetectorCompat(context,object :GestureDetector.SimpleOnGestureListener(){
        override fun onDoubleTap(e: MotionEvent?): Boolean {
            return super.onDoubleTap(e)
        }
    })*/


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        offSetX = (width.toFloat() - bitmap.width) / 2
        offSetY = (height.toFloat() - bitmap.height) / 2

        if ((bitmap.width/bitmap.height).toFloat() > (width/height).toFloat()){
            smallScale = width.toFloat()/bitmap.width
            bigScale = height.toFloat()/bitmap.height * OVER_SCALE_FACTOR
        }else{
            smallScale = height.toFloat()/bitmap.height
            bigScale = width.toFloat()/bitmap.width * OVER_SCALE_FACTOR
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return decetor.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //val scale = if (big) bigScale else smallScale
        val scale = smallScale + (bigScale - smallScale) * scaleFraction
        canvas!!.scale(scale,scale,width/2f,height/2f)
        canvas.drawBitmap(bitmap,offSetX,offSetY,paint)
    }

    //所有事件的执行的入口，
    // 只有onDown返回true的时候才证明该view消费了此事件，后面的方法才会被调用
    //后面方法返回值没有用
    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }
    //对事件执行延迟100ms之后进行执行
    override fun onShowPress(e: MotionEvent?) {
    }

    /**
     * //点击之后抬起
     *注意，设置之后它会在每一次抬起之后调用
     *当设置双击之后再设置单击，就不能在此方法中执行单击逻辑，此时单击设置 {@link ScalableImageView#onSingleTapConfirmed(e: MotionEvent?)}
     */
    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return true//false
    }

    //onMove
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
    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    //确认单击而不是双击
    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return false
    }
    //300ms内第二次点击  但是两次间隔低于40ms会被排除（被认为收抖）
    override fun onDoubleTap(e: MotionEvent?): Boolean {
        big = !big
        if (big){
            scaleAnimator.start()
        }else{
            scaleAnimator.reverse()
        }
        return false
    }

    //第二次点击ACTION_DOWN之后的移动抬起都会被侦测到
    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return false
    }
}