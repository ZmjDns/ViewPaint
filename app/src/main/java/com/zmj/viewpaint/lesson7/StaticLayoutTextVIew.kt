package com.zmj.viewpaint.lesson7

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.zmj.viewpaint.common.dp2px
import com.zmj.viewpaint.common.getAvatarBit

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/1
 * Description :
 */
class StaticLayoutTextVIew(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

    private val bitmap : Bitmap

    private val text = "nciwebifbewuf不i额我不i房屋被毁v被认为v变为夫i0nciwebifbewuf不i额我不i房屋被毁v被认为v变为夫i0nciwebifbewuf不i额我不i房屋被毁v被认为v变为夫i0"

    private val cutWidth = FloatArray(1)
    private lateinit var staticLayout: StaticLayout

    init {
        textPaint.textSize = dp2px(20f)
        paint.textSize = dp2px(20f)
        bitmap = getAvatarBit(resources,dp2px(100f))

        /*staticLayout = StaticLayout.Builder()
            .setText("nciwebifbewuf不i额我不i房屋被毁v被认为v变为夫i0")
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .build()*/

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        /*staticLayout = StaticLayout("nciwebifbewuf不i额我不i房屋被毁v被认为v变为夫i0nciwebifbewuf不i额我不i房屋被毁v被认为v变为夫i0nciwebifbewuf不i额我不i房屋被毁v被认为v变为夫i0",
            textPaint,width,Layout.Alignment.ALIGN_NORMAL,1f,0f,false)
        staticLayout.draw(canvas)*/

        canvas?.drawBitmap(bitmap,(width - bitmap.width).toFloat(), 100f,paint)

        var index = paint.breakText(text,true,width.toFloat(),cutWidth)
        canvas?.drawText(text,0,index,0f,50f,paint)
        val oldIndex = index
        index = paint.breakText(text,index,text.length,true,width.toFloat() - bitmap.width,cutWidth)
        canvas?.drawText(text,oldIndex,oldIndex + index,0f,50 + paint.fontSpacing,paint)

    }
}