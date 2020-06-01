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
import android.widget.OverScroller
import android.widget.Scroller
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
class ScalableImageView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val BIT_WIDTH = dp2px(200f)
    private val OVER_SCALE_FACTOR = 1.5f        //放大系数
    private var bitmap: Bitmap = getAvatarBit(resources,BIT_WIDTH)
    private var originalOffSetX:Float = 0f
    private var originalOffSetY: Float = 0f
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

    private var offsetX = 0f
    private var offsetY = 0f
    private val scroller = OverScroller(context)

    private val henGestureListener = HenGestureListener()

    private var decetor: GestureDetectorCompat = GestureDetectorCompat(context,henGestureListener)
    /*private val decteor2 = GestureDetectorCompat(context,object :GestureDetector.SimpleOnGestureListener(){
        override fun onDoubleTap(e: MotionEvent?): Boolean {
            return super.onDoubleTap(e)
        }
    })*/

    private val henFlingRunner by lazy { HenFlingRunner() }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        originalOffSetX = (width.toFloat() - bitmap.width) / 2
        originalOffSetY = (height.toFloat() - bitmap.height) / 2

        //取决于图片的宽高比
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
        //canvas执行顺序是倒序的
        super.onDraw(canvas!!)

        //缩放之后的第二次偏移
        canvas.translate(offsetX * scaleFraction,offsetY * scaleFraction)

        //val scale = if (big) bigScale else smallScale
        val scale = smallScale + (bigScale - smallScale) * scaleFraction
        canvas.scale(scale,scale,width/2f,height/2f)
        canvas.drawBitmap(bitmap,originalOffSetX,originalOffSetY,paint)
    }

    inner class HenGestureListener: GestureDetector.SimpleOnGestureListener() {//GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener
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
            down: MotionEvent?,     //按下事件
            event: MotionEvent?,    //当前事件
            distanceX: Float,       //当前点距离上一个事件X方向上的偏移  注意   旧位置 - 新位置
            distanceY: Float       //当前点距离上一个事件Y方向上的偏移   注意   旧位置 - 新位置
        ): Boolean {
            if (big){
                offsetX -= distanceX        //注意  distanceX = 旧位置X - 新位置X           （画图计算）
                offsetX = Math.min(offsetX,(bitmap.width * bigScale - width)/2)
                offsetX = Math.max(offsetX,-(bitmap.width * bigScale - width)/2)
                offsetY -= distanceY
                offsetY = Math.min(offsetY,(bitmap.height * bigScale - height)/2)
                offsetY = Math.max(offsetY,-(bitmap.height * bigScale - height)/2)
                invalidate()
            }
            return false
        }

        override fun onLongPress(e: MotionEvent?) {

        }
        override fun onFling(
            down: MotionEvent?,
            event: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (big){
                scroller.fling(offsetX.toInt(),offsetY.toInt(),velocityX.toInt(),velocityY.toInt(),
                    -((bitmap.width * bigScale - width)/2).toInt(),
                    ((bitmap.width * bigScale - width)/2).toInt(),
                    -((bitmap.height * bigScale - height)/2).toInt(),
                    ((bitmap.height * bigScale - height)/2).toInt()
                    /*,100,100*/)//最后两个参数，过度滚动的位置
                //下一帧执行run方法中的代码
                postOnAnimation(henFlingRunner)
            }
            return false
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            big = !big
            if (big){
                offsetX = (e!!.x - width/2) - (e.x - width/2) * scaleFraction
                offsetY = (e.y - height/2) - (e.y - height/2) * scaleFraction
                scaleAnimator.start()
            }else{
                scaleAnimator.reverse()
            }
           return false
        }

        override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
            return false
        }

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            return false
        }
    }

    //Fling刷新界面
    inner class HenFlingRunner: Runnable{
        override fun run() {
            if (scroller.computeScrollOffset()){    //测量动画是否完成
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()    //此时只会刷新一次，要将动画执行完可能会需要执行跟多次刷新
                //一次在动画没有执行完毕的时候需要再次执行 postOnAnimation(this)
                postOnAnimation(this)
            }
        }
    }
}