package com.zmj.viewpaint.lesson6

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.zmj.viewpaint.R
import com.zmj.viewpaint.common.dp2px
import com.zmj.viewpaint.common.getAvatarBit
import kotlinx.android.synthetic.main.drag_to_collect_layout.view.*
import kotlin.math.cos
import kotlin.math.sin

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2021-03-22
 * Description :
 */
class TestView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val angle = 120f
    private val radius = dp2px(150f)

    //  刻度线
    private val dash = Path()
    private val effect: PathDashPathEffect


    init {
        paint.strokeWidth = dp2px(2f)
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
//        paint.strokeCap = Paint.Cap.ROUND //  头尾部分会出现圆形的头

        dash.addRect(0f,0f, dp2px(2f), dp2px(10f),Path.Direction.CW)    //  间隔线
        //  测量需要画多长
        val arc = Path()
        arc.addArc(width/2 - radius,height/2 - radius,width/2 + radius,height/2 + radius,90 + angle/2, 360-angle)
        val pathMeasure = PathMeasure(arc,false)
        //  绘画效果加上 间隔线
        effect = PathDashPathEffect(dash,(pathMeasure.length - dp2px(2f)) / 20,0f,PathDashPathEffect.Style.ROTATE)
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas!!)

        //  画线
        canvas.drawArc(width/2 - radius,height/2 - radius,width/2 + radius,height/2 + radius,90 + angle/2, 360-angle,false,paint)

        //  画dash
        paint.pathEffect = effect
        canvas.drawArc(width/2 - radius,height/2 - radius,width/2 + radius,height/2 + radius,90 + angle/2, 360-angle,false,paint)
        paint.pathEffect = null

        //  画针
        val marker = getAngle(1)
        canvas.drawLine(width/2f,height/2f, cos(Math.toRadians(marker.toDouble()).toFloat()) * dp2px(100f) + width/2, sin(Math.toRadians(marker.toDouble()).toFloat()) * dp2px(100f) + height/2,paint)

    }

    private fun getAngle(marker: Int):Float {
        return angle/2 + 90 + (360f-angle) / 20 * marker
    }
}

class TestPie (context: Context, attrs: AttributeSet?): View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val radius = dp2px(150f)
    private val offset = dp2px(20f)
    private var bounds: RectF = RectF()
    private val angles = arrayOf(60f,100f,120f,80f)
    private val colors = arrayOf("#294523","#A12399","#AB6699","#F65689")

    init {
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bounds.set(width/2 - radius, height/2 - radius, width/2 + radius, height/2 + radius)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas!!)

        var currentAngle = 0f
        for (index in angles.indices) {
            paint.color = Color.parseColor(colors[index])
            if (index == 2) {
                canvas.save()   //  保存之前的状态
//                canvas.translate(-20f,-20f)
                canvas.translate(cos(Math.toRadians((currentAngle + angles[index] / 2f).toDouble()).toFloat()) * offset,
                    sin(cos(Math.toRadians((currentAngle + angles[index] / 2f).toDouble()).toFloat())) * offset)
                canvas.drawArc(bounds, currentAngle, angles[index],true,paint)
                canvas.restore()//  恢复保存之前的状态，上一步做的变换不会影响后面操作
            } else {
                canvas.drawArc(bounds, currentAngle, angles[index],true,paint)
            }
            currentAngle += angles[index]
        }
    }
}

class TestAvatar(context: Context,attrs: AttributeSet?): View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val WIDTH = dp2px(300f)
    private val PADDING = dp2px(50f)
    private val EDG_WIDTH = dp2px(10f)
    private val xFermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    private val bitmap: Bitmap

    init {
        bitmap = getAvatar(WIDTH)//getAvatarBit(resources,WIDTH)//
    }

    private val savedArea = RectF()
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        savedArea.set(PADDING,PADDING,PADDING + WIDTH, PADDING + WIDTH)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas!!)

        canvas.drawOval(PADDING,PADDING,PADDING + WIDTH, PADDING + WIDTH, paint)
        val saved = canvas.saveLayer(savedArea,paint)
        canvas.drawOval(PADDING + EDG_WIDTH,PADDING + EDG_WIDTH,PADDING + WIDTH - EDG_WIDTH, PADDING + WIDTH - EDG_WIDTH, paint)

        paint.xfermode = xFermode
        canvas.drawBitmap(bitmap, PADDING, PADDING,paint)
        paint.xfermode = null
        canvas.restoreToCount(saved)
    }


    private fun getAvatar(width: Float): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true   //  设置只取图片的大小范围，而不加载图片
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian,options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width.toInt()
        return  BitmapFactory.decodeResource(resources,R.drawable.avatar_rengwuxian,options)

    }
}