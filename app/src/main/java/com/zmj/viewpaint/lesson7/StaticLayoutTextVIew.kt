package com.zmj.viewpaint.lesson7

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.zmj.viewpaint.common.dp2px

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

    private lateinit var staticLayout: StaticLayout

    init {
        textPaint.textSize = dp2px(12f)

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        staticLayout = StaticLayout("nciwebifbewuf不i额我不i房屋被毁v被认为v变为夫i0",
            textPaint,width,Layout.Alignment.ALIGN_NORMAL,1f,0f,false)
        staticLayout.draw(canvas)
    }



}