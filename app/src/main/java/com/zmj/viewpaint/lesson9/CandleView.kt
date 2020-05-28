package com.zmj.viewpaint.lesson9

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/28
 * Description :
 */
class CandleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val candleDrawable = CandleDrawable()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas!!)

        candleDrawable.draw(canvas)
    }

}