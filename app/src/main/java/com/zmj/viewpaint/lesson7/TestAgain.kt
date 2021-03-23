package com.zmj.viewpaint.lesson7

import android.content.Context
import android.graphics.*
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextDirectionHeuristic
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.zmj.viewpaint.common.dp2px
import com.zmj.viewpaint.common.getAvatarBit
import com.zmj.viewpaint.common.getZForCamera

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2021-03-22
 * Description :
 */
class TestCircle(context: Context,attributeSet: AttributeSet): View(context,attributeSet) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val radius = dp2px(100f)
    private var textContent = "abjgioQ"
    private var textBounds = Rect()
    init {
        paint.color = Color.GRAY
        paint.strokeWidth = dp2px(10f)
        paint.strokeCap = Paint.Cap.ROUND
        paint.style = Paint.Style.STROKE

        //设置字体大小
        paint.textSize = dp2px(30f)
//        paint.typeface = Typeface.createFromFile("xxxxx.ttf") //设置自己的字体
        paint.textAlign = Paint.Align.CENTER
        paint.getTextBounds(textContent,0,textContent.length,textBounds)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas!!)
        //  背景圆
        canvas.drawCircle(width/2f,height/2f,radius,paint)
        //  显示圆
        paint.color = Color.RED
        canvas.drawArc(width/2-radius, height/2-radius,width/2+radius,height/2+radius,0f,240f,false,paint)

        //绘制文字
        paint.style = Paint.Style.FILL
        canvas.drawText(textContent,width/2f,height/2f + textBounds.height()/2,paint)

    }
}

@RequiresApi(Build.VERSION_CODES.M)
class TestStaticLayout(context: Context, attributeSet: AttributeSet?) : View(context,attributeSet) {

    private val textPaint = TextPaint()
    private val textContent = "女次卧而合肥芜湖覅我二话覅我和覅欧委会农村十五年是哦胶带机哇哦Jedi维护费从i撒娇刺激wise偶就违法"
    private var staticLayout: StaticLayout? = null

    // 插图绘字
    private val bitmap: Bitmap = getAvatarBit(resources, dp2px(100f))
    private val paint: Paint
    private val textBounds = Rect()
    private var cutWith = FloatArray(1)


    init {
        textPaint.textSize = dp2px(40f)
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = dp2px(15f)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //StaticLayout绘制text的两种方法，注意实力StaticLayout的时候不能放在init{} 或静态代码块中
//        staticLayout = StaticLayout(textContent,textPaint,width, Layout.Alignment.ALIGN_NORMAL,1f,0f,false)

//        staticLayout = StaticLayout.Builder
//            .obtain(textContent,0,textContent.length,textPaint,width)
//            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
//            .setLineSpacing(0f,1f)
//            .setIncludePad(false)
//            .build()

        paint.getTextBounds(textContent,0, textContent.length,textBounds)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas!!)
//        staticLayout?.draw(canvas)

        // 插图绘字
        canvas.drawBitmap(bitmap,width - bitmap.width.toFloat(), 100f,paint)
        var index = paint.breakText(textContent,true,width.toFloat(),cutWith)
        canvas.drawText(textContent,0,index,0f,50f,paint)
        var oldIndex = index
        index = paint.breakText(textContent,oldIndex,textContent.length,true,width.toFloat(),cutWith)
        canvas.drawText(textContent,oldIndex,oldIndex + index,0f,50f + paint.fontSpacing,paint)
    }

}
class TestTrans(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatarBit(resources, dp2px(150f))

    private val camera = Camera()

    private val MARGIN = dp2px(20f)

    init {
        camera.rotateX(30f)
        //  移动相机位置
        camera.setLocation(0f,0f, getZForCamera())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas!!)

        //  从下往上看比较符合执行结果
//        canvas.translate(100f,100f)
//        canvas.rotate(45f,bitmap.width/2f,bitmap.height/2f)
//        canvas.drawBitmap(bitmap, 0f,0f,paint)


        //  只有camera旋转，会出现糊脸效果
//        camera.applyToCanvas(canvas)
//        canvas.drawBitmap(bitmap,0f,0f,paint)

        //  将camera旋转与canvas平移结合起来解决糊脸效果
        //  需要将代码的执行流程倒过来看
//        canvas.translate(bitmap.width/2f,bitmap.height/2f)      //4.移回来
//        camera.applyToCanvas(canvas)                                    //3.加上camera旋转效果
//        canvas.translate(-bitmap.width/2f,-bitmap.height/2f)    //2.将图片移动到（0，0）
//        canvas.drawBitmap(bitmap,0f,0f,paint)                 //1.绘图


        //  图片切割旋转
        //  1.先画上半部分
//        canvas.save()
//        canvas.clipRect(MARGIN,MARGIN,(bitmap.width + MARGIN),bitmap.height/2 + MARGIN)
//        canvas.drawBitmap(bitmap,MARGIN,MARGIN,paint)
//        canvas.restore()
//
////        //  2.再画下半部分，需要旋转
//        canvas.save()
//        canvas.translate((bitmap.width/2f+ MARGIN),(bitmap.height/2f + MARGIN))
//        camera.applyToCanvas(canvas)
//        canvas.translate(-(bitmap.width/2f+ MARGIN),-(bitmap.height/2f + MARGIN))
//        canvas.clipRect(MARGIN,MARGIN + bitmap.height/2f,bitmap.width + MARGIN,bitmap.height + MARGIN)
//        canvas.drawBitmap(bitmap,MARGIN,MARGIN,paint)
//        canvas.restore()


        //  1.先画上半部分
        canvas.save()
        canvas.translate(MARGIN + bitmap.width/2,MARGIN + bitmap.height/2)
        canvas.rotate(-20f)
        canvas.clipRect(-bitmap.width.toFloat(),-bitmap.height.toFloat(),bitmap.width.toFloat(),0f)
        canvas.rotate(20f)
        canvas.translate(-(MARGIN + bitmap.width/2),-(MARGIN + bitmap.height/2))
        canvas.drawBitmap(bitmap,MARGIN,MARGIN,paint)
        canvas.restore()

//        //  2.再画下半部分，需要旋转
        canvas.save()
        canvas.translate(MARGIN + bitmap.width/2,MARGIN + bitmap.height/2)
        canvas.rotate(-20f)
        camera.applyToCanvas(canvas)
        canvas.clipRect(-bitmap.width.toFloat(),0f,bitmap.width.toFloat(),bitmap.height.toFloat())
        canvas.rotate(20f)
        canvas.translate(-(MARGIN + bitmap.width/2),-(MARGIN + bitmap.height/2))
        canvas.drawBitmap(bitmap,MARGIN,MARGIN,paint)
        canvas.restore()
    }
}